package de.mss.utils.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.mss.utils.StopWatch;
import de.mss.utils.Tools;
import de.mss.utils.exception.ErrorCodes;

public class DBSingleConnection {

   protected DBServer   server       = null;
   protected Connection dbConnection = null;
   protected boolean    busy         = false;
   protected long       usedCount    = 0;


   protected static String        loggerName = null;
   private static volatile Logger logger     = null;


   protected static Logger getLogger() {
      if (logger != null)
         return logger;
      if (!Tools.isSet(DBSingleConnection.loggerName))
         DBSingleConnection.loggerName = "default";

      logger = LogManager.getLogger(DBSingleConnection.loggerName);
      return logger;
   }


   public DBSingleConnection(String l, DBServer s) {
      DBSingleConnection.loggerName = l;
      this.server = s;
   }


   public DBSingleConnection(String l, DBServer s, Connection c) {
      DBSingleConnection.loggerName = l;
      this.server = s;
      this.dbConnection = c;
   }


   public void close() throws DBException {
      try {
         if (isClosed())
            return;

         if (!this.dbConnection.isClosed())
            this.dbConnection.close();
      }
      catch (SQLException e) {
         throw new DBException(ErrorCodes.ERROR_DB_CLOSE_FAILURE, e);
      }
      finally {
         this.dbConnection = null;
         this.usedCount = 0;
      }
   }


   public void connect() throws DBException {
      this.dbConnection = DBConnectionFactory.getConnection(this.server);
   }


   public boolean isConnected() {
      return (this.dbConnection != null);
   }


   public Connection getDbConnection() {
      return this.dbConnection;
   }


   public boolean isClosed() throws DBException {
      try {
         return this.dbConnection == null || this.dbConnection.isClosed();
      }
      catch (SQLException e) {
         throw new DBException(ErrorCodes.ERROR_DB_CLOSE_FAILURE, e);
      }
   }


   public DBResult executeQuery(String loggingId, String query) throws DBException {
      connect();
      try {
         return executeQuery(loggingId, this.dbConnection.prepareStatement(query));
      }
      catch (SQLException e) {
         throw new DBException(ErrorCodes.ERROR_DB_EXECUTE_QUERY_FAILURE, e);
      }
   }


   public DBResult executeQuery(String loggingId, PreparedStatement stmt) throws DBException {
      if (stmt == null)
         throw new DBException(ErrorCodes.ERROR_INVALID_PARAM, "The Statement is null");

      DBResult result = new DBResult();

      int resultSetNumber = 1;
      this.busy = true;
      this.usedCount++ ;
      getLogger().debug("<" + loggingId + "> Executing Query " + stmt);
      StopWatch s = new StopWatch();
      try (ResultSet res = stmt.executeQuery()) {
         getLogger().debug("<" + loggingId + "> Duration for executing query [" + s.getDuration() + "ms]");
         if (res != null)
            result.addResult(getResultFromDb(resultSetNumber, res));

         result = handleMoreResults(result, resultSetNumber + 1, stmt);
         getLogger().debug("<" + loggingId + "> Result " + result);
      }
      catch (SQLException e) {
         throw new DBException(ErrorCodes.ERROR_DB_EXECUTE_QUERY_FAILURE, e, "Failure while executing query");
      }
      finally {
         this.busy = false;
      }
      return result;
   }


   public int executeUpdate(String loggingId, String query) throws DBException {
      try {
         return executeUpdate(loggingId, this.dbConnection.prepareStatement(query));
      }
      catch (SQLException e) {
         throw new DBException(ErrorCodes.ERROR_DB_EXECUTE_UPDATE_FAILURE, e);
      }
   }


   public int executeUpdate(String loggingId, PreparedStatement stmt) throws DBException {
      if (stmt == null)
         throw new DBException(ErrorCodes.ERROR_INVALID_PARAM, "The Statement is null");

      getLogger().debug("<" + loggingId + "> Executing Update " + stmt);
      int rows = 0;
      this.usedCount++ ;
      StopWatch s = new StopWatch();
      try {
         this.busy = true;
         rows = stmt.executeUpdate();
         getLogger().debug("<" + loggingId + "> Duration for executing update [" + s.getDuration() + "ms]");
         getLogger().debug("<" + loggingId + "> " + rows + " affected");
      }
      catch (SQLException e) {
         throw new DBException(ErrorCodes.ERROR_DB_EXECUTE_UPDATE_FAILURE, e, "Failure while executing update");
      }
      finally {
         this.busy = false;
      }
      return rows;
   }


   private DBResult handleMoreResults(DBResult result, int resultSetNumber, PreparedStatement stmt) throws DBException {
      try {
         if (!stmt.getMoreResults())
            return result;
      }
      catch (SQLException e) {
         throw new DBException(ErrorCodes.ERROR_DB_RESUTSET_FAILURE, e);
      }

      try (ResultSet res = stmt.getResultSet()) {
         result.addResult(getResultFromDb(resultSetNumber, res));

         return handleMoreResults(result, resultSetNumber + 1, stmt);
      }
      catch (SQLException e) {
         throw new DBException(ErrorCodes.ERROR_DB_RESUTSET_FAILURE, e);
      }
   }


   private DBSingleResult getResultFromDb(int resultSetNumber, ResultSet res) throws SQLException {
      if (res == null)
         return null;

      ResultSetMetaData meta = res.getMetaData();

      DBSingleResult result = new DBSingleResult(resultSetNumber, meta.getColumnCount());
      for (int i = 0; i < meta.getColumnCount(); i++ )
         result.setColumnName(meta.getColumnName(i), i);

      while (res.next()) {
         int row = result.addRow() - 1;
         for (int i = 0; i < result.getColumnCount(); i++ ) {
            result.setValue(i, row, res.getString(i + 1));
         }
      }

      return result;
   }


   public String getLoggerName() {
      return DBSingleConnection.loggerName;
   }


   public boolean isBusy() {
      return this.busy;
   }


   public long getUsedCount() {
      return this.usedCount;
   }
}

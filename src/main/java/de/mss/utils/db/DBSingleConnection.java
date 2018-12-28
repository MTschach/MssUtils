package de.mss.utils.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import de.mss.logging.BaseLogger;
import de.mss.logging.LoggingFactory;
import de.mss.utils.StopWatch;
import de.mss.utils.exception.ErrorCodes;

public class DBSingleConnection {

   protected DBServer   server       = null;
   protected Connection dbConnection = null;
   protected String     loggerName   = null;
   private BaseLogger   logger       = null;
   private boolean      busy         = false;
   private long         usedCount    = 0;


   public DBSingleConnection(String l, DBServer s) {
      this.loggerName = l;
      this.server = s;
   }


   public DBSingleConnection(String l, DBServer s, Connection c) {
      this.loggerName = l;
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
      getLogger().logDebug(loggingId, "Executing Query " + stmt);
      StopWatch s = new StopWatch();
      try (ResultSet res = stmt.executeQuery()) {
         getLogger().logDebug(loggingId, "Duration for executing query [" + s.getDuration() + "ms]");
         if (res != null)
            result.addResult(getResultFromDb(resultSetNumber, res));

         result = handleMoreResults(result, resultSetNumber + 1, stmt);
         getLogger().logDebug(loggingId, "Result " + result);
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

      getLogger().logDebug(loggingId, "Executing Update " + stmt);
      int rows = 0;
      this.usedCount++ ;
      StopWatch s = new StopWatch();
      try {
         this.busy = true;
         rows = stmt.executeUpdate();
         getLogger().logDebug(loggingId, "Duration for executing update [" + s.getDuration() + "ms]");
         getLogger().logDebug(loggingId, rows + " affected");
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


   protected BaseLogger getLogger() {
      if (this.logger != null)
         return this.logger;

      this.logger = LoggingFactory.createInstance(this.loggerName, new BaseLogger());

      return this.logger;
   }


   public String getLoggerName() {
      return this.loggerName;
   }


   public boolean isBusy() {
      return this.busy;
   }


   public long getUsedCount() {
      return this.usedCount;
   }
}

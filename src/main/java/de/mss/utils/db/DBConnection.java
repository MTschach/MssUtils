package de.mss.utils.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.mss.logging.BaseLogger;
import de.mss.logging.LoggingFactory;
import de.mss.utils.StopWatch;
import de.mss.utils.exception.ErrorCodes;
import de.mss.utils.exception.MssException;

public class DBConnection {

   protected List<DBServer> serverlist   = null;
   protected List<DBSingleConnection> connectionList = null;
   protected String              loggerName   = null;
   private BaseLogger            logger       = null;


   public DBConnection(String loggerName, DBServer server) {
      this.serverlist = new ArrayList<>();
      this.serverlist.add(server);
      this.loggerName = loggerName;
      init();
   }


   public DBConnection(String loggerName, DBServer[] servers) {
      this.serverlist = new ArrayList<>();
      for (DBServer s : servers)
         this.serverlist.add(s);
      this.loggerName = loggerName;
      init();
   }


   public DBConnection(String loggerName, List<DBServer> list) {
      this.serverlist = list;
      this.loggerName = loggerName;
      init();
   }


   private void init() {
      this.connectionList = new ArrayList<>();
      for (DBServer s : this.serverlist) {
         this.connectionList.add(new DBSingleConnection(this.loggerName, s));
      }
   }


   public void close() throws MssException {
      if (!isConnected())
         return;

      for (DBSingleConnection c : this.connectionList) {
         if (!c.isClosed())
            c.close();
      }
   }


   public void connect() throws MssException {
      if (isConnected())
         return;

      for (DBSingleConnection c : this.connectionList) {
         c.connect();
      }
   }


   public DBResult executeQuery(String loggingId, String query) throws MssException {
      connect();
      return getConnectionFromPool().executeQuery(loggingId, query);
   }


   public int executeUpdate (String loggingId, String query) throws MssException {
      connect();
      
      int ret = -1;
      
      for (DBSingleConnection c : this.connectionList) {
         int r = c.executeUpdate(loggingId, query);
         if (ret == -1)
            ret = r;

         if (r != ret)
            throw new MssException(
                  ErrorCodes.ERROR_DB_POSSIBLE_DATA_INCONSISTENCE,
                  "Servers did return different results. possible data inconsistence. CHECK OUT");
      }
      
      return ret;
   }


   public int executeUpdate(String loggingId, PreparedStatement stmt) throws MssException {
      if (stmt == null)
         throw new MssException(ErrorCodes.ERROR_INVALID_PARAM, "The Statement is null");

      getLogger().logDebug(loggingId, "Executing Update " + stmt);
      StopWatch s = new StopWatch();
      try {
         int rows = stmt.executeUpdate();
         getLogger().logDebug(loggingId, "Duration for executing update [" + s.getDuration() + "ms]");
         getLogger().logDebug(loggingId, rows + " affected");
         return rows;
      }
      catch (SQLException e) {
         throw new MssException(e);
      }
   }


   private DBResult handleMoreResults(DBResult result, int resultSetNumber, PreparedStatement stmt) throws MssException {
      try {
         if (stmt == null || !stmt.getMoreResults())
            return result;
      }
      catch (SQLException e) {
         throw new MssException(e);
      }

      try (ResultSet res = stmt.getResultSet()) {
         result.addResult(getResultFromDb(resultSetNumber, res));

         return handleMoreResults(result, resultSetNumber + 1, stmt);
      }
      catch (SQLException e) {
         throw new MssException(e);
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


   public boolean isConnected() {
      return (this.dbConnection != null);
   }


   public void setLoggerName(String loggerName) {
      this.loggerName = loggerName;
   }


   private DBSingleConnection getConnectionFromPool() throws MssException {
      return getConnectionFromPool(3);
   }


   private DBSingleConnection getConnectionFromPool(int retry) throws MssException {
      if (retry < 0)
         throw new MssException(ErrorCodes.ERROR_DB_NO_AVAILABLE_CONNECTION, "No Connection found");

      DBSingleConnection ret = null;
      long usedCount = Long.MAX_VALUE;

      for (DBSingleConnection c : this.connectionList) {
         if (!c.isBusy() && c.getUsedCount() < usedCount) {
            ret = c;
            usedCount = c.getUsedCount();
         }
      }

      if (ret == null) {
         try {
            Thread.sleep(25);
            return getConnectionFromPool(retry - 1);
         }
         catch (InterruptedException e) {
            throw new MssException(e);
         }
      }

      return ret;
   }
}

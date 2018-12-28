package de.mss.utils.db;

import java.util.ArrayList;
import java.util.List;

import de.mss.logging.BaseLogger;
import de.mss.logging.LoggingFactory;
import de.mss.utils.exception.ErrorCodes;

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


   public void close() throws DBException {
      if (!isConnected())
         return;

      for (DBSingleConnection c : this.connectionList) {
         if (!c.isClosed())
            c.close();
      }
   }


   public void connect() throws DBException {
      if (isConnected())
         return;

      for (DBSingleConnection c : this.connectionList) {
         c.connect();
      }
   }


   public DBResult executeQuery(String loggingId, String query) throws DBException {
      connect();
      return getConnectionFromPool(loggingId).executeQuery(loggingId, query);
   }


   public int executeUpdate(String loggingId, String query) throws DBException {
      connect();
      
      int ret = -1;
      
      for (DBSingleConnection c : this.connectionList) {
         int r = c.executeUpdate(loggingId, query);
         if (ret == -1)
            ret = r;

         if (r != ret)
            throw new DBException(
                  ErrorCodes.ERROR_DB_POSSIBLE_DATA_INCONSISTENCE,
                  "Servers did return different results. possible data inconsistence. CHECK OUT");
      }
      
      return ret;
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
      for (DBSingleConnection c : this.connectionList) {
         if (!c.isConnected())
            return false;
      }
      return true;
   }


   public void setLoggerName(String loggerName) {
      this.loggerName = loggerName;
   }


   private DBSingleConnection getConnectionFromPool(String loggingId) throws DBException {
      return getConnectionFromPool(loggingId, 3);
   }


   private DBSingleConnection getConnectionFromPool(String loggingId, int retry) throws DBException {
      if (retry < 0)
         throw new DBException(ErrorCodes.ERROR_DB_NO_AVAILABLE_CONNECTION, "No Connection found");

      DBSingleConnection ret = null;
      long usedCount = Long.MAX_VALUE;

      for (DBSingleConnection c : this.connectionList) {
         if (!c.isBusy() && c.getUsedCount() < usedCount) {
            ret = c;
            usedCount = c.getUsedCount();
         }
      }

      if (ret == null) {
         getLogger().logDebug(loggingId, "no available connection found, retry");
         try {
            Thread.sleep(25);
            return getConnectionFromPool(loggingId, retry - 1);
         }
         catch (InterruptedException e) {
            getLogger().logError(loggingId, e);
            throw new DBException(e);
         }
      }

      return ret;
   }
}

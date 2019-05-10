package de.mss.utils.db;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.mss.utils.Tools;
import de.mss.utils.exception.ErrorCodes;

public class DBConnection {

   protected List<DBServer> serverlist   = null;
   protected List<DBSingleConnection> connectionList = null;
   protected static String            loggerName     = null;

   private static volatile Logger     logger         = null;


   protected static Logger getLogger() {
      if (logger != null)
         return logger;
      if (!Tools.isSet(DBConnection.loggerName))
         DBConnection.loggerName = "default";

      logger = LogManager.getLogger(DBConnection.loggerName);
      return logger;
   }


   public DBConnection(String loggerName, DBServer server) {
      this.serverlist = new ArrayList<>();
      this.serverlist.add(server);
      DBConnection.loggerName = loggerName;
      init();
   }


   public DBConnection(String loggerName, DBServer[] servers) {
      this.serverlist = new ArrayList<>();
      for (DBServer s : servers)
         this.serverlist.add(s);
      DBConnection.loggerName = loggerName;
      init();
   }


   public DBConnection(String loggerName, List<DBServer> list) {
      this.serverlist = list;
      DBConnection.loggerName = loggerName;
      init();
   }


   private void init() {
      this.connectionList = new ArrayList<>();
      for (DBServer s : this.serverlist) {
         this.connectionList.add(new DBSingleConnection(DBConnection.loggerName, s));
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



   public String getLoggerName() {
      return DBConnection.loggerName;
   }


   public boolean isConnected() {
      for (DBSingleConnection c : this.connectionList) {
         if (!c.isConnected())
            return false;
      }
      return true;
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
         getLogger().debug("<" + loggingId + "> no available connection found, retry");
         try {
            Thread.sleep(25);
            return getConnectionFromPool(loggingId, retry - 1);
         }
         catch (InterruptedException e) {
            getLogger().error(e);
            throw new DBException(e);
         }
      }

      return ret;
   }
}

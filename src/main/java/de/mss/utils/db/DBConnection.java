package de.mss.utils.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;

import de.mss.logging.BaseLogger;
import de.mss.logging.LoggingFactory;
import de.mss.utils.exception.ErrorCodes;
import de.mss.utils.exception.MssException;

public class DBConnection {

   protected ArrayList<DBServer> serverlist   = null;
   protected String              loggerName   = null;

   protected Connection          dbConnection = null;

   private BaseLogger            logger       = null;


   public DBConnection(String loggerName, DBServer server) {
      this.serverlist = new ArrayList<>();
      this.serverlist.add(server);
      this.loggerName = loggerName;
   }


   public DBConnection(String loggerName, DBServer[] servers) {
      this.serverlist = new ArrayList<>();
      for (DBServer s : servers)
         serverlist.add(s);
      this.loggerName = loggerName;
   }


   public DBConnection(String loggerName, ArrayList<DBServer> list) {
      this.serverlist = list;
      this.loggerName = loggerName;
   }


   public void close() throws SQLException {
      if (!isConnected())
         return;

      if (!this.dbConnection.isClosed())
         this.dbConnection.close();

      this.dbConnection = null;
   }


   public void connect() throws MssException {
      if (isConnected())
         return;

      for (DBServer server : serverlist) {
         try {
            connectToDbServer(server);
            return;
         }
         catch (Exception e) {
            getLogger().log(Level.SEVERE, "could not connect to server " + server.toString(), e);
         }
      }

      throw new MssException(ErrorCodes.ERROR_DB_NO_SERVER_TO_CONNECT);
   }


   private void connectToDbServer(DBServer server) throws MssException {
      try {
         Class.forName(server.getDbDriver()).newInstance();
      }
      catch (Exception e) {
         throw new MssException(e);
      }

      try {
         if (server.getDbDriver().contains("mysql"))
            this.dbConnection = DriverManager
                  .getConnection("jdbc:mysql://" + server.getConnectionUrl() + "/" + server.getDbname(), server.getUsername(), server.getPasswd());
         else if (server.getDbDriver().contains("sybase"))
            this.dbConnection = DriverManager
                  .getConnection("jdbc:sybase:Tds:" + server.getConnectionUrl() + "/" + server.getDbname(), server.getUsername(), server.getPasswd());
         else
            throw new MssException(ErrorCodes.ERROR_DB_DRIVER_NOT_SUPPORTED, "Driver '" + server.getDbDriver() + "' is not supported");
      }
      catch (SQLException e) {
         throw new MssException(e);
      }
      catch (MssException e) {
         throw e;
      }
   }


   public DBResult executeQuery(String query) throws MssException {
      connect();

      DBResult result = new DBResult();

      try {
         PreparedStatement statement = dbConnection.prepareStatement(query);
         ResultSet res = statement.executeQuery();

         int resultSetNumber = 1;
         while (res != null) {
            result.addResult(getResultFromDb(resultSetNumber++ , res));

            res = null;
            if (statement.getMoreResults())
               res = statement.getResultSet();
         }
      }
      catch (SQLException e) {
         throw new MssException(e);
      }

      return null;
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
}
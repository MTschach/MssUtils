package de.mss.utils.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionFactory {

   private static Connection connection = null;


   public static void initConnectionFactory(Connection con) {
      connection = con;
   }


   public static Connection getConnection(DBServer server) throws DBException {
      if (connection != null)
         return connection;

      try {
         Class.forName(server.getDbDriver()).newInstance();
      }
      catch (Exception e) {
         throw new DBException(e, "Driver '" + server.getDbDriver() + "' not found");
      }

      try {
         return DriverManager
               .getConnection(
                     server.getConnectionPrefix() + server.getConnectionUrl() + "/" + server.getDbname(),
                     server.getUsername(),
                     server.getPasswd());
      }
      catch (SQLException e) {
         throw new DBException(e, "Could not get a connection from the DriverManager");
      }
   }
}

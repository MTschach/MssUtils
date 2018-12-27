package de.mss.utils.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import de.mss.utils.exception.MssException;

public class DBConnectionFactory {

   private static Connection connection = null;


   public static void initConnectionFactory(Connection con) {
      connection = con;
   }


   public static Connection getConnection(DBServer server) throws MssException {
      if (connection != null)
         return connection;

      try {
         Class.forName(server.getDbDriver()).newInstance();
      }
      catch (Exception e) {
         throw new MssException(e);
      }

      try {
         return DriverManager
               .getConnection(
                     server.getConnectionPrefix() + server.getConnectionUrl() + "/" + server.getDbname(),
                     server.getUsername(),
                     server.getPasswd());
      }
      catch (SQLException e) {
         throw new MssException(e);
      }
   }
}

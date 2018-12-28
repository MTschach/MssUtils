package de.mss.utils.db;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import junit.framework.TestCase;

public class DBConnectionTest extends TestCase {


   @Test
   public void testConstructor() {
      List<DBServer> list = prepareServerList(1);

      DBConnection con = new DBConnection("default", list.get(0));
      assertNotNull("Connection", con);

      con = new DBConnection("default", list.toArray(new DBServer[list.size()]));
      assertNotNull("Connection", con);

      con = new DBConnection("default", list);
      assertNotNull("Connection", con);

      assertEquals("Loggername", "default", con.getLoggerName());
   }


   //   @Test
   //   public void testConnectOk() throws MssException {
   //      @SuppressWarnings("resource")
   //      Connection connectionMock = EasyMock.createMock(Connection.class);
   //
   //      DBConnectionFactory.initConnectionFactory(connectionMock);
   //
   //      DBConnection con = new DBConnection("default", prepareServerList(1));
   //
   //      EasyMock.replay(connectionMock);
   //      con.connect();
   //      assertTrue("Connected", con.isConnected());
   //   }
   //
   //
   //   @Test
   //   public void testConnectOkAlreadyConnected() throws MssException {
   //      @SuppressWarnings("resource")
   //      Connection connectionMock = EasyMock.createMock(Connection.class);
   //
   //      DBConnectionFactory.initConnectionFactory(connectionMock);
   //
   //      DBConnection con = new DBConnection("default", prepareServerList(1));
   //
   //      con.connect();
   //      assertTrue("Connected", con.isConnected());
   //
   //      con.connect();
   //      assertTrue("Connected 2", con.isConnected());
   //   }
   //
   //
   //   @Test
   //   public void testConnectNook() {
   //      DBConnection con = new DBConnection("default", prepareServerList(1));
   //
   //      try {
   //         con.connect();
   //      }
   //      catch (MssException e) {
   //         assertEquals("ErrorCode", 1002, e.getError().getErrorCode());
   //      }
   //   }
   //
   //
   //   @Test
   //   public void testCloseOk() throws SQLException, MssException {
   //      DBConnection con = new DBConnection("default", prepareServerList(1));
   //      con.close();
   //      assertFalse("Connected", con.isConnected());
   //
   //      @SuppressWarnings("resource")
   //      Connection connectionMock = EasyMock.createMock(Connection.class);
   //      connectionMock.close();
   //      EasyMock.expectLastCall();
   //      EasyMock.expect(connectionMock.isClosed()).andReturn(Boolean.FALSE);
   //
   //      DBConnectionFactory.initConnectionFactory(connectionMock);
   //
   //      EasyMock.replay(connectionMock);
   //      con.connect();
   //      assertTrue("Connected before close", con.isConnected());
   //
   //      con.close();
   //      assertFalse("Connected after close", con.isConnected());
   //
   //      EasyMock.verify(connectionMock);
   //   }
   //
   //
   //   @Test
   //   public void testCloseOkAlreadyClosed() throws SQLException, MssException {
   //      DBConnection con = new DBConnection("default", prepareServerList(1));
   //      con.close();
   //      assertFalse("Connected", con.isConnected());
   //
   //      @SuppressWarnings("resource")
   //      Connection connectionMock = EasyMock.createMock(Connection.class);
   //      EasyMock.expect(connectionMock.isClosed()).andReturn(Boolean.TRUE);
   //
   //      DBConnectionFactory.initConnectionFactory(connectionMock);
   //
   //      EasyMock.replay(connectionMock);
   //      con.connect();
   //      assertTrue("Connected before close", con.isConnected());
   //
   //      con.close();
   //      assertFalse("Connected after close", con.isConnected());
   //
   //      EasyMock.verify(connectionMock);
   //   }
   //
   //
   private List<DBServer> prepareServerList(int count) {
      ArrayList<DBServer> list = new ArrayList<>();

      for (int i = 1; i <= count; i++ ) {
         list.add(new DBServer("mysql", "127.0.0." + i, Integer.valueOf(1233 + i), "db", "user", "password", null));
      }

      return list;
   }
}

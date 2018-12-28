package de.mss.utils.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Test;

import de.mss.utils.exception.MssException;
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


   @Test
   public void testConnectAndClose() throws SQLException {
      List<DBServer> list = prepareServerList(1);

      @SuppressWarnings("resource")
      Connection connectionMock = EasyMock.createMock(Connection.class);
      EasyMock.expect(connectionMock.isClosed()).andReturn(Boolean.FALSE).times(3);
      connectionMock.close();
      EasyMock.expectLastCall();
      
      DBConnectionFactory.initConnectionFactory(connectionMock);

      DBConnection con = new DBConnection("default", list);
      
      EasyMock.replay(connectionMock);

      assertFalse("before connect", con.isConnected());

      try {
         con.connect();
      }
      catch (MssException e) {
         e.printStackTrace();
         fail();
      }

      assertTrue("after connect", con.isConnected());

      try {
         con.close();
      }
      catch (MssException e) {
         e.printStackTrace();
         fail();
      }

      assertFalse("after close", con.isConnected());

      EasyMock.verify(connectionMock);
   }


   @Test
   public void testConnectAlready() throws SQLException {
      List<DBServer> list = prepareServerList(1);

      @SuppressWarnings("resource")
      Connection connectionMock = EasyMock.createMock(Connection.class);

      DBConnectionFactory.initConnectionFactory(connectionMock);

      DBConnection con = new DBConnection("default", list);

      assertFalse("before connect", con.isConnected());

      try {
         con.connect();
      }
      catch (MssException e) {
         e.printStackTrace();
         fail();
      }

      assertTrue("after connect", con.isConnected());

      try {
         con.connect();
      }
      catch (MssException e) {
         e.printStackTrace();
         fail();
      }

      assertTrue("after re-connect", con.isConnected());
   }


   @Test
   public void testConnectAndCloseAlreadyClosed() throws SQLException {
      List<DBServer> list = prepareServerList(1);

      @SuppressWarnings("resource")
      Connection connectionMock = EasyMock.createMock(Connection.class);

      DBConnectionFactory.initConnectionFactory(connectionMock);

      DBConnection con = new DBConnection("default", list);

      assertFalse("before connect", con.isConnected());

      try {
         con.close();
      }
      catch (MssException e) {
         e.printStackTrace();
         fail();
      }

      assertFalse("after close", con.isConnected());
   }


   @Test
   public void testCloseMultiple() throws SQLException {
      List<DBServer> list = prepareServerList(2);

      @SuppressWarnings("resource")
      Connection connectionMock = EasyMock.createMock(Connection.class);
      EasyMock.expect(connectionMock.isClosed()).andReturn(Boolean.FALSE).times(3).andReturn(Boolean.TRUE);
      connectionMock.close();
      EasyMock.expectLastCall();

      DBConnectionFactory.initConnectionFactory(connectionMock);

      EasyMock.replay(connectionMock);

      DBConnection con = new DBConnection("default", list);

      assertFalse("before connect", con.isConnected());

      try {
         con.connect();
      }
      catch (MssException e) {
         e.printStackTrace();
         fail();
      }

      try {
         con.close();
      }
      catch (MssException e) {
         e.printStackTrace();
         fail();
      }

      assertFalse("after close", con.isConnected());

      EasyMock.verify(connectionMock);
   }


   private List<DBServer> prepareServerList(int count) {
      ArrayList<DBServer> list = new ArrayList<>();

      for (int i = 1; i <= count; i++ ) {
         list.add(new DBServer("mysql", "127.0.0." + i, Integer.valueOf(1233 + i), "db", "user", "password", null));
      }

      return list;
   }
}

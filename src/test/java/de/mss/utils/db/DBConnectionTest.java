package de.mss.utils.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Test;

import de.mss.utils.Tools;
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


   @Test
   public void testExecuteQueryOk() throws SQLException {
      List<DBServer> list = prepareServerList(1);
      String loggingId = Tools.getId(new Throwable());

      ResultSetMetaData metaMock = EasyMock.createMock(ResultSetMetaData.class);
      EasyMock.expect(metaMock.getColumnCount()).andReturn(Integer.valueOf(4)).times(6);
      EasyMock.expect(metaMock.getColumnName(EasyMock.eq(0))).andReturn("Column1");
      EasyMock.expect(metaMock.getColumnName(EasyMock.eq(1))).andReturn("Col2");
      EasyMock.expect(metaMock.getColumnName(EasyMock.eq(2))).andReturn("Column3");
      EasyMock.expect(metaMock.getColumnName(EasyMock.eq(3))).andReturn("Col4");

      @SuppressWarnings("resource")
      ResultSet resultMock = EasyMock.createMock(ResultSet.class);
      EasyMock.expect(resultMock.getMetaData()).andReturn(metaMock);
      EasyMock.expect(resultMock.next()).andReturn(Boolean.TRUE).times(2).andReturn(Boolean.FALSE);
      EasyMock.expect(resultMock.getString(1)).andReturn("Cell0x0").andReturn("Cell1x0");
      EasyMock.expect(resultMock.getString(2)).andReturn("Cell0x1").andReturn("Cell1x1");
      EasyMock.expect(resultMock.getString(3)).andReturn("Cell0x2").andReturn("Cell1x2");
      EasyMock.expect(resultMock.getString(4)).andReturn("Cell0x3").andReturn("Cell1x3");

      resultMock.close();
      EasyMock.expectLastCall();

      @SuppressWarnings("resource")
      PreparedStatement stmtMock = EasyMock.createMock(PreparedStatement.class);
      EasyMock.expect(stmtMock.executeQuery()).andReturn(resultMock);
      EasyMock.expect(stmtMock.getMoreResults()).andReturn(Boolean.FALSE);

      @SuppressWarnings("resource")
      Connection connectionMock = EasyMock.createMock(Connection.class);
      EasyMock.expect(connectionMock.prepareStatement(EasyMock.eq("select * from table1"))).andReturn(stmtMock);

      DBConnectionFactory.initConnectionFactory(connectionMock);
      DBConnection con = new DBConnection("default", list);

      EasyMock.replay(connectionMock, stmtMock, resultMock, metaMock);
      DBResult res = null;

      try {
         res = con.executeQuery(loggingId, "select * from table1");
         assertNotNull("Result not null", res);
      }
      catch (DBException e) {
         e.printStackTrace();
         fail();
      }

      EasyMock.verify(connectionMock, stmtMock, resultMock, metaMock);
   }


   @Test
   public void testExecuteUpdateOk() throws SQLException {
      List<DBServer> list = prepareServerList(1);
      String loggingId = Tools.getId(new Throwable());

      PreparedStatement stmtMock = EasyMock.createMock(PreparedStatement.class);
      EasyMock.expect(stmtMock.executeUpdate()).andReturn(1);

      @SuppressWarnings("resource")
      Connection connectionMock = EasyMock.createMock(Connection.class);
      EasyMock.expect(connectionMock.prepareStatement(EasyMock.eq("update table1 set column2 = 3 where column4 = 1"))).andReturn(stmtMock);

      DBConnectionFactory.initConnectionFactory(connectionMock);

      DBConnection con = new DBConnection("default", list);

      EasyMock.replay(connectionMock, stmtMock);

      try {
         int rows = con.executeUpdate(loggingId, "update table1 set column2 = 3 where column4 = 1");
         assertEquals("Rows afected", 1, rows);
      }
      catch (DBException e) {
         e.printStackTrace();
         fail();
      }

      EasyMock.verify(connectionMock, stmtMock);
   }


   @Test
   public void testExecuteMultipleUpdateOk() throws SQLException {
      List<DBServer> list = prepareServerList(2);
      String loggingId = Tools.getId(new Throwable());

      PreparedStatement stmtMock = EasyMock.createMock(PreparedStatement.class);
      EasyMock.expect(stmtMock.executeUpdate()).andReturn(1).times(2);

      @SuppressWarnings("resource")
      Connection connectionMock = EasyMock.createMock(Connection.class);
      EasyMock.expect(connectionMock.prepareStatement(EasyMock.eq("update table1 set column2 = 3 where column4 = 1"))).andReturn(stmtMock).times(2);

      DBConnectionFactory.initConnectionFactory(connectionMock);

      DBConnection con = new DBConnection("default", list);

      EasyMock.replay(connectionMock, stmtMock);

      try {
         int rows = con.executeUpdate(loggingId, "update table1 set column2 = 3 where column4 = 1");
         assertEquals("Rows afected", 1, rows);
      }
      catch (DBException e) {
         e.printStackTrace();
         fail();
      }

      EasyMock.verify(connectionMock, stmtMock);
   }


   @Test
   public void testExecuteMultipleUpdateInconsistence() throws SQLException {
      List<DBServer> list = prepareServerList(2);
      String loggingId = Tools.getId(new Throwable());

      PreparedStatement stmtMock = EasyMock.createMock(PreparedStatement.class);
      EasyMock.expect(stmtMock.executeUpdate()).andReturn(1).andReturn(2);

      @SuppressWarnings("resource")
      Connection connectionMock = EasyMock.createMock(Connection.class);
      EasyMock.expect(connectionMock.prepareStatement(EasyMock.eq("update table1 set column2 = 3 where column4 = 1"))).andReturn(stmtMock).times(2);

      DBConnectionFactory.initConnectionFactory(connectionMock);

      DBConnection con = new DBConnection("default", list);

      EasyMock.replay(connectionMock, stmtMock);

      try {
         int rows = con.executeUpdate(loggingId, "update table1 set column2 = 3 where column4 = 1");
         fail();
      }
      catch (DBException e) {
         assertEquals("ErrorCode", 1004, e.getError().getErrorCode());
      }

      EasyMock.verify(connectionMock, stmtMock);
   }


   private List<DBServer> prepareServerList(int count) {
      ArrayList<DBServer> list = new ArrayList<>();

      for (int i = 1; i <= count; i++ ) {
         list.add(new DBServer("mysql", "127.0.0." + i, Integer.valueOf(1233 + i), "db", "user", "password", null));
      }

      return list;
   }
}

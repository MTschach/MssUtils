package de.mss.utils.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.easymock.EasyMock;
import org.junit.Test;

import de.mss.utils.Tools;
import de.mss.utils.exception.MssException;
import junit.framework.TestCase;

public class DBSingleConnectionTest extends TestCase {


   @Test
   public void testConstructor() {
      DBServer server = prepareServer(1);

      DBSingleConnection c = new DBSingleConnection("default", server);
      
      assertNull("DBConnection", c.getDbConnection());
      assertEquals("LoggerName", "default", c.getLoggerName());
      assertFalse("is busy", c.isBusy());
      assertEquals("Used count", Long.valueOf(0), Long.valueOf(c.getUsedCount()));

      @SuppressWarnings("resource")
      Connection connectionMock = EasyMock.createMock(Connection.class);
      DBConnectionFactory.initConnectionFactory(connectionMock);
      c = new DBSingleConnection("default", server, connectionMock);

      assertEquals("DBConnection", connectionMock, c.getDbConnection());
      assertEquals("LoggerName", "default", c.getLoggerName());
      assertFalse("is busy", c.isBusy());
      assertEquals("Used count", Long.valueOf(0), Long.valueOf(c.getUsedCount()));
   }


   @Test
   public void testCloseConnectionIsNull() {
      DBServer server = prepareServer(1);

      DBSingleConnection c = new DBSingleConnection("default", server);
      try {
         c.close();
      }
      catch (MssException e) {
         fail();
      }

      assertNull("DBConnection", c.getDbConnection());
      assertEquals("UsedCount", Long.valueOf(0), Long.valueOf(c.getUsedCount()));
   }


   @Test
   public void testCloseAlreadyClosed() throws SQLException {
      DBServer server = prepareServer(1);

      @SuppressWarnings("resource")
      Connection connectionMock = EasyMock.createMock(Connection.class);
      EasyMock.expect(connectionMock.isClosed()).andReturn(Boolean.TRUE);

      DBConnectionFactory.initConnectionFactory(connectionMock);
      DBSingleConnection c = new DBSingleConnection("default", server, connectionMock);

      EasyMock.replay(connectionMock);

      try {
         c.close();
         assertNull("DBConnection", c.getDbConnection());
      }
      catch (DBException e) {
         e.printStackTrace();
         fail();
      }

      EasyMock.verify(connectionMock);
      assertNull("DBConnection", c.getDbConnection());
      assertEquals("UsedCount", Long.valueOf(0), Long.valueOf(c.getUsedCount()));
   }


   @Test
   public void testCloseWithException() throws SQLException {
      DBServer server = prepareServer(1);

      @SuppressWarnings("resource")
      Connection connectionMock = EasyMock.createMock(Connection.class);
      EasyMock.expect(connectionMock.isClosed()).andThrow(new SQLException());

      DBConnectionFactory.initConnectionFactory(connectionMock);
      DBSingleConnection c = new DBSingleConnection("default", server, connectionMock);

      EasyMock.replay(connectionMock);

      try {
         c.close();
         fail();
      }
      catch (DBException e) {
         assertEquals("ErrorCode", 1005, e.getError().getErrorCode());
      }

      EasyMock.verify(connectionMock);
      assertNull("DBConnection", c.getDbConnection());
      assertEquals("UsedCount", Long.valueOf(0), Long.valueOf(c.getUsedCount()));
   }


   @Test
   public void testCloseWithExceptionOnClose() throws SQLException {
      DBServer server = prepareServer(1);

      @SuppressWarnings("resource")
      Connection connectionMock = EasyMock.createMock(Connection.class);
      EasyMock.expect(connectionMock.isClosed()).andReturn(Boolean.FALSE).times(2);
      connectionMock.close();
      EasyMock.expectLastCall().andThrow(new SQLException());

      DBConnectionFactory.initConnectionFactory(connectionMock);
      DBSingleConnection c = new DBSingleConnection("default", server, connectionMock);

      EasyMock.replay(connectionMock);

      try {
         c.close();
         fail();
      }
      catch (DBException e) {
         assertEquals("ErrorCode", 1005, e.getError().getErrorCode());
      }

      EasyMock.verify(connectionMock);
      assertNull("DBConnection", c.getDbConnection());
      assertEquals("UsedCount", Long.valueOf(0), Long.valueOf(c.getUsedCount()));
   }


   @Test
   public void testCloseAlreadyClosed_0002() throws SQLException {
      DBServer server = prepareServer(1);

      @SuppressWarnings("resource")
      Connection connectionMock = EasyMock.createMock(Connection.class);
      EasyMock.expect(connectionMock.isClosed()).andReturn(Boolean.FALSE).andReturn(Boolean.TRUE);

      DBConnectionFactory.initConnectionFactory(connectionMock);
      DBSingleConnection c = new DBSingleConnection("default", server, connectionMock);

      EasyMock.replay(connectionMock);

      try {
         c.close();
      }
      catch (DBException e) {
         fail();
      }

      EasyMock.verify(connectionMock);
      assertNull("DBConnection", c.getDbConnection());
      assertEquals("UsedCount", Long.valueOf(0), Long.valueOf(c.getUsedCount()));
   }


   @Test
   public void testCloseOk() throws SQLException {
      DBServer server = prepareServer(1);

      @SuppressWarnings("resource")
      Connection connectionMock = EasyMock.createMock(Connection.class);
      EasyMock.expect(connectionMock.isClosed()).andReturn(Boolean.FALSE).times(2);
      connectionMock.close();
      EasyMock.expectLastCall();

      DBConnectionFactory.initConnectionFactory(connectionMock);
      DBSingleConnection c = new DBSingleConnection("default", server, connectionMock);

      EasyMock.replay(connectionMock);

      try {
         c.close();
      }
      catch (DBException e) {
         fail();
      }

      EasyMock.verify(connectionMock);
      assertNull("DBConnection", c.getDbConnection());
      assertEquals("UsedCount", Long.valueOf(0), Long.valueOf(c.getUsedCount()));
   }


   @Test
   public void testConnectAndIsConnected() {
      DBServer server = prepareServer(1);

      @SuppressWarnings("resource")
      Connection connectionMock = EasyMock.createMock(Connection.class);

      DBConnectionFactory.initConnectionFactory(connectionMock);
      DBSingleConnection c = new DBSingleConnection("default", server);

      assertFalse("not connected", c.isConnected());

      try {
         c.connect();
      }
      catch (DBException e) {
         e.printStackTrace();
         fail();
      }

      assertEquals("DBConnection", connectionMock, c.getDbConnection());
      assertTrue("connected", c.isConnected());
   }


   @Test
   public void testExecuteUpdateExceptioOnPrepare() throws SQLException {
      String loggingId = Tools.getId(new Throwable());

      DBServer server = prepareServer(1);

      @SuppressWarnings("resource")
      Connection connectionMock = EasyMock.createMock(Connection.class);
      EasyMock.expect(connectionMock.prepareStatement(EasyMock.eq("update table1 set column1 = 1 where column2 = 2"))).andThrow(new SQLException());

      DBConnectionFactory.initConnectionFactory(connectionMock);
      DBSingleConnection c = new DBSingleConnection("default", server, connectionMock);

      EasyMock.replay(connectionMock);
      try {
         c.executeUpdate(loggingId, "update table1 set column1 = 1 where column2 = 2");
      }
      catch (DBException e) {
         assertEquals("ErrorCode", 1007, e.getError().getErrorCode());
      }

      EasyMock.verify(connectionMock);
   }


   @Test
   public void testExecuteUpdateStmtNull() {
      String loggingId = Tools.getId(new Throwable());

      DBServer server = prepareServer(1);

      @SuppressWarnings("resource")
      Connection connectionMock = EasyMock.createMock(Connection.class);

      DBConnectionFactory.initConnectionFactory(connectionMock);
      DBSingleConnection c = new DBSingleConnection("default", server, connectionMock);

      try {
         c.executeUpdate(loggingId, (PreparedStatement)null);
      }
      catch (DBException e) {
         assertEquals("ErrorCode", 2, e.getError().getErrorCode());
      }
   }


   @Test
   public void testExecuteUpdateExceptionWhileExec() throws SQLException {
      String loggingId = Tools.getId(new Throwable());

      DBServer server = prepareServer(1);

      @SuppressWarnings("resource")
      PreparedStatement stmtMock = EasyMock.createMock(PreparedStatement.class);
      EasyMock.expect(stmtMock.executeUpdate()).andThrow(new SQLException());

      @SuppressWarnings("resource")
      Connection connectionMock = EasyMock.createMock(Connection.class);
      EasyMock.expect(connectionMock.prepareStatement(EasyMock.eq("update table1 set column1 = 1 where column2 = 2"))).andReturn(stmtMock);

      DBConnectionFactory.initConnectionFactory(connectionMock);
      DBSingleConnection c = new DBSingleConnection("default", server, connectionMock);

      EasyMock.replay(connectionMock, stmtMock);
      try {
         c.executeUpdate(loggingId, "update table1 set column1 = 1 where column2 = 2");
      }
      catch (DBException e) {
         assertEquals("ErrorCode", 1007, e.getError().getErrorCode());
      }

      EasyMock.verify(connectionMock, stmtMock);
   }


   @Test
   public void testExecuteUpdateNonEffected() throws SQLException {
      String loggingId = Tools.getId(new Throwable());

      DBServer server = prepareServer(1);

      @SuppressWarnings("resource")
      PreparedStatement stmtMock = EasyMock.createMock(PreparedStatement.class);
      EasyMock.expect(stmtMock.executeUpdate()).andReturn(0);

      @SuppressWarnings("resource")
      Connection connectionMock = EasyMock.createMock(Connection.class);
      EasyMock.expect(connectionMock.prepareStatement(EasyMock.eq("update table1 set column1 = 1 where column2 = 2"))).andReturn(stmtMock);

      DBConnectionFactory.initConnectionFactory(connectionMock);
      DBSingleConnection c = new DBSingleConnection("default", server, connectionMock);

      EasyMock.replay(connectionMock, stmtMock);
      try {
         int ret = c.executeUpdate(loggingId, "update table1 set column1 = 1 where column2 = 2");

         assertEquals("Rows affected", 0, ret);
      }
      catch (DBException e) {
         fail();
      }

      EasyMock.verify(connectionMock, stmtMock);
   }


   @Test
   public void testExecuteUpdateEffected() throws SQLException {
      String loggingId = Tools.getId(new Throwable());

      DBServer server = prepareServer(1);

      @SuppressWarnings("resource")
      PreparedStatement stmtMock = EasyMock.createMock(PreparedStatement.class);
      EasyMock.expect(stmtMock.executeUpdate()).andReturn(2);

      @SuppressWarnings("resource")
      Connection connectionMock = EasyMock.createMock(Connection.class);
      EasyMock.expect(connectionMock.prepareStatement(EasyMock.eq("update table1 set column1 = 1 where column2 = 2"))).andReturn(stmtMock);

      DBConnectionFactory.initConnectionFactory(connectionMock);
      DBSingleConnection c = new DBSingleConnection("default", server, connectionMock);

      EasyMock.replay(connectionMock, stmtMock);
      try {
         int ret = c.executeUpdate(loggingId, "update table1 set column1 = 1 where column2 = 2");

         assertEquals("Rows affected", 2, ret);
      }
      catch (DBException e) {
         fail();
      }

      EasyMock.verify(connectionMock, stmtMock);
   }


   @Test
   public void testExecuteQueryExceptioOnPrepare() throws SQLException {
      String loggingId = Tools.getId(new Throwable());

      DBServer server = prepareServer(1);

      @SuppressWarnings("resource")
      Connection connectionMock = EasyMock.createMock(Connection.class);
      EasyMock.expect(connectionMock.prepareStatement(EasyMock.eq("select * from table1"))).andThrow(new SQLException());

      DBConnectionFactory.initConnectionFactory(connectionMock);
      DBSingleConnection c = new DBSingleConnection("default", server, connectionMock);

      EasyMock.replay(connectionMock);
      try {
         c.executeQuery(loggingId, "select * from table1");
      }
      catch (DBException e) {
         assertEquals("ErrorCode", 1006, e.getError().getErrorCode());
      }

      EasyMock.verify(connectionMock);
   }


   @Test
   public void testExecuteQueryStmtNull() {
      String loggingId = Tools.getId(new Throwable());

      DBServer server = prepareServer(1);

      @SuppressWarnings("resource")
      Connection connectionMock = EasyMock.createMock(Connection.class);

      DBConnectionFactory.initConnectionFactory(connectionMock);
      DBSingleConnection c = new DBSingleConnection("default", server, connectionMock);

      try {
         c.executeQuery(loggingId, (PreparedStatement)null);
      }
      catch (DBException e) {
         assertEquals("ErrorCode", 2, e.getError().getErrorCode());
      }
   }


   @Test
   public void testExecuteQueryExceptionWhileExec() throws SQLException {
      String loggingId = Tools.getId(new Throwable());

      DBServer server = prepareServer(1);

      @SuppressWarnings("resource")
      PreparedStatement stmtMock = EasyMock.createMock(PreparedStatement.class);
      EasyMock.expect(stmtMock.executeQuery()).andThrow(new SQLException());

      @SuppressWarnings("resource")
      Connection connectionMock = EasyMock.createMock(Connection.class);
      EasyMock.expect(connectionMock.prepareStatement(EasyMock.eq("select * from table1"))).andReturn(stmtMock);

      DBConnectionFactory.initConnectionFactory(connectionMock);
      DBSingleConnection c = new DBSingleConnection("default", server, connectionMock);

      EasyMock.replay(connectionMock, stmtMock);
      try {
         c.executeQuery(loggingId, "select * from table1");
      }
      catch (DBException e) {
         assertEquals("ErrorCode", 1006, e.getError().getErrorCode());
      }

      EasyMock.verify(connectionMock, stmtMock);
   }


   @Test
   public void testExecuteQueryResultNull() throws SQLException {
      String loggingId = Tools.getId(new Throwable());

      DBServer server = prepareServer(1);


      @SuppressWarnings("resource")
      PreparedStatement stmtMock = EasyMock.createMock(PreparedStatement.class);
      EasyMock.expect(stmtMock.executeQuery()).andReturn(null);
      EasyMock.expect(stmtMock.getMoreResults()).andReturn(Boolean.FALSE);

      @SuppressWarnings("resource")
      Connection connectionMock = EasyMock.createMock(Connection.class);
      EasyMock.expect(connectionMock.prepareStatement(EasyMock.eq("select * from table1"))).andReturn(stmtMock);

      DBConnectionFactory.initConnectionFactory(connectionMock);
      DBSingleConnection c = new DBSingleConnection("default", server, connectionMock);

      EasyMock.replay(connectionMock, stmtMock);
      DBResult res = null;
      try {
         res = c.executeQuery(loggingId, "select * from table1");
      }
      catch (DBException e) {
         fail();
      }

      EasyMock.verify(connectionMock, stmtMock);

      assertNotNull("Result not null", res);
      assertEquals("ResultCount = 0", 0, res.getNumberOfResults());
   }


   @Test
   public void testExecuteQuerySingleResult() throws SQLException {
      String loggingId = Tools.getId(new Throwable());

      DBServer server = prepareServer(1);

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
      DBSingleConnection c = new DBSingleConnection("default", server, connectionMock);

      EasyMock.replay(connectionMock, stmtMock, resultMock, metaMock);
      DBResult res = null;
      try {
         res = c.executeQuery(loggingId, "select * from table1");
      }
      catch (DBException e) {
         fail();
      }

      EasyMock.verify(connectionMock, stmtMock, resultMock, metaMock);

      assertNotNull("Result not null", res);
      assertEquals("ResultCount = 1", 1, res.getNumberOfResults());
      assertEquals("ColumnCount = 4", 4, res.getColumnCount(0));
      assertEquals("RowCount = 2", 2, res.getRowCount(0));

      checkResult(res, 0, "Cell");
   }


   @Test
   public void testExecuteQuerySingleResult2ndNull() throws SQLException {
      String loggingId = Tools.getId(new Throwable());

      DBServer server = prepareServer(1);

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
      EasyMock.expect(stmtMock.getMoreResults()).andReturn(Boolean.TRUE).andReturn(Boolean.FALSE);
      EasyMock.expect(stmtMock.getResultSet()).andReturn(null);

      @SuppressWarnings("resource")
      Connection connectionMock = EasyMock.createMock(Connection.class);
      EasyMock.expect(connectionMock.prepareStatement(EasyMock.eq("select * from table1"))).andReturn(stmtMock);

      DBConnectionFactory.initConnectionFactory(connectionMock);
      DBSingleConnection c = new DBSingleConnection("default", server, connectionMock);

      EasyMock.replay(connectionMock, stmtMock, resultMock, metaMock);
      DBResult res = null;
      try {
         res = c.executeQuery(loggingId, "select * from table1");
      }
      catch (DBException e) {
         fail();
      }

      EasyMock.verify(connectionMock, stmtMock, resultMock, metaMock);

      assertNotNull("Result not null", res);
      assertEquals("ResultCount = 1", 1, res.getNumberOfResults());
      assertEquals("ColumnCount = 4", 4, res.getColumnCount(0));
      assertEquals("RowCount = 2", 2, res.getRowCount(0));

      checkResult(res, 0, "Cell");
   }


   @Test
   public void testExecuteQuerySingleResult2ndException() throws SQLException {
      String loggingId = Tools.getId(new Throwable());

      DBServer server = prepareServer(1);

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
      EasyMock.expect(stmtMock.getMoreResults()).andThrow(new SQLException());
      @SuppressWarnings("resource")
      Connection connectionMock = EasyMock.createMock(Connection.class);
      EasyMock.expect(connectionMock.prepareStatement(EasyMock.eq("select * from table1"))).andReturn(stmtMock);

      DBConnectionFactory.initConnectionFactory(connectionMock);
      DBSingleConnection c = new DBSingleConnection("default", server, connectionMock);

      EasyMock.replay(connectionMock, stmtMock, resultMock, metaMock);
      try {
         c.executeQuery(loggingId, "select * from table1");
      }
      catch (DBException e) {
         assertEquals("ErrorCode", 1008, e.getError().getErrorCode());
      }

      EasyMock.verify(connectionMock, stmtMock, resultMock, metaMock);
   }


   @Test
   public void testExecuteQuerySingleResult2ndException_0002() throws SQLException {
      String loggingId = Tools.getId(new Throwable());

      DBServer server = prepareServer(1);

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
      EasyMock.expect(stmtMock.getMoreResults()).andReturn(Boolean.TRUE);
      EasyMock.expect(stmtMock.getResultSet()).andThrow(new SQLException());
      @SuppressWarnings("resource")
      Connection connectionMock = EasyMock.createMock(Connection.class);
      EasyMock.expect(connectionMock.prepareStatement(EasyMock.eq("select * from table1"))).andReturn(stmtMock);

      DBConnectionFactory.initConnectionFactory(connectionMock);
      DBSingleConnection c = new DBSingleConnection("default", server, connectionMock);

      EasyMock.replay(connectionMock, stmtMock, resultMock, metaMock);
      try {
         c.executeQuery(loggingId, "select * from table1");
      }
      catch (DBException e) {
         assertEquals("ErrorCode", 1008, e.getError().getErrorCode());
      }

      EasyMock.verify(connectionMock, stmtMock, resultMock, metaMock);
   }


   @Test
   public void testExecuteQueryMultipleResult() throws SQLException {
      String loggingId = Tools.getId(new Throwable());

      DBServer server = prepareServer(1);

      ResultSetMetaData metaMock = EasyMock.createMock(ResultSetMetaData.class);
      EasyMock.expect(metaMock.getColumnCount()).andReturn(Integer.valueOf(4)).times(6).andReturn(Integer.valueOf(2)).times(4);
      EasyMock.expect(metaMock.getColumnName(EasyMock.eq(0))).andReturn("Column1").andReturn("VALUE1");
      EasyMock.expect(metaMock.getColumnName(EasyMock.eq(1))).andReturn("Col2").andReturn("Value2");
      EasyMock.expect(metaMock.getColumnName(EasyMock.eq(2))).andReturn("Column3");
      EasyMock.expect(metaMock.getColumnName(EasyMock.eq(3))).andReturn("Col4");

      @SuppressWarnings("resource")
      ResultSet resultMock = EasyMock.createMock(ResultSet.class);
      EasyMock.expect(resultMock.getMetaData()).andReturn(metaMock).times(2);
      EasyMock
            .expect(resultMock.next())
            .andReturn(Boolean.TRUE)
            .times(2)
            .andReturn(Boolean.FALSE)
            .andReturn(Boolean.TRUE)
            .times(3)
            .andReturn(Boolean.FALSE);
      EasyMock
            .expect(resultMock.getString(1))
            .andReturn("Cell0x0")
            .andReturn("Cell1x0")
            .andReturn("Value 0x0")
            .andReturn("Value 1x0")
            .andReturn("Value 2x0");
      EasyMock
            .expect(resultMock.getString(2))
            .andReturn("Cell0x1")
            .andReturn("Cell1x1")
            .andReturn("Value 0x1")
            .andReturn("Value 1x1")
            .andReturn("Value 2x1");
      EasyMock.expect(resultMock.getString(3)).andReturn("Cell0x2").andReturn("Cell1x2");
      EasyMock.expect(resultMock.getString(4)).andReturn("Cell0x3").andReturn("Cell1x3");

      resultMock.close();
      EasyMock.expectLastCall().times(2);

      @SuppressWarnings("resource")
      PreparedStatement stmtMock = EasyMock.createMock(PreparedStatement.class);
      EasyMock.expect(stmtMock.executeQuery()).andReturn(resultMock);
      EasyMock.expect(stmtMock.getMoreResults()).andReturn(Boolean.TRUE).andReturn(Boolean.FALSE);
      EasyMock.expect(stmtMock.getResultSet()).andReturn(resultMock);

      @SuppressWarnings("resource")
      Connection connectionMock = EasyMock.createMock(Connection.class);
      EasyMock.expect(connectionMock.prepareStatement(EasyMock.eq("select * from table1"))).andReturn(stmtMock);

      DBConnectionFactory.initConnectionFactory(connectionMock);
      DBSingleConnection c = new DBSingleConnection("default", server, connectionMock);

      EasyMock.replay(connectionMock, stmtMock, resultMock, metaMock);
      DBResult res = null;
      try {
         res = c.executeQuery(loggingId, "select * from table1");
      }
      catch (DBException e) {
         fail();
      }

      EasyMock.verify(connectionMock, stmtMock, resultMock, metaMock);

      assertNotNull("Result not null", res);
      assertEquals("ResultCount = 2", 2, res.getNumberOfResults());
      assertEquals("ColumnCount(0) = 4", 4, res.getColumnCount(0));
      assertEquals("RowCount(0) = 2", 2, res.getRowCount(0));
      assertEquals("ColumnCount(1) = 2", 2, res.getColumnCount(1));
      assertEquals("RowCount(1) = 3", 3, res.getRowCount(1));

      checkResult(res, 0, "Cell");
      checkResult(res, 1, "Value ");
   }


   private void checkResult(DBResult res, int resultSetNumber, String prefix) {
      for (int r = 0; r < res.getRowCount(resultSetNumber); r++ ) {
         for (int c = 0; c < res.getColumnCount(resultSetNumber); c++ ) {
            assertEquals("Cellvalue " + r + "x" + c, prefix + r + "x" + c, res.getValue(resultSetNumber, c, r));
         }
      }
   }


   private DBServer prepareServer(int number) {
      return new DBServer("mysql", "127.0.0." + number, Integer.valueOf(1233 + number), "db", "user", "password", null);
   }
}

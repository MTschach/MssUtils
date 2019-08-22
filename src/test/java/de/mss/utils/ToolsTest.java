package de.mss.utils;

import java.math.BigDecimal;

import org.junit.Test;

import de.mss.utils.exception.MssException;
import junit.framework.TestCase;

public class ToolsTest extends TestCase {

   @SuppressWarnings("unused")
   @Test
   public void testToolsTest() {
      try {
         new Tools();
         fail();
      }
      catch (MssException e) {
         assertEquals("ErrorCode", 1, e.getError().getErrorCode());
      }
   }


   @Test
   public void testGetId() {
      String loggingId = Tools.getId("prefix");
      assertNotNull("LoggingId not null", loggingId);
      assertTrue("Startswith prefix", loggingId.startsWith("prefix"));
   }


   @Test
   public void testGetIdWithThrowable() {
      String loggingId = Tools.getId(new Throwable());
      assertNotNull("LoggingId not null", loggingId);
      assertTrue("Startswith testGetIdWithThrowable", loggingId.startsWith("testGetIdWithThrowable"));
   }


   @Test
   public void testIsSet() {
      assertTrue("Is set 1", Tools.isSet("1"));
      assertFalse("Is not set null", Tools.isSet(null));
      assertFalse("Is not set ''", Tools.isSet(""));
   }


   @Test
   public void testIsFalseBoolean() {
      assertFalse("isFalse null", Tools.isFalse((Boolean)null));
      assertTrue("IsFalse false", Tools.isFalse(Boolean.FALSE));
      assertFalse("IsFalse true", Tools.isFalse(Boolean.TRUE));
   }


   @Test
   public void testIsFalseString() {
      assertFalse("isFalse null", Tools.isFalse((String)null));
      assertFalse("isFalse ''", Tools.isFalse(""));
      assertFalse("isFalse 'bla'", Tools.isFalse("bla"));
      assertTrue("isFalse '0'", Tools.isFalse("0"));
      assertTrue("isFalse 'n'", Tools.isFalse("n"));
      assertTrue("isFalse 'N'", Tools.isFalse("N"));
      assertTrue("isFalse 'false'", Tools.isFalse("false"));
      assertTrue("isFalse 'False'", Tools.isFalse("False"));
      assertTrue("isFalse 'no'", Tools.isFalse("no"));
      assertTrue("isFalse 'NO'", Tools.isFalse("NO"));
   }


   @Test
   public void testIsTrueBoolean() {
      assertFalse("isTrue null", Tools.isTrue((Boolean)null));
      assertFalse("IsTrue false", Tools.isTrue(Boolean.FALSE));
      assertTrue("IsTrue true", Tools.isTrue(Boolean.TRUE));
   }


   @Test
   public void testIsTrueString() {
      assertFalse("isTrue null", Tools.isTrue((String)null));
      assertFalse("isTrue ''", Tools.isTrue(""));
      assertFalse("isTrue 'bla'", Tools.isTrue("bla"));
      assertTrue("isTrue '1'", Tools.isTrue("1"));
      assertTrue("isTrue 'j'", Tools.isTrue("j"));
      assertTrue("isTrue 'J'", Tools.isTrue("J"));
      assertTrue("isTrue 'true'", Tools.isTrue("true"));
      assertTrue("isTrue 'True'", Tools.isTrue("True"));
      assertTrue("isTrue 'yes'", Tools.isTrue("yes"));
      assertTrue("isTrue 'YES'", Tools.isTrue("YES"));
   }


   @Test
   public void testGetLoggingId() {
      assertEquals("<myLoggingId> ", Tools.formatLoggingId("myLoggingId"));
   }


   @Test
   public void testDoNullLog() {
      Tools.doNullLog(new Exception("ein Test"));
   }


   @Test
   public void testConBigDecimal() {
      BigDecimal value = new BigDecimal("1.2");

      assertNull("null to Integer", Tools.conBigDecimal2Integer(null));
      assertTrue("null to int", Tools.conBigDecimal2PrimitiveInteger(null) == 0);
      assertNull("null to Double", Tools.conBigDecimal2Double(null));
      assertTrue("null to double", Tools.conBigDecimal2PrimitiveDouble(null) == 0);
      assertNull("null to Float", Tools.conBigDecimal2Float(null));
      assertTrue("null to float", Tools.conBigDecimal2PrimitiveFloat(null) == 0);

      assertEquals("1.2 to Integer", Integer.valueOf(1), Tools.conBigDecimal2Integer(value));
      assertTrue("1.2 to int", Tools.conBigDecimal2PrimitiveInteger(value) == 1);
      assertEquals("1.2 to Double", Double.valueOf(1.2d), Tools.conBigDecimal2Double(value));
      assertTrue("1.2 to double", Tools.conBigDecimal2PrimitiveDouble(value) == 1.2d);
      assertEquals("1.2 to Float", Float.valueOf(1.2f), Tools.conBigDecimal2Float(value));
      assertTrue("1.2 to float", Tools.conBigDecimal2PrimitiveFloat(value) == 1.2f);
   }


   @Test
   public void testConDouble() {
      Double value = new Double("1.2");

      assertNull("null to Integer", Tools.conDouble2Integer(null));
      assertTrue("null to int", Tools.conDouble2PrimitiveInteger(null) == 0);
      assertNull("null to BigDecimal", Tools.conDouble2BigDecimal(null));
      assertNull("null to Float", Tools.conDouble2Float(null));
      assertTrue("null to float", Tools.conDouble2PrimitiveFloat(null) == 0);

      assertEquals("1.2 to Integer", Integer.valueOf(1), Tools.conDouble2Integer(value));
      assertTrue("1.2 to int", Tools.conDouble2PrimitiveInteger(value) == 1);
      assertEquals("1.2 to BigDecimal", BigDecimal.valueOf(1.2d), Tools.conDouble2BigDecimal(value));
      assertEquals("1.2 to Float", Float.valueOf(1.2f), Tools.conDouble2Float(value));
      assertTrue("1.2 to float", Tools.conDouble2PrimitiveFloat(value) == 1.2f);
   }


   @Test
   public void testConFloat() {
      Float value = new Float("1.2");

      assertNull("null to Integer", Tools.conFloat2Integer(null));
      assertTrue("null to int", Tools.conFloat2PrimitiveInteger(null) == 0);
      assertNull("null to BigDecimal", Tools.conFloat2BigDecimal(null));
      assertNull("null to Double", Tools.conFloat2Double(null));
      assertTrue("null to double", Tools.conFloat2PrimitiveDouble(null) == 0);

      assertEquals("1.2 to Integer", Integer.valueOf(1), Tools.conFloat2Integer(value));
      assertTrue("1.2 to int", Tools.conFloat2PrimitiveInteger(value) == 1);
      assertEquals("1.2 to BigDecimal", BigDecimal.valueOf(1.2f), Tools.conFloat2BigDecimal(value));
      assertEquals("1.2 to Double", Double.valueOf(1.2f), Tools.conFloat2Double(value));
      assertTrue("1.2 to double", Tools.conFloat2PrimitiveDouble(value) == 1.2f);
   }


   @Test
   public void testConInteger() {
      Integer value = Integer.valueOf(2);

      assertNull("null to Float", Tools.conInteger2Float(null));
      assertTrue("null to float", Tools.conInteger2PrimitiveFloat(null) == 0);
      assertNull("null to BigDecimal", Tools.conInteger2BigDecimal(null));
      assertNull("null to Double", Tools.conInteger2Double(null));
      assertTrue("null to double", Tools.conInteger2PrimitiveDouble(null) == 0);

      assertEquals("2 to Float", Float.valueOf(2), Tools.conInteger2Float(value));
      assertTrue("2 to float", Tools.conInteger2PrimitiveFloat(value) == 2f);
      assertEquals("2 to BigDecimal", BigDecimal.valueOf(2), Tools.conInteger2BigDecimal(value));
      assertEquals("2 to Double", Double.valueOf(2f), Tools.conInteger2Double(value));
      assertTrue("2 to double", Tools.conInteger2PrimitiveDouble(value) == 2d);
   }
}

package de.mss.utils;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.junit.jupiter.api.Test;

public class ToolsTest {

   @Test
   public void testConBigDecimal() {
      final BigDecimal value = new BigDecimal("1.2");

      assertNull(Tools.conBigDecimal2Integer(null));
      assertTrue(Tools.conBigDecimal2PrimitiveInteger(null) == 0);
      assertNull(Tools.conBigDecimal2Double(null));
      assertTrue(Tools.conBigDecimal2PrimitiveDouble(null) == 0);
      assertNull(Tools.conBigDecimal2Float(null));
      assertTrue(Tools.conBigDecimal2PrimitiveFloat(null) == 0);

      assertEquals(Integer.valueOf(1), Tools.conBigDecimal2Integer(value));
      assertTrue(Tools.conBigDecimal2PrimitiveInteger(value) == 1);
      assertEquals(Double.valueOf(1.2d), Tools.conBigDecimal2Double(value));
      assertTrue(Tools.conBigDecimal2PrimitiveDouble(value) == 1.2d);
      assertEquals(Float.valueOf(1.2f), Tools.conBigDecimal2Float(value));
      assertTrue(Tools.conBigDecimal2PrimitiveFloat(value) == 1.2f);
   }


   @Test
   public void testConDouble() {
      final Double value = Double.valueOf("1.2");

      assertNull(Tools.conDouble2Integer(null));
      assertTrue(Tools.conDouble2PrimitiveInteger(null) == 0);
      assertNull(Tools.conDouble2BigDecimal(null));
      assertNull(Tools.conDouble2Float(null));
      assertTrue(Tools.conDouble2PrimitiveFloat(null) == 0);

      assertEquals(Integer.valueOf(1), Tools.conDouble2Integer(value));
      assertTrue(Tools.conDouble2PrimitiveInteger(value) == 1);
      assertEquals(BigDecimal.valueOf(1.2d), Tools.conDouble2BigDecimal(value));
      assertEquals(Float.valueOf(1.2f), Tools.conDouble2Float(value));
      assertTrue(Tools.conDouble2PrimitiveFloat(value) == 1.2f);
   }


   @Test
   public void testConFloat() {
      final Float value = Float.valueOf("1.2");

      assertNull(Tools.conFloat2Integer(null));
      assertTrue(Tools.conFloat2PrimitiveInteger(null) == 0);
      assertNull(Tools.conFloat2BigDecimal(null));
      assertNull(Tools.conFloat2Double(null));
      assertTrue(Tools.conFloat2PrimitiveDouble(null) == 0);

      assertEquals(Integer.valueOf(1), Tools.conFloat2Integer(value));
      assertTrue(Tools.conFloat2PrimitiveInteger(value) == 1);
      assertEquals(BigDecimal.valueOf(1.2f), Tools.conFloat2BigDecimal(value));
      assertEquals(Double.valueOf(1.2f), Tools.conFloat2Double(value));
      assertTrue(Tools.conFloat2PrimitiveDouble(value) == 1.2f);
   }


   @Test
   public void testConInteger() {
      final Integer value = Integer.valueOf(2);

      assertNull(Tools.conInteger2Float(null));
      assertTrue(Tools.conInteger2PrimitiveFloat(null) == 0);
      assertNull(Tools.conInteger2BigDecimal(null));
      assertNull(Tools.conInteger2Double(null));
      assertTrue(Tools.conInteger2PrimitiveDouble(null) == 0);

      assertEquals(Float.valueOf(2), Tools.conInteger2Float(value));
      assertTrue(Tools.conInteger2PrimitiveFloat(value) == 2f);
      assertEquals(BigDecimal.valueOf(2), Tools.conInteger2BigDecimal(value));
      assertEquals(Double.valueOf(2f), Tools.conInteger2Double(value));
      assertTrue(Tools.conInteger2PrimitiveDouble(value) == 2d);
   }


   @Test
   public void testDoNullLog() {
      Tools.doNullLog(new Exception("ein Test"));
   }


   @Test
   public void testGetHash() throws Exception {
      assertEquals("982d9e3eb996f559e633f4d194def3761d909f5a3b647d1a851fead67c32c9d1", Tools.getHash("SHA-256", "text", () -> {
         return new Exception();
      }));
      assertNull(Tools.getHash("blah", "text", null));
      try {
         Tools.getHash("blah", "text", () -> {
            return new Exception();
         });
         fail("no exception was thrown");
      }
      catch (final Exception e) {
         Tools.doNullLog(e);
      }
   }


   @Test
   public void testGetId() {
      final String loggingId = Tools.getId("prefix");
      assertNotNull(loggingId);
      assertTrue(loggingId.startsWith("prefix"));
   }


   @Test
   public void testGetIdWithThrowable() {
      final String loggingId = Tools.getId(new Throwable());
      assertNotNull("LoggingId not null", loggingId);
      assertTrue(loggingId.startsWith("testGetIdWithThrowable"));
   }


   @Test
   public void testGetLoggingId() {
      assertEquals("<myLoggingId> ", Tools.formatLoggingId("myLoggingId"));
   }


   @Test
   public void testIsEmptyList() {
      List<String> list = null;
      assertTrue(Tools.isEmpty(list));
      list = new ArrayList<>();
      assertTrue(Tools.isEmpty(list));
      list.add("1");
      assertFalse(Tools.isEmpty(list));
   }


   @Test
   public void testIsEmptyMap() {
      Map<String, String> map = null;
      assertTrue(Tools.isEmpty(map));
      map = new HashMap<>();
      assertTrue(Tools.isEmpty(map));
      map.put("1", "2");
      assertFalse(Tools.isEmpty(map));
   }


   @Test
   public void testIsEmptyVector() {
      Vector<String> vec = null;
      assertTrue(Tools.isEmpty(vec));
      vec = new Vector<>();
      assertTrue(Tools.isEmpty(vec));
      vec.add("1");
      assertFalse(Tools.isEmpty(vec));
   }


   @Test
   public void testIsFalseBoolean() {
      assertFalse(Tools.isFalse((Boolean)null));
      assertTrue(Tools.isFalse(Boolean.FALSE));
      assertFalse(Tools.isFalse(Boolean.TRUE));
   }


   @Test
   public void testIsFalseString() {
      assertFalse(Tools.isFalse((String)null));
      assertFalse(Tools.isFalse(""));
      assertFalse(Tools.isFalse("bla"));
      assertTrue(Tools.isFalse("0"));
      assertTrue(Tools.isFalse("n"));
      assertTrue(Tools.isFalse("N"));
      assertTrue(Tools.isFalse("false"));
      assertTrue(Tools.isFalse("False"));
      assertTrue(Tools.isFalse("no"));
      assertTrue(Tools.isFalse("NO"));
   }


   @Test
   public void testIsSet() {
      assertTrue(Tools.isSet("1"));
      assertFalse(Tools.isSet((String)null));
      assertFalse(Tools.isSet(""));
   }


   @Test
   public void testIsSetByte() {
      byte[] value = null;
      assertFalse(Tools.isSet(value));
      value = new byte[0];
      assertFalse(Tools.isSet(value));
      value = new byte[1];
      value[0] = 48;
      assertTrue(Tools.isSet(value));
   }


   @Test
   public void testIsSetObject() {
      String[] value = null;
      assertFalse(Tools.isSet(value));
      value = new String[0];
      assertFalse(Tools.isSet(value));
      value = new String[1];
      value[0] = "48";
      assertTrue(Tools.isSet(value));
   }


   @Test
   public void testIsTrueBoolean() {
      assertFalse(Tools.isTrue((Boolean)null));
      assertFalse(Tools.isTrue(Boolean.FALSE));
      assertTrue(Tools.isTrue(Boolean.TRUE));
   }


   @Test
   public void testIsTrueString() {
      assertFalse(Tools.isTrue((String)null));
      assertFalse(Tools.isTrue(""));
      assertFalse(Tools.isTrue("bla"));
      assertTrue(Tools.isTrue("1"));
      assertTrue(Tools.isTrue("j"));
      assertTrue(Tools.isTrue("J"));
      assertTrue(Tools.isTrue("true"));
      assertTrue(Tools.isTrue("True"));
      assertTrue(Tools.isTrue("yes"));
      assertTrue(Tools.isTrue("YES"));
   }
}

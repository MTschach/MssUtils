package de.mss.utils.logging;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.junit.jupiter.api.Test;

import de.mss.utils.DateTimeTools;
import de.mss.utils.exception.MssException;

public class LoggingUtilTest {


   @Test
   public void testBigDecimal() {
      Map<String, String> props = new HashMap<>();

      props = LoggingUtil.addLogging("simple", BigDecimal.ONE, props);
      props = LoggingUtil.addLogging("array", new BigDecimal[] {BigDecimal.TEN, BigDecimal.ZERO}, props);
      props = LoggingUtil.addLogging("nullValue", (BigDecimal)null, props);
      props = LoggingUtil.addLogging("nullArray", (BigDecimal[])null, props);

      assertNull(LoggingUtil.addLogging("nullProps", BigDecimal.ONE, null));
      assertNull(LoggingUtil.addLogging("nullProps", new BigDecimal[] {BigDecimal.TEN, BigDecimal.ZERO}, null));
      assertEquals(Integer.valueOf(2), Integer.valueOf(props.size()));
      assertEquals("1", props.get("simple"));
      assertEquals("[ size {2} [0] {10} [1] {0} ] ", props.get("array"));
   }


   @Test
   public void testBigInteger() {
      Map<String, String> props = new HashMap<>();

      props = LoggingUtil.addLogging("simple", BigInteger.ONE, props);
      props = LoggingUtil.addLogging("array", new BigInteger[] {BigInteger.TEN, BigInteger.ZERO}, props);
      props = LoggingUtil.addLogging("nullValue", (BigInteger)null, props);
      props = LoggingUtil.addLogging("nullValue", (BigInteger[])null, props);

      assertNull(LoggingUtil.addLogging("nullProps", BigInteger.ONE, null));
      assertNull(LoggingUtil.addLogging("nullProps", new BigInteger[] {BigInteger.TEN, BigInteger.ZERO}, null));
      assertEquals(Integer.valueOf(2), Integer.valueOf(props.size()));
      assertEquals("1", props.get("simple"));
      assertEquals("[ size {2} [0] {10} [1] {0} ] ", props.get("array"));
   }


   @Test
   public void testBool() {
      Map<String, String> props = new HashMap<>();

      props = LoggingUtil.addLogging("simple", true, props);
      props = LoggingUtil.addLogging("array", new boolean[] {true, false}, props);
      props = LoggingUtil.addLogging("arrayNull", (boolean[])null, props);

      assertNull(LoggingUtil.addLogging("nullProps", true, null));
      assertNull(LoggingUtil.addLogging("nullProps", new boolean[] {true, false}, null));
      assertEquals(Integer.valueOf(2), Integer.valueOf(props.size()));
      assertEquals("true", props.get("simple"));
      assertEquals("[ size {2} [0] {true} [1] {false} ] ", props.get("array"));
   }


   @Test
   public void testBoolean() {
      Map<String, String> props = new HashMap<>();

      props = LoggingUtil.addLogging("simple", Boolean.TRUE, props);
      props = LoggingUtil.addLogging("array", new Boolean[] {Boolean.TRUE, Boolean.FALSE}, props);
      props = LoggingUtil.addLogging("nullValue", (Boolean)null, props);
      props = LoggingUtil.addLogging("nullValue", (Boolean[])null, props);

      assertNull(LoggingUtil.addLogging("nullProps", Boolean.TRUE, null));
      assertNull(LoggingUtil.addLogging("nullProps", new Boolean[] {Boolean.TRUE, Boolean.FALSE}, null));
      assertEquals(Integer.valueOf(2), Integer.valueOf(props.size()));
      assertEquals("true", props.get("simple"));
      assertEquals("[ size {2} [0] {true} [1] {false} ] ", props.get("array"));
   }


   @Test
   public void testbyte() {
      Map<String, String> props = new HashMap<>();

      props = LoggingUtil.addLogging("simple", (byte)1, props);
      props = LoggingUtil.addLogging("array", new byte[] {(byte)10, (byte)0}, props);
      props = LoggingUtil.addLogging("arrayNull", (byte[])null, props);

      assertNull(LoggingUtil.addLogging("nullProps", (byte)1, null));
      assertNull(LoggingUtil.addLogging("nullProps", new byte[] {(byte)10, (byte)0}, null));
      assertEquals(Integer.valueOf(2), Integer.valueOf(props.size()));
      assertEquals("1", props.get("simple"));
      assertEquals("[ size {2} [0] {10} [1] {0} ] ", props.get("array"));
   }


   @Test
   public void testByte() {
      Map<String, String> props = new HashMap<>();

      props = LoggingUtil.addLogging("simple", Byte.valueOf("1"), props);
      props = LoggingUtil.addLogging("array", new Byte[] {Byte.valueOf("10"), Byte.valueOf("0")}, props);
      props = LoggingUtil.addLogging("nullValue", (Byte)null, props);
      props = LoggingUtil.addLogging("nullValue", (Byte[])null, props);

      assertNull(LoggingUtil.addLogging("nullProps", Byte.valueOf("1"), null));
      assertNull(LoggingUtil.addLogging("nullProps", new Byte[] {Byte.valueOf("10"), Byte.valueOf("0")}, null));
      assertEquals(Integer.valueOf(2), Integer.valueOf(props.size()));
      assertEquals("1", props.get("simple"));
      assertEquals("[ size {2} [0] {10} [1] {0} ] ", props.get("array"));
   }


   @Test
   public void testDate() throws MssException {
      Map<String, String> props = new HashMap<>();

      props = LoggingUtil.addLogging("simple", DateTimeTools.parseString2Date("2022-01-06 12:34:56"), props);
      props = LoggingUtil
            .addLogging(
                  "array",
                  new Date[] {DateTimeTools.parseString2Date("2021-12-31 23:59:59"), DateTimeTools.parseString2Date("2020-12-24 18:56:12")},
                  props);
      props = LoggingUtil.addLogging("nullValue", (Date)null, props);
      props = LoggingUtil.addLogging("nullValue", (Date[])null, props);

      assertNull(LoggingUtil.addLogging("nullProps", DateTimeTools.parseString2Date("2021-12-31 23:59:56"), null));
      assertNull(
            LoggingUtil
                  .addLogging(
                        "nullProps",
                        new Date[] {DateTimeTools.parseString2Date("2021-12-31 23:59:59"), DateTimeTools.parseString2Date("2020-12-24 18:56:12")},
                        null));
      assertEquals(Integer.valueOf(2), Integer.valueOf(props.size()));
      assertEquals("2022-01-06T12:34:56 +0100", props.get("simple"));
      assertEquals("[ size {2} [0] {2021-12-31T23:59:59 +0100} [1] {2020-12-24T18:56:12 +0100} ] ", props.get("array"));
   }


   @Test
   public void testdouble() {
      Map<String, String> props = new HashMap<>();

      props = LoggingUtil.addLogging("simple", 1.2, props);
      props = LoggingUtil.addLogging("array", new double[] {10, 0.1}, props);
      props = LoggingUtil.addLogging("arrayNull", (double[])null, props);

      assertNull(LoggingUtil.addLogging("nullProps", 1.2, null));
      assertNull(LoggingUtil.addLogging("nullProps", new double[] {1.2, 1.1}, null));
      assertEquals(Integer.valueOf(2), Integer.valueOf(props.size()));
      assertEquals("1.2", props.get("simple"));
      assertEquals("[ size {2} [0] {10.0} [1] {0.1} ] ", props.get("array"));
   }


   @Test
   public void testDouble() {
      Map<String, String> props = new HashMap<>();

      props = LoggingUtil.addLogging("simple", Double.valueOf(1.2), props);
      props = LoggingUtil.addLogging("array", new Double[] {Double.valueOf(10), Double.valueOf(0.1)}, props);
      props = LoggingUtil.addLogging("nullValue", (Double)null, props);
      props = LoggingUtil.addLogging("nullValue", (Double[])null, props);

      assertNull(LoggingUtil.addLogging("nullProps", Double.valueOf(1.2), null));
      assertNull(LoggingUtil.addLogging("nullProps", new Double[] {Double.valueOf(1.2), Double.valueOf(1.1)}, null));
      assertEquals(Integer.valueOf(2), Integer.valueOf(props.size()));
      assertEquals("1.2", props.get("simple"));
      assertEquals("[ size {2} [0] {10.0} [1] {0.1} ] ", props.get("array"));
   }


   @Test
   public void testfloat() {
      Map<String, String> props = new HashMap<>();

      props = LoggingUtil.addLogging("simple", 1.2f, props);
      props = LoggingUtil.addLogging("array", new float[] {10, 0.1f}, props);
      props = LoggingUtil.addLogging("arrayNull", (float[])null, props);

      assertNull(LoggingUtil.addLogging("nullProps", 0.1f, null));
      assertNull(LoggingUtil.addLogging("nullProps", new float[] {1, 0.9f}, null));
      assertEquals(Integer.valueOf(2), Integer.valueOf(props.size()));
      assertEquals("1.2", props.get("simple"));
      assertEquals("[ size {2} [0] {10.0} [1] {0.1} ] ", props.get("array"));
   }


   @Test
   public void testFloat() {
      Map<String, String> props = new HashMap<>();

      props = LoggingUtil.addLogging("simple", Float.valueOf(1.2f), props);
      props = LoggingUtil.addLogging("array", new Float[] {Float.valueOf(10), Float.valueOf(0.1f)}, props);
      props = LoggingUtil.addLogging("nullValue", (Float)null, props);
      props = LoggingUtil.addLogging("nullValue", (Float[])null, props);

      assertNull(LoggingUtil.addLogging("nullProps", Float.valueOf(1.2f), null));
      assertNull(LoggingUtil.addLogging("nullProps", new Float[] {Float.valueOf(2.1f), Float.valueOf(1.1f)}, null));
      assertEquals(Integer.valueOf(2), Integer.valueOf(props.size()));
      assertEquals("1.2", props.get("simple"));
      assertEquals("[ size {2} [0] {10.0} [1] {0.1} ] ", props.get("array"));
   }


   @Test
   public void testGetLogString() {
      assertEquals("", LoggingUtil.getLogString(null));

      Map<String, String> props = new HashMap<>();
      assertEquals("", LoggingUtil.getLogString(props));

      props = LoggingUtil.addLogging("intValue", 1, props);
      props = LoggingUtil.addLogging("boolValue", true, props);
      props = LoggingUtil.addLogging("stringValue", "String", props);
      props = LoggingUtil.addLogging("object", new LoggingTestObject("testValue"), props);

      assertEquals("boolValue {true} intValue {1} object {Name {testValue} } stringValue {String} ", LoggingUtil.getLogString(props));
   }


   @Test
   public void testint() {
      Map<String, String> props = new HashMap<>();

      props = LoggingUtil.addLogging("simple", 1, props);
      props = LoggingUtil.addLogging("array", new int[] {10, 1}, props);
      props = LoggingUtil.addLogging("arrayNull", (int[])null, props);

      assertNull(LoggingUtil.addLogging("nullProps", 1, null));
      assertNull(LoggingUtil.addLogging("nullProps", new int[] {3, 1}, null));
      assertEquals(Integer.valueOf(2), Integer.valueOf(props.size()));
      assertEquals("1", props.get("simple"));
      assertEquals("[ size {2} [0] {10} [1] {1} ] ", props.get("array"));
   }


   @Test
   public void testInteger() {
      Map<String, String> props = new HashMap<>();

      props = LoggingUtil.addLogging("simple", Integer.valueOf(1), props);
      props = LoggingUtil.addLogging("array", new Integer[] {Integer.valueOf(10), Integer.valueOf(1)}, props);
      props = LoggingUtil.addLogging("nullValue", (Integer)null, props);
      props = LoggingUtil.addLogging("nullValue", (Integer[])null, props);

      assertNull(LoggingUtil.addLogging("nullProps", Integer.valueOf(1), null));
      assertNull(LoggingUtil.addLogging("nullProps", new Integer[] {Integer.valueOf(2), Integer.valueOf(4)}, null));
      assertEquals(Integer.valueOf(2), Integer.valueOf(props.size()));
      assertEquals("1", props.get("simple"));
      assertEquals("[ size {2} [0] {10} [1] {1} ] ", props.get("array"));
   }


   @Test
   public void testList() {
      final List<String> list = new ArrayList<>();
      list.add("first");
      list.add("second");

      Map<String, String> props = new HashMap<>();
      props = LoggingUtil.addLogging("list", list, props);
      props = LoggingUtil.addLogging("null", (List<?>)null, props);

      assertNull(LoggingUtil.addLogging("null", list, null));
      assertEquals(Integer.valueOf(1), Integer.valueOf(props.size()));
      assertEquals("[ size {2} [0] {first} [1] {second} ] ", props.get("list"));
   }


   @Test
   public void testlong() {
      Map<String, String> props = new HashMap<>();

      props = LoggingUtil.addLogging("simple", 1l, props);
      props = LoggingUtil.addLogging("array", new long[] {10l, 1l}, props);
      props = LoggingUtil.addLogging("arrayNull", (long[])null, props);

      assertNull(LoggingUtil.addLogging("nullProps", 1l, null));
      assertNull(LoggingUtil.addLogging("nullProps", new long[] {2l, 1l}, null));
      assertEquals(Integer.valueOf(2), Integer.valueOf(props.size()));
      assertEquals("1", props.get("simple"));
      assertEquals("[ size {2} [0] {10} [1] {1} ] ", props.get("array"));
   }


   @Test
   public void testLong() {
      Map<String, String> props = new HashMap<>();

      props = LoggingUtil.addLogging("simple", Long.valueOf(1), props);
      props = LoggingUtil.addLogging("array", new Long[] {Long.valueOf(10), Long.valueOf(1)}, props);
      props = LoggingUtil.addLogging("nullValue", (Long)null, props);
      props = LoggingUtil.addLogging("nullValue", (Long[])null, props);

      assertNull(LoggingUtil.addLogging("nullProps", Long.valueOf(1), null));
      assertNull(LoggingUtil.addLogging("nullProps", new Long[] {Long.valueOf(2), Long.valueOf(1)}, null));
      assertEquals(Integer.valueOf(2), Integer.valueOf(props.size()));
      assertEquals("1", props.get("simple"));
      assertEquals("[ size {2} [0] {10} [1] {1} ] ", props.get("array"));
   }


   @Test
   public void testMap() {
      final Map<String, String> list = new HashMap<>();
      list.put("firstName", "firstValue");
      list.put("secondName", "secondValue");

      Map<String, String> props = new HashMap<>();
      props = LoggingUtil.addLogging("list", list, props);
      props = LoggingUtil.addLogging("null", (List<?>)null, props);

      assertNull(LoggingUtil.addLogging("null", list, null));
      assertEquals(Integer.valueOf(1), Integer.valueOf(props.size()));
      assertEquals("[ size {2} [firstName] {firstValue} [secondName] {secondValue} ] ", props.get("list"));
   }


   @Test
   public void testMaxArrayLenght() {
      LoggingUtil.setMaxArrayLength(2);

      Map<String, String> props = new HashMap<>();

      final Map<Integer, String> map = new HashMap<>();
      map.put(Integer.valueOf(2), "first Entry");
      map.put(Integer.valueOf(5), "second Entry");
      map.put(Integer.valueOf(1), "third Entry");
      props = LoggingUtil.addLogging("Map", map, props);
      final String[] array = new String[3];
      array[0] = "first String";
      array[1] = "second String";
      array[2] = "third String";
      props = LoggingUtil.addLogging("Array", array, props);
      final List<Integer> list = new ArrayList<>();
      list.add(Integer.valueOf(10));
      list.add(Integer.valueOf(8));
      list.add(Integer.valueOf(25));
      props = LoggingUtil.addLogging("List", list, props);
      final Vector<String> vec = new Vector<>();
      vec.add("first Vector");
      vec.add("second Vector");
      vec.add("third Vector");
      props = LoggingUtil.addLogging("Vector", vec, props);

      assertEquals(
            "Array {[ size {3} [0] {first String} [1] {second String} ... ] } List {[ size {3} [0] {10} [1] {8} ... ] } Map {[ size {3} [1] {third Entry} [2] {first Entry} [5] {second Entry} ... ] } Vector {[ size {3} [0] {first Vector} [1] {second Vector} ... ] } ",
            LoggingUtil.getLogString(props));
   }


   @Test
   public void testObject() {
      Map<String, String> props = new HashMap<>();

      props = LoggingUtil.addLogging("simple", new LoggingTestObject("single"), props);
      props = LoggingUtil.addLogging("array", new LoggingTestObject[] {new LoggingTestObject("first"), new LoggingTestObject("second")}, props);
      props = LoggingUtil.addLogging("nullValue", (LoggingTestObject)null, props);
      props = LoggingUtil.addLogging("nullValue", (LoggingTestObject[])null, props);

      assertNull(LoggingUtil.addLogging("nullProps", new LoggingTestObject("single"), null));
      assertNull(
            LoggingUtil.addLogging("nullProps", new LoggingTestObject[] {new LoggingTestObject("first"), new LoggingTestObject("second")}, null));
      assertEquals(Integer.valueOf(2), Integer.valueOf(props.size()));
      assertEquals("Name {single} ", props.get("simple"));
      assertEquals("[ size {2} [0] {Name {first} } [1] {Name {second} } ] ", props.get("array"));
   }


   @Test
   public void testshort() {
      Map<String, String> props = new HashMap<>();

      props = LoggingUtil.addLogging("simple", (short)1, props);
      props = LoggingUtil.addLogging("array", new short[] {10, 1}, props);
      props = LoggingUtil.addLogging("arrayNull", (short[])null, props);

      assertNull(LoggingUtil.addLogging("nullProps", (short)1, null));
      assertNull(LoggingUtil.addLogging("nullProps", new short[] {(short)1, (short)0}, null));
      assertEquals(Integer.valueOf(2), Integer.valueOf(props.size()));
      assertEquals("1", props.get("simple"));
      assertEquals("[ size {2} [0] {10} [1] {1} ] ", props.get("array"));
   }


   @Test
   public void testShort() {
      Map<String, String> props = new HashMap<>();

      props = LoggingUtil.addLogging("simple", Short.valueOf("1"), props);
      props = LoggingUtil.addLogging("array", new Short[] {Short.valueOf("10"), Short.valueOf("1")}, props);
      props = LoggingUtil.addLogging("nullValue", (Short)null, props);
      props = LoggingUtil.addLogging("nullValue", (Short[])null, props);

      assertNull(LoggingUtil.addLogging("nullProps", Short.valueOf("1"), null));
      assertNull(LoggingUtil.addLogging("nullProps", new Short[] {Short.valueOf("3"), Short.valueOf("1")}, null));
      assertEquals(Integer.valueOf(2), Integer.valueOf(props.size()));
      assertEquals("1", props.get("simple"));
      assertEquals("[ size {2} [0] {10} [1] {1} ] ", props.get("array"));
   }


   @Test
   public void testString() {
      Map<String, String> props = new HashMap<>();

      props = LoggingUtil.addLogging("simple", "1", props);
      props = LoggingUtil.addLogging("array", new String[] {"10", "1"}, props);
      props = LoggingUtil.addLogging("nullValue", (String)null, props);
      props = LoggingUtil.addLogging("nullValue", (String[])null, props);

      assertNull(LoggingUtil.addLogging("nullProps", "1", null));
      assertNull(LoggingUtil.addLogging("nullProps", new String[] {"3", "1"}, null));
      assertEquals(Integer.valueOf(2), Integer.valueOf(props.size()));
      assertEquals("1", props.get("simple"));
      assertEquals("[ size {2} [0] {10} [1] {1} ] ", props.get("array"));
   }


   @Test
   public void testVector() {
      final Vector<String> list = new Vector<>();
      list.add("first");
      list.add("second");

      Map<String, String> props = new HashMap<>();
      props = LoggingUtil.addLogging("list", list, props);
      props = LoggingUtil.addLogging("null", (List<?>)null, props);

      assertNull(LoggingUtil.addLogging("null", list, null));
      assertEquals(Integer.valueOf(1), Integer.valueOf(props.size()));
      assertEquals("[ size {2} [0] {first} [1] {second} ] ", props.get("list"));
   }
}

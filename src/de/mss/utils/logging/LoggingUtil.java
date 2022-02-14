package de.mss.utils.logging;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

import de.mss.utils.DateTimeFormat;
import de.mss.utils.DateTimeTools;

public class LoggingUtil {

   private static int maxArrayLength = 2000;

   public static Map<String, String> addLogging(String name, BigDecimal val, Map<String, String> props) {
      if (props == null || val == null) {
         return props;
      }

      props.put(name, val.toString());

      return props;
   }


   public static Map<String, String> addLogging(String name, BigDecimal[] val, Map<String, String> props) {
      if (props == null || val == null) {
         return props;
      }

      props.put(name, getValue(val));

      return props;
   }


   public static Map<String, String> addLogging(String name, BigInteger val, Map<String, String> props) {
      if (props == null || val == null) {
         return props;
      }

      props.put(name, val.toString());

      return props;
   }


   public static Map<String, String> addLogging(String name, BigInteger[] val, Map<String, String> props) {
      if (props == null || val == null) {
         return props;
      }

      props.put(name, getValue(val));

      return props;
   }


   public static Map<String, String> addLogging(String name, boolean val, Map<String, String> props) {
      if (props == null) {
         return props;
      }

      props.put(name, getValue(Boolean.valueOf(val)));

      return props;
   }


   public static Map<String, String> addLogging(String name, Boolean val, Map<String, String> props) {
      if (props == null || val == null) {
         return props;
      }

      props.put(name, val.toString());

      return props;
   }


   public static Map<String, String> addLogging(String name, boolean[] val, Map<String, String> props) {
      if (props == null || val == null) {
         return props;
      }

      final Boolean[] v = new Boolean[val.length];
      for (int i = 0; i < val.length; i++ ) {
         v[i] = Boolean.valueOf(val[i]);
      }

      props.put(name, getValue(v));

      return props;
   }


   public static Map<String, String> addLogging(String name, Boolean[] val, Map<String, String> props) {
      if (props == null || val == null) {
         return props;
      }

      props.put(name, getValue(val));

      return props;
   }


   public static Map<String, String> addLogging(String name, byte val, Map<String, String> props) {
      if (props == null) {
         return props;
      }

      props.put(name, getValue(Byte.valueOf(val)));

      return props;
   }


   public static Map<String, String> addLogging(String name, Byte val, Map<String, String> props) {
      if (props == null || val == null) {
         return props;
      }

      props.put(name, val.toString());

      return props;
   }


   public static Map<String, String> addLogging(String name, byte[] val, Map<String, String> props) {
      if (props == null || val == null) {
         return props;
      }

      final Byte[] v = new Byte[val.length];
      for (int i = 0; i < val.length; i++ ) {
         v[i] = Byte.valueOf(val[i]);
      }

      props.put(name, getValue(v));

      return props;
   }


   public static Map<String, String> addLogging(String name, Byte[] val, Map<String, String> props) {
      if (props == null || val == null) {
         return props;
      }

      props.put(name, getValue(val));

      return props;
   }


   public static Map<String, String> addLogging(String name, Date val, Map<String, String> props) {
      if (props == null || val == null) {
         return props;
      }

      props.put(name, getValue(val));

      return props;
   }


   public static Map<String, String> addLogging(String name, Date[] val, Map<String, String> props) {
      if (props == null || val == null) {
         return props;
      }

      props.put(name, getValue(val));

      return props;
   }


   public static Map<String, String> addLogging(String name, double val, Map<String, String> props) {
      if (props == null) {
         return props;
      }

      props.put(name, getValue(Double.valueOf(val)));

      return props;
   }


   public static Map<String, String> addLogging(String name, Double val, Map<String, String> props) {
      if (props == null || val == null) {
         return props;
      }

      props.put(name, val.toString());

      return props;
   }


   public static Map<String, String> addLogging(String name, double[] val, Map<String, String> props) {
      if (props == null || val == null) {
         return props;
      }

      final Double[] v = new Double[val.length];
      for (int i = 0; i < val.length; i++ ) {
         v[i] = Double.valueOf(val[i]);
      }

      props.put(name, getValue(v));

      return props;
   }


   public static Map<String, String> addLogging(String name, Double[] val, Map<String, String> props) {
      if (props == null || val == null) {
         return props;
      }

      props.put(name, getValue(val));

      return props;
   }


   public static Map<String, String> addLogging(String name, float val, Map<String, String> props) {
      if (props == null) {
         return props;
      }

      props.put(name, getValue(Float.valueOf(val)));

      return props;
   }


   public static Map<String, String> addLogging(String name, Float val, Map<String, String> props) {
      if (props == null || val == null) {
         return props;
      }

      props.put(name, val.toString());

      return props;
   }


   public static Map<String, String> addLogging(String name, float[] val, Map<String, String> props) {
      if (props == null || val == null) {
         return props;
      }

      final Float[] v = new Float[val.length];
      for (int i = 0; i < val.length; i++ ) {
         v[i] = Float.valueOf(val[i]);
      }

      props.put(name, getValue(v));

      return props;
   }


   public static Map<String, String> addLogging(String name, Float[] val, Map<String, String> props) {
      if (props == null || val == null) {
         return props;
      }

      props.put(name, getValue(val));

      return props;
   }


   public static Map<String, String> addLogging(String name, int val, Map<String, String> props) {
      if (props == null) {
         return props;
      }

      props.put(name, getValue(Integer.valueOf(val)));

      return props;
   }


   public static Map<String, String> addLogging(String name, int[] val, Map<String, String> props) {
      if (props == null || val == null) {
         return props;
      }

      final Integer[] v = new Integer[val.length];
      for (int i = 0; i < val.length; i++ ) {
         v[i] = Integer.valueOf(val[i]);
      }

      props.put(name, getValue(v));

      return props;
   }


   public static Map<String, String> addLogging(String name, Integer val, Map<String, String> props) {
      if (props == null || val == null) {
         return props;
      }

      props.put(name, val.toString());

      return props;
   }


   public static Map<String, String> addLogging(String name, Integer[] val, Map<String, String> props) {
      if (props == null || val == null) {
         return props;
      }

      props.put(name, getValue(val));

      return props;
   }


   public static Map<String, String> addLogging(String name, List<?> val, Map<String, String> props) {
      if (props == null || val == null) {
         return props;
      }

      props.put(name, getValue(val));

      return props;
   }


   public static Map<String, String> addLogging(String name, long val, Map<String, String> props) {
      if (props == null) {
         return props;
      }

      props.put(name, getValue(Long.valueOf(val)));

      return props;
   }


   public static Map<String, String> addLogging(String name, Long val, Map<String, String> props) {
      if (props == null || val == null) {
         return props;
      }

      props.put(name, val.toString());

      return props;
   }


   public static Map<String, String> addLogging(String name, long[] val, Map<String, String> props) {
      if (props == null || val == null) {
         return props;
      }

      final Long[] v = new Long[val.length];
      for (int i = 0; i < val.length; i++ ) {
         v[i] = Long.valueOf(val[i]);
      }

      props.put(name, getValue(v));

      return props;
   }


   public static Map<String, String> addLogging(String name, Long[] val, Map<String, String> props) {
      if (props == null || val == null) {
         return props;
      }

      props.put(name, getValue(val));

      return props;
   }


   public static Map<String, String> addLogging(String name, Map<?, ?> val, Map<String, String> props) {
      if (props == null || val == null) {
         return props;
      }

      props.put(name, getValue(val));

      return props;
   }


   public static Map<String, String> addLogging(String name, Object val, Map<String, String> props) {
      if (props == null || val == null) {
         return props;
      }

      props.put(name, val.toString());

      return props;
   }


   public static Map<String, String> addLogging(String name, Object[] val, Map<String, String> props) {
      if (props == null || val == null) {
         return props;
      }

      props.put(name, getValue(val));

      return props;
   }


   public static Map<String, String> addLogging(String name, short val, Map<String, String> props) {
      if (props == null) {
         return props;
      }

      props.put(name, getValue(Short.valueOf(val)));

      return props;
   }


   public static Map<String, String> addLogging(String name, Short val, Map<String, String> props) {
      if (props == null || val == null) {
         return props;
      }

      props.put(name, val.toString());

      return props;
   }


   public static Map<String, String> addLogging(String name, short[] val, Map<String, String> props) {
      if (props == null || val == null) {
         return props;
      }

      final Short[] v = new Short[val.length];
      for (int i = 0; i < val.length; i++ ) {
         v[i] = Short.valueOf(val[i]);
      }

      props.put(name, getValue(v));

      return props;
   }


   public static Map<String, String> addLogging(String name, Short[] val, Map<String, String> props) {
      if (props == null || val == null) {
         return props;
      }

      props.put(name, getValue(val));

      return props;
   }


   public static Map<String, String> addLogging(String name, String val, Map<String, String> props) {
      if (props == null || val == null) {
         return props;
      }

      props.put(name, val.toString());

      return props;
   }


   public static Map<String, String> addLogging(String name, String[] val, Map<String, String> props) {
      if (props == null || val == null) {
         return props;
      }

      props.put(name, getValue(val));

      return props;
   }


   public static Map<String, String> addLogging(String name, Vector<?> val, Map<String, String> props) {
      if (props == null || val == null) {
         return props;
      }

      props.put(name, getValue(val));

      return props;
   }


   public static String getLogString(Map<String, String> props) {
      if (props == null || props.isEmpty()) {
         return "";
      }

      final StringBuilder sb = new StringBuilder();

      final SortedSet<String> keys = new TreeSet<>(props.keySet());

      for (final String key : keys) {
         sb.append(key + " {" + props.get(key) + "} ");
      }

      return sb.toString();
   }


   private static String getValue(List<?> val) {
      final StringBuilder sb = new StringBuilder("[ size {" + val.size() + "} ");

      int idx = 0;
      for (final Object v : val) {
         sb.append("[" + idx + "] {" + getValue(v) + "} ");
         idx++ ;
         if (idx >= maxArrayLength) {
            break;
         }
      }
      if (val.size() > maxArrayLength) {
         sb.append("... ");
      }

      sb.append("] ");

      return sb.toString();
   }


   private static String getValue(Map<?, ?> val) {
      final StringBuilder sb = new StringBuilder("[ size {" + val.size() + "} ");

      int idx = 0;
      for (final Entry<?, ?> v : val.entrySet()) {
         sb.append("[" + getValue(v.getKey()) + "] {" + getValue(v.getValue()) + "} ");
         idx++ ;
         if (idx > maxArrayLength) {
            break;
         }
      }
      if (val.size() > maxArrayLength) {
         sb.append("... ");
      }


      sb.append("] ");

      return sb.toString();
   }


   private static String getValue(Object v) {
      if (Date.class.isInstance(v)) {
         return DateTimeTools.formatDate((Date)v, DateTimeFormat.DATE_TIMESTAMP_FORMAT_UTC);
      }
      return v.toString();
   }


   private static String getValue(Object[] val) {
      final StringBuilder sb = new StringBuilder("[ size {" + val.length + "} ");

      int len = val.length;
      if (len > maxArrayLength) {
         len = maxArrayLength;
      }

      for (int i = 0; i < len; i++ ) {
         sb.append("[" + i + "] {" + getValue(val[i]) + "} ");
      }

      if (val.length > maxArrayLength) {
         sb.append("... ");
      }

      sb.append("] ");
      return sb.toString();
   }


   private static String getValue(Vector<?> val) {
      final StringBuilder sb = new StringBuilder("[ size {" + val.size() + "} ");

      int idx = 0;
      for (final Object v : val) {
         sb.append("[" + idx + "] {" + getValue(v) + "} ");
         idx++ ;
         if (idx >= maxArrayLength) {
            break;
         }
      }

      if (val.size() > maxArrayLength) {
         sb.append("... ");
      }

      sb.append("] ");

      return sb.toString();
   }


   public static void setMaxArrayLength(int ml) {
      maxArrayLength = ml;
   }


   private LoggingUtil() {}
}

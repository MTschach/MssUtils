package de.mss.utils;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;
import java.util.function.Supplier;

public class Tools {

   public static Double conBigDecimal2Double(BigDecimal value) {
      return value == null ? null : Double.valueOf(value.doubleValue());
   }


   public static Float conBigDecimal2Float(BigDecimal value) {
      return value == null ? null : Float.valueOf(value.floatValue());
   }


   public static Integer conBigDecimal2Integer(BigDecimal value) {
      return value == null ? null : Integer.valueOf(value.intValue());
   }


   public static double conBigDecimal2PrimitiveDouble(BigDecimal value) {
      return value == null ? 0 : value.doubleValue();
   }


   public static float conBigDecimal2PrimitiveFloat(BigDecimal value) {
      return value == null ? 0 : value.floatValue();
   }


   public static int conBigDecimal2PrimitiveInteger(BigDecimal value) {
      return value == null ? 0 : value.intValue();
   }


   public static BigDecimal conDouble2BigDecimal(Double value) {
      return value == null ? null : BigDecimal.valueOf(value.doubleValue());
   }


   public static Float conDouble2Float(Double value) {
      return value == null ? null : Float.valueOf(value.floatValue());
   }


   public static Integer conDouble2Integer(Double value) {
      return value == null ? null : Integer.valueOf(value.intValue());
   }


   public static float conDouble2PrimitiveFloat(Double value) {
      return value == null ? 0 : value.floatValue();
   }


   public static int conDouble2PrimitiveInteger(Double value) {
      return value == null ? 0 : value.intValue();
   }


   public static BigDecimal conFloat2BigDecimal(Float value) {
      return value == null ? null : BigDecimal.valueOf(value.doubleValue());
   }


   public static Double conFloat2Double(Float value) {
      return value == null ? null : Double.valueOf(value.doubleValue());
   }


   public static Integer conFloat2Integer(Float value) {
      return value == null ? null : Integer.valueOf(value.intValue());
   }


   public static double conFloat2PrimitiveDouble(Float value) {
      return value == null ? 0 : value.doubleValue();
   }


   public static int conFloat2PrimitiveInteger(Float value) {
      return value == null ? 0 : value.intValue();
   }


   public static BigDecimal conInteger2BigDecimal(Integer value) {
      return value == null ? null : BigDecimal.valueOf(value.intValue());
   }


   public static Double conInteger2Double(Integer value) {
      return value == null ? null : Double.valueOf(value.doubleValue());
   }


   public static Float conInteger2Float(Integer value) {
      return value == null ? null : Float.valueOf(value.floatValue());
   }


   public static double conInteger2PrimitiveDouble(Integer value) {
      return value == null ? 0 : value.doubleValue();
   }


   public static float conInteger2PrimitiveFloat(Integer value) {
      return value == null ? 0 : value.floatValue();
   }


   public static void doNullLog(@SuppressWarnings("unused") Exception e) {
      // Exception is not logged
   }


   public static void doNullLog(@SuppressWarnings("unused") Throwable e) {
      // Exception is not logged
   }


   public static String formatLoggingId(String loggingId) {
      return "<" + loggingId + "> ";
   }


   public static <T extends Exception> String getHash(String algo, String stringToHash, Supplier<T> throwException) throws T {

      MessageDigest md;
      try {
         md = MessageDigest.getInstance(algo);
         md.update(stringToHash.getBytes());

         final StringBuilder hash = new StringBuilder();
         final byte[] hashData = md.digest();
         for (final byte b : hashData) {
            final String h = Integer.toHexString(b & 0xff);
            if (h.length() == 1) {
               hash.append("0");
            }
            hash.append(h);
         }

         return hash.toString();
      }
      catch (final NoSuchAlgorithmException e) {
         if (throwException != null) {
            final T ex = throwException.get();
            ex.initCause(e);
            throw ex;
         }
      }

      return null;
   }


   public static String getId(String prefix) {
      return prefix + UUID.randomUUID().toString();
   }


   public static String getId(Throwable prefix) {
      return getId(prefix.getStackTrace()[0].getMethodName() + "_");
   }


   public static boolean isEmpty(List<?> l) {
      return (l == null) || l.isEmpty();
   }


   public static boolean isEmpty(Map<?, ?> m) {
      return (m == null) || m.isEmpty();
   }


   public static boolean isEmpty(Vector<?> v) {
      return (v == null) || v.isEmpty();
   }


   public static boolean isFalse(Boolean b) {
      return (b != null) && (Boolean.FALSE.compareTo(b) == 0);
   }


   public static boolean isFalse(String s) {
      final String[] falses = {"0", "false", "n", "nein", "no"};
      if (s == null) {
         return false;
      }

      for (final String f : falses) {
         if (f.equalsIgnoreCase(s)) {
            return true;
         }
      }


      return false;
   }


   public static boolean isSet(byte[] b) {
      return (b != null) && (b.length > 0);
   }


   public static boolean isSet(Object[] o) {
      return (o != null) && (o.length > 0);
   }


   public static boolean isSet(String s) {
      return (s != null) && (s.length() > 0);
   }


   public static boolean isTrue(Boolean b) {
      return (b != null) && (Boolean.TRUE.compareTo(b) == 0);
   }


   public static boolean isTrue(String s) {
      final String[] trues = {"1", "true", "j", "ja", "yes"};
      if (s == null) {
         return false;
      }

      for (final String f : trues) {
         if (f.equalsIgnoreCase(s)) {
            return true;
         }
      }

      return false;
   }


   private Tools() {}


}

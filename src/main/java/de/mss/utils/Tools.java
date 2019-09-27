package de.mss.utils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import de.mss.utils.exception.Error;
import de.mss.utils.exception.ErrorCodes;
import de.mss.utils.exception.MssException;

public class Tools {

   public Tools() throws MssException {
      throw new MssException(
            new Error(
                  ErrorCodes.ERROR_NOT_INSTANCABLE.getErrorCode(),
                  ErrorCodes.ERROR_NOT_INSTANCABLE.getErrorText() + " (" + getClass().getName() + ")"));
   }


   public static String getId(String prefix) {
      return prefix + UUID.randomUUID().toString();
   }


   public static String getId(Throwable prefix) {
      return getId(prefix.getStackTrace()[0].getMethodName() + "_");
   }


   public static boolean isFalse(Boolean b) {
      return (b != null && Boolean.FALSE.compareTo(b) == 0);
   }


   public static boolean isFalse(String s) {
      String[] falses = {"0", "false", "n", "nein", "no"};
      if (s == null)
         return false;

      for (String f : falses) {
         if (f.equalsIgnoreCase(s))
            return true;
      }


      return false;
   }


   public static boolean isTrue(Boolean b) {
      return (b != null && Boolean.TRUE.compareTo(b) == 0);
   }


   public static boolean isTrue(String s) {
      String[] trues = {"1", "true", "j", "ja", "yes"};
      if (s == null)
         return false;

      for (String f : trues) {
         if (f.equalsIgnoreCase(s))
            return true;
      }

      return false;
   }


   public static boolean isEmpty(Map<?, ?> m) {
      return m == null || m.isEmpty();
   }


   public static boolean isEmpty(List<?> l) {
      return l == null || l.isEmpty();
   }


   public static boolean isEmpty(Vector<?> v) {
      return v == null || v.isEmpty();
   }


   public static boolean isSet(byte[] b) {
      return b != null && b.length > 0;
   }


   public static boolean isSet(Object[] o) {
      return o != null && o.length > 0;
   }


   public static boolean isSet(String s) {
      return s != null && s.length() > 0;
   }


   public static void doNullLog(Exception e) {
      LogManager.getLogger("default").log(Level.OFF, "Error", e);
   }


   public static String formatLoggingId(String loggingId) {
      return "<" + loggingId + "> ";
   }


   public static Integer conBigDecimal2Integer(BigDecimal value) {
      return (value == null ? null : Integer.valueOf(value.intValue()));
   }


   public static int conBigDecimal2PrimitiveInteger(BigDecimal value) {
      return (value == null ? 0 : value.intValue());
   }


   public static Double conBigDecimal2Double(BigDecimal value) {
      return (value == null ? null : Double.valueOf(value.doubleValue()));
   }


   public static double conBigDecimal2PrimitiveDouble(BigDecimal value) {
      return (value == null ? 0 : value.doubleValue());
   }


   public static Float conBigDecimal2Float(BigDecimal value) {
      return (value == null ? null : Float.valueOf(value.floatValue()));
   }


   public static float conBigDecimal2PrimitiveFloat(BigDecimal value) {
      return (value == null ? 0 : value.floatValue());
   }


   public static BigDecimal conInteger2BigDecimal(Integer value) {
      return (value == null ? null : BigDecimal.valueOf(value.intValue()));
   }


   public static Double conInteger2Double(Integer value) {
      return (value == null ? null : Double.valueOf(value.doubleValue()));
   }


   public static double conInteger2PrimitiveDouble(Integer value) {
      return (value == null ? 0 : value.doubleValue());
   }


   public static Float conInteger2Float(Integer value) {
      return (value == null ? null : Float.valueOf(value.floatValue()));
   }


   public static float conInteger2PrimitiveFloat(Integer value) {
      return (value == null ? 0 : value.floatValue());
   }


   public static BigDecimal conDouble2BigDecimal(Double value) {
      return (value == null ? null : BigDecimal.valueOf(value.doubleValue()));
   }


   public static Integer conDouble2Integer(Double value) {
      return (value == null ? null : Integer.valueOf(value.intValue()));
   }


   public static int conDouble2PrimitiveInteger(Double value) {
      return (value == null ? 0 : value.intValue());
   }


   public static Float conDouble2Float(Double value) {
      return (value == null ? null : Float.valueOf(value.floatValue()));
   }


   public static float conDouble2PrimitiveFloat(Double value) {
      return (value == null ? 0 : value.floatValue());
   }


   public static BigDecimal conFloat2BigDecimal(Float value) {
      return (value == null ? null : BigDecimal.valueOf(value.doubleValue()));
   }


   public static Integer conFloat2Integer(Float value) {
      return (value == null ? null : Integer.valueOf(value.intValue()));
   }


   public static int conFloat2PrimitiveInteger(Float value) {
      return (value == null ? 0 : value.intValue());
   }


   public static Double conFloat2Double(Float value) {
      return (value == null ? null : Double.valueOf(value.doubleValue()));
   }


   public static double conFloat2PrimitiveDouble(Float value) {
      return (value == null ? 0 : value.doubleValue());
   }


}

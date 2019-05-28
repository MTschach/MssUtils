package de.mss.utils;

import java.util.UUID;

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
      String[] falses = {"1", "true", "j", "ja", "yes"};
      if (s == null)
         return false;

      for (String f : falses) {
         if (f.equalsIgnoreCase(s))
            return true;
      }

      return false;
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

}

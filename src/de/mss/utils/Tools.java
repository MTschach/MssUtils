package de.mss.utils;

import java.util.UUID;

public class Tools {

   public static String getId(String prefix) {
      return prefix + UUID.randomUUID().toString();
   }


   public static String getId(Throwable prefix) {
      return getId(prefix.getStackTrace()[0].getMethodName() + "_");
   }


   public static boolean isSet(String s) {
      return s != null && s.length() > 0;
   }


}

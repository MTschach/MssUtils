package de.mss.utils;


public class SyntaxChecker {

   private SyntaxChecker() {}

   public static boolean checkEmail(String email) {
      return check(email, "^(\\w+[-_\\.])*\\w+\\@(\\w+[-_\\.])*\\w+\\.\\w{2,}+$");
   }


   public static boolean checkPhone(String phone) {
      return check(phone.replace(" ", ""), "^(\\+\\d+){0,1}(\\(\\d+\\)){0,1}\\d+/{0,1}(\\d+-)*\\d+$");
   }

   public static boolean check(String value, String pattern) {
      if (!Tools.isSet(value) || !Tools.isSet(pattern))
         return false;

      return value.matches(pattern);
   }
}

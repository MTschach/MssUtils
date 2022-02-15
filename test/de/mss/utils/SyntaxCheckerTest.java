package de.mss.utils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class SyntaxCheckerTest {

   @Test
   public void testCheck() {
      assertFalse(SyntaxChecker.check(null, "\\w+"));
      assertFalse(SyntaxChecker.check("", "\\w+"));
      assertFalse(SyntaxChecker.check("value", null));
      assertFalse(SyntaxChecker.check("value", ""));
   }


   @Test
   public void testEmail() {
      assertTrue(SyntaxChecker.checkEmail("john.doe@gmail.com"));
      assertTrue(SyntaxChecker.checkEmail("johndoe79@gmail.com"));
      assertTrue(SyntaxChecker.checkEmail("ter-ra_at-lan.ti.ca@gmx-topmail-domain.net"));

      assertFalse(SyntaxChecker.checkEmail("John Doe"));
      assertFalse(SyntaxChecker.checkEmail("john.doe@gmail"));
      assertFalse(SyntaxChecker.checkEmail("john.doe@gmail."));
      assertFalse(SyntaxChecker.checkEmail("john.doe@gmail.d"));
      assertFalse(SyntaxChecker.checkEmail("john.doe@"));
      assertFalse(SyntaxChecker.checkEmail("@gmail.com"));
   }


   @Test
   public void testPhone() {
      assertTrue(SyntaxChecker.checkPhone("0375 81 92 87 65"));
      assertTrue(SyntaxChecker.checkPhone("0 375 / 81 92 87 - 65"));
      assertTrue(SyntaxChecker.checkPhone("+49 (0) 375 / 81 92 87 - 65"));
      assertTrue(SyntaxChecker.checkPhone("0049 375 / 81 92 87 - 65"));

      assertFalse(SyntaxChecker.checkPhone("+49 (0) 375 / 81 92 - a"));
   }
}

package de.mss.utils.exception;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ErrorTest {

   @Test
   public void testError() {
      Error e = new Error(1, "Testfehler");

      assertEquals("ErrorCode", 1, e.getErrorCode());
      assertEquals("ErrorText", "Testfehler", e.getErrorText());
      assertEquals("toString", "Error : 1(Testfehler)", e.toString());
   }


   @Test
   public void testErrorEquals() {
      Error e1 = new Error(1, "Testfehler");
      Error e2 = new Error(1, "Testfehler");

      assertTrue("Equals", e1.equals(e2));
   }
}

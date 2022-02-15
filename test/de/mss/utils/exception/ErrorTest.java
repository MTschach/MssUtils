package de.mss.utils.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ErrorTest {

   @Test
   public void testError() {
      final Error e = new Error(1, "Testfehler", Integer.valueOf(531));

      assertEquals(1, e.getErrorCode());
      assertEquals("Testfehler", e.getErrorText());
      assertEquals("Error : 1(Testfehler)", e.toString());
      assertEquals(Integer.valueOf(531), e.getStatusCode());
   }


   @Test
   public void testErrorEquals() {
      final Error e1 = new Error(1, "Testfehler");
      final Error e2 = new Error(1, "Testfehler");

      assertTrue(e1.equals(e2));

      assertFalse(e1.equals(null));
      assertFalse(e1.equals(new Error(2, "Testfehler2")));
   }
}

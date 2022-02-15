package de.mss.utils.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MssExceptionTest {

   protected void checkException(MssException e, Error expError, boolean throwable, String expErrorText, int expErrorCode) {
      assertEquals(expError.getErrorCode(), e.getError().getErrorCode());
      assertEquals(expError.getErrorText(), e.getError().getErrorText());

      assertEquals(expErrorCode, e.getAltErrorCode());

      if (expErrorText != null) {
         assertEquals(expErrorText, e.getAltErrorText());
      } else {
         assertNull(e.getAltErrorText());
      }

      if (throwable) {
         assertNotNull(e.getCause());
      } else {
         assertNull(e.getCause());
      }

      final StringBuilder expMsg = new StringBuilder("Error : ");
      if (expError != ErrorCodes.NO_ERROR) {
         expMsg.append(expError.getErrorCode() + "(" + (expErrorText == null ? expError.getErrorText() : expErrorText) + ")");
      } else {
         expMsg.append(expErrorCode + "(" + expErrorText + ")");
      }

      final String msg = e.toString();

      assertTrue(msg.startsWith(expMsg.toString()));

   }


   @Test
   public void testAltErrorCode() {
      final MssException e = new MssException(4);
      checkException(e, ErrorCodes.NO_ERROR, false, null, 4);
   }


   @Test
   public void testCodeText() {
      final MssException e = new MssException(5, "an error");
      checkException(e, ErrorCodes.NO_ERROR, false, "an error", 5);
   }


   @Test
   public void testErrorCode() {
      final MssException e = new MssException(
            new Error(ErrorCodes.ERROR_NOT_INSTANCABLE.getErrorCode(), ErrorCodes.ERROR_NOT_INSTANCABLE.getErrorText()));
      checkException(e, ErrorCodes.ERROR_NOT_INSTANCABLE, false, null, 0);
   }


   @Test
   public void testErrorCodeMessage() {
      final MssException e = new MssException(
            new Error(ErrorCodes.ERROR_NOT_INSTANCABLE.getErrorCode(), ErrorCodes.ERROR_NOT_INSTANCABLE.getErrorText()),
            "own errortext");
      checkException(e, ErrorCodes.ERROR_NOT_INSTANCABLE, false, "own errortext", 0);
   }


   @Test
   public void testErrorCodeThrowable() {
      final MssException e = new MssException(
            new Error(ErrorCodes.ERROR_NOT_INSTANCABLE.getErrorCode(), ErrorCodes.ERROR_NOT_INSTANCABLE.getErrorText()),
            new Throwable());
      checkException(e, ErrorCodes.ERROR_NOT_INSTANCABLE, true, null, 0);
   }


   @Test
   public void testErrorCodeThrowableMsg() {
      final MssException e = new MssException(
            new Error(ErrorCodes.ERROR_NOT_INSTANCABLE.getErrorCode(), ErrorCodes.ERROR_NOT_INSTANCABLE.getErrorText()),
            new Throwable(),
            "own errortext");
      checkException(e, ErrorCodes.ERROR_NOT_INSTANCABLE, true, "own errortext", 0);
   }


   @Test
   public void testGetErrorCode() {
      assertEquals(Integer.valueOf(2), new MssException(ErrorCodes.ERROR_INVALID_PARAM).getErrorCode());
      assertEquals(Integer.valueOf(3), new MssException(3).getErrorCode());
   }


   @Test
   public void testGetErrorText() {
      assertEquals("invalid parameter", new MssException(ErrorCodes.ERROR_INVALID_PARAM).getErrorText());
      assertEquals("an error", new MssException(ErrorCodes.ERROR_INVALID_PARAM, "an error").getErrorText());
   }


   @Test
   public void testThrowable() {
      final MssException e = new MssException(new Throwable());
      checkException(e, ErrorCodes.NO_ERROR, true, null, 0);
   }


   @Test
   public void testThrowableMsg() {
      final MssException e = new MssException(new Throwable(), "own errortext");
      checkException(e, ErrorCodes.NO_ERROR, true, "own errortext", 0);
   }
}

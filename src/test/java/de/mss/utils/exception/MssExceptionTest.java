package de.mss.utils.exception;

import org.junit.Test;

import junit.framework.TestCase;

public class MssExceptionTest extends TestCase {

   @Test
   public void testErrorCode() {
      MssException e = new MssException(new Error(ErrorCodes.ERROR_NOT_INSTANCABLE.getErrorCode(), ErrorCodes.ERROR_NOT_INSTANCABLE.getErrorText()));
      checkException(e, ErrorCodes.ERROR_NOT_INSTANCABLE, false, null, 0);
   }


   @Test
   public void testErrorCodeThrowable() {
      MssException e = new MssException(
            new Error(ErrorCodes.ERROR_NOT_INSTANCABLE.getErrorCode(), ErrorCodes.ERROR_NOT_INSTANCABLE.getErrorText()),
            new Throwable());
      checkException(e, ErrorCodes.ERROR_NOT_INSTANCABLE, true, null, 0);
   }


   @Test
   public void testErrorCodeMessage() {
      MssException e = new MssException(
            new Error(ErrorCodes.ERROR_NOT_INSTANCABLE.getErrorCode(), ErrorCodes.ERROR_NOT_INSTANCABLE.getErrorText()),
            "own errortext");
      checkException(e, ErrorCodes.ERROR_NOT_INSTANCABLE, false, "own errortext", 0);
   }


   @Test
   public void testAltErrorCode() {
      MssException e = new MssException(4);
      checkException(e, ErrorCodes.NO_ERROR, false, null, 4);
   }


   @Test
   public void testThrowable() {
      MssException e = new MssException(new Throwable());
      checkException(e, ErrorCodes.NO_ERROR, true, null, 0);
   }


   @Test
   public void testCodeText() {
      MssException e = new MssException(5, "an error");
      checkException(e, ErrorCodes.NO_ERROR, false, "an error", 5);
   }


   protected void checkException(MssException e, Error expError, boolean throwable, String expErrorText, int expErrorCode) {
      assertEquals("ErrorCode", expError.getErrorCode(), e.getError().getErrorCode());
      assertEquals("ErrorText", expError.getErrorText(), e.getError().getErrorText());

      assertEquals("Alt ErrorCode", expErrorCode, e.getAltErrorCode());

      if (expErrorText != null)
         assertEquals("Alt Error text", expErrorText, e.getAltErrorText());
      else
         assertNull("Alt ErrorText", e.getAltErrorText());

      if (throwable)
         assertNotNull("Cause not null", e.getCause());
      else
         assertNull("Cause is null", e.getCause());

      StringBuilder expMsg = new StringBuilder("Error : ");
      if (expError != ErrorCodes.NO_ERROR) {
         expMsg.append(expError.getErrorCode() + "(" + (expErrorText == null ? expError.getErrorText() : expErrorText) + ")");
      }
      else {
         expMsg.append(expErrorCode + "(" + expErrorText + ")");
      }

      String msg = e.toString();

      assertTrue("toString", msg.startsWith(expMsg.toString()));

   }
}

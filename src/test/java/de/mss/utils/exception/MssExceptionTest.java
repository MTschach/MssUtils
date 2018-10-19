package de.mss.utils.exception;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class MssExceptionTest {

   @Test
   public void testMssException() {
      MssException e = new MssException(new Error(ErrorCodes.ERROR_NOT_INSTANCABLE.getErrorCode(), ErrorCodes.ERROR_NOT_INSTANCABLE.getErrorText()));
      assertEquals("ErrorCode", ErrorCodes.ERROR_NOT_INSTANCABLE.getErrorCode(), e.getError().getErrorCode());
      assertEquals("ErrorText", ErrorCodes.ERROR_NOT_INSTANCABLE.getErrorText(), e.getError().getErrorText());
      assertEquals("Alt ErrorCode", 0, e.getAltErrorCode());
      assertNull("Alt ErrorText", e.getAltErrorText());
   }
}

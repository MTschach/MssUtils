package de.mss.utils.exception;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class ErrorCodesTest {

   @Test
   public void testErrorCodes() {
      try {
         new ErrorCodes();
         fail();
      }
      catch (MssException e) {
         assertEquals("ErrorCode", ErrorCodes.ERROR_NOT_INSTANCABLE.getErrorCode(), e.getError().getErrorCode());
      }
   }
}

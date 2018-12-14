package de.mss.utils.db;

import org.junit.Test;

import de.mss.utils.exception.Error;
import de.mss.utils.exception.ErrorCodes;
import de.mss.utils.exception.MssExceptionTest;

public class DBExceptionTest extends MssExceptionTest {


   @Test
   public void testError() {
      DBException e = new DBException(new Error(ErrorCodes.ERROR_NOT_INSTANCABLE.getErrorCode(), ErrorCodes.ERROR_NOT_INSTANCABLE.getErrorText()));

      checkException(e, ErrorCodes.ERROR_NOT_INSTANCABLE, false, null, 0);
   }


   @Test
   public void testErrorThrowable() {
      DBException e = new DBException(
            new Error(ErrorCodes.ERROR_NOT_INSTANCABLE.getErrorCode(), ErrorCodes.ERROR_NOT_INSTANCABLE.getErrorText()),
            new Throwable());

      checkException(e, ErrorCodes.ERROR_NOT_INSTANCABLE, true, null, 0);
   }


   @Test
   public void testErrorString() {
      DBException e = new DBException(
            new Error(ErrorCodes.ERROR_NOT_INSTANCABLE.getErrorCode(), ErrorCodes.ERROR_NOT_INSTANCABLE.getErrorText()),
            "an error");

      checkException(e, ErrorCodes.ERROR_NOT_INSTANCABLE, false, "an error", 0);
   }


   @Override
   @Test
   public void testErrorCode() {
      DBException e = new DBException(2);

      checkException(e, ErrorCodes.NO_ERROR, false, null, 2);
   }


   @Override
   @Test
   public void testThrowable() {
      DBException e = new DBException(new Throwable());

      checkException(e, ErrorCodes.NO_ERROR, true, null, 0);
   }


   @Test
   public void testErrorCodeMsg() {
      DBException e = new DBException(2, "an error");

      checkException(e, ErrorCodes.NO_ERROR, false, "an error", 2);
   }
}

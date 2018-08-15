package de.mss.utils.exception;


public class ErrorCodes {

   public static final Error NO_ERROR                      = new Error(0, "no error");
   public static final Error ERROR_NOT_INSTANCABLE         = new Error(1, "could not create an instance of this class");

   public static final Error ERROR_DB_DRIVER_NOT_SUPPORTED = new Error(1001, "Driver is not supported");
   public static final Error ERROR_DB_NO_SERVER_TO_CONNECT = new Error(1002, "no connectable server found");


   public ErrorCodes() throws MssException {
      throw new MssException(
            new Error(ERROR_NOT_INSTANCABLE.getErrorCode(), ERROR_NOT_INSTANCABLE.getErrorText() + " (" + getClass().getName() + ")"));
   }
}

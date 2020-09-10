package de.mss.utils.exception;


public class ErrorCodes {

   public static final Error NO_ERROR                             = new Error(0, "no error");
   public static final Error ERROR_NOT_INSTANCABLE                = new Error(1, "could not create an instance of this class");
   public static final Error ERROR_INVALID_PARAM                  = new Error(2, "invalid parameter");
   public static final Error ERROR_DATE_TIME_FORMAT_UNKNOWN       = new Error(3, "unknown date/time format");

   public static final Error ERROR_DB_DRIVER_NOT_SUPPORTED        = new Error(1001, "Driver is not supported");
   public static final Error ERROR_DB_NO_SERVER_TO_CONNECT        = new Error(1002, "no connectable server found");
   public static final Error ERROR_DB_NO_AVAILABLE_CONNECTION     = new Error(1003, "no available connection found");
   public static final Error ERROR_DB_POSSIBLE_DATA_INCONSISTENCE = new Error(1004, "possible data inconsistence");
   public static final Error ERROR_DB_CLOSE_FAILURE               = new Error(1005, "failure whie closing");
   public static final Error ERROR_DB_EXECUTE_QUERY_FAILURE       = new Error(1006, "failure while executing query");
   public static final Error ERROR_DB_EXECUTE_UPDATE_FAILURE      = new Error(1007, "failure while executing update");
   public static final Error ERROR_DB_RESUTSET_FAILURE            = new Error(1008, "failure while handling resut set");
   public static final Error ERROR_PROTOCOL_NOT_SUPPORTED         = new Error(1009, "the protocol is not supported");
   public static final Error ERROR_NOT_PARSABLE                   = new Error(1010, "the value is not parsable");
   public static final Error ERROR_UNABLE_TO_EXECUTE_REQUEST      = new Error(1011, "unable to execute therest request");


   private ErrorCodes() {}
}

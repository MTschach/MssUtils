package de.mss.utils.exception;


public class MssException extends Exception {

   private static final long serialVersionUID = -3480449385872006327L;

   protected Error           error            = ErrorCodes.NO_ERROR;
   protected int             altErrorCode     = 0;
   protected String          altErrorText     = null;


   public MssException(Error ec) {
      init(ec, 0, null, null);
   }


   public MssException(Error ec, Throwable t) {
      init(ec, 0, null, t);
   }


   public MssException(Error ec, Throwable t, String msg) {
      init(ec, 0, msg, t);
   }


   public MssException(Error ec, String msg) {
      init(ec, 0, msg, null);
   }


   public MssException(int code) {
      init(null, code, null, null);
   }


   public MssException(Throwable t) {
      init(null, 0, null, t);
   }


   public MssException(Throwable t, String msg) {
      init(null, 0, msg, t);
   }


   public MssException(int code, String msg) {
      init(null, code, msg, null);
   }


   protected void init(Error error, int altErrorCode, String msg, Throwable t) {
      if (error != null)
         this.error = error;

      this.altErrorCode = altErrorCode;
      this.altErrorText = msg;

      if (t != null)
         this.initCause(t);
   }


   public String toString() {
      StringBuilder sb = new StringBuilder();
      if (!ErrorCodes.NO_ERROR.equals(this.error))
         sb
               .append(
                     "Error : "
                           + this.error.getErrorCode()
                           + "("
                           + (this.altErrorText == null ? this.error.getErrorText() : this.altErrorText)
                           + ")");
      else
         sb.append("Error : " + this.altErrorCode + "(" + this.altErrorText + ")");

      sb.append(getStackTrace(this));

      return sb.toString();
   }


   protected String getStackTrace(Exception e) {
      StringBuilder sb = new StringBuilder();

      for (StackTraceElement trace : e.getStackTrace()) {
         sb.append(trace.toString() + "\n");
      }

      return sb.toString();
   }


   public Error getError() {
      return this.error;
   }


   public int getAltErrorCode() {
      return this.altErrorCode;
   }


   public String getAltErrorText() {
      return this.altErrorText;
   }

}

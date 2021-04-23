package de.mss.utils.exception;


public class Error {

   private int     errorCode  = 0;
   private String  errorText  = "";
   private Integer statusCode = null;


   public Error(int ec, String et) {
      this.errorCode = ec;
      this.errorText = et;
   }


   public Error(int ec, String et, Integer st) {
      this.errorCode = ec;
      this.errorText = et;
      this.statusCode = st;
   }


   public boolean equals(Error e) {
      return e != null && this.errorCode == e.getErrorCode();
   }


   public int getErrorCode() {
      return this.errorCode;
   }


   public String getErrorText() {
      return this.errorText;
   }


   public Integer getStatusCode() {
      return this.statusCode;
   }


   @Override
   public String toString() {
      return "Error : " + this.errorCode + "(" + this.errorText + ")";
   }

}

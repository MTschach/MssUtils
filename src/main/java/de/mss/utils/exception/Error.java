package de.mss.utils.exception;


public class Error {

   private int    errorCode = 0;
   private String errorText = "";


   public Error(int ec, String et) {
      this.errorCode = ec;
      this.errorText = et;
   }


   public int getErrorCode() {
      return this.errorCode;
   }


   public String getErrorText() {
      return this.errorText;
   }


   public boolean equals(Error e) {
      return (e != null && this.errorCode == e.getErrorCode());
   }


   public String toString() {
      return "Error : " + this.errorCode + "(" + this.errorText + ")";
   }

}

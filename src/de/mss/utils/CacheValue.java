package de.mss.utils;


public class CacheValue<V extends Object> implements java.io.Serializable {

   private static final long    serialVersionUID = -4662084284442242410L;

   private final java.util.Date validUntil;
   private final V              value;

   public CacheValue(V v, long timeout) {
      this.value = v;
      this.validUntil = new java.util.Date(new java.util.Date().getTime() + timeout);
   }


   public V getValue() {
      if (valid()) {
         return this.value;
      }

      return null;
   }


   private boolean valid() {
      return this.validUntil.after(new java.util.Date());
   }


   @Override
   public String toString() {
      return "Value {" + this.value + "}, ValidUntil {" + DateTimeTools.formatDate(this.validUntil, DateTimeFormat.DATE_TIMESTAMP_FORMAT_UTC) + "} ";
   }
}

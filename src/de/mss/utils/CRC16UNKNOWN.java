package de.mss.utils;


public class CRC16UNKNOWN {

   private int value;


   public CRC16UNKNOWN() {
      this.value = 0;
   }


   public CRC16UNKNOWN(int seed) {
      this.value = seed;
   }


   public void update(byte aByte) {
       int a;
       int b;

      a = aByte;
       for (int count = 7; count >=0; count--) {
           a = a << 1;
           b = (a >>> 8) & 1;
         if ((this.value & 0x8000) != 0) {
            this.value = ((this.value << 1) + b) ^ 0x1021;
           } else {
            this.value = (this.value << 1) + b;
           }
       }
      this.value = this.value & 0xffff;
       return;
   }
   
   
   public void update(byte[] bytes) {
      for (byte b : bytes)
         update(b);
   }
   
   
   public int getValue() {
      return this.value;
   }


   public void reset() {
      this.value = 0;
   }


   public void reset(int seed) {
      this.value = seed;
   }
}

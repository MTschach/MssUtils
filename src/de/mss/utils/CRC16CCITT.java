package de.mss.utils;

/*
 * Uses irreducible polynomial:  1 + x + x^5 + x^12 + x^16
 * 
 * 123456789 -> 29b1
 */
public class CRC16CCITT {

   private static final int POLYNOMINAL = 0x1021;

   private int              value       = 0xffff;

   public CRC16CCITT() {
      this.value = 0xffff;
   }


   public CRC16CCITT(int seed) {
      this.value = seed;
   }


   public void update(byte aByte) {
      for (int i = 0; i < 8; i++ ) {
         boolean bit = ((aByte >> (7 - i) & 1) == 1);
         boolean c15 = ((this.value >> 15 & 1) == 1);
         this.value <<= 1;
         if (c15 ^ bit) this.value ^= POLYNOMINAL;
      }
      this.value = this.value & 0xffff;
   }
   
   
   public void update(byte[] bytes) {
      for (byte b : bytes)
         update(b);
   }
   
   
   public int getValue() {
      return this.value;
   }


   public void reset() {
      this.value = 0xffff;
   }


   public void reset(int seed) {
      this.value = seed;
   }
}

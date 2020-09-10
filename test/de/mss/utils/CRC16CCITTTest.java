package de.mss.utils;

import org.junit.Test;

public class CRC16CCITTTest extends CRC16Test {

   @Override
   @Test
   public void testSeed0() {
      CRC16CCITT c = new CRC16CCITT();
      c.update(this.TESTSTRING.getBytes());

      assertEquals("CRC16", 0x29b1, c.getValue());
   }


   @Override
   @Test
   public void testSeed0AndReset() {
      CRC16CCITT c = new CRC16CCITT();
      c.update(this.TESTSTRING.getBytes());

      assertEquals("CRC16", 0x29b1, c.getValue());
      c.reset();
      assertEquals("CRC16 after reset", 0xffff, c.getValue());
   }


   @Override
   @Test
   public void testSeedNot0() {
      CRC16CCITT c = new CRC16CCITT(1);
      c.update(this.TESTSTRING.getBytes());

      assertEquals("CRC16", 30224, c.getValue());
   }


   @Override
   @Test
   public void testSeedNot0AndReset() {
      CRC16CCITT c = new CRC16CCITT(1);
      c.update(this.TESTSTRING.getBytes());

      assertEquals("CRC16", 30224, c.getValue());
      c.reset(1);
      assertEquals("CRC16 after reset", 1, c.getValue());
   }


}

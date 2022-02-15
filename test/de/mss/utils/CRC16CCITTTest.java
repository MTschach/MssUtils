package de.mss.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CRC16CCITTTest extends CRC16Test {

   @Override
   @Test
   public void testSeed0() {
      final CRC16CCITT c = new CRC16CCITT();
      c.update(this.TESTSTRING.getBytes());

      assertEquals(0x29b1, c.getValue());
   }


   @Override
   @Test
   public void testSeed0AndReset() {
      final CRC16CCITT c = new CRC16CCITT();
      c.update(this.TESTSTRING.getBytes());

      assertEquals(0x29b1, c.getValue());
      c.reset();
      assertEquals(0xffff, c.getValue());
   }


   @Override
   @Test
   public void testSeedNot0() {
      final CRC16CCITT c = new CRC16CCITT(1);
      c.update(this.TESTSTRING.getBytes());

      assertEquals(30224, c.getValue());
   }


   @Override
   @Test
   public void testSeedNot0AndReset() {
      final CRC16CCITT c = new CRC16CCITT(1);
      c.update(this.TESTSTRING.getBytes());

      assertEquals(30224, c.getValue());
      c.reset(1);
      assertEquals(1, c.getValue());
   }


}

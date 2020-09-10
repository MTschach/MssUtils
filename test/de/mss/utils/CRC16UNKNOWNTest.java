package de.mss.utils;

import org.junit.Test;

public class CRC16UNKNOWNTest extends CRC16Test {

   @Override
   @Test
   public void testSeed0() {
      CRC16UNKNOWN c = new CRC16UNKNOWN();
      c.update(this.TESTSTRING.getBytes());

      assertEquals("CRC16", 48879, c.getValue());
   }


   @Override
   @Test
   public void testSeed0AndReset() {
      CRC16UNKNOWN c = new CRC16UNKNOWN();
      c.update(this.TESTSTRING.getBytes());

      assertEquals("CRC16", 48879, c.getValue());
      c.reset();
      assertEquals("CRC16 after reset", 0, c.getValue());
   }


   @Override
   @Test
   public void testSeedNot0() {
      CRC16UNKNOWN c = new CRC16UNKNOWN(1);
      c.update(this.TESTSTRING.getBytes());

      assertEquals("CRC16", 63804, c.getValue());
   }


   @Override
   @Test
   public void testSeedNot0AndReset() {
      CRC16UNKNOWN c = new CRC16UNKNOWN(1);
      c.update(this.TESTSTRING.getBytes());

      assertEquals("CRC16", 63804, c.getValue());
      c.reset(1);
      assertEquals("CRC16 after reset", 1, c.getValue());
   }


}

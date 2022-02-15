package de.mss.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CRC16UNKNOWNTest extends CRC16Test {

   @Override
   @Test
   public void testSeed0() {
      final CRC16UNKNOWN c = new CRC16UNKNOWN();
      c.update(this.TESTSTRING.getBytes());

      assertEquals(48879, c.getValue());
   }


   @Override
   @Test
   public void testSeed0AndReset() {
      final CRC16UNKNOWN c = new CRC16UNKNOWN();
      c.update(this.TESTSTRING.getBytes());

      assertEquals(48879, c.getValue());
      c.reset();
      assertEquals(0, c.getValue());
   }


   @Override
   @Test
   public void testSeedNot0() {
      final CRC16UNKNOWN c = new CRC16UNKNOWN(1);
      c.update(this.TESTSTRING.getBytes());

      assertEquals(63804, c.getValue());
   }


   @Override
   @Test
   public void testSeedNot0AndReset() {
      final CRC16UNKNOWN c = new CRC16UNKNOWN(1);
      c.update(this.TESTSTRING.getBytes());

      assertEquals(63804, c.getValue());
      c.reset(1);
      assertEquals(1, c.getValue());
   }


}

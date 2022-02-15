package de.mss.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CRC16Test {

   protected final String TESTSTRING = "123456789";

   @Test
   public void testSeed0() {
      final CRC16 c = new CRC16();
      c.update(this.TESTSTRING.getBytes());

      assertEquals(47933, c.getValue());
   }


   @Test
   public void testSeed0AndReset() {
      final CRC16 c = new CRC16();
      c.update(this.TESTSTRING.getBytes());

      assertEquals(47933, c.getValue());
      c.reset();
      assertEquals(0, c.getValue());
   }


   @Test
   public void testSeedNot0() {
      final CRC16 c = new CRC16(1);
      c.update(this.TESTSTRING.getBytes());

      assertEquals(11056, c.getValue());
   }


   @Test
   public void testSeedNot0AndReset() {
      final CRC16 c = new CRC16(1);
      c.update(this.TESTSTRING.getBytes());

      assertEquals(11056, c.getValue());
      c.reset(1);
      assertEquals(1, c.getValue());
   }


}

package de.mss.utils;

import org.junit.Test;

import junit.framework.TestCase;

public class CRC16Test extends TestCase {

   protected final String TESTSTRING = "123456789";

   @Test
   public void testSeed0() {
      CRC16 c = new CRC16();
      c.update(this.TESTSTRING.getBytes());

      assertEquals("CRC16", 47933, c.getValue());
   }


   @Test
   public void testSeed0AndReset() {
      CRC16 c = new CRC16();
      c.update(this.TESTSTRING.getBytes());

      assertEquals("CRC16", 47933, c.getValue());
      c.reset();
      assertEquals("CRC16 after reset", 0, c.getValue());
   }


   @Test
   public void testSeedNot0() {
      CRC16 c = new CRC16(1);
      c.update(this.TESTSTRING.getBytes());

      assertEquals("CRC16", 11056, c.getValue());
   }


   @Test
   public void testSeedNot0AndReset() {
      CRC16 c = new CRC16(1);
      c.update(this.TESTSTRING.getBytes());

      assertEquals("CRC16", 11056, c.getValue());
      c.reset(1);
      assertEquals("CRC16 after reset", 1, c.getValue());
   }


}

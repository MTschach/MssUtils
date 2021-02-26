package de.mss.utils;

import org.junit.Test;

import junit.framework.TestCase;

public class CacheValueTest extends TestCase {

   private static final String TEST_VALUE = "Testvalue 1234567 abcdef";

   @Test
   public void test() {
      final CacheValue<String> cv = new CacheValue<>(TEST_VALUE, 1000l);

      assertEquals("Value", TEST_VALUE, cv.getValue());
      assertNotNull("ToString", cv.toString());

      try {
         Thread.sleep(1500l);
         assertNull("Value", cv.getValue());
      }
      catch (final InterruptedException e) {
         e.printStackTrace();
      }
   }
}

package de.mss.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class CacheValueTest {

   private static final String TEST_VALUE = "Testvalue 1234567 abcdef";

   @Test
   public void test() {
      final CacheValue<String> cv = new CacheValue<>(TEST_VALUE, 1000l);

      assertEquals(TEST_VALUE, cv.getValue());
      assertNotNull(cv.toString());

      try {
         Thread.sleep(1500l);
         assertNull(cv.getValue());
      }
      catch (final InterruptedException e) {
         e.printStackTrace();
      }
   }
}

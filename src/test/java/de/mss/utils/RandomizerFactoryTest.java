package de.mss.utils;

import java.util.Random;

import org.junit.Test;

import de.mss.utils.exception.MssException;
import junit.framework.TestCase;

public class RandomizerFactoryTest extends TestCase {

   @Override
   public void tearDown() {
      RandomizerFactory.closeAllInstances();
   }


   @SuppressWarnings("unused")
   @Test
   public void testRandomizerFactoryTest() {
      try {
         new RandomizerFactory();
         fail();
      }
      catch (MssException e) {
         assertEquals("ErrorCode", 1, e.getError().getErrorCode());
      }
   }


   @Test
   public void testCreateInstance() {
      Random r = new Random();
      Random r1 = RandomizerFactory.createInstance("test1", r);

      assertEquals("Same", r, r1);
   }


   @Test
   public void testCreateInstance2() {
      Random r1 = new Random();
      RandomizerFactory.createInstance("test1", r1);
      Random r2 = new Random();
      RandomizerFactory.createInstance("test2", r2);

      Random r = RandomizerFactory.createInstance("test1", r2);

      assertEquals("Same", r1, r);
   }

}

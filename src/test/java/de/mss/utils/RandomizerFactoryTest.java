package de.mss.utils;

import java.util.Random;

import org.junit.Test;

import junit.framework.TestCase;

public class RandomizerFactoryTest extends TestCase {

   @Override
   public void tearDown() {
      RandomizerFactory.closeAllInstances();
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

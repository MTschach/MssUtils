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
      final Random r = new Random();
      RandomizerFactory.createInstance("test1", r);
      Random r1 = RandomizerFactory.getInstance("test1");

      assertEquals("Same", r, r1);

      r1 = RandomizerFactory.getInstance("newInstance");

      RandomizerFactory.closeInstance("test1");
      RandomizerFactory.closeInstance("new Instance");
   }


   @Test
   public void testCreateInstance2() {
      final Random r1 = new Random();
      RandomizerFactory.createInstance("test1", r1);
      final Random r2 = new Random();
      RandomizerFactory.createInstance("test2", r2);
      RandomizerFactory.createInstance("test2", r2);

      assertEquals("Same", r1, RandomizerFactory.getInstance("test1"));
   }

}

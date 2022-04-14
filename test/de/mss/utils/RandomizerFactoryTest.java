package de.mss.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Random;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class RandomizerFactoryTest {

   @AfterEach
   public void tearDown() {
      RandomizerFactory.closeAllInstances();
   }


   @Test
   public void testCreateInstance() {
      final Random r = new Random();
      RandomizerFactory.createInstance("test1", r);
      Random r1 = RandomizerFactory.getInstance("test1");

      assertEquals(r, r1);

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

      assertEquals(r1, RandomizerFactory.getInstance("test1"));
   }

}

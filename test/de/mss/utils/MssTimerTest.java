package de.mss.utils;

import org.junit.Test;

import junit.framework.TestCase;

public class MssTimerTest extends TestCase {

   @Test
   public void testSleep() throws InterruptedException {
      MssTimer t = new MssTimer();

      t.sleep(10);
   }


   @Test
   public void testPrepareSleep() throws InterruptedException {
      MssTimer t = new MssTimer();

      long currentTime = System.currentTimeMillis();
      t.prepareSleep(25);
      assertTrue(currentTime <= System.currentTimeMillis());
      assertTrue(currentTime + 2 >= System.currentTimeMillis());
      t.sleep();
   }


   @Test
   public void testSleepWithoutPrepare() throws InterruptedException {
      MssTimer t = new MssTimer();

      long currentTime = System.currentTimeMillis();
      assertTrue(currentTime <= System.currentTimeMillis());
      assertTrue(currentTime + 2 >= System.currentTimeMillis());
      t.sleep();
   }


   @Test
   public void testSleepNoSleep() throws InterruptedException {
      MssTimer t = new MssTimer();

      long currentTime = System.currentTimeMillis();
      t.prepareSleep(25);
      assertTrue(currentTime <= System.currentTimeMillis());
      assertTrue(currentTime + 2 >= System.currentTimeMillis());
      Thread.sleep(50);
      t.sleep();
   }
}

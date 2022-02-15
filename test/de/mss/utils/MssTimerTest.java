package de.mss.utils;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MssTimerTest {

   @Test
   public void testPrepareSleep() throws InterruptedException {
      final MssTimer t = new MssTimer();

      final long currentTime = System.currentTimeMillis();
      t.prepareSleep(25);
      assertTrue(currentTime <= System.currentTimeMillis());
      assertTrue(currentTime + 2 >= System.currentTimeMillis());
      t.sleep();
   }


   @Test
   public void testSleep() throws InterruptedException {
      final MssTimer t = new MssTimer();

      t.sleep(10);
   }


   @Test
   public void testSleepNoSleep() throws InterruptedException {
      final MssTimer t = new MssTimer();

      final long currentTime = System.currentTimeMillis();
      t.prepareSleep(25);
      assertTrue(currentTime <= System.currentTimeMillis());
      assertTrue(currentTime + 2 >= System.currentTimeMillis());
      Thread.sleep(50);
      t.sleep();
   }


   @Test
   public void testSleepWithoutPrepare() throws InterruptedException {
      final MssTimer t = new MssTimer();

      final long currentTime = System.currentTimeMillis();
      assertTrue(currentTime <= System.currentTimeMillis());
      assertTrue(currentTime + 2 >= System.currentTimeMillis());
      t.sleep();
   }
}

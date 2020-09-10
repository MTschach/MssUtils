package de.mss.utils;

import org.junit.Test;

import junit.framework.TestCase;

public class StopWatchTest extends TestCase {

   @Test
   public void test() {
      StopWatch s = new StopWatch();
      s.stop();
      assertTrue("Duration >= 0", s.getDuration() >= 0);

      s = new StopWatch();
      assertTrue("Duration >= 0", s.getDuration() >= 0);
   }


   @Test
   public void testReset() throws InterruptedException {
      StopWatch s = new StopWatch();
      Thread.sleep(25);
      long duration = s.getDuration();
      assertTrue("Duration >= 1 ms (" + duration + ")", duration >= 1);
      s.reset();
      duration = s.getDuration();
      assertTrue("Duration <= 1ms", duration <= 1);
   }

}

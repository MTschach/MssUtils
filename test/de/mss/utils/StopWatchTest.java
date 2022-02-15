package de.mss.utils;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class StopWatchTest {

   @Test
   public void test() {
      StopWatch s = new StopWatch();
      s.stop();
      assertTrue(s.getDuration() >= 0);

      s = new StopWatch();
      assertTrue(s.getDuration() >= 0);
   }


   @Test
   public void testReset() throws InterruptedException {
      final StopWatch s = new StopWatch();
      Thread.sleep(25);
      long duration = s.getDuration();
      assertTrue(duration >= 1);
      s.reset();
      duration = s.getDuration();
      assertTrue(duration <= 1);
   }

}

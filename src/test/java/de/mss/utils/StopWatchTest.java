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

}

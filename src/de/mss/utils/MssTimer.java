package de.mss.utils;


public class MssTimer implements IfMssTimer {

   private long waitUntil = 0;


   @Override
   public void prepareSleep(long millies) {
      this.waitUntil = System.currentTimeMillis() + millies;
   }


   @Override
   public void sleep() {
      if (this.waitUntil <= 0) {
         return;
      }

      final long waitMillies = this.waitUntil - System.currentTimeMillis();
      if (waitMillies <= 0) {
         return;
      }

      sleep(waitMillies);
   }


   @Override
   public void sleep(long millies) {
      try {
         Thread.sleep(millies);
      }
      catch (final InterruptedException e) {
         Tools.doNullLog(e);
      }
   }

}

package de.mss.utils;


public class MssTimer implements IfMssTimer {

   private long waitUntil = 0;


   @Override
   public void prepareSleep(long millies) {
      this.waitUntil = System.currentTimeMillis() + millies;
   }


   @Override
   public void sleep() throws InterruptedException {
      if (this.waitUntil <= 0)
         return;

      long waitMillies = this.waitUntil - System.currentTimeMillis();
      if (waitMillies <= 0)
         return;

      sleep(waitMillies);
   }


   @Override
   public void sleep(long millies) throws InterruptedException {
      Thread.sleep(millies);
   }

}

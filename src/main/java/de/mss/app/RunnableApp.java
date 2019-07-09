package de.mss.app;

import org.apache.logging.log4j.LogManager;

public abstract class RunnableApp {

   private boolean running = false;

   private int     waitInterval = 60;


   protected abstract void doRunning();


   public void run() {
      while (checkRunning()) {
         long waitUntil = System.currentTimeMillis() + this.waitInterval * 1000;

         doRunning();

         waitUntil(waitUntil);
      }
   }

   protected boolean checkRunning() {
      return this.running;
   }


   protected void stop() {
      this.running = false;
   }


   protected void start() {
      this.running = true;
   }


   private void waitUntil(long time) {
      long current = System.currentTimeMillis();
      if (current >= time)
         return;

      try {
         sleep(time - current);
      }
      catch (InterruptedException e) {
         LogManager.getLogger().error("Error while waiting", e);
         Thread.currentThread().interrupt();
      }
   }


   protected void sleep(long millies) throws InterruptedException {
      Thread.sleep(millies);
   }
}

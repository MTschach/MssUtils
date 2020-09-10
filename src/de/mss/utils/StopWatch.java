package de.mss.utils;

public class StopWatch implements IfStopWatch {

   private long start = 0l;
   private long stop  = 0l;


   public StopWatch() {
      start();
   }


   @Override
   public void start() {
      this.start = System.currentTimeMillis();
   }


   @Override
   public void stop() {
      this.stop = System.currentTimeMillis();
   }


   @Override
   public void reset() {
      this.start = System.currentTimeMillis();
      this.stop = 0l;
   }


   @Override
   public long getDuration() {
      if (this.stop <= 0l)
         stop();

      return this.stop - this.start;
   }


}
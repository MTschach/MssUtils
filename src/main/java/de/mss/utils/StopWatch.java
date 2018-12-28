package de.mss.utils;

import java.util.Date;

public class StopWatch {

   private java.util.Date start = null;
   private java.util.Date stop  = null;


   public StopWatch() {
      this.start = new Date();
   }


   public void stop() {
      this.stop = new Date();
   }


   public long getDuration() {
      if (this.stop == null)
         stop();

      return this.stop.getTime() - this.start.getTime();
   }
}
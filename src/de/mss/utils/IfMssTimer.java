package de.mss.utils;


public interface IfMssTimer {

   public void prepareSleep(long millies);


   public void sleep() throws InterruptedException;


   public void sleep(long millies) throws InterruptedException;
}

package de.mss.utils.logging;

import java.util.HashMap;
import java.util.Map;

public class LoggingTestObject implements Logable {

   private final String name;

   public LoggingTestObject(String n) {
      this.name = n;
   }


   @Override
   public Map<String, String> doLogging() {
      Map<String, String> props = new HashMap<>();

      props = LoggingUtil.addLogging("Name", this.name, props);

      return props;
   }


   public String getName() {
      return this.name;
   }


   @Override
   public String toString() {
      return LoggingUtil.getLogString(doLogging());
   }
}

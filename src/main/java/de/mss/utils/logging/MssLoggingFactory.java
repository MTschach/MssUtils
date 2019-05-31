package de.mss.utils.logging;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MssLoggingFactory {

   private static Map<String, Logger> loggerMap = new HashMap<>();


   private MssLoggingFactory() {}


   public static Logger getLogger() {
      return getLogger("default");
   }


   public static void setLoggerInstance(String loggerName, Logger l) {
      MssLoggingFactory.loggerMap.put(loggerName, l);
   }


   public static Logger getLogger(String loggerName) {
      if (MssLoggingFactory.loggerMap.containsKey(loggerName))
         return MssLoggingFactory.loggerMap.get(loggerName);

      MssLoggingFactory.loggerMap.put(loggerName, LogManager.getLogger(loggerName));
      return MssLoggingFactory.loggerMap.get(loggerName);
   }
}

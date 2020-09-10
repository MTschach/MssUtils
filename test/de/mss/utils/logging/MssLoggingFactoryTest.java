package de.mss.utils.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import junit.framework.TestCase;

public class MssLoggingFactoryTest extends TestCase {

   private Logger defaultLogger = null;
   private Logger dbLogger      = null;


   @Override
   public void setUp() {
      if (this.defaultLogger == null) {
         this.defaultLogger = LogManager.getLogger("default");
         MssLoggingFactory.setLoggerInstance("default", this.defaultLogger);
      }

      if (this.dbLogger == null) {
         this.dbLogger = LogManager.getLogger("db");
         MssLoggingFactory.setLoggerInstance("db", this.dbLogger);
      }
   }


   @Test
   public void testDefaultLogger() {
      Logger l = MssLoggingFactory.getLogger();
      assertEquals("Default logger", this.defaultLogger, l);
   }


   @Test
   public void testDbLogger() {
      Logger l = MssLoggingFactory.getLogger("db");
      assertEquals("DB logger", this.dbLogger, l);
   }


   @Test
   public void testGetLogger() {
      Logger l = MssLoggingFactory.getLogger("newLogger");
      assertFalse("not default logger", this.defaultLogger.equals(l));
      assertFalse("not db logger", this.dbLogger.equals(l));
   }

}

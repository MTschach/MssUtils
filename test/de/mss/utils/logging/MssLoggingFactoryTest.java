package de.mss.utils.logging;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MssLoggingFactoryTest {

   private Logger defaultLogger = null;
   private Logger dbLogger      = null;


   @BeforeEach
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
   public void testDbLogger() {
      final Logger l = MssLoggingFactory.getLogger("db");
      assertEquals(this.dbLogger, l);
   }


   @Test
   public void testDefaultLogger() {
      final Logger l = MssLoggingFactory.getLogger();
      assertEquals(this.defaultLogger, l);
   }


   @Test
   public void testGetLogger() {
      final Logger l = MssLoggingFactory.getLogger("newLogger");
      assertFalse(this.defaultLogger.equals(l));
      assertFalse(this.dbLogger.equals(l));
   }

}

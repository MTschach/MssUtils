package de.mss.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ToolsTest {

   @Test
   public void testGetId() {
      String loggingId = Tools.getId("prefix");
      assertNotNull("LoggingId not null", loggingId);
      assertTrue("Startswith prefix", loggingId.startsWith("prefix"));
   }


   @Test
   public void testGetIdWithThrowable() {
      String loggingId = Tools.getId(new Throwable());
      assertNotNull("LoggingId not null", loggingId);
      assertTrue("Startswith testGetIdWithThrowable", loggingId.startsWith("testGetIdWithThrowable"));
   }


   @Test
   public void testIsSet() {
      assertTrue("Is set 1", Tools.isSet("1"));
      assertFalse("Is not set null", Tools.isSet(null));
      assertFalse("Is not set ''", Tools.isSet(""));
   }
}

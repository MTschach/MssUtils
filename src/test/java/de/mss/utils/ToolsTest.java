package de.mss.utils;

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

}

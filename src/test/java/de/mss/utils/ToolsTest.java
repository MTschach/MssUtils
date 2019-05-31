package de.mss.utils;

import org.junit.Test;

import de.mss.utils.exception.MssException;
import junit.framework.TestCase;

public class ToolsTest extends TestCase {

   @SuppressWarnings("unused")
   @Test
   public void testToolsTest() {
      try {
         new Tools();
         fail();
      }
      catch (MssException e) {
         assertEquals("ErrorCode", 1, e.getError().getErrorCode());
      }
   }


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


   @Test
   public void testIsFalseBoolean() {
      assertFalse("isFalse null", Tools.isFalse((Boolean)null));
      assertTrue("IsFalse false", Tools.isFalse(Boolean.FALSE));
      assertFalse("IsFalse true", Tools.isFalse(Boolean.TRUE));
   }


   @Test
   public void testIsFalseString() {
      assertFalse("isFalse null", Tools.isFalse((String)null));
      assertFalse("isFalse ''", Tools.isFalse(""));
      assertFalse("isFalse 'bla'", Tools.isFalse("bla"));
      assertTrue("isFalse '0'", Tools.isFalse("0"));
      assertTrue("isFalse 'n'", Tools.isFalse("n"));
      assertTrue("isFalse 'N'", Tools.isFalse("N"));
      assertTrue("isFalse 'false'", Tools.isFalse("false"));
      assertTrue("isFalse 'False'", Tools.isFalse("False"));
      assertTrue("isFalse 'no'", Tools.isFalse("no"));
      assertTrue("isFalse 'NO'", Tools.isFalse("NO"));
   }


   @Test
   public void testIsTrueBoolean() {
      assertFalse("isTrue null", Tools.isTrue((Boolean)null));
      assertFalse("IsTrue false", Tools.isTrue(Boolean.FALSE));
      assertTrue("IsTrue true", Tools.isTrue(Boolean.TRUE));
   }


   @Test
   public void testIsTrueString() {
      assertFalse("isTrue null", Tools.isTrue((String)null));
      assertFalse("isTrue ''", Tools.isTrue(""));
      assertFalse("isTrue 'bla'", Tools.isTrue("bla"));
      assertTrue("isTrue '1'", Tools.isTrue("1"));
      assertTrue("isTrue 'j'", Tools.isTrue("j"));
      assertTrue("isTrue 'J'", Tools.isTrue("J"));
      assertTrue("isTrue 'true'", Tools.isTrue("true"));
      assertTrue("isTrue 'True'", Tools.isTrue("True"));
      assertTrue("isTrue 'yes'", Tools.isTrue("yes"));
      assertTrue("isTrue 'YES'", Tools.isTrue("YES"));
   }


   @Test
   public void testGetLoggingId() {
      assertEquals("<myLoggingId> ", Tools.formatLoggingId("myLoggingId"));
   }


   @Test
   public void testDoNullLog() {
      Tools.doNullLog(new Exception("ein Test"));
   }
}

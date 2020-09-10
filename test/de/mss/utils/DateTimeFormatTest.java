package de.mss.utils;

import org.junit.Test;

import de.mss.utils.exception.MssException;
import junit.framework.TestCase;

public class DateTimeFormatTest extends TestCase {

   @Test
   public void testGetByName() throws MssException {
      DateTimeFormat d = null;
      try {
         d = DateTimeFormat.getDateTimeFormatByName(null);
         fail();
      }
      catch (MssException e) {
         assertEquals("ErrorCode", 3, e.getError().getErrorCode());
      }

      try {
         d = DateTimeFormat.getDateTimeFormatByName("");
         fail();
      }
      catch (MssException e) {
         assertEquals("ErrorCode", 3, e.getError().getErrorCode());
      }

      try {
         d = DateTimeFormat.getDateTimeFormatByName("dd.mm.jjjj");
         fail();
      }
      catch (MssException e) {
         assertEquals("ErrorCode", 3, e.getError().getErrorCode());
      }

      d = DateTimeFormat.getDateTimeFormatByName("yyyy-MM-dd");
      checkDateTimeFormat(DateTimeFormat.DATE_FORMAT_EN, d);

      d = DateTimeFormat.getDateTimeFormatByName("dd.MM.yyyy");
      checkDateTimeFormat(DateTimeFormat.DATE_FORMAT_DE, d);

      d = DateTimeFormat.getDateTimeFormatByName("yyyy-MM-dd HH:mm:ss");
      checkDateTimeFormat(DateTimeFormat.DATE_TIME_FORMAT_DB, d);

      d = DateTimeFormat.getDateTimeFormatByName("yyyy-MM-dd HH:mm:ss.SSS");
      checkDateTimeFormat(DateTimeFormat.DATE_TIMESTAMP_FORMAT_DB, d);

      d = DateTimeFormat.getDateTimeFormatByName("yyyy-MM-dd hh:mm a");
      checkDateTimeFormat(DateTimeFormat.DATE_TIME_FORMAT_EN, d);

      d = DateTimeFormat.getDateTimeFormatByName("yyyy-MM-dd hh:mm:ss a");
      checkDateTimeFormat(DateTimeFormat.DATE_TIMESTAMP_FORMAT_EN, d);

      d = DateTimeFormat.getDateTimeFormatByName("dd.MM.yyyy HH:mm");
      checkDateTimeFormat(DateTimeFormat.DATE_TIME_FORMAT_DE, d);

      d = DateTimeFormat.getDateTimeFormatByName("dd.MM.yyyy HH:mm:ss");
      checkDateTimeFormat(DateTimeFormat.DATE_TIMESTAMP_FORMAT_DE, d);

      d = DateTimeFormat.getDateTimeFormatByName("yyyy-MM-dd'T'HH:mm:ss Z");
      checkDateTimeFormat(DateTimeFormat.DATE_TIMESTAMP_FORMAT_UTC, d);

   }



   private void checkDateTimeFormat(DateTimeFormat exp, DateTimeFormat o) {
      assertEquals("Format " + exp.getFormat(), exp.getFormat(), o.getFormat());
   }
}

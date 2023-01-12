package de.mss.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.jupiter.api.Test;

import de.mss.utils.exception.ErrorCodes;
import de.mss.utils.exception.MssException;

public class DateTimeToolsTest {

   private static final String STR_DATE_STRING = "2018-12-11 17:15:35.128";
   private static final String STR_DATE_EN     = "2018-12-11";
   private static final String STR_DATE_DE     = "11.12.2018";

   private void checkDate(Date d, boolean withTime, boolean withSecond, boolean withMillisecond) {
      final GregorianCalendar gc = new GregorianCalendar();
      gc.setTime(d);
      assertEquals(2018, gc.get(Calendar.YEAR));
      assertEquals(11, gc.get(Calendar.MONTH));
      assertEquals(11, gc.get(Calendar.DAY_OF_MONTH));

      if (withTime) {
         assertEquals(17, gc.get(Calendar.HOUR_OF_DAY));
         assertEquals(15, gc.get(Calendar.MINUTE));
      }
      if (withSecond) {
         assertEquals(35, gc.get(Calendar.SECOND));
      }
      if (withMillisecond) {
         assertEquals(128, gc.get(Calendar.MILLISECOND));
      }
   }


   @Test
   public void testAddDate() {
      final java.util.Date tomorrow = DateTimeTools.getTomorrowDate();
      assertTrue(DateTimeTools.isSameDay(tomorrow, DateTimeTools.addDate(new java.util.Date(), 1, Calendar.DAY_OF_MONTH)));
      assertTrue(DateTimeTools.isSameDay(tomorrow, DateTimeTools.addDate(null, 1, Calendar.DAY_OF_MONTH)));

      assertTrue(DateTimeTools.isSameDay(new java.util.Date(), DateTimeTools.addDate(1, 1234567)));
   }


   @Test
   public void testFormatDate() throws MssException {
      assertNull(DateTimeTools.formatDate(null, "dd.MM.yyyy"));

      final Date d = DateTimeTools.parseString2Date(STR_DATE_STRING);
      final String ampm = new SimpleDateFormat("a").format(d);

      assertEquals(STR_DATE_DE, DateTimeTools.formatDate(d, "dd.MM.yyyy"));
      assertEquals("2018/11/12", DateTimeTools.formatDate(d, "yyyy/dd/MM"));
      try {
         DateTimeTools.formatDate(d, "abc");
         fail();
      }
      catch (final MssException e) {
         assertEquals(ErrorCodes.ERROR_DATE_TIME_FORMAT_UNKNOWN.getErrorCode(), e.getError().getErrorCode());
      }

      assertEquals(STR_DATE_DE, DateTimeTools.formatDate(d, DateTimeFormat.DATE_FORMAT_DE));
      assertEquals(STR_DATE_EN, DateTimeTools.formatDate(d, DateTimeFormat.DATE_FORMAT_EN));
      assertEquals("2018-12-11 17:15:35", DateTimeTools.formatDate(d, DateTimeFormat.DATE_TIME_FORMAT_DB));
      assertEquals("11.12.2018 17:15", DateTimeTools.formatDate(d, DateTimeFormat.DATE_TIME_FORMAT_DE));
      assertEquals("2018-12-11 05:15 " + ampm, DateTimeTools.formatDate(d, DateTimeFormat.DATE_TIME_FORMAT_EN));
      assertEquals(STR_DATE_STRING, DateTimeTools.formatDate(d, DateTimeFormat.DATE_TIMESTAMP_FORMAT_DB));
      assertEquals("11.12.2018 17:15:35", DateTimeTools.formatDate(d, DateTimeFormat.DATE_TIMESTAMP_FORMAT_DE));
      assertEquals("2018-12-11 05:15:35 " + ampm, DateTimeTools.formatDate(d, DateTimeFormat.DATE_TIMESTAMP_FORMAT_EN));
      assertEquals("2018-12-11T17:15:35 +0100", DateTimeTools.formatDate(d, DateTimeFormat.DATE_TIMESTAMP_FORMAT_UTC));
   }


   @Test
   public void testGetFriday() {
      final GregorianCalendar gc = new GregorianCalendar();

      final java.util.Date monday = DateTimeTools.getFridayDate();

      switch (gc.get(Calendar.DAY_OF_WEEK)) {
         case Calendar.MONDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) + 4);
            break;
         case Calendar.TUESDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) + 3);
            break;
         case Calendar.WEDNESDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) + 2);
            break;
         case Calendar.THURSDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) + 1);
            break;
         case Calendar.FRIDAY:
            break;
         case Calendar.SATURDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) - 1);
            break;
         case Calendar.SUNDAY:
         default:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) - 2);
            break;
      }

      assertTrue(DateTimeTools.isSameDay(monday, gc.getTime()));
   }


   @Test
   public void testGetMonday() {
      final GregorianCalendar gc = new GregorianCalendar();

      final java.util.Date monday = DateTimeTools.getMondayDate();

      switch (gc.get(Calendar.DAY_OF_WEEK)) {
         case Calendar.MONDAY:
            break;
         case Calendar.TUESDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) - 1);
            break;
         case Calendar.WEDNESDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) - 2);
            break;
         case Calendar.THURSDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) - 3);
            break;
         case Calendar.FRIDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) - 4);
            break;
         case Calendar.SATURDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) - 5);
            break;
         case Calendar.SUNDAY:
         default:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) - 6);
            break;
      }

      assertTrue(DateTimeTools.isSameDay(monday, gc.getTime()));
   }


   @Test
   public void testGetSaturday() {
      final GregorianCalendar gc = new GregorianCalendar();

      final java.util.Date monday = DateTimeTools.getSaturdayDate();

      switch (gc.get(Calendar.DAY_OF_WEEK)) {
         case Calendar.MONDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) + 5);
            break;
         case Calendar.TUESDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) + 4);
            break;
         case Calendar.WEDNESDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) + 3);
            break;
         case Calendar.THURSDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) + 2);
            break;
         case Calendar.FRIDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) + 1);
            break;
         case Calendar.SATURDAY:
            break;
         case Calendar.SUNDAY:
         default:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) - 1);
            break;
      }

      assertTrue(DateTimeTools.isSameDay(monday, gc.getTime()));
   }


   @Test
   public void testGetSunday() {
      final GregorianCalendar gc = new GregorianCalendar();

      final java.util.Date monday = DateTimeTools.getSundayDate();

      switch (gc.get(Calendar.DAY_OF_WEEK)) {
         case Calendar.MONDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) + 6);
            break;
         case Calendar.TUESDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) + 5);
            break;
         case Calendar.WEDNESDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) + 4);
            break;
         case Calendar.THURSDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) + 3);
            break;
         case Calendar.FRIDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) + 2);
            break;
         case Calendar.SATURDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) + 1);
            break;
         case Calendar.SUNDAY:
         default:
            break;
      }

      assertTrue(DateTimeTools.isSameDay(monday, gc.getTime()));
   }


   @Test
   public void testGetThursday() {
      final GregorianCalendar gc = new GregorianCalendar();

      final java.util.Date monday = DateTimeTools.getThursdayDate();

      switch (gc.get(Calendar.DAY_OF_WEEK)) {
         case Calendar.MONDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) + 3);
            break;
         case Calendar.TUESDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) + 2);
            break;
         case Calendar.WEDNESDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) + 1);
            break;
         case Calendar.THURSDAY:
            break;
         case Calendar.FRIDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) - 1);
            break;
         case Calendar.SATURDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) - 2);
            break;
         case Calendar.SUNDAY:
         default:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) - 3);
            break;
      }

      assertTrue(DateTimeTools.isSameDay(monday, gc.getTime()));
   }


   @Test
   public void testGetTuesday() {
      final GregorianCalendar gc = new GregorianCalendar();

      final java.util.Date monday = DateTimeTools.getTuesdayDate();

      switch (gc.get(Calendar.DAY_OF_WEEK)) {
         case Calendar.MONDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) + 1);
            break;
         case Calendar.TUESDAY:
            break;
         case Calendar.WEDNESDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) - 1);
            break;
         case Calendar.THURSDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) - 2);
            break;
         case Calendar.FRIDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) - 3);
            break;
         case Calendar.SATURDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) - 4);
            break;
         case Calendar.SUNDAY:
         default:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) - 5);
            break;
      }

      assertTrue(DateTimeTools.isSameDay(monday, gc.getTime()));
   }


   @Test
   public void testGetWednesday() {
      final GregorianCalendar gc = new GregorianCalendar();

      final java.util.Date monday = DateTimeTools.getWednesdayDate();

      switch (gc.get(Calendar.DAY_OF_WEEK)) {
         case Calendar.MONDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) + 2);
            break;
         case Calendar.TUESDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) + 1);
            break;
         case Calendar.WEDNESDAY:
            break;
         case Calendar.THURSDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) - 1);
            break;
         case Calendar.FRIDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) - 2);
            break;
         case Calendar.SATURDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) - 3);
            break;
         case Calendar.SUNDAY:
         default:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) - 4);
            break;
      }

      assertTrue(DateTimeTools.isSameDay(monday, gc.getTime()));
   }


   @Test
   public void testIsSameDay() throws MssException {
      assertFalse(DateTimeTools.isSameDay(null, null));

      java.util.Date today = DateTimeTools.getActDate();

      assertFalse(DateTimeTools.isSameDay(null, today));
      assertFalse(DateTimeTools.isSameDay(today, null));

      assertTrue(DateTimeTools.isSameDay(today, DateTimeTools.getActDate()));
      assertFalse(DateTimeTools.isSameDay(today, DateTimeTools.getYesterdayDate()));
      assertFalse(DateTimeTools.isSameDay(today, DateTimeTools.getTomorrowDate()));

      today = DateTimeTools.parseString2Date(STR_DATE_EN);

      assertFalse(DateTimeTools.isSameDay(today, DateTimeTools.parseString2Date("2017-12-11")));
      assertFalse(DateTimeTools.isSameDay(today, DateTimeTools.parseString2Date("2018-11-11")));
      assertFalse(DateTimeTools.isSameDay(today, DateTimeTools.parseString2Date("2018-12-12")));
   }


   @Test
   public void testNow() throws MssException {
      DateTimeTools.initNowForTest((Date)null);
      final java.util.Date d1 = DateTimeTools.parseString2Date("2020-07-20 12:34:56");
      final java.util.Date d2 = DateTimeTools.parseString2Date("2020-07-19 01:23:45");

      assertEquals(
            DateTimeTools.formatDate(new java.util.Date(), DateTimeFormat.DATE_TIME_FORMAT_DB),
            DateTimeTools.formatDate(DateTimeTools.now(), DateTimeFormat.DATE_TIME_FORMAT_DB));
      DateTimeTools.initNowForTest(d1);
      assertEquals("2020-07-20 12:34:56", DateTimeTools.formatDate(DateTimeTools.now(), DateTimeFormat.DATE_TIME_FORMAT_DB));

      DateTimeTools.initNowForTest((Date[])null);
      DateTimeTools.initNowForTest(new Date[] {d1, d2});
      assertEquals("2020-07-20 12:34:56", DateTimeTools.formatDate(DateTimeTools.now(), DateTimeFormat.DATE_TIME_FORMAT_DB));
      assertEquals("2020-07-19 01:23:45", DateTimeTools.formatDate(DateTimeTools.now(), DateTimeFormat.DATE_TIME_FORMAT_DB));
      assertEquals("2020-07-20 12:34:56", DateTimeTools.formatDate(DateTimeTools.now(), DateTimeFormat.DATE_TIME_FORMAT_DB));
   }


   @Test
   public void testParseString2Date() throws MssException {
      assertNull(DateTimeTools.parseString2Date(null));
      assertNull(DateTimeTools.parseString2Date(""));
      try {
         DateTimeTools.parseString2Date("bla");
         fail();
      }
      catch (final MssException e) {
         assertEquals(3, e.getError().getErrorCode());
      }

      final String ampm = new SimpleDateFormat("a").format(DateTimeTools.parseString2Date(STR_DATE_STRING));

      Date d = DateTimeTools.parseString2Date("2018-12-11T17:15:35 +0100");
      checkDate(d, true, true, false);

      d = DateTimeTools.parseString2Date(STR_DATE_STRING);
      checkDate(d, true, true, true);

      d = DateTimeTools.parseString2Date("2018-12-11 05:15:35 " + ampm);
      checkDate(d, true, true, false);

      d = DateTimeTools.parseString2Date("2018-12-11 17:15:35");
      checkDate(d, true, true, false);

      d = DateTimeTools.parseString2Date("11.12.2018 17:15:35");
      checkDate(d, true, true, false);

      d = DateTimeTools.parseString2Date("2018-12-11 05:15 " + ampm);
      checkDate(d, true, false, false);

      d = DateTimeTools.parseString2Date("11.12.2018 17:15");
      checkDate(d, true, false, false);

      d = DateTimeTools.parseString2Date(STR_DATE_EN);
      checkDate(d, false, false, false);

      d = DateTimeTools.parseString2Date(STR_DATE_DE);
      checkDate(d, false, false, false);
   }

}

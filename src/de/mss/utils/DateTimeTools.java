package de.mss.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import de.mss.utils.exception.ErrorCodes;
import de.mss.utils.exception.MssException;

public class DateTimeTools {

   private static java.util.Date nowForTest = null;

   private static GregorianCalendar addDate(GregorianCalendar gc, int diff, int field) {
      final GregorianCalendar ret = gc;

      if (diff != 0) {
         switch (field) {
            case Calendar.YEAR:
            case Calendar.MONTH:
            case Calendar.DAY_OF_MONTH:
            case Calendar.DAY_OF_YEAR:
            case Calendar.DAY_OF_WEEK:
            case Calendar.HOUR:
            case Calendar.HOUR_OF_DAY:
            case Calendar.MINUTE:
            case Calendar.SECOND:
               ret.set(field, ret.get(field) + diff);
               break;

            default:
               break;
         }
      }

      return ret;
   }


   public static java.util.Date addDate(int diff, int field) {
      final GregorianCalendar gc = new GregorianCalendar();

      return addDate(gc, diff, field).getTime();
   }


   public static java.util.Date addDate(java.util.Date date, int diff, int field) {
      java.util.Date ret = date;
      if (ret == null) {
         ret = new java.util.Date();
      }

      final GregorianCalendar gc = new GregorianCalendar();
      gc.setTime(ret);

      return addDate(gc, diff, field).getTime();
   }


   public static String formatDate(java.util.Date date, DateTimeFormat format) {
      if (date == null) {
         return null;
      }

      final SimpleDateFormat sdf = new SimpleDateFormat(format.getFormat());
      return sdf.format(date);
   }


   public static String formatDate(java.util.Date date, String format) throws MssException {
      try {
         return formatDate(date, DateTimeFormat.getDateTimeFormatByName(format));
      }
      catch (final MssException e) {
         Tools.doNullLog(e);
         try {
            return new SimpleDateFormat(format).format(date);
         }
         catch (final Exception e1) {
            throw new MssException(ErrorCodes.ERROR_DATE_TIME_FORMAT_UNKNOWN, e1, "Could not format date");
         }
      }
   }


   public static java.util.Date getActDate() {
      return new java.util.Date();
   }


   public static java.util.Date getFridayDate() {
      final GregorianCalendar gc = new GregorianCalendar();

      return addDate(gc, Calendar.FRIDAY - gc.get(Calendar.DAY_OF_WEEK), Calendar.DAY_OF_WEEK).getTime();
   }


   public static java.util.Date getMondayDate() {
      final GregorianCalendar gc = new GregorianCalendar();

      return addDate(gc, Calendar.MONDAY - gc.get(Calendar.DAY_OF_WEEK), Calendar.DAY_OF_WEEK).getTime();
   }


   public static java.util.Date getSaturdayDate() {
      final GregorianCalendar gc = new GregorianCalendar();

      return addDate(gc, Calendar.SATURDAY - gc.get(Calendar.DAY_OF_WEEK), Calendar.DAY_OF_WEEK).getTime();
   }


   public static java.util.Date getSundayDate() {
      final GregorianCalendar gc = new GregorianCalendar();

      return addDate(gc, Calendar.SUNDAY - gc.get(Calendar.DAY_OF_WEEK), Calendar.DAY_OF_WEEK).getTime();
   }


   public static java.util.Date getThursdayDate() {
      final GregorianCalendar gc = new GregorianCalendar();

      return addDate(gc, Calendar.THURSDAY - gc.get(Calendar.DAY_OF_WEEK), Calendar.DAY_OF_WEEK).getTime();
   }


   public static java.util.Date getTomorrowDate() {
      return addDate(1, Calendar.DAY_OF_MONTH);
   }


   public static java.util.Date getTuesdayDate() {
      final GregorianCalendar gc = new GregorianCalendar();

      return addDate(gc, Calendar.TUESDAY - gc.get(Calendar.DAY_OF_WEEK), Calendar.DAY_OF_WEEK).getTime();
   }


   public static java.util.Date getWednesdayDate() {
      final GregorianCalendar gc = new GregorianCalendar();

      return addDate(gc, Calendar.WEDNESDAY - gc.get(Calendar.DAY_OF_WEEK), Calendar.DAY_OF_WEEK).getTime();
   }


   public static java.util.Date getYesterdayDate() {
      return addDate(-1, Calendar.DAY_OF_MONTH);
   }


   public static void initNowForTest(java.util.Date v) {
      nowForTest = v;
   }


   public static boolean isSameDay(java.util.Date date1, java.util.Date date2) {
      if ((date1 == null) || (date2 == null)) {
         return false;
      }

      final GregorianCalendar gc1 = new GregorianCalendar();
      gc1.setTime(date1);
      final GregorianCalendar gc2 = new GregorianCalendar();
      gc2.setTime(date2);

      return (gc1.get(Calendar.YEAR) == gc2.get(Calendar.YEAR))
            && (gc1.get(Calendar.MONTH) == gc2.get(Calendar.MONTH))
            && (gc1.get(Calendar.DAY_OF_MONTH) == gc2.get(Calendar.DAY_OF_MONTH));
   }


   public static java.util.Date now() {
      if (nowForTest != null) {
         return nowForTest;
      }

      return new java.util.Date();
   }


   public static java.util.Date parseString2Date(String dateString) throws MssException {
      if (!Tools.isSet(dateString)) {
         return null;
      }

      for (final DateTimeFormat format : DateTimeFormat.values()) {
         try {
            final SimpleDateFormat sdf = new SimpleDateFormat(format.getFormat());
            return sdf.parse(dateString);
         }
         catch (final Exception e) {
            Tools.doNullLog(e);
         }
      }

      throw new MssException(
            new de.mss.utils.exception.Error(
                  ErrorCodes.ERROR_DATE_TIME_FORMAT_UNKNOWN.getErrorCode(),
                  "The String '" + dateString + "' could not be parsed as a Date"));
   }


   private DateTimeTools() {}
}

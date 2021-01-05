package de.mss.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import de.mss.utils.exception.ErrorCodes;
import de.mss.utils.exception.MssException;

public class DateTimeTools {

   private static java.util.Date nowForTest = null;

   private static GregorianCalendar addDate(GregorianCalendar gc, int diff, int field) throws MssException {
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
               gc.set(field, gc.get(field) + diff);
               break;

            default:
               throw new MssException(
                     new de.mss.utils.exception.Error(ErrorCodes.ERROR_DATE_TIME_FORMAT_UNKNOWN.getErrorCode(), "Unknown field provided"));
         }
      }

      return gc;
   }


   public static java.util.Date addDate(int diff, int field) throws MssException {
      final GregorianCalendar gc = new GregorianCalendar();

      return addDate(gc, diff, field).getTime();
   }


   public static java.util.Date addDate(java.util.Date date, int diff, int field) throws MssException {
      if (date == null) {
         throw new MssException(
               new de.mss.utils.exception.Error(ErrorCodes.ERROR_INVALID_PARAM.getErrorCode(), "Parameter date was null"));
      }

      final GregorianCalendar gc = new GregorianCalendar();
      gc.setTime(date);

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


   public static java.util.Date getFridayDate() throws MssException {
      final GregorianCalendar gc = new GregorianCalendar();

      return addDate(gc, Calendar.FRIDAY - gc.get(Calendar.DAY_OF_WEEK), Calendar.DAY_OF_WEEK).getTime();
   }


   public static java.util.Date getMondayDate() throws MssException {
      final GregorianCalendar gc = new GregorianCalendar();

      return addDate(gc, Calendar.MONDAY - gc.get(Calendar.DAY_OF_WEEK), Calendar.DAY_OF_WEEK).getTime();
   }


   public static java.util.Date getSaturdayDate() throws MssException {
      final GregorianCalendar gc = new GregorianCalendar();

      return addDate(gc, Calendar.SATURDAY - gc.get(Calendar.DAY_OF_WEEK), Calendar.DAY_OF_WEEK).getTime();
   }


   public static java.util.Date getSundayDate() throws MssException {
      final GregorianCalendar gc = new GregorianCalendar();

      return addDate(gc, Calendar.SUNDAY - gc.get(Calendar.DAY_OF_WEEK), Calendar.DAY_OF_WEEK).getTime();
   }


   public static java.util.Date getThursdayDate() throws MssException {
      final GregorianCalendar gc = new GregorianCalendar();

      return addDate(gc, Calendar.THURSDAY - gc.get(Calendar.DAY_OF_WEEK), Calendar.DAY_OF_WEEK).getTime();
   }


   public static java.util.Date getTomorrowDate() throws MssException {
      return addDate(1, Calendar.DAY_OF_MONTH);
   }


   public static java.util.Date getTuesdayDate() throws MssException {
      final GregorianCalendar gc = new GregorianCalendar();

      return addDate(gc, Calendar.TUESDAY - gc.get(Calendar.DAY_OF_WEEK), Calendar.DAY_OF_WEEK).getTime();
   }


   public static java.util.Date getWednesdayDate() throws MssException {
      final GregorianCalendar gc = new GregorianCalendar();

      return addDate(gc, Calendar.WEDNESDAY - gc.get(Calendar.DAY_OF_WEEK), Calendar.DAY_OF_WEEK).getTime();
   }


   public static java.util.Date getYesterdayDate() throws MssException {
      return addDate(-1, Calendar.DAY_OF_MONTH);
   }


   public static void initNowForTest(java.util.Date v) {
      nowForTest = v;
   }


   public static boolean isSameDay(java.util.Date date1, java.util.Date date2) {
      if (date1 == null || date2 == null) {
         return false;
      }

      final GregorianCalendar gc1 = new GregorianCalendar();
      gc1.setTime(date1);
      final GregorianCalendar gc2 = new GregorianCalendar();
      gc2.setTime(date2);

      return gc1.get(Calendar.YEAR) == gc2.get(Calendar.YEAR)
            && gc1.get(Calendar.MONTH) == gc2.get(Calendar.MONTH)
            && gc1.get(Calendar.DAY_OF_MONTH) == gc2.get(Calendar.DAY_OF_MONTH);
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

package de.mss.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import de.mss.utils.exception.ErrorCodes;
import de.mss.utils.exception.MssException;

public class DateTimeTools {

   public static java.util.Date parseString2Date(String dateString) throws MssException {
      for (DateTimeFormat format : DateTimeFormat.values()) {
         try {
            SimpleDateFormat sdf = new SimpleDateFormat(format.getFormat());
            return sdf.parse(dateString);
         }
         catch (@SuppressWarnings("unused") Exception e) {
            // nothing to do, we're searching all patterns
         }
      }

      throw new MssException(
            new de.mss.utils.exception.Error(
                  ErrorCodes.ERROR_DATE_TIME_FORMAT_UNKNOWN.getErrorCode(),
                  "The String '" + dateString + "' could not be parsed as a Date"));
   }


   public static String formatDate(java.util.Date date, String format) throws MssException {
      return formatDate(date, DateTimeFormat.getDateTimeFormatByName(format));
   }


   public static String formatDate(java.util.Date date, DateTimeFormat format) throws MssException {
      if (date == null)
         throw new MssException(new de.mss.utils.exception.Error(ErrorCodes.ERROR_INVALID_PARAM.getErrorCode(), "Parameter date was null"));

      SimpleDateFormat sdf = new SimpleDateFormat(format.getFormat());
      return sdf.format(date);
   }


   public static java.util.Date getActDate() {
      return new java.util.Date();
   }


   public static java.util.Date getYesterdayDate() throws MssException {
      return addDate(-1, Calendar.DAY_OF_MONTH);
   }


   public static java.util.Date getTomorrowDate() throws MssException {
      return addDate(1, Calendar.DAY_OF_MONTH);
   }


   public static java.util.Date getMondayDate() throws MssException {
      GregorianCalendar gc = new GregorianCalendar();

      return addDate(gc, 1 - gc.get(Calendar.DAY_OF_WEEK), Calendar.DAY_OF_WEEK).getTime();
   }


   public static java.util.Date getTuesdayDate() throws MssException {
      GregorianCalendar gc = new GregorianCalendar();

      return addDate(gc, 2 - gc.get(Calendar.DAY_OF_WEEK), Calendar.DAY_OF_WEEK).getTime();
   }


   public static java.util.Date getWednesdayDate() throws MssException {
      GregorianCalendar gc = new GregorianCalendar();

      return addDate(gc, 3 - gc.get(Calendar.DAY_OF_WEEK), Calendar.DAY_OF_WEEK).getTime();
   }


   public static java.util.Date getThursdayDate() throws MssException {
      GregorianCalendar gc = new GregorianCalendar();

      return addDate(gc, 4 - gc.get(Calendar.DAY_OF_WEEK), Calendar.DAY_OF_WEEK).getTime();
   }


   public static java.util.Date getFridayDate() throws MssException {
      GregorianCalendar gc = new GregorianCalendar();

      return addDate(gc, 5 - gc.get(Calendar.DAY_OF_WEEK), Calendar.DAY_OF_WEEK).getTime();
   }


   public static java.util.Date getSaturdayDate() throws MssException {
      GregorianCalendar gc = new GregorianCalendar();

      return addDate(gc, 6 - gc.get(Calendar.DAY_OF_WEEK), Calendar.DAY_OF_WEEK).getTime();
   }


   public static java.util.Date getSunndayDate() throws MssException {
      GregorianCalendar gc = new GregorianCalendar();

      return addDate(gc, 7 - gc.get(Calendar.DAY_OF_WEEK), Calendar.DAY_OF_WEEK).getTime();
   }


   public static java.util.Date addDate(int diff, int field) throws MssException {
      GregorianCalendar gc = new GregorianCalendar();

      return addDate(gc, diff, field).getTime();
   }


   public static java.util.Date addDate(java.util.Date date, int diff, int field) throws MssException {
      GregorianCalendar gc = new GregorianCalendar();
      gc.setTime(date);

      return addDate(gc, diff, field).getTime();
   }


   private static GregorianCalendar addDate(GregorianCalendar gc, int diff, int field) throws MssException {
      if (gc == null)
         throw new MssException(
               new de.mss.utils.exception.Error(ErrorCodes.ERROR_INVALID_PARAM.getErrorCode(), "Parameter gregorianCalendar was null"));

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
}

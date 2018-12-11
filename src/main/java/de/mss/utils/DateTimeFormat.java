package de.mss.utils;

import de.mss.utils.exception.ErrorCodes;
import de.mss.utils.exception.MssException;

public enum DateTimeFormat {
   DATE_FORMAT_EN("yyyy-MM-dd"),
   DATE_FORMAT_EN_SHORT("yy-MM-dd"),
   DATE_FORMAT_DE("dd.MM.yyyy"),
   DATE_FORMAT_DE_SHORT("dd.M.yy"),
   DATE_TIME_FORMAT_DB("yyyy-MM-dd HH:mm:ss"),
   DATE_TIME_FORMAT_EN("yyyy-MM-dd hh:mm a"),
   DATE_TIME_FORMAT_EN_SHORT("yy-MM-dd hh:mm a"),
   DATE_TIMESTAMP_FORMAT_EN("yyyy-MM-dd hh:mm:ss a"),
   DATE_TIMESTAMP_FORMAT_EN_SHORT("yy-MM-dd hh:mm:ss a"),
   DATE_TIME_FORMAT_DE("dd.MM.yyyy HH:mm"),
   DATE_TIME_FORMAT_DE_SHORT("dd.MM.yy HH:mm"),
   DATE_TIMESTAMP_FORMAT_DE("dd.MM.yyyy HH:mm:ss"),
   DATE_TIMESTAMP_FORMAT_DE_SHORT("dd.MM.yy HH:mm:ss"),
   DATE_TIMESTAMP_FORMAT_UTC("yyyy-MM-dd'T'HH:mm:ss Z");


   private String format = null;


   private DateTimeFormat(String n) {
      this.format = n;
   }


   public String getFormat() {
      return this.format;
   }


   public static final DateTimeFormat getDateTimeFormatByName(String n) throws MssException {
      for (DateTimeFormat os : DateTimeFormat.values())
         if (os.getFormat().equals(n))
            return os;

      throw new MssException(ErrorCodes.ERROR_DATE_TIME_FORMAT_UNKNOWN);
   }
}

package de.mss.utils.db;

import de.mss.utils.exception.Error;
import de.mss.utils.exception.MssException;


public class DBException extends MssException {

   private static final long serialVersionUID = -5537836125724473795L;


   public DBException(Error ec) {
      super(ec);
   }


   public DBException(Error ec, Throwable t) {
      super(ec, t);
   }


   public DBException(Error ec, String msg) {
      super(ec, msg);
   }


   public DBException(int code) {
      super(code);
   }


   public DBException(Throwable t) {
      super(t);
   }


   public DBException(int code, String msg) {
      super(code, msg);
   }

}

package de.mss.utils.os;


public enum OsType {
   UNKNOWN("unknown"),
   WINDOWS("Windows"),
   LINUX("Linux"),
   MACOS("MacOS");


   private String name = null;


   private OsType(String n) {
      this.name = n;
   }


   public String getName() {
      return this.name;
   }


   public static final OsType getOsTypeByName(String n) {
      for (OsType os : OsType.values())
         if (os.getName().equals(n))
            return os;

      return OsType.UNKNOWN;
   }


   public static final OsType getOsType() {

      String name = System.getProperty("os.name");

      if (name.indexOf("MAC") >= 0 || name.indexOf("darwin") >= 0)
         return OsType.MACOS;

      if (name.indexOf("Windows") >= 0)
         return OsType.WINDOWS;

      if (name.indexOf("nux") >= 0)
         return OsType.LINUX;

      return OsType.UNKNOWN;
   }
}

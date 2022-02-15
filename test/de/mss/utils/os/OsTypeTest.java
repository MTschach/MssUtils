package de.mss.utils.os;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class OsTypeTest {

   private String osName = null;


   private void checkOsType(OsType exp, OsType o) {
      assertEquals(exp.getName(), o.getName());
   }


   public void setUp() {
      this.osName = System.getProperty("os.name");
   }


   public void tearDown() {
      System.setProperty("os.name", this.osName);
   }


   @Test
   public void testGetByName() {
      OsType o = OsType.getOsTypeByName(null);
      checkOsType(OsType.UNKNOWN, o);
      o = OsType.getOsTypeByName("");
      checkOsType(OsType.UNKNOWN, o);
      o = OsType.getOsTypeByName("lala");
      checkOsType(OsType.UNKNOWN, o);

      o = OsType.getOsTypeByName("Windows");
      checkOsType(OsType.WINDOWS, o);

      o = OsType.getOsTypeByName("Linux");
      checkOsType(OsType.LINUX, o);

      o = OsType.getOsTypeByName("MacOS");
      checkOsType(OsType.MACOS, o);

   }


   @Test
   public void testGetOsType() {
      System.setProperty("os.name", "Linux");
      OsType o = OsType.getOsType();

      checkOsType(OsType.LINUX, o);

      System.setProperty("os.name", "Windows");
      o = OsType.getOsType();

      checkOsType(OsType.WINDOWS, o);

      System.setProperty("os.name", "MAC");
      o = OsType.getOsType();

      checkOsType(OsType.MACOS, o);

      System.setProperty("os.name", "darwin");
      o = OsType.getOsType();

      checkOsType(OsType.MACOS, o);

      System.setProperty("os.name", "egal");
      o = OsType.getOsType();

      checkOsType(OsType.UNKNOWN, o);
   }
}

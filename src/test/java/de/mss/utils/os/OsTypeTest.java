package de.mss.utils.os;

import org.junit.Test;

import junit.framework.TestCase;

public class OsTypeTest extends TestCase {

   private String osName = null;


   @Override
   public void setUp() {
      this.osName = System.getProperty("os.name");
   }


   @Override
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


   private void checkOsType(OsType exp, OsType o) {
      assertEquals("Name " + exp.getName(), exp.getName(), o.getName());
   }
}

package de.mss.utils.os;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class OsTypeTest {

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


   private void checkOsType(OsType exp, OsType o) {
      assertEquals("Name " + exp.getName(), exp.getName(), o.getName());
   }
}

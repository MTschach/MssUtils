package de.mss.utils.db;

import org.junit.Test;

import junit.framework.TestCase;

public class DBServerEnumTest extends TestCase {


   @Test
   public void testGetByName() {
      DBServerEnum e = DBServerEnum.getByName(null);
      check(DBServerEnum.USERDEFINED, e);

      e = DBServerEnum.getByName("");
      check(DBServerEnum.USERDEFINED, e);

      e = DBServerEnum.getByName("meins");
      check(DBServerEnum.USERDEFINED, e);

      e = DBServerEnum.getByName("mysql");
      check(DBServerEnum.MYSQL, e);

      e = DBServerEnum.getByName("sybasetds");
      check(DBServerEnum.SYBASETDS, e);
   }


   private void check(DBServerEnum exp, DBServerEnum cur) {
      assertEquals("Name", exp.getName(), cur.getName());
      assertEquals("DBDriver", exp.getDriverClass(), cur.getDriverClass());
      assertEquals("ConnectionPrefix", exp.getConnectionPrefix(), cur.getConnectionPrefix());
   }
}

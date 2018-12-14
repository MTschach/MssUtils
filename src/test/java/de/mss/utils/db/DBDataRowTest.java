package de.mss.utils.db;

import org.junit.Test;

import junit.framework.TestCase;

public class DBDataRowTest extends TestCase {

   @Test
   public void test() {
      DBDataRow row = new DBDataRow(2);
      row.setValue("field 0", -1);
      row.setValue("field 1", 0);
      row.setValue("field 2", 1);
      row.setValue("field 3", 2);

      assertEquals("Size", 2, row.getSize());
      assertNull("Field -1", row.getValue(-1));
      assertEquals("Field 0", "field 1", row.getValue(0));
      assertEquals("Field 1", "field 2", row.getValue(1));
      assertNull("Field 2", row.getValue(2));
   }


   @Test
   public void testToString() {
      DBDataRow row = new DBDataRow(4);
      row.setValue("field 1", 0);
      row
            .setValue(
                  "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxcxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
                  2);
      row.setValue("field 4", 3);

      assertEquals("field 1;NULL;binary data not displayed;field 4", row.toString());
   }
}

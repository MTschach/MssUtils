package de.mss.utils.db;

import java.util.ArrayList;

public class DBDataRow {

   private ArrayList<String> values = null;


   public DBDataRow(int size) {
      values = new ArrayList<>(size);
   }


   public String getValue(int index) {
      if (index < 0 || index >= values.size())
         return null;

      return values.get(index);
   }


   public void setValue(String value, int index) {
      if (index < 0 || index > values.size())
         return;

      values.set(index, value);
   }


   public int getSize() {
      return this.values.size();
   }


   public String toString() {
      StringBuilder sb = new StringBuilder();

      sb.append(values.get(0));

      for (int i = 1; i < values.size(); i++ ) {
         String s = values.get(i);
         if (s == null)
            sb.append(";NULL");
         else if (s.length() > 255)
            sb.append(";binary data not displayed");
         else
            sb.append(";" + s);

      }

      return sb.toString();
   }

}

package de.mss.utils.db;

import java.util.ArrayList;

public class DBSingleResult {

   DBDataRow            columnNames     = null;
   ArrayList<DBDataRow> rowValues       = null;
   int                  resultSetNumber = 0;


   public DBSingleResult(int resNumber, int columnCount) {
      columnNames = new DBDataRow(columnCount);
      rowValues = new ArrayList<>();
      this.resultSetNumber = resNumber;
   }


   public int addRow() {
      this.rowValues.add(new DBDataRow(columnNames.getSize()));
      return this.rowValues.size();
   }


   public void setValue(int column, int row, String value) {
      if (column < 0 || column >= this.columnNames.getSize())
         return;

      if (row < 0 || row >= this.rowValues.size())
         return;

      this.rowValues.get(row).setValue(value, column);
   }


   public void setValue(String columnName, int row, String value) {
      setValue(getColumnIndex(columnName), row, value);
   }


   public String getValue(int column, int row) {
      if (column < 0 || column >= this.columnNames.getSize())
         return null;

      if (row < 0 || row >= this.rowValues.size())
         return null;

      return this.rowValues.get(row).getValue(column);
   }


   public String getValue(String columnName, int row) {
      return getValue(getColumnIndex(columnName), row);
   }


   public int getColumnCount() {
      return this.columnNames.getSize();
   }


   public int getRowCount() {
      return this.rowValues.size();
   }


   private int getColumnIndex(String columnName) {
      for (int i = 0; i < this.columnNames.getSize(); i++ ) {
         if (this.columnNames.getValue(i).equals(columnName))
            return i;
      }

      return -1;
   }


   public void setColumnName(String columnName, int col) {
      if (col < 0 || col >= columnNames.getSize())
         return;

      columnNames.setValue(columnName, col);
   }


   public String toString() {
      StringBuilder sb = new StringBuilder();

      sb.append("[" + this.resultSetNumber + ";" + this.columnNames.toString() + "]\n");
      for (DBDataRow row : this.rowValues)
         sb.append("[" + this.resultSetNumber + ";" + row.toString() + "]\n");

      return sb.toString();
   }
}

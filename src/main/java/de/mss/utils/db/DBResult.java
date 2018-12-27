package de.mss.utils.db;

import java.math.BigDecimal;
import java.util.ArrayList;

public class DBResult {

   public static final String     AS_STRING_VALUE     = null;
   public static final Integer    AS_INTEGER_VALUE    = null;
   public static final Boolean    AS_BOOLEAN_VALUE    = null;
   public static final BigDecimal AS_BIGDECIMAL_VALUE = null;

   ArrayList<DBSingleResult> resultList = null;


   public DBResult() {
      this.resultList = new ArrayList<>();
   }


   public int getNumberOfResults() {
      return this.resultList.size();
   }


   public int getColumnCount(int resultSetNumber) {
      if (resultSetNumber < 0 || resultSetNumber >= this.resultList.size())
         return 0;

      return this.resultList.get(resultSetNumber).getColumnCount();
   }


   public int getRowCount(int resultSetNumber) {
      if (resultSetNumber < 0 || resultSetNumber >= this.resultList.size())
         return 0;

      return this.resultList.get(resultSetNumber).getRowCount();
   }


   public int addResult(DBSingleResult result) {
      if (result == null)
         return -1;

      this.resultList.add(result);

      return this.resultList.size();
   }


   public BigDecimal getValue(int resultSetNumber, int column, int row, BigDecimal asBigDecimalValue) {
      return getValue(resultSetNumber, column, row, (BigDecimal)null, asBigDecimalValue);
   }


   public BigDecimal getValue(int resultSetNumber, int column, int row, BigDecimal defaultValue, BigDecimal asBigDecimalValue) {
      if (!checkResultSetNumber(resultSetNumber))
         return defaultValue;

      return this.resultList.get(resultSetNumber).getValue(column, row, defaultValue, asBigDecimalValue);
   }


   public BigDecimal getValue(int resultSetNumber, String columnName, int row, BigDecimal asBigDecimalValue) {
      return getValue(resultSetNumber, columnName, row, (BigDecimal)null, asBigDecimalValue);
   }


   public BigDecimal getValue(int resultSetNumber, String columnName, int row, BigDecimal defaultValue, BigDecimal asBigDecimalValue) {
      if (!checkResultSetNumber(resultSetNumber))
         return defaultValue;

      return this.resultList.get(resultSetNumber).getValue(columnName, row, defaultValue, asBigDecimalValue);
   }


   public Boolean getValue(int resultSetNumber, int column, int row, Boolean asBooleanValue) {
      return getValue(resultSetNumber, column, row, (Boolean)null, asBooleanValue);
   }


   public Boolean getValue(int resultSetNumber, int column, int row, Boolean defaultValue, Boolean asBooleanValue) {
      if (!checkResultSetNumber(resultSetNumber))
         return defaultValue;

      return this.resultList.get(resultSetNumber).getValue(column, row, defaultValue, asBooleanValue);
   }


   public Boolean getValue(int resultSetNumber, String columnName, int row, Boolean asBooleanValue) {
      return getValue(resultSetNumber, columnName, row, (Boolean)null, asBooleanValue);
   }


   public Boolean getValue(int resultSetNumber, String columnName, int row, Boolean defaultValue, Boolean asBooleanValue) {
      if (!checkResultSetNumber(resultSetNumber))
         return defaultValue;

      return this.resultList.get(resultSetNumber).getValue(columnName, row, defaultValue, asBooleanValue);
   }


   public Integer getValue(int resultSetNumber, int column, int row, Integer asIntegerValue) {
      return getValue(resultSetNumber, column, row, (Integer)null, asIntegerValue);
   }


   public Integer getValue(int resultSetNumber, int column, int row, Integer defaultValue, Integer asIntegerValue) {
      if (!checkResultSetNumber(resultSetNumber))
         return defaultValue;

      return this.resultList.get(resultSetNumber).getValue(column, row, defaultValue, asIntegerValue);
   }


   public Integer getValue(int resultSetNumber, String columnName, int row, Integer asIntegerValue) {
      return getValue(resultSetNumber, columnName, row, (Integer)null, asIntegerValue);
   }


   public Integer getValue(int resultSetNumber, String columnName, int row, Integer defaultValue, Integer asIntegerValue) {
      if (!checkResultSetNumber(resultSetNumber))
         return defaultValue;

      return this.resultList.get(resultSetNumber).getValue(columnName, row, defaultValue, asIntegerValue);
   }


   public String getValue(int resultSetNumber, String ColumnName, int row) {
      return getValue(resultSetNumber, ColumnName, row, (String)null, (String)null);
   }


   public String getValue(int resultSetNumber, String ColumnName, int row, String defaultValue) {
      return getValue(resultSetNumber, ColumnName, row, defaultValue, (String)null);
   }


   public String getValue(int resultSetNumber, String columnName, int row, String defaultValue, String asStringValue) {
      if (!checkResultSetNumber(resultSetNumber))
         return defaultValue;

      return this.resultList.get(resultSetNumber).getValue(columnName, row, defaultValue, asStringValue);
   }


   public String getValue(int resultSetNumber, int column, int row) {
      return getValue(resultSetNumber, column, row, (String)null, (String)null);
   }


   public String getValue(int resultSetNumber, int column, int row, String defaultValue) {
      return getValue(resultSetNumber, column, row, defaultValue, (String)null);
   }


   public String getValue(int resultSetNumber, int column, int row, String defaultValue, String asStringValue) {
      if (!checkResultSetNumber(resultSetNumber))
         return defaultValue;

      return this.resultList.get(resultSetNumber).getValue(column, row, defaultValue, asStringValue);
   }


   private boolean checkResultSetNumber(int resultSetNumber) {
      return (resultSetNumber >= 0 && resultSetNumber < this.resultList.size());
   }


   public String toString() {
      StringBuilder sb = new StringBuilder();

      for (DBSingleResult res : this.resultList)
         sb.append(res.toString());

      return sb.toString();
   }
}

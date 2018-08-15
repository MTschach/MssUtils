package de.mss.utils.db;

import java.util.ArrayList;

public class DBResult {

   ArrayList<DBSingleResult> resultList = null;


   public DBResult() {
      resultList = new ArrayList<>();
   }


   public int getNumberOfResults() {
      return resultList.size();
   }


   public int getColumnCount(int resultSetNumber) {
      if (resultSetNumber < 0 || resultSetNumber >= resultList.size())
         return 0;

      return resultList.get(resultSetNumber).getColumnCount();
   }


   public int getRowCount(int resultSetNumber) {
      if (resultSetNumber < 0 || resultSetNumber >= resultList.size())
         return 0;

      return resultList.get(resultSetNumber).getRowCount();
   }


   public int addResult(DBSingleResult result) {
      if (result == null)
         return -1;

      resultList.add(result);

      return resultList.size();
   }


   public String toString() {
      StringBuilder sb = new StringBuilder();

      for (DBSingleResult res : resultList)
         sb.append(res.toString());

      return sb.toString();
   }
}

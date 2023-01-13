package de.mss.utils;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class UUIDTool {

   private static List<String> uuids      = null;
   private static int          uuidsIndex = 0;

   public static String getUuid() {
      if ((uuids == null) || uuids.isEmpty()) {
         return UUID.randomUUID().toString();
      }

      final String uuid = uuids.get(uuidsIndex++ );
      if (uuidsIndex >= uuids.size()) {
         uuidsIndex = 0;
      }

      return uuid;
   }


   public static void initUuidsForTest(List<String> l) {
      uuids = l;
      uuidsIndex = 0;
   }


   public static void initUuidsForTest(String uuid) {
      if (uuid == null) {
         initUuidsForTest((List<String>)null);
         return;
      }
      initUuidsForTest(Arrays.asList(new String[] {uuid}));
   }


   public static void initUuidsForTest(String[] l) {
      if (l == null) {
         initUuidsForTest((List<String>)null);
         return;
      }
      initUuidsForTest(Arrays.asList(l));
   }


   private UUIDTool() {}
}

package de.mss.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class UUIDToolTest {

   @AfterEach
   public void tearDown() {
      UUIDTool.initUuidsForTest((List<String>)null);
   }


   @Test
   public void test() {
      assertNotNull(UUIDTool.getUuid());
   }


   @Test
   public void testString() {
      UUIDTool.initUuidsForTest("uuid1");

      assertEquals("uuid1", UUIDTool.getUuid());
      assertEquals("uuid1", UUIDTool.getUuid());
   }


   @Test
   public void testStringArray() {
      UUIDTool.initUuidsForTest(new String[] {"uuid1", "uuid2"});

      assertEquals("uuid1", UUIDTool.getUuid());
      assertEquals("uuid2", UUIDTool.getUuid());
      assertEquals("uuid1", UUIDTool.getUuid());
   }


   @Test
   public void testStringList() {
      final List<String> uuids = new ArrayList<>();
      uuids.add("uuid1");
      uuids.add("uuid2");
      uuids.add("uuid3");
      UUIDTool.initUuidsForTest(uuids);

      assertEquals("uuid1", UUIDTool.getUuid());
      assertEquals("uuid2", UUIDTool.getUuid());
      assertEquals("uuid3", UUIDTool.getUuid());
      assertEquals("uuid1", UUIDTool.getUuid());
   }
}

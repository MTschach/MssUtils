package de.mss.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class CacheMapTest {

   private static final String       TEST_VALUE1 = "Testvalue 1234567 abcdef";
   private static final String       TEST_VALUE2 = "blieh blah blubb";

   private CacheMap<Integer, String> cacheMap    = null;

   @BeforeEach
   public void setUp() throws Exception {

      this.cacheMap = new CacheMap<>(1000l);
      this.cacheMap.put(Integer.valueOf(1), TEST_VALUE1);
      this.cacheMap.put(Integer.valueOf(2), TEST_VALUE2);
      this.cacheMap.put(Integer.valueOf(0), null);
   }


   @Test
   public void testClear() {
      this.cacheMap.clear();
      assertNull(this.cacheMap.get(Integer.valueOf(1)));
      assertNull(this.cacheMap.get(Integer.valueOf(2)));
      assertNull(this.cacheMap.get(Integer.valueOf(3)));
      assertTrue(this.cacheMap.isEmpty());
   }


   @Test
   public void testContainsKey() {
      assertTrue(this.cacheMap.containsKey(Integer.valueOf(1)));
      assertTrue(this.cacheMap.containsKey(Integer.valueOf(2)));
      assertFalse(this.cacheMap.containsKey(Integer.valueOf(3)));
   }


   @Test
   public void testGet() {
      assertNull(this.cacheMap.get(Integer.valueOf(0)));
      assertEquals(TEST_VALUE1, this.cacheMap.get(Integer.valueOf(1)));
      assertEquals(TEST_VALUE2, this.cacheMap.get(Integer.valueOf(2)));
      assertNull(this.cacheMap.get(Integer.valueOf(3)));
   }


   @Test
   public void testGetMethods() {
      assertNotNull(this.cacheMap.entrySet());
      assertFalse(this.cacheMap.isEmpty());
      assertNotNull(this.cacheMap.keySet());
      assertNotNull(this.cacheMap.values());
      assertEquals(Integer.valueOf(2), Integer.valueOf(this.cacheMap.size()));
   }


   @Test
   public void testInvalid() throws InterruptedException {
      Thread.sleep(1500l);

      assertTrue(this.cacheMap.isEmpty());
      assertNull(this.cacheMap.get(Integer.valueOf(1)));
      assertNull(this.cacheMap.get(Integer.valueOf(2)));
      assertNull(this.cacheMap.get(Integer.valueOf(3)));

      assertNull(this.cacheMap.remove(Integer.valueOf(1)));
      assertNull(this.cacheMap.put(Integer.valueOf(12), "Test12"));
   }


   @Test
   public void testPut() {
      this.cacheMap.put(Integer.valueOf(4), "Test4");
      this.cacheMap.put(Integer.valueOf(0), "");
      assertEquals(TEST_VALUE1, this.cacheMap.get(Integer.valueOf(1)));
      assertEquals(TEST_VALUE2, this.cacheMap.get(Integer.valueOf(2)));
      assertNull(this.cacheMap.get(Integer.valueOf(3)));
      assertEquals("Test4", this.cacheMap.get(Integer.valueOf(4)));
   }


   @Test
   public void testPutaLL() {
      this.cacheMap.putAll(null);
      final Map<Integer, String> m = new HashMap<>();
      this.cacheMap.putAll(m);
      m.put(Integer.valueOf(4), "Test4");
      this.cacheMap.putAll(m);
      assertEquals(TEST_VALUE1, this.cacheMap.get(Integer.valueOf(1)));
      assertEquals(TEST_VALUE2, this.cacheMap.get(Integer.valueOf(2)));
      assertNull(this.cacheMap.get(Integer.valueOf(3)));
      assertEquals("Test4", this.cacheMap.get(Integer.valueOf(4)));
   }


   @Test
   public void testRemove() {
      this.cacheMap.remove(Integer.valueOf(1));
      assertNull(this.cacheMap.get(Integer.valueOf(1)));
      assertEquals(TEST_VALUE2, this.cacheMap.get(Integer.valueOf(2)));
      assertNull(this.cacheMap.get(Integer.valueOf(3)));
   }
}

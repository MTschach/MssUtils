package de.mss.utils;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import junit.framework.TestCase;

public class CacheMapTest extends TestCase {

   private static final String       TEST_VALUE1 = "Testvalue 1234567 abcdef";
   private static final String       TEST_VALUE2 = "blieh blah blubb";

   private CacheMap<Integer, String> cacheMap    = null;


   @Override
   public void setUp() throws Exception {
      super.setUp();

      this.cacheMap = new CacheMap<>(1000l);
      this.cacheMap.put(Integer.valueOf(1), TEST_VALUE1);
      this.cacheMap.put(Integer.valueOf(2), TEST_VALUE2);
      this.cacheMap.put(Integer.valueOf(0), null);
   }


   @Test
   public void testClear() {
      this.cacheMap.clear();
      assertNull("Get 1", this.cacheMap.get(Integer.valueOf(1)));
      assertNull("Get 2", this.cacheMap.get(Integer.valueOf(2)));
      assertNull("Get 3", this.cacheMap.get(Integer.valueOf(3)));
      assertTrue("empty", this.cacheMap.isEmpty());
   }


   @Test
   public void testContainsKey() {
      assertTrue("Contains 1", this.cacheMap.containsKey(Integer.valueOf(1)));
      assertTrue("Contains 2", this.cacheMap.containsKey(Integer.valueOf(2)));
      assertFalse("Contains 3", this.cacheMap.containsKey(Integer.valueOf(3)));
   }


   @Test
   public void testGet() {
      assertNull("Get 0", this.cacheMap.get(Integer.valueOf(0)));
      assertEquals("Get 1", TEST_VALUE1, this.cacheMap.get(Integer.valueOf(1)));
      assertEquals("Get 2", TEST_VALUE2, this.cacheMap.get(Integer.valueOf(2)));
      assertNull("Get 3", this.cacheMap.get(Integer.valueOf(3)));
   }


   @Test
   public void testGetMethods() {
      assertNotNull("entry set", this.cacheMap.entrySet());
      assertFalse("is empty", this.cacheMap.isEmpty());
      assertNotNull("key set", this.cacheMap.keySet());
      assertNotNull("values", this.cacheMap.values());
      assertEquals("size", Integer.valueOf(2), Integer.valueOf(this.cacheMap.size()));
   }


   @Test
   public void testInvalid() throws InterruptedException {
      Thread.sleep(1500l);

      assertTrue("empty", this.cacheMap.isEmpty());
      assertNull("Get 1", this.cacheMap.get(Integer.valueOf(1)));
      assertNull("Get 2", this.cacheMap.get(Integer.valueOf(2)));
      assertNull("Get 3", this.cacheMap.get(Integer.valueOf(3)));

      assertNull("remove", this.cacheMap.remove(Integer.valueOf(1)));
      assertNull("put", this.cacheMap.put(Integer.valueOf(12), "Test12"));
   }


   @Test
   public void testPut() {
      this.cacheMap.put(Integer.valueOf(4), "Test4");
      this.cacheMap.put(Integer.valueOf(0), "");
      assertEquals("Get 1", TEST_VALUE1, this.cacheMap.get(Integer.valueOf(1)));
      assertEquals("Get 2", TEST_VALUE2, this.cacheMap.get(Integer.valueOf(2)));
      assertNull("Get 3", this.cacheMap.get(Integer.valueOf(3)));
      assertEquals("Get 4", "Test4", this.cacheMap.get(Integer.valueOf(4)));
   }


   @Test
   public void testPutaLL() {
      this.cacheMap.putAll(null);
      final Map<Integer, String> m = new HashMap<>();
      this.cacheMap.putAll(m);
      m.put(Integer.valueOf(4), "Test4");
      this.cacheMap.putAll(m);
      assertEquals("Get 1", TEST_VALUE1, this.cacheMap.get(Integer.valueOf(1)));
      assertEquals("Get 2", TEST_VALUE2, this.cacheMap.get(Integer.valueOf(2)));
      assertNull("Get 3", this.cacheMap.get(Integer.valueOf(3)));
      assertEquals("Get 4", "Test4", this.cacheMap.get(Integer.valueOf(4)));
   }


   @Test
   public void testRemove() {
      this.cacheMap.remove(Integer.valueOf(1));
      assertNull("Get 1", this.cacheMap.get(Integer.valueOf(1)));
      assertEquals("Get 2", TEST_VALUE2, this.cacheMap.get(Integer.valueOf(2)));
      assertNull("Get 3", this.cacheMap.get(Integer.valueOf(3)));
   }
}

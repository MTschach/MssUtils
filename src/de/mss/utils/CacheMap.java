package de.mss.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


public class CacheMap<K extends Object, V extends Object> {

   private long                        cacheLeaseTime = 3600000L;
   private final Map<K, CacheValue<V>> map;


   public CacheMap(long t) {
      this.cacheLeaseTime = t;
      this.map = new HashMap<>();
   }


   private void cleanUp(Object key) {
      if (!this.map.containsKey(key)) {
         return;
      }

      if (this.map.get(key) == null || this.map.get(key).getValue() == null) {
         this.map.remove(key);
      }
   }


   public void clear() {
      this.map.clear();
   }


   public boolean containsKey(Object k) {
      cleanUp(k);
      return this.map.containsKey(k);
   }


   public Set<Entry<K, V>> entrySet() {
      return mapForMethods().entrySet();
   }


   public V get(Object key) {
      cleanUp(key);
      if (!this.map.containsKey(key)) {
         return null;
      }

      return this.map.get(key).getValue();
   }


   public boolean isEmpty() {
      return mapForMethods().isEmpty();
   }


   public Set<K> keySet() {
      return mapForMethods().keySet();
   }


   private Map<K, V> mapForMethods() {
      final Map<K, V> ret = new HashMap<>();

      for (final Entry<K, CacheValue<V>> entry : this.map.entrySet()) {
         if (entry.getValue().getValue() != null) {
            ret.put(entry.getKey(), entry.getValue().getValue());
         }
      }

      return ret;
   }


   public V put(K key, V value) {
      final CacheValue<V> oldValue = this.map.put(key, new CacheValue<>(value, this.cacheLeaseTime));

      return oldValue != null ? oldValue.getValue() : null;
   }


   public void putAll(Map<? extends K, ? extends V> list) {
      if (list == null || list.isEmpty()) {
         return;
      }

      for (final Entry<? extends K, ? extends V> entry : list.entrySet()) {
         this.map.put(entry.getKey(), new CacheValue<>(entry.getValue(), this.cacheLeaseTime));
      }
   }


   public V remove(Object key) {
      final CacheValue<V> oldValue = this.map.remove(key);
      return oldValue != null ? oldValue.getValue() : null;
   }


   public int size() {
      return mapForMethods().size();
   }


   public Collection<V> values() {
      return mapForMethods().values();
   }
}

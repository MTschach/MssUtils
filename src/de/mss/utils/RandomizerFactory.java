package de.mss.utils;

import java.util.HashMap;
import java.util.Random;

public class RandomizerFactory {

   protected static HashMap<String, Random> randomizerMap = new HashMap<>();

   private RandomizerFactory() {}


   public static void createInstance(String name, Random rand) {
      if (!randomizerMap.containsKey(name))
         randomizerMap.put(name, rand);
   }


   public static Random getInstance(String name) {
      Random rand = randomizerMap.get(name);

      if (rand == null)
         rand = getDefaultInstance();

      return rand;
   }


   public static Random getDefaultInstance() {
      return new Random();
   }


   public static void closeInstance(String name) {
      if (randomizerMap.containsKey(name))
         randomizerMap.remove(name);
   }


   public static void closeAllInstances() {
      randomizerMap.clear();
   }

}

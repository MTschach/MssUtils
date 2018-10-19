package de.mss.utils;

import java.util.HashMap;
import java.util.Random;

public class RandomizerFactory {

   protected static HashMap<String, Random> randomizerMap = new HashMap<>();

   static {
      randomizerMap.put("default", new Random());
   }


   public static Random createInstance(String name, Random rand) {
      if (!randomizerMap.containsKey(name))
         randomizerMap.put(name, rand);

      return randomizerMap.get(name);
   }

}

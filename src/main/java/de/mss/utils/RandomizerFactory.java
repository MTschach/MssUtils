package de.mss.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import de.mss.utils.exception.Error;
import de.mss.utils.exception.ErrorCodes;
import de.mss.utils.exception.MssException;

public class RandomizerFactory {

   protected static HashMap<String, Random> randomizerMap = new HashMap<>();

   static {
      randomizerMap.put("default", new Random());
   }


   public RandomizerFactory() throws MssException {
      throw new MssException(
            new Error(
                  ErrorCodes.ERROR_NOT_INSTANCABLE.getErrorCode(),
                  ErrorCodes.ERROR_NOT_INSTANCABLE.getErrorText() + " (" + getClass().getName() + ")"));
   }


   public static Random createInstance(String name, Random rand) {
      if (!randomizerMap.containsKey(name))
         randomizerMap.put(name, rand);

      return randomizerMap.get(name);
   }


   public static void closeInstance(String name) {
      if (randomizerMap.containsKey(name))
         randomizerMap.remove(name);
   }


   public static void closeAllInstances() {
      List<String> keys = new ArrayList<>();
      for (String key : randomizerMap.keySet()) {
         if (!"default".equals(key))
            keys.add(key);
      }

      for (String key : keys)
         closeInstance(key);
   }

}

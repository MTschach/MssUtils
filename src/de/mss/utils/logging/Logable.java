package de.mss.utils.logging;

import java.io.Serializable;
import java.util.Map;

public interface Logable extends Serializable {

   public Map<String, String> doLogging();

}

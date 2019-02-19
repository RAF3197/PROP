package lib;

import java.util.HashMap;
import java.util.Map;

/**
 * @author
 */
public final class Conf {
    private static Map<String, String> constants = new HashMap<String, String>()
    {
        {
            put("appName", "Hidato Game");
            put("width", "800");
            put("height", "700");
            put("env", "DEBUG");
        }
    };

    /**
     *
     * @param key
     * @return Return program constants with key
     */
    public static String getConst(String key) {
        return Conf.constants.get(key);
    }
}

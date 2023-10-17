package csci.project.server;

import java.util.HashMap;

public class RequestObject {

    private final HashMap<String, String> pairs;

    /**
     * Abstraction from a HashMap with extra helper functions for handling requests and responses
     */
    public RequestObject() {
        this.pairs = new HashMap<>();
    }

    /**
     * Adds a key value pair. The value will be stored as a String.
     * @param key The key in the key value pair
     * @param value The value in the key value pair
     */
    public void put(String key, Object value) {
        pairs.put(key, value.toString());
    }

    /**
     * Removes a key value pair
     * @param key The key of the key value pair to remove
     */
    public void remove(String key) {
        pairs.remove(key);
    }

    /**
     * Gets the value associated with the key
     * @param key Key to get the value for
     * @return Value associated with the key
     */
    public String get(String key) {
        return pairs.get(key);
    }

    /**
     * Gets the value associated with the key as an int
     * @param key Key to get the value for
     * @return Value associated with the key as an int
     */
    public int getAsInt(String key) {
        return Integer.parseInt(pairs.get(key));
    }

    /**
     * Gets the value associated with the key as a double
     * @param key Key to get the value for
     * @return Value associated with the key as a double
     */
    public double getAsDouble(String key) {
        return Double.parseDouble(pairs.get(key));
    }

    @Override
    public String toString() {
        String result = "";
        if (pairs.keySet().contains("response")) {
            result += String.format("response : %s\n", pairs.get("response"));
        }
        if (pairs.keySet().contains("current")) {
            result += String.format("current : %s\n", pairs.get("current"));
        }
        for (String key : pairs.keySet()) {
            if (!key.equals("response") && !key.equals("current")) {
                result += String.format("%s : %s\n", key, pairs.get(key));
            }
        }
        return result.strip();
    }

}

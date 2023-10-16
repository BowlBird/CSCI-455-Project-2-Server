package csci.project.server;

import java.util.HashMap;

public class RequestObject {

    private final HashMap<String, String> pairs;

    public RequestObject() {
        this.pairs = new HashMap<>();
    }

    public void put(String key, Object value) {
        pairs.put(key, value.toString());
    }

    public void remove(String key) {
        pairs.remove(key);
    }

    public String get(String key) {
        return pairs.get(key);
    }

    public int getAsInt(String key) {
        return Integer.parseInt(pairs.get(key));
    }

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

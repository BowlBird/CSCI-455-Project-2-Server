package csci.project.server;

public class RequestParser {

    public static RequestObject parse(String request) {
        RequestObject result = new RequestObject();
        String[] lines = request.split("\n");
        for (String line : lines) {
            String[] parts = line.split(":");
            String key = parts[0].strip();
            String value = parts[1].strip();
            result.put(key, value);
        }
        return result;
    }

}

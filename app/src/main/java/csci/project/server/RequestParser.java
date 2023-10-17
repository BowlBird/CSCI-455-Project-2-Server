package csci.project.server;

public class RequestParser {

    /**
     * Parses a RequestObject from a request string sent from a client
     * @param request Request string sent from the client
     * @return RequestObject generated from parsing the request string
     */
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

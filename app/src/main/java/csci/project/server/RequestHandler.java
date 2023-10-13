package csci.project.server;

public class RequestHandler {

    public static RequestObject handle(RequestObject request) {
        RequestObject response;
        switch (request.get("endpoint")) {
            case "DONATE":
                response = handleDonate(request);
                break;
            default:
                response = new RequestObject();
                break;
        }
        return response;
    }

    private static RequestObject handleDonate(RequestObject request) {
        RequestObject response = new RequestObject();
        response.put("data", "Hello from server API");
        return response;
    }

}

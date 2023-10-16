package csci.project.server;

import java.util.Date;

public class RequestHandler {

    public static RequestObject handle(RequestObject request, Database dbcon) {
        RequestObject response;
        switch (request.get("endpoint")) {
            case "DONATE":
                response = handleDonate(request, dbcon);
                break;
            case "LIST":
                response = handleList(request, dbcon);
                break;
            default:
                response = new RequestObject();
                break;
        }
        return response;
    }

    private static RequestObject handleDonate(RequestObject request, Database dbcon) {
        RequestObject response = new RequestObject();
        response.put("response", "DONATE");
        response.put("data", "Hello from server API");
        return response;
    }

    private static RequestObject handleList(RequestObject request, Database dbcon) {
        RequestObject response = new RequestObject();
        response.put("response", "LIST");
        Event[] events = dbcon.getAllEvents();
        for (Event event : events) {
            if (event.endDate().compareTo(new Date(System.currentTimeMillis())) > 0 == Boolean.parseBoolean(request.get("current"))) {
                response.put("" + event.id(), event.toMessageString());
            }
        }
        return response;
    }

}

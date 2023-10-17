package csci.project.server;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class RequestHandler {

    /**
     * Handles a request from a client
     * @param request Request from the client
     * @param dbcon Database connection for the client
     * @return Response to send to the client
     */
    public static RequestObject handle(RequestObject request, Database dbcon) {
        RequestObject response;
        switch (request.get("endpoint")) {
            case "DONATE":
                response = handleDonate(request, dbcon);
                break;
            case "LIST":
                response = handleList(request, dbcon);
                break;
            case "CREATE":
                response = handleCreate(request, dbcon);
                break;
            default:
                response = new RequestObject();
                break;
        }
        return response;
    }

    /**
     * Handles a DONATE request
     * @param request Request from the client
     * @param dbcon Database connection for the client
     * @return Response to send to the client
     */
    private static RequestObject handleDonate(RequestObject request, Database dbcon) {
        Optional<Event> event = dbcon.getEvent(request.getAsInt("id"));
        RequestObject response = new RequestObject();
        response.put("response", "DONATE");
        response.put("successful", false);
        event.ifPresent(e -> {
            dbcon.putEvent(
                    new Event(e.id(), e.name(), e.goal(), e.balance() + request.getAsDouble("amount"), e.endDate()));
            response.put("successful", true);
        });
        return response;
    }

    /**
     * Handles a LIST request
     * @param request Request from the client
     * @param dbcon Database connection for the client
     * @return Response to send to the client
     */
    private static RequestObject handleList(RequestObject request, Database dbcon) {
        RequestObject response = new RequestObject();
        response.put("response", "LIST");
        response.put("current", request.get("current"));
        Event[] events = dbcon.getAllEvents();
        for (Event event : events) {
            if (event.endDate().compareTo(new Date(System.currentTimeMillis())) > 0 == Boolean
                    .parseBoolean(request.get("current"))) {
                response.put("" + event.id(), event.toMessageString());
            }
        }
        return response;
    }

    /**
     * Handles a CREATE request
     * @param request Request from the client
     * @param dbcon Database connection for the client
     * @return Response to send to the client
     */
    private static RequestObject handleCreate(RequestObject request, Database dbcon) {
        RequestObject response = new RequestObject();
        response.put("response", "CREATE");
        response.put("id", -1);
        try {
            PartialEvent newEvent = new PartialEvent(
                    request.get("name"),
                    request.getAsDouble("targetAmount"),
                    0.0,
                    new SimpleDateFormat("yyyy-MM-dd").parse(request.get("deadline"))
            );
            int id = dbcon.createEvent(newEvent);
            response.put("id", id);
            return response;
        } catch (Exception e) {
            return response;
        }
    }

}

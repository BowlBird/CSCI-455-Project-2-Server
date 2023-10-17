package csci.project.server;

import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import java.util.Date;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.text.SimpleDateFormat;

public class Database {

    private static final ReentrantLock lock = new ReentrantLock();
    private final HttpClient client;

    public Database() {
        this.client = HttpClient.newHttpClient();
    }

    private int getAndIncrementNextId() {
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(buildURI("nextId"))
                .GET()
                .build();
        lock.lock();
        int id = client.sendAsync(getRequest, BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(Integer::parseInt)
                .join();
        HttpRequest incrementRequest = HttpRequest.newBuilder()
                .uri(buildURI("nextId"))
                .PUT(BodyPublishers.ofString("" + (id + 1)))
                .build();
        client.sendAsync(incrementRequest, BodyHandlers.ofString()).join();
        lock.unlock();
        return id;
    }

    public void putEvent(Event event) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(buildURI("events/_" + event.id()))
                .PUT(BodyPublishers.ofString(event.toDatabaseString()))
                .build();
        lock.lock();
        client.sendAsync(request, BodyHandlers.ofString())
                .join();
        lock.unlock();
    }

    public int createEvent(PartialEvent partialEvent) {
        int id = getAndIncrementNextId();
        Event event = partialEvent.addId(id);
        putEvent(event);
        return id;
    }

    public Optional<Event> getEvent(int id) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(buildURI("events/_" + id))
                .GET()
                .build();

        Function<String, Optional<Event>> parseEvent = str -> {
            try {
                String trimmed = str.substring(1, str.length() - 1).replaceAll("\"", "");
                String[] parts = trimmed.split("(,|:)");
                double balance = Double.parseDouble(parts[1]);
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(parts[3]);
                double goal = Double.parseDouble(parts[5]);
                String name = parts[7];
                return Optional.ofNullable(new Event(id, name, balance, goal, date));
            } catch (Exception e) {
                return Optional.empty();
            }
        };

        lock.lock();
        Optional<Event> result = client.sendAsync(request, BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(body -> {
                    if (body.equals("null")) {
                        Optional<String> x = Optional.empty();
                        return x;
                    }
                    return Optional.ofNullable(body);
                })
                .thenApply(body -> body.flatMap(parseEvent))
                .join();
        lock.lock();
        return result;
    }

    public Event[] getAllEvents() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(buildURI("events"))
                .GET()
                .build();

        Function<String, Event[]> parseEvents = str -> {
            try {
                String trimmed = str.replaceAll("([|]|\\{|\\}|\")", "");
                String[] parts = trimmed.split("(,|:)");
                Event[] result = new Event[parts.length / 9];
                for (int i = 0; i < result.length; i++) {
                    int offset = i * 9;
                    int id = Integer.parseInt(parts[offset].substring(1));
                    double balance = Double.parseDouble(parts[offset + 2]);
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(parts[offset + 4]);
                    double goal = Double.parseDouble(parts[offset + 6]);
                    String name = parts[offset + 8];
                    result[i] = new Event(id, name, goal, balance, date);
                }
                return result;
            } catch (Exception e) {
                return new Event[0];
            }
        };

        lock.lock();
        Event[] result = client.sendAsync(request, BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(body -> {
                    if (body.equals("null")) {
                        Optional<String> x = Optional.empty();
                        return x;
                    }
                    return Optional.ofNullable(body);
                })
                .thenApply(body -> body.map(parseEvents).orElse(new Event[0]))
                .join();
        lock.unlock();
        return result;
    }

    private URI buildURI(String endpointId) {
        return URI.create(String.format("https://csci-455-project-1-default-rtdb.firebaseio.com/%s.json", endpointId));
    }

}

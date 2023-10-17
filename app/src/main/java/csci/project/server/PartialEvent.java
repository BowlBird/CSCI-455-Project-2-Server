package csci.project.server;

import java.util.Date;

public record PartialEvent(String name, double goal, double balance, Date endDate) {

    /**
     * Attaches an ID to a PartialEvent and returns an Event with the same data and the given ID
     * @param id ID to give the Event
     * @return Event with the same data and the given ID
     */
    public Event addId(int id) {
        return new Event(id, name, goal, balance, endDate);
    }

}

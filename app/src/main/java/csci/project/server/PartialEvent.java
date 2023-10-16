package csci.project.server;

import java.util.Date;

public record PartialEvent(String name, double goal, double balance, Date endDate) {

    public Event addId(int id) {
        return new Event(id, name, goal, balance, endDate);
    }

}

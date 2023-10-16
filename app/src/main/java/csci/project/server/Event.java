package csci.project.server;

import java.text.SimpleDateFormat;
import java.util.Date;

public record Event(int id, String name, double goal, Date endDate) {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public String toMessageString() {
        return String.format("%s,%f,%s", name, goal, dateFormat.format(endDate));
    }

    public String toDatabaseString() {
        return String.format("{\"name\":\"%s\",\"goal\":\"%f\",\"endDate\":\"%s\"}", name, goal, dateFormat.format(endDate));
    }

}

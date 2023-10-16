package csci.project.server;

import java.text.SimpleDateFormat;
import java.util.Date;

public record Event(int id, String name, double goal, double balance, Date endDate) {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public String toMessageString() {
        return String.format("%s,%f,%f,%s", name, goal, balance, dateFormat.format(endDate));
    }

    public String toDatabaseString() {
        return String.format("{\"name\":\"%s\",\"goal\":%f,\"balance\":%f,\"endDate\":\"%s\"}", name, goal, balance, dateFormat.format(endDate));
    }

}

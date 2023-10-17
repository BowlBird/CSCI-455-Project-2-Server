package csci.project.server;

import java.text.SimpleDateFormat;
import java.util.Date;

public record Event(int id, String name, double goal, double balance, Date endDate) {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Creates a string of the data elements for sending in a response message
     * @return String to send as the value in a response message
     */
    public String toMessageString() {
        return String.format("%s,%f,%f,%s", name, goal, balance, dateFormat.format(endDate));
    }

    /**
     * Creates a string of the data elements for writing to the database
     * @return String for writing to the database
     */
    public String toDatabaseString() {
        return String.format("{\"name\":\"%s\",\"goal\":%f,\"balance\":%f,\"endDate\":\"%s\"}", name, goal, balance, dateFormat.format(endDate));
    }

}

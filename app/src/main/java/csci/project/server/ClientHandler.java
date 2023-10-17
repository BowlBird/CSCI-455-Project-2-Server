package csci.project.server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.function.BooleanSupplier;

public class ClientHandler implements Runnable {

    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    private final BooleanSupplier isConnected;
    private final Runnable closeConnection;
    private final Database dbcon;

    /**
     * The ClientHandler handles a single client's requests and responses
     * @param connection The socket connection with the client
     */
    public ClientHandler(Socket connection) throws IOException {
        this.outputStream = new ObjectOutputStream(connection.getOutputStream());
        this.outputStream.flush();
        this.inputStream = new ObjectInputStream(connection.getInputStream());
        this.isConnected = () -> connection.isConnected() || connection.isInputShutdown()
                || connection.isOutputShutdown();
        this.closeConnection = () -> {
            try {
                connection.close();
            } catch (Exception e) {
            }
        };
        this.dbcon = new Database();
    }

    @Override
    public void run() {
        try {
            while (this.isConnected.getAsBoolean()) {
                RequestObject request = RequestParser.parse((String) inputStream.readObject()); // Get the client request
                System.out.printf("HEARD\n%s\n", request.toString());
                RequestObject response = RequestHandler.handle(request, dbcon); // Handle the client request and get the response
                System.out.printf("SENDING\n%s\n", response.toString());
                outputStream.writeObject(response.toString()); // Send the response
                outputStream.flush();
            } // Continue until the client disconnects or an error occurs
        } catch (EOFException e) {
        } catch (ClassNotFoundException e) {
        } catch (IOException e) {
        } finally {
            // Close the connection when the client disconnects or when an error occurs
            System.out.println("Closing the connection");
            this.closeConnection.run();
        }
    }

}

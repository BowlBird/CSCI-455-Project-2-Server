package csci.project.server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private final ObjectOutputStream outputStream;
    private final InputStreamReader inputStreamReader;

    public ClientHandler(Socket connection) throws IOException {
        this.outputStream = new ObjectOutputStream(connection.getOutputStream());
        this.inputStreamReader = new InputStreamReader(connection.getInputStream());
    }

    @Override
    public void run() {
        
    }

}

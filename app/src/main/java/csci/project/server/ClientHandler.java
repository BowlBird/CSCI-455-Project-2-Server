package csci.project.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class ClientHandler implements Runnable {

    private final InetSocketAddress returnAddress;
    private final RequestObject request;
    private final Database dbcon;

    /**
     * The ClientHandler handles a single client's requests and responses
     * @param connection The socket connection with the client
     */
    public ClientHandler(InetSocketAddress returnAddress, RequestObject request) throws IOException {
        this.returnAddress = returnAddress;
        this.request = request;
        this.dbcon = new Database();
    }

    @Override
    public void run() {
        try {
            RequestObject response = RequestHandler.handle(this.request, this.dbcon);
            System.out.println("HEARD:\n" + this.request.toString());
            System.out.println("SENDING:\n" + response.toString());
            DatagramPacket packet = new DatagramPacket(response.toString().getBytes(), response.toString().length(), this.returnAddress);
            DatagramSocket socket = new DatagramSocket();
            socket.send(packet);
            socket.close();
        } catch (Exception e) {}
    }

}

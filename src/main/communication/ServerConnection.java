package communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Implements the connection logic by prompting the end user for
 * the communication's IP address, connecting, setting up streams, and
 * consuming the welcome messages from the communication.  The Capitalizer
 * protocol says that the communication sends three lines of text to the
 * client immediately after establishing a connection.
 */

public class ServerConnection {
    ObjectInputStream in;
    ObjectOutputStream out;
    Socket socket;


    ServerConnection(Socket socket) throws Exception {
        this.socket = socket;
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());

        Thread read = new Thread() {
            public void run() {
                while (true) {
                    try {
                        Object obj = in.readObject();
                        Client.messages.put(obj);
                    } catch (SocketException e) {
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        read.setDaemon(true);
        read.start();
    }

    public void write(Object obj) {
        try {
            out.writeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

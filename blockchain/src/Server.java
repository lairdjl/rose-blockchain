import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * A server program which accepts requests from clientList to
 * capitalize strings.  When clientList connect, a new thread is
 * started to handle an interactive dialog in which the client
 * sends in a string and the server thread sends back the
 * capitalized version of the string.
 * <p>
 * The program is runs in an infinite loop, so shutdown in platform
 * dependent.  If you ran it from a console window with the "java"
 * interpreter, Ctrl+C generally will shut it down.
 */
public class Server {

    /**
     * Application method to run the server runs in an infinite loop
     * listening on port 9898.  When a connection is requested, it
     * spawns a new thread to do the servicing and immediately returns
     * to listening.  The server keeps a unique client number for each
     * client that connects just to show interesting logging
     * messages.  It is certainly not necessary to do this.
     */

    private static Server server;
    private ServerSocket listener;
    private ArrayList<ClientConnection> clientList;
    private LinkedBlockingQueue<Object> messages;

    private Server() throws Exception {
        log("The server is running.");
        listener = new ServerSocket(9898);
        clientList = new ArrayList<ClientConnection>();
        messages = new LinkedBlockingQueue<Object>();


        Thread accept = new Thread() {
            public void run() {
                int clientNumber = 0;
                while (true) {
                    try {
                        Socket s = listener.accept();
                        clientList.add(new ClientConnection(s, clientNumber));
                    } catch (Exception e) {
                        log("error");
                    }
                }
            }
        };

        accept.setDaemon(true);
        accept.start();
        Thread messageHandling = new Thread() {
            public void run() {
                while (true) {
                    try {
                        Object message = messages.take();
                        // Do some handling here...
                        log("Message Received: " + message);
                    } catch (InterruptedException e) {
                    }
                }
            }
        };

        messageHandling.setDaemon(true);
        messageHandling.start();
    }

    public static Server getServer() {
        if (server == null) {
            try {
                server = new Server();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return server;
    }


    /**
     * A private thread to handle capitalization requests on a particular
     * socket.  The client terminates the dialogue by sending a single line
     * containing only a period.
     */
    private class ClientConnection {
        ObjectInputStream in;
        ObjectOutputStream out;
        Socket socket;
        int clientNumber;

        ClientConnection(Socket socket, int clientNumber) throws Exception {
            this.socket = socket;
            this.clientNumber = clientNumber;

            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            log("New connection with client# " + clientNumber + " at " + socket);
            Thread read = new Thread() {
                public void run() {
                    while (true) {
                        try {
                            Object obj = in.readObject();
                            if (obj != null || obj.toString().compareTo("") != 0) {
                                messages.put(obj);
                                sendToAll(obj);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            read.setDaemon(true); // terminate when main ends
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

    public void sendTo(int index, Object message) throws IndexOutOfBoundsException {
        clientList.get(index).write(message);
    }

    public void sendToAll(Object message) {
        for (ClientConnection client : clientList)
            client.write(message);
    }

    /**
     * Logs a simple message.  In this case we just write the
     * message to the server applications standard output.
     */
    private static void log(Object message) {
        System.out.println(message);
    }
}
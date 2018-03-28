package communication;

import com.google.gson.Gson;
import frontend.Frontend;

import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

import static helpers.Helpers.DEFAULT_PORT;
import static helpers.Helpers.DEFAULT_SERVER;

/**
 * A simple Swing-based client for the capitalization communication.
 * It has a main frame window with a text field for entering
 * strings and a textarea to see the results of capitalizing
 * them.
 */
public class Client {

    private ServerConnection server;
    protected static final LinkedBlockingQueue<Object> messages = new LinkedBlockingQueue<Object>();;
//    private Socket socket;
//    private String serverAddress;
    private int port;

    Frontend frontend;
    private static Gson gson = new Gson();

    /**
     * Constructs the client by laying out the GUI and registering a
     * listener with the textfield so that pressing Enter in the
     * listener sends the textfield contents to the communication.
     */
    public Client(){
        this(DEFAULT_SERVER, DEFAULT_PORT);
    }

    public Client(String serverAddress){
        this(serverAddress, DEFAULT_PORT);
    }

    public Client(String serverAddress, int port){
        this.port = port;

        Socket socket;
        try{
            socket = new Socket(serverAddress, this.port);
            this.server = new ServerConnection(socket);
            frontend = new Frontend(this, this.server, socket);

        }catch (Exception e){
            e.printStackTrace();
        }



        Thread messageHandling = new Thread() {
            public void run() {
                while (true) {
                    try {
                        Object message = messages.take();
                        // Do some handling here...
//                        frontend.messageArea.append(message + "\n");
                        appendMessageToFrontend(message);
                    } catch (InterruptedException e) {
                    }
                }
            }
        };

        messageHandling.setDaemon(true);
        messageHandling.start();
    }

    private void appendMessageToFrontend(Object message){
        frontend.messageArea.append(message + "\n");

    }

    public void addConnection(String serverAddress){
        System.out.println(serverAddress);

        try{
            Socket socket = new Socket(serverAddress, this.port);
            server = new ServerConnection(socket);

//            Socket socket2 = new Socket("192.168.1.238", this.port);
//            server = new ServerConnection(socket);

        }catch (Exception e){
            System.out.println("could not connect to new server");
        }
    }

    /**
     *
     * @param obj the object being sent to the communication
     */
    public void send(Object obj) {
        server.write(obj);
    }

    /**
     * Runs the client application.
     */
    public static void main(String[] args) {
        try {
            Client client = new Client("192.168.1.238");
            Client client2 = new Client("192.168.1.238");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
package communication;

import com.google.gson.Gson;
import frontend.Frontend;
import netscape.javascript.JSObject;
import com.google.gson.JsonObject;
import java.net.Socket;
import java.util.ArrayList;
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
    protected static final ArrayList<ServerConnection> serverList = new ArrayList<>();
    protected static final LinkedBlockingQueue<Object> messages = new LinkedBlockingQueue<Object>();;
//    private int port;

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
        Socket socket;
        try{
            socket = new Socket(serverAddress, port);
            this.serverList.add(new ServerConnection(socket));
            frontend = new Frontend(this, socket);

        }catch (Exception e){
            e.printStackTrace();
        }



        Thread messageHandling = new Thread() {
            public void run() {
                while (true) {
                    try {
                        JsonObject json = gson.fromJson(messages.take().toString(), JsonObject.class);

                        // Do some handling here...
//                        if(json.has("connections")){
//
//                        }else{
                            appendMessageToFrontend(json.toString());

//                        }
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

    public static void addConnection(String serverAddress, int port){
        try{
            Socket socket = new Socket(serverAddress, port);
            ServerConnection conn = new ServerConnection(socket);
            serverList.add(conn);
//            conn.write(serverList)

        }catch (Exception e){
            System.out.println("could not connect to new server");
        }
    }

    /**
     *
     * @param obj the object being sent to the communication
     */
    public void send(Object obj) {
        for(ServerConnection conn: this.serverList){
            conn.write(obj);
        }
    }

    public static ArrayList<ServerConnection> getServerList() {
        return serverList;
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
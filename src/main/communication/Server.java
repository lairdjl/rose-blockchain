package communication;

import blockchain.Blockchain;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;

import static helpers.Helpers.DEFAULT_PORT;
import static helpers.Helpers.getSocketIP;
import static helpers.Helpers.log;

/**
 * A communication program which accepts requests from clientList to
 * capitalize strings.  When clientList connect, a new thread is
 * started to handle an interactive dialog in which the client
 * sends in a string and the communication thread sends back the
 * capitalized version of the string.
 * <p>
 * The program is runs in an infinite loop, so shutdown in platform
 * dependent.  If you ran it from a console window with the "java"
 * interpreter, Ctrl+C generally will shut it down.
 */
public class Server {

    /**
     * Application method to run the communication runs in an infinite loop
     * listening on port 9898.  When a connection is requested, it
     * spawns a new thread to do the servicing and immediately returns
     * to listening.  The communication keeps a unique client number for each
     * client that connects just to show interesting logging
     * messages.  It is certainly not necessary to do this.
     */

    private static final Server server = new Server();
    private ServerSocket listener;
    protected static CopyOnWriteArrayList<ClientConnection> clientList;
    protected static LinkedBlockingQueue<Object> messages = new LinkedBlockingQueue<Object>();;

    private Server() {
        log("The communication is running.");
        boolean started = false;
        while(!started){
            try{
                listener = new ServerSocket(DEFAULT_PORT);
                started = true;
            }catch(Exception e){
                DEFAULT_PORT++;
            }
        }
        clientList = new CopyOnWriteArrayList<ClientConnection>();



        Thread accept = new Thread() {
            public void run() {
                int clientNumber = 0;
                while (true) {
                    try {
                        Socket s = listener.accept();
                        clientList.add(new ClientConnection(s, clientNumber));
                        boolean inList = false;
                        for(ServerConnection conn : Client.serverList){
                            if(getSocketIP(conn.socket).compareTo(getSocketIP(s)) == 0){
                                inList = true;
                                break;
                            }
                        }
                        if(!inList){
                            //TODO:Add connection from client to server here.
                            String serverAddress = getSocketIP(s);
                            System.out.println(serverAddress);
                            Client.addConnection(serverAddress, DEFAULT_PORT);
                        }
                        clientNumber++;
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
                Blockchain blockchain = Blockchain.getInstance();
                while (true) {
                    try {
                        Object message = messages.take();
                        // Do some handling here...
                        JsonElement jelement = new JsonParser().parse(message.toString());
                        JsonObject jobject = jelement.getAsJsonObject();

                        String sender = jobject.get("sender").toString();
                        String receiver = jobject.get("receiver").toString();
                        Object obj = jobject.get("obj");
                        blockchain.newTransaction(sender,receiver,obj);
                        log("Message Received: " + message);
                    } catch (InterruptedException e) {
                    }
                }
            }
        };

        messageHandling.setDaemon(true);
        messageHandling.start();

        Thread mining = new Thread() {
            public void run() {
                Blockchain blockchain = Blockchain.getInstance();

                while (true) {
                    if(blockchain.currentTransactions.size() > 0){
                        boolean wait = blockchain.mine();

                    }
                }
            }
        };

        mining.setDaemon(true);
        mining.start();
    }

    public static synchronized Server getInstance() {
        return server;
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
     * message to the communication applications standard output.
     */
}
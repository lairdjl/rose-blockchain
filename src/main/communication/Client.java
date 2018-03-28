package communication;

import com.google.gson.Gson;
import frontend.Frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
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
    private LinkedBlockingQueue<Object> messages;
    private Socket socket;
    private String serverAddress;
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
        this.serverAddress = serverAddress;
        this.port = port;

        this.messages = new LinkedBlockingQueue<Object>();
        try{
            this.socket = new Socket(this.serverAddress, this.port);
            this.server = new ServerConnection(socket,messages);
        }catch (Exception e){
            e.printStackTrace();
        }


        Frontend frontend = new Frontend(this.server, this.socket);

        Thread messageHandling = new Thread() {
            public void run() {
                while (true) {
                    try {
                        Object message = messages.take();
                        // Do some handling here...
                        frontend.messageArea.append(message + "\n");
                    } catch (InterruptedException e) {
                    }
                }
            }
        };

        messageHandling.setDaemon(true);
        messageHandling.start();
//        Blockchain blockchain = Blockchain.getInstance();
//        messageArea.append(Helpers.toPrettyFormat(blockchain.getBlockchainJSON()));

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
            Client client = new Client();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
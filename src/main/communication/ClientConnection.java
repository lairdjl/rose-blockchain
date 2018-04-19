package communication;

import helpers.ConnectionJSONObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static communication.Client.serverList;
import static helpers.Helpers.getJSON;
import static helpers.Helpers.log;
import static interfaces.JSONObjectInterface.gson;

/**
 * A private thread to handle capitalization requests on a particular
 * socket.  The client terminates the dialogue by sending a single line
 * containing only a period.
 */
public class ClientConnection {
    private ObjectInputStream in;
    private ObjectOutputStream out;
    protected Socket socket;
    private static final Server server = Server.getInstance();
    private int clientNumber;

    ClientConnection(Socket socket, int clientNumber) throws Exception {
        this.socket = socket;
        this.clientNumber = clientNumber;

        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());

//        server.clientList.add(this);
        log("New connection with client# " + clientNumber + " at " + socket);
        Thread read = new Thread() {
            public void run() {
                while (true) {
                    try {
                        Object obj = in.readObject();
                        if (obj != null || obj.toString().compareTo("") != 0) {
                            server.messages.put(obj);
                            server.sendToAll(obj);
                        }
                    }catch (Exception e) {
                        try{
                            socket.close();
                            System.out.println("Client# "+clientNumber +" connection lost");
                        }catch (Exception e2){
                            e.printStackTrace();
                        }finally {
                            break;
                        }
                    }
                }
            }
        };
        read.setDaemon(true); // terminate when main ends
        read.start();
        ConnectionJSONObject connectionsJSON = new ConnectionJSONObject(Client.getServerConnectionList());
        this.write(getJSON(connectionsJSON));
    }

    public void write(Object obj) {
        try {
            out.writeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
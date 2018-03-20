import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Client implements Runnable {

    protected static String server_IP = "141.117.57.42";
    private static final int server_Port = 5555;
    protected static String client_IP;


    Client() throws IOException {
        final String host = "localhost";
        int init = 0;

        try {
            InetAddress iAddress = InetAddress.getLocalHost();
            client_IP = iAddress.getHostAddress();
            System.out.println("Current IP address : " + client_IP);
        } catch (UnknownHostException e) {

        }

        try {
            Socket socket = new Socket(server_IP, server_Port);
            init = initialize(socket);

        } catch (SocketException e) {
            System.out.println("Error: Unable to connect");
        }


        if (init == 0) {
            System.out.println("error: Failed to initialize ");
            System.exit(0);

        }
        //Thread init_Thread = new Thread();
    }

    private static int initialize(Socket socket) throws IOException {
        int rt_value = 0;

        OutputStream os = socket.getOutputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter pw = new PrintWriter(os, true);

        pw.println("192.343.34.321");

        socket.close();
        return rt_value = 1;


    }

    public void run() {

    }

}
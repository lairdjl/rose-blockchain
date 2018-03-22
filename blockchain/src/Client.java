import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Client implements Runnable {

    private static String server_IP = "141.117.57.42";
    private static final int server_Port = 5555;
    private static Socket socket;
    private final String host = "localhost";

    private static String client_IP;
    private static InputStream is;
    private static BufferedReader br;
    private static Client client;

    private Client() throws IOException {

        int init = 0;

        try {
            InetAddress iAddress = InetAddress.getLocalHost();
            client_IP = iAddress.getHostAddress();
            System.out.println("Current IP address : " + client_IP);
        } catch (UnknownHostException e) {

        }

        try {
            socket = new Socket(server_IP, server_Port);
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

    public static Client getClient() {
        if(client == null){
            try{
                client = new Client();
            }catch (Exception e){
                return null;
            }
        }
        return client;
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
        readFromConsole();
    }

    private void readFromConsole(){
        try {

            is = System.in;
            br = new BufferedReader(new InputStreamReader(is));

            String line = null;

loop:        while ((line = br.readLine()) != null) {
                switch(line){
                    case "quit":
                        break loop;
                    case "m":
                        String address, message;

                        System.out.print("Enter Address : " );
                        address = br.readLine();

                        System.out.print("Enter Message : ");
                        message = br.readLine();

                        break;
                    case "h":
                        System.out.println("'quit' quits the application");
                        System.out.println("'m' sends a message");

                    default:
                        System.out.println("Type h for help");
                }
            }

        }
        catch (IOException ioe) {
            System.out.println("Exception while reading input " + ioe);
        }
        finally {
            // close the streams using close method
            try {
                if (br != null) {
                    br.close();
                }
            }
            catch (IOException ioe) {
                System.out.println("Error while closing stream: " + ioe);
            }

        }
    }

}
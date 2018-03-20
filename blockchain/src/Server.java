import java.io.*;
import java.net.*;
public class Server {

    protected static String server_IP;

    Server(){

        int server_Port = 5555;

        try {
            InetAddress iAddress = InetAddress.getLocalHost();
            server_IP = iAddress.getHostAddress();
            System.out.println("Server IP address : " + server_IP);
        } catch (UnknownHostException e) {

        }

        try {
            ServerSocket serverSocket = new ServerSocket(server_Port);

            while (true) {
                Socket socket = serverSocket.accept();

                OutputStream os = socket.getOutputStream();
                PrintWriter pw = new PrintWriter(os, true);
                InputStreamReader isr = new InputStreamReader(socket.getInputStream());
                pw.println("Connection confirmed ");
                BufferedReader br = new BufferedReader(isr);
                String str = br.readLine();

                pw.println("your ip address is " + str);

                pw.close();
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
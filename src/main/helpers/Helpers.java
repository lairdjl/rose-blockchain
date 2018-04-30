package helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import communication.ServerConnection;
import interfaces.JSONObjectInterface;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Enumeration;


public class Helpers implements JSONObjectInterface {
    public static final String MINED_ADDRESS = "0";

    public static final boolean DEBUG = true;
    private static MessageDigest md;

    public static final int INITIAL_DEFAULT_PORT = 9898;
    public static int DEFAULT_PORT = INITIAL_DEFAULT_PORT;
    public static final String DEFAULT_SERVER = "";


    public static String hashString(String str){
        try{
            //hash function not encryption
            md = MessageDigest.getInstance("SHA-256");

        }catch (NoSuchAlgorithmException e){
            return null;
        }
        md.update(str.getBytes(StandardCharsets.UTF_8));
        byte[] digest = md.digest();
        String hex = String.format("%064x", new BigInteger( 1, digest ));
        return hex;
    }

    public static String toPrettyFormat(String jsonString)
    {
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(jsonString).getAsJsonObject();

        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = prettyGson.toJson(json);

        return prettyJson;
    }


    public static String getExternalAddress(){
        String ip = "-1";
        try {
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));

            ip = in.readLine(); //you get the IP as a String
        }
        catch (Exception e){

        }finally {
            return ip;
        }

    }
    public static String IP()
    {
        String IP_address = "";
        int count = 0 ;
        try{
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements())
            {
                NetworkInterface current = interfaces.nextElement();
                //  System.out.println(current);
                if (!current.isUp() || current.isLoopback() || current.isVirtual()) continue;
                Enumeration<InetAddress> addresses = current.getInetAddresses();
                while (addresses.hasMoreElements()){
                    InetAddress current_addr = addresses.nextElement();
                    if (current_addr.isLoopbackAddress()) continue;
                    if (current_addr instanceof Inet4Address &&  count == 0)
                    {
                        IP_address = current_addr.getHostAddress() ;
                        System.out.println(current_addr.getHostAddress());
                        count++;
                        break;
                    }
                }
            }
        }
        catch(SocketException SE)
        {
            SE.printStackTrace();
        }
        return  IP_address;
    }

    public static String getEncryptedJSON(Object object){
            return hashString(getJSON(object));
    }

    public static String getSocketIP(Socket socket){
        return socket.getInetAddress().toString().substring(1);
    }

    public static void log(Object obj) {
        System.out.println(obj);
    }

    public static String getJSON(Object obj) {
        return gson.toJson(obj);
    }

}

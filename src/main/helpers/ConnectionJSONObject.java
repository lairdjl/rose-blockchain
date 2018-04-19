package helpers;

import com.google.gson.Gson;
import communication.ServerConnection;
import interfaces.JSONObjectInterface;

import java.net.InetAddress;
import java.util.ArrayList;

public class ConnectionJSONObject implements JSONObjectInterface {

        private static ArrayList<String> connections = new ArrayList<>();
        private static ConnectionJSONObject instance = new ConnectionJSONObject();
        private ConnectionJSONObject() {

        }

        public static ConnectionJSONObject getInstance(){
            return instance;
        }
}
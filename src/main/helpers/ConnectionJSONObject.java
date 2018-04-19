package helpers;

import com.google.gson.Gson;
import communication.ServerConnection;
import interfaces.JSONObjectInterface;

import java.net.InetAddress;
import java.util.ArrayList;

public class ConnectionJSONObject implements JSONObjectInterface {

        public static ArrayList<String> connections = new ArrayList<>();
//        private static ConnectionJSONObject instance = new ConnectionJSONObject();
        public ConnectionJSONObject(ArrayList<String> connections) {
                this.connections = connections;
        }

//        public static ConnectionJSONObject getInstance(){
//            return instance;
//        }
}

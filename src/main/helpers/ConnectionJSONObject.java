package helpers;

import com.google.gson.Gson;
import communication.ServerConnection;
import interfaces.JSONObjectInterface;

import java.net.InetAddress;
import java.util.ArrayList;

public class ConnectionJSONObject implements JSONObjectInterface {


        private static Gson gson = new Gson();

        private static ArrayList<String> connections;
        public ConnectionJSONObject(ArrayList<ServerConnection> serverList) {
            for(ServerConnection conn: serverList){
                String ip = conn.getInetAddress().toString().substring(1);
                if (connections.contains(ip)) {
                    continue;
                } else {
                    connections.add(ip);
                }
            }
        }

        @Override
        public String getJSON() {
            return gson.toJson(this);
        }

}

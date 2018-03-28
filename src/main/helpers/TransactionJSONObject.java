package helpers;

import com.google.gson.Gson;

import java.net.InetAddress;

/**
 * An object for transactions
 */
public class TransactionJSONObject {

    InetAddress sender;
    String receiver = "test";
    Object obj;
    private static Gson gson = new Gson();


    public TransactionJSONObject(InetAddress sender, Object obj) {
        this.obj = obj;
        this.sender = sender;
    }


    public String getJSONTransaction() {
        return gson.toJson(this);
    }

}
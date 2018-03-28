package helpers;

import com.google.gson.Gson;

import java.net.InetAddress;

/**
 * An object for transactions
 */
public class TransactionJSONObject {

    InetAddress sender, receiver;
    Object obj;
    private static Gson gson = new Gson();


    public TransactionJSONObject(InetAddress sender, InetAddress receiver,Object obj) {
        this.receiver = receiver;
        this.sender = sender;
        this.obj = obj;

    }


    public String getJSONTransaction() {
        return gson.toJson(this);
    }

}

package helpers;

import com.google.gson.Gson;
import interfaces.JSONObjectInterface;

import java.net.InetAddress;

/**
 * An object for transactions
 */
public class TransactionJSONObject implements JSONObjectInterface {

    InetAddress sender, receiver;
    Object obj;

    public TransactionJSONObject(InetAddress sender, InetAddress receiver,Object obj) {
        this.receiver = receiver;
        this.sender = sender;
        this.obj = obj;

    }
}

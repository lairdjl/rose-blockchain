package blockchain;

import interfaces.JSONObjectInterface;

public class Transaction implements JSONObjectInterface {

    String sender, recipient;
    Object obj;

    public static Transaction newTransaction(String sender, String recipient, Object obj){
        return new Transaction(sender, recipient, obj);
    }

    public Transaction(String sender, String recipient, Object obj){
        this.sender = sender;
        this.recipient = recipient;
        this.obj = obj;
    }

    /**
     *
     * @return the address of the sender in String format
     */
    public String getSender(){
        return this.sender;
    }

    /**
     *
     * @return the address of the recipient in String format
     */
    public String getRecipient(){
        return this.recipient;
    }

    /**
     *
     * @return the message sent in String format
     */
    public Object getTransactionData(){
        return this.obj;
    }

    /** TODO:Use a library like gson in order to keep format consistency instead of manually making it.
     *
     * @return the transaction in JSON format
     */
    public String getJSON(){
        return gson.toJson(this);
    }

}

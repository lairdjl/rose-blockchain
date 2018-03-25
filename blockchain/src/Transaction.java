import com.google.gson.Gson;

public class Transaction {

    String sender, recipient;
    Object obj;
    private static Gson gson = new Gson();
    public static Transaction newTransaction(String sender, String recipient, Object obj){
        return new Transaction(sender, recipient, obj);
    }

    public Transaction(String sender, String recipient, Object message){
        this.sender = sender;
        this.recipient = recipient;
        this.obj = message;
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
    public String getTransaction(){
        return gson.toJson(this);
//        return  "{sender: "+ this.sender + ",recipient:" + this.recipient + ",object:" +  +"}";
    }

}

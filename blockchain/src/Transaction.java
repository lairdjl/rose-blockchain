public class Transaction {

    private String sender, recipient, message;

    public static Transaction newTransaction(String sender, String recipient, String message){
        return new Transaction(sender, recipient, message);
    }

    public Transaction(String sender, String recipient, String message){
        this.sender = sender;
        this.recipient = recipient;
        this.message = message;
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
    public String getMessage(){
        return this.message;
    }

    /** TODO:Use a library like gson in order to keep format consistency instead of manually making it.
     *
     * @return the transaction in JSON format
     */
    public String getTransaction(){
        return  "{sender: "+ this.sender + ",recipient:" + this.recipient + ",message:" + this.message +"}";
    }
}

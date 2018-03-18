public class Transaction {

    private String sender, recipient;

    public static Transaction newTransaction(String sender, String recipient){
      return new Transaction(sender, recipient);
    }

    public Transaction(String sender, String recipient){
        this.sender = sender;
        this.recipient = recipient;
    }

    public String getSender(){
        return this.sender;
    }

    public String getRecipient(){
        return this.recipient;
    }

    public String getTransaction(){
        return this.sender + ":" + this.recipient;
    }
}

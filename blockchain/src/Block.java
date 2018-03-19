import java.util.ArrayList;

public class Block {

//    int index = -1;
    ArrayList<Transaction> transactions;
    String previousHash;

    int proof;

    public Block(ArrayList<Transaction> transactions, String previousHash, int proof){
        this.transactions = transactions;
        this.previousHash = previousHash;
        this.proof = proof;
    }

}

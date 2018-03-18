import java.lang.reflect.Array;
import java.util.ArrayList;

public class Block {

//    int index = -1;
    ArrayList<Transaction> transactions;
    String previousHash, proof;

    Block(ArrayList<Transaction> transactions, String previousHash, String proof){
        this.transactions = transactions;
        this.previousHash = previousHash;
        this.proof = proof;
    }

}

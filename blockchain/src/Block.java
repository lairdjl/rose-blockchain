import java.util.ArrayList;

public class Block {

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public int getProof() {
        return proof;
    }

    //    int index = -1;
    private ArrayList<Transaction> transactions;
    private String previousHash;

    private int proof;

    public Block(ArrayList<Transaction> transactions, String previousHash, int proof){
        this.transactions = (ArrayList<Transaction>) transactions.clone();
        this.previousHash = previousHash;
        this.proof = proof;
    }

}

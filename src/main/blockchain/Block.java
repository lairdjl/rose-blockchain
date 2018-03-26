package blockchain;

import java.util.concurrent.LinkedBlockingQueue;

public class Block {

    public Object[] getTransactions() {
        return transactions;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public int getProof() {
        return proof;
    }

    //    int index = -1;
    private Object[] transactions;
    private String previousHash;

    private int proof;

    public Block(LinkedBlockingQueue<Transaction> transactions, String previousHash, int proof){
        this.transactions =  transactions.toArray();
        this.previousHash = previousHash;
        this.proof = proof;
    }

}

//package Blockchain;

import java.util.ArrayList;

/**
 *
 */
public class Blockchain implements BlockchainInterface {

    //A list of transactions that will be added to the current block.
    private ArrayList<Transaction> currentTransactions;

    public Blockchain(){
        currentTransactions = instantiateTransactionList();
        //creating the 'genesis' block
        newBlock(1, 100);
    }


    /**
     * This is the function for creating a new block
     *
     * @param proof the proof of work
     * @param previousHash the previous hash
     * @return the new Block
     */
    @Override
    public Block newBlock(int previousHash, int proof) {
        Block block =  new Block(currentTransactions, "-1", "-1");
        currentTransactions = instantiateTransactionList();
        chain.add(block);
        return block;
    }

    /**
     *
     * @param sender the person sending the transaction
     * @param recipient the person receiving the transaction
     * @param message the message being sent
     */
    @Override
    public void newTransaction(String sender, String recipient, String message) {
        currentTransactions.add(Transaction.newTransaction(sender, recipient, message));

    }


    /**
     * @param block the block to be hashed
     * @return
     */
    @Override
    public String hash(Block block){
        return Helpers.getEncryptedJSON(block);
    }

    /**
     *
     * @return the last block
     */
    @Override
    public Block lastBlock() {
        return null;
    }

    /**
     * The proof of work algorithm goes here
     *
     * @param previousProof the previous proof to do the proof of work.
     */
    @Override
    public int proofOfWork(int previousProof) {

        int proof = 0;
        while(!validateProof(previousProof, proof)){
            proof += 1;
        }

        return proof;

    }

    /**
     * Validates the Proof: Does hash(last_proof, proof) contain 4 leading zeroes?
     *
     * @param previousProof
     * @param proof
     */
    @Override
    public boolean validateProof(int previousProof, int proof) {
        String guess = previousProof + "" + proof;
        String guessHash = Helpers.encrypt(guess);
        return guessHash.substring(0,3) == "0000";
    }

    /**
     *
     * @return an empty ArrayList of Transactions
     */
    public ArrayList<Transaction> instantiateTransactionList(){
        return new ArrayList<Transaction>();
    }
}

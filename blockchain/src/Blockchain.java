//package Blockchain;

import java.util.ArrayList;

/**
 *
 */
public class Blockchain implements BlockchainInterface {

    //A list of transactions that will be added to the current block.
    public ArrayList<Transaction> currentTransactions;
    private Block lastBlock;
    private int proof = 100;
    private String previousHash = "1";
    private Server server;
    private Client client;

    public Blockchain(){
        currentTransactions = instantiateTransactionList();
        //creating the 'genesis' block
        lastBlock = newBlock(previousHash, proof);

        try{
            server = Server.getServer();

        } catch(Exception e){
            System.out.println("Error running server");
        }

    }


    /**
     * This is the function for creating a new block
     *
     * @param proof the proof of work
     * @param previousHash the previous hash
     * @return the new Block
     */
    @Override
    public Block newBlock(String previousHash, int proof) {
        Block block =  new Block(currentTransactions, previousHash, proof);
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
        return this.lastBlock;
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
        String guessHash = Helpers.hashString(guess);
        return guessHash.substring(0,4).compareTo("0000") == 0;
    }

    @Override
    public boolean mine() {
        int lastProof = this.lastBlock.getProof();
        int proof = this.proofOfWork(lastProof);
        this.newTransaction(Helpers.MINED_ADDRESS, "TEST_ADDRESS", "Mined Block");
        previousHash = this.hash(this.lastBlock);

        this.lastBlock = this.newBlock(previousHash, proof);
        return true;
    }

    /**
     *
     * @return an empty ArrayList of Transactions
     */
    public ArrayList<Transaction> instantiateTransactionList(){
        return new ArrayList<Transaction>();
    }
}

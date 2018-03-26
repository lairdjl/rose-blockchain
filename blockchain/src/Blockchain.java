//package Blockchain;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 *
 */
public class Blockchain implements BlockchainInterface {

    public static final ArrayList<Transaction> currentTransactions = new ArrayList<Transaction>();
    private Block lastBlock;
    private int proof = 100;
    private String previousHash = "1";
    private Client client;
    private Server server;

    private static final Blockchain blockchain = new Blockchain();
//    private static final Server server = Server.getInstance();


    private Blockchain() {
//        currentTransactions.cl
        //creating the 'genesis' block
        lastBlock = newBlock(previousHash, proof);

        try {
            server = Server.getInstance();
            client = new Client();

        } catch (Exception e) {
            System.out.println("Error starting communication");
        }

    }


    /**
     * This is the function for creating a new block
     *
     * @param proof        the proof of work
     * @param previousHash the previous hash
     * @return the new Block
     */
    @Override
    public Block newBlock(String previousHash, int proof) {
        Block block = new Block(currentTransactions, previousHash, proof);
        currentTransactions.clear();
        chain.add(block);
        Gson gson = new Gson();
        String printed = gson.toJson(chain);
        System.out.println(printed);
        return block;
    }

    /**
     * @param sender    the person sending the transaction
     * @param recipient the person receiving the transaction
     * @param message   the message being sent
     */
    @Override
    public void newTransaction(String sender, String recipient, Object message) {

        currentTransactions.add(Transaction.newTransaction(sender, recipient, message));
    }


    /**
     * @param block the block to be hashed
     * @return
     */
    @Override
    public String hash(Block block) {
        return Helpers.getEncryptedJSON(block);
    }

    /**
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
        while (!validateProof(previousProof, proof)) {
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
        return guessHash.substring(0, 4).compareTo("0000") == 0;
    }


    /**
     *
     * @return the blockchain singleton
     */
    public static Blockchain getInstance() {
        return blockchain;
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
     * Start up the blockchain
     *
     * @param args
     */
    public static void main(String[] args) {
        Blockchain.getInstance();
    }

}

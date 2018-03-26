package blockchain;

import com.google.gson.Gson;
import communication.Client;
import communication.Server;
import helpers.Helpers;

import java.util.concurrent.LinkedBlockingQueue;

import static helpers.Helpers.DEBUG;
import static helpers.Helpers.toPrettyFormat;

/**
 *
 */
public class Blockchain implements BlockchainInterface {
//    private LinkedBlockingQueue<Object> messages;

    public static final LinkedBlockingQueue<Transaction> currentTransactions = new LinkedBlockingQueue<Transaction>();
    private Block lastBlock;
    private int proof = 100;
    private String previousHash = "1";
    private Client client;
    private Server server;

    private static final Gson gson = new Gson();
    private static final Blockchain blockchain = new Blockchain();


    private Blockchain() {
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
     * @return the new blockchain.Block
     */
    @Override
    public Block newBlock(String previousHash, int proof) {
        Block block = new Block(currentTransactions, previousHash, proof);
        currentTransactions.clear();
        chain.add(block);
        if(DEBUG){
            printBlockchainJSON();
        }

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
     * @return the blockchain singleton
     */
    public static Blockchain getInstance() {
        return blockchain;
    }


    @Override
    public boolean mine() {
        int lastProof = this.lastBlock.getProof();
        int proof = this.proofOfWork(lastProof);
        this.newTransaction(Helpers.MINED_ADDRESS, "TEST_ADDRESS", "Mined blockchain.Block");
        previousHash = this.hash(this.lastBlock);
        this.lastBlock = this.newBlock(previousHash, proof);
        return true;
    }

    public static String getBlockchainJSON(){
        String print = "{blocks:";
        print += gson.toJson(chain);
        print += "}";
        return print;
    }

    public static void printBlockchainJSON(){
        System.out.println(toPrettyFormat(getBlockchainJSON()));

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

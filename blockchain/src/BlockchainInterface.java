import java.util.ArrayList;

;

/**
 * Blockchain Implemented using instructions from https://hackernoon.com/learn-blockchains-by-building-one-117428612f46
 *
 */
public interface BlockchainInterface {

    /*
    These are the variables that are needed for a blockchain to properly work.
     */
    //The 'chain' itself
    ArrayList<Block> chain = new ArrayList<Block>();

    /**
     * This is the function for creating a new block
     *
     * @param proof the proof of work
     * @param previousHash the previous hash
     * @return the new Block
     */
    Block newBlock(String previousHash, int proof);

    /**
     * This method defines what a transaction is - currently messages are the only thing being sent through this  blockchain.
     *
     * @param sender the person sending the transaction
     * @param recipient the person receiving the transaction
     * @param message the message being sent
     */
    void newTransaction(String sender, String recipient, Object message);

    /**
     * This is the hashing function for the blockchain
     *
     * @param block the block to be hashed
     */
    String hash(Block block);

    /**
     * This is how to get the last block in the chain
     *
     * @return the last block of the blockchain
     */
    Block lastBlock();

    /**
     * The proof of work algorithm goes here
     *
     * @param previousProof the previous proof to do the proof of work.
     */
    int proofOfWork(int previousProof);

    /**
     * Validates the Proof: Does hash(last_proof, proof) contain 4 leading zeroes?
     *
     * @param previousProof
     * @param proof
     */
    boolean validateProof(int previousProof, int proof);

    boolean mine();
}

import java.util.ArrayList;

public interface BlockchainInterface {

    /*
    These are the variables that are needed for a blockchain to properly work.
     */
    //The 'chain' itself
    ArrayList<Block> chain = new ArrayList<Block>();


    //This is the index of the last block of the chain
    int lastBlock = -1;

    /*
    Methods
     */

    /**
     *
     * @return a new block of transactions
     */
    Block newBlock();

    /**
     * This method defines what a transaction is - currently messages are the only thing being sent through this  blockchain.
     *
     * @param sender the person sending the transaction
     * @param recipient the person receiving the transaction
     * @param message the message being sent
     */
    void newTransaction(String sender, String recipient, String message);

    /**
     * This is the hashing function for the blockchain
     *
     * @param block the block to be hashed
     */
    void hash(Block block);

    /**
     * This is how to get the last block in the chain
     *
     * @return the last block of the blockchain
     */
    Block lastBlock();
}

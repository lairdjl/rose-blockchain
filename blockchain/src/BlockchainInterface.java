import java.util.ArrayList;

public interface BlockchainInterface {

    /*
    These are the variables that are needed for a blockchain to properly work.
     */
    //The 'chain' itself
    ArrayList<Block> chain = new ArrayList<Block>();

    //A list of transactions that will be added to the current block.
    ArrayList<Transaction> currentTransactions  = new ArrayList<Transaction>();

    //This is the last block of the chain
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
     *
     * @param sender the person sending the transaction
     * @param recipient the person receiving the transaction
     */
    void newTransaction(String sender, String recipient, String message);

    /**
     *
     * @param block
     */
    void hash(Block block);

    /**
     *
     * @return the last block of the blockchain
     */
    Block lastBlock();
}

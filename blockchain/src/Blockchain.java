import java.util.ArrayList;

public class Blockchain implements BlockchainInterface{

    //A list of transactions that will be added to the current block.
    private ArrayList<Transaction> currentTransactions;

    public Blockchain(){
        currentTransactions = instantiateTransactionList();
        //creating the 'genesis' block
        newBlock();
    }

    /**
     *
     * @return the new block
     */
    @Override
    public Block newBlock() {
        Block block =  new Block(currentTransactions, "-1", "-1");
        currentTransactions = instantiateTransactionList();
        chain.add(block);
        return block;

    }

    /**
     *
     * @param sender the person sending the transaction
     * @param recipient the person receiving the transaction
     * @param message
     */
    @Override
    public void newTransaction(String sender, String recipient, String message) {
        currentTransactions.add(Transaction.newTransaction(sender, recipient, message));
    }

    @Override
    public void hash(Block block) {

    }

    @Override
    public Block lastBlock() {
        return null;
    }

    /**
     *
     * @return an empty ArrayList of Transactions
     */
    public ArrayList<Transaction> instantiateTransactionList(){
        return new ArrayList<Transaction>();
    }
}

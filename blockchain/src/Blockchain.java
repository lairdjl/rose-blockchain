
public class Blockchain implements BlockchainInterface{


    @Override
    public Block newBlock() {
        return null;
    }

    @Override
    public void newTransaction(String sender, String recipient) {
        currentTransactions.add(Transaction.newTransaction(sender, recipient));
    }

    @Override
    public void hash(Block block) {

    }

    @Override
    public Block lastBlock() {
        return null;
    }
}

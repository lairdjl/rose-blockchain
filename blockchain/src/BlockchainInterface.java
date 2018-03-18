public interface BlockchainInterface {
    Block newBlock();
    void newTransaction();
    void hash();
    Block lastBlock();
}

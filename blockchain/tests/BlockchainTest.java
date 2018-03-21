import org.junit.jupiter.api.Test;

class BlockchainTest {
    Blockchain blockchain;

    @Test
    void createBlockchain(){
        blockchain = new Blockchain();
        assert(true);
    }

    @Test
    void newTransaction() {
        blockchain = new Blockchain();
        blockchain.newTransaction("bob","joe", "hello world");
        blockchain.newTransaction("joe","bob", "communicating thru blockchain!");
    }

    @Test
    void newBlock() {
        blockchain = new Blockchain();
        blockchain.newTransaction("bob","joe", "hello world");
        blockchain.newTransaction("joe","bob", "communicating thru blockchain!");
        assert(blockchain.mine());
        blockchain.newTransaction("bob","joe", "test 3");
        blockchain.newTransaction("joe","bob", "test 4");
        blockchain.newTransaction("joe","bob", "test 5");
        assert(blockchain.mine());



    }


    @Test
    void hash() {
    }

    @Test
    void lastBlock() {
        blockchain = new Blockchain();
        blockchain.newTransaction("bob","joe", "hello world");
        blockchain.newTransaction("joe","bob", "communicating thru blockchain!");
        assert(blockchain.mine());
        blockchain.newTransaction("bob","joe", "test 3");
        blockchain.newTransaction("joe","bob", "test 4");
        blockchain.newTransaction("joe","bob", "test 5");
        assert(blockchain.mine());
        Block lastBlock = blockchain.lastBlock();
        assert(lastBlock!=null);
        /**
         * There are 4 transactions because the mined block is being added into the transaction list currently.
         */
        assert(lastBlock.getTransactions().size() == 4);
    }

    @Test
    void proofOfWork() {
    }

    @Test
    void validateProof() {
    }

    @Test
    void instantiateTransactionList() {
    }
}
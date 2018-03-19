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
        createBlockchain();
        blockchain.newTransaction("bob","joe", "hello world");
        blockchain.newTransaction("joe","bob", "communicating thru blockchain!");

        assert(blockchain.currentTransactions.size() == 2);

    }

    @Test
    void newBlock() {
        newTransaction();
        blockchain.mine();
    }


    @Test
    void hash() {
    }

    @Test
    void lastBlock() {
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
public interface BlockchainAPIInterface {

    /**
     *
     * @return the blockchain singleton
     */
    Blockchain getBlockchain();

    /**
     *
     * @return the created transaction
     */
    Transaction newTransaction();

    /**
     * How to start mining
     * When should this start? Probably
     */
    void mine();

}

import org.junit.jupiter.api.Test;

class ClientTest {
    Client client;

    @Test
    void getClient() {
        client = new Client();
        client.startInterface();
        assert(client != null);
    }

    @Test
    void communication(){

    }

}
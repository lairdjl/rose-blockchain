import org.junit.jupiter.api.Test;

class ClientTest {
    Client client;

    @Test
    void getClient() {
        client = Client.getClient();
        assert(client != null);
    }

    @Test
    void communication(){

    }

}
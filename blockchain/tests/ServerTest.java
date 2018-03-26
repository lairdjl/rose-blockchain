import org.junit.jupiter.api.Test;

class ServerTest {

    Server server;
    Client client;
    @Test
    void getServer() {
        server = Server.getInstance();
        assert(server != null);
    }

}
import communication.Client;
import org.junit.jupiter.api.Test;
import communication.Server;

class ServerTest {

    Server server;
    Client client;
    @Test
    void getServer() {
        server = Server.getInstance();
        assert(server != null);
    }

}
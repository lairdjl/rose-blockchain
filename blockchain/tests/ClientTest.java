import org.junit.jupiter.api.Test;

class ClientTest {
    Client client;

    @Test
    void getClient() {
        try{
            client = new Client();
        }catch (Exception e){
            e.printStackTrace();
        }
        assert(client != null);
    }

    @Test
    void communication(){

    }

}
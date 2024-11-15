package client;

import facade.ServerFacade;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.*;
import server.Server;


public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade sF;
    private UserData myUser;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        sF = new ServerFacade("http://localhost:" + port);
    }

    @BeforeEach
    public void startTest() {
        myUser = new UserData("chess_player", "chess", "chess champ");
    }

    //@AfterEach
    //public void endTest() {
    //    sF.
    //}

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void sampleTest() {
        Assertions.assertTrue(true);
    }

    @Test
    public void testAddUser() {
            AuthData aD = sF.addUser(myUser);
            Assertions.assertTrue(aD.authToken().equals(aD.authToken()));
    }

}

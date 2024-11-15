package client;

import facade.ServerFacade;
import model.AuthData;
import model.LoginData;
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

    @AfterEach
    public void endTest() {
        sF.clearThing();
    }

    @AfterAll
    static void stopServer() {
        sF.clearThing();
        server.stop();
    }


    @Test
    public void sampleTest() {
        Assertions.assertTrue(true);
    }

    @Test
    public void testAddUser() {
            AuthData aD = sF.addUser(myUser);
            Assertions.assertNotNull(aD.authToken());
    }
    @Test
    public void failedAddUser() {
        AuthData aD = sF.addUser(myUser);
        boolean failed = false;
        try {
            aD = sF.addUser(myUser);
        } catch (Exception e) {
            failed = true;
        } Assertions.assertNotNull(aD.authToken());
        Assertions.assertNotNull(failed);
        if (!failed) {
            Assertions.fail();
        }
        else {
            Assertions.assertTrue(true);
        }
    }
    @Test
    public void testLogin() {
        AuthData aD = sF.addUser(myUser);
        sF.logoutUser(aD.authToken());
        aD = sF.loginUser(new LoginData(myUser.username(), myUser.password()));
        Assertions.assertNotNull(aD.authToken());
    }
    @Test
    public void failedLogin() {
        AuthData aD = sF.addUser(myUser);
        sF.logoutUser(aD.authToken());
        boolean failed = false;
        try {
            aD = sF.loginUser(new LoginData(myUser.username() + "goofy", myUser.password()));
        } catch (Exception e) {
            failed = true;
        } Assertions.assertTrue(failed);
        if (!failed) {
            Assertions.fail();
        }
        else {
            Assertions.assertTrue(true);
        }
    }

}

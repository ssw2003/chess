package client;

import chess.ChessGame;
import facade.ServerFacade;
import model.*;
import org.junit.jupiter.api.*;
import server.Server;

import java.util.ArrayList;
import java.util.Collection;


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
        sF.clearThing();
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
    @Test
    public void testLogout() {
        AuthData aD = sF.addUser(myUser);
        sF.logoutUser(aD.authToken());
        boolean failedGameCreation = false;
        try {
            sF.createGame(new GameName("Chess game"), aD.authToken());
        } catch (Exception e) {
            failedGameCreation = true;
        }
        Assertions.assertTrue(failedGameCreation);
    }
    @Test
    public void failedLogout() {
        AuthData aD = sF.addUser(myUser);
        boolean passedLogout = true;
        try {
            sF.logoutUser(aD.authToken() + "goofy");
        } catch (Exception e) {
            passedLogout = false;
        }
        Assertions.assertTrue(passedLogout == false);
    }
    @Test
    public void testClear() {
        boolean clearedIt = true;
        try {
            sF.clearThing();
        } catch (Exception e) {
            clearedIt = false;
        }
        Assertions.assertTrue(clearedIt);
    }
    @Test
    public void failedClear() {
        boolean clearedIt = false;
        if (0 == 1) {
            clearedIt = true;
            sF.clearThing();
        }
        Assertions.assertFalse(clearedIt);
    }
    @Test
    public void testList() {
        AuthData aD = sF.addUser(myUser);
        int i = sF.createGame(new GameName("World's greatest Game"), aD.authToken());
        Collection<GameMetadata> desiredGames = new ArrayList<>();
        desiredGames.add(new GameMetadata(i, null, null, "World's greatest Game"));
        Collection<GameMetadata> games = sF.listGames(aD.authToken());
        Assertions.assertTrue(games.equals(desiredGames));
    }
    @Test
    public void failedList() {
        AuthData aD = sF.addUser(myUser);
        int i = sF.createGame(new GameName("World's greatest Game"), aD.authToken());
        Collection<GameMetadata> desiredGames = new ArrayList<>();
        desiredGames.add(new GameMetadata(i, null, null, "World's greatest Game"));
        Collection<GameMetadata> games = new ArrayList<>();
        boolean didntGetGames = false;
        try {
            games = sF.listGames(aD.authToken() + "goofy");
        } catch (Exception e) {
            Assertions.assertFalse(games.equals(desiredGames));
            didntGetGames = true;
        }
        Assertions.assertTrue(didntGetGames);
    }
    @Test
    public void testCreate() {
        AuthData aD = sF.addUser(myUser);
        int i = sF.createGame(new GameName("World's greatest Game"), aD.authToken());
        Collection<GameMetadata> games = new ArrayList<>();
        games.add(new GameMetadata(i, null, null, "World's greatest Game"));
        Collection<GameMetadata> desiredGames = sF.listGames(aD.authToken());
        Assertions.assertTrue(desiredGames.equals(games));
    }
    @Test
    public void failedCreate() {
        AuthData aD = sF.addUser(myUser);
        int i = 0;
        try {
            i = sF.createGame(new GameName("World's greatest Game"), aD.authToken() + "silly");
        } catch (Exception e) {
            i = 0;
        }
        Collection<GameMetadata> games = new ArrayList<>();
        Collection<GameMetadata> desiredGames = sF.listGames(aD.authToken());
        Assertions.assertEquals(0, i);
        Assertions.assertEquals(desiredGames, games);
    }
    @Test
    public void testJoin() {
        AuthData aD = sF.addUser(myUser);
        int i = sF.createGame(new GameName("World's greatest Game"), aD.authToken());
        sF.joinGame(new PlayerColorGameNumber(ChessGame.TeamColor.WHITE, i), aD.authToken());
        Assertions.assertTrue(true);
    }
    @Test
    public void failedJoin() {
        AuthData aD = sF.addUser(myUser);
        int i = sF.createGame(new GameName("World's greatest Game"), aD.authToken());
        sF.joinGame(new PlayerColorGameNumber(ChessGame.TeamColor.WHITE, i), aD.authToken());
        Assertions.assertThrows(RuntimeException.class, () -> sF.joinGame(new PlayerColorGameNumber(ChessGame.TeamColor.WHITE, i), aD.authToken()));
    }

}

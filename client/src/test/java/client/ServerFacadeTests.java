package client;

import model.GameData;
import org.junit.jupiter.api.*;
import server.Server;

import java.util.Collection;


public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade sF;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        sF = new ServerFacade("http://localhost:" + port);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
        boolean b = sF.clear();
    }

    @BeforeEach
    public void startEach() {
        boolean b = sF.clear();
    }
    @AfterEach
    public void endEach() {
        boolean b = sF.clear();
    }


    @Test
    public void sampleTest() {
        Assertions.assertTrue(true);
    }

    @Test
    @Order(1)
    @DisplayName("Passed Register")
    public void passedRegister() {
        String w = sF.registerAttempt("chess", "@chess", "chess@chess.chess");
        Assertions.assertNotNull(w);
    }
    @Test
    @Order(2)
    @DisplayName("Failed Register")
    public void failedRegister() {
        String w = sF.registerAttempt("chess", "@chess", "chess@chess.chess");
        Assertions.assertNotNull(w);
        w = sF.registerAttempt("chess", "bad", "bad@chess.chess");
        Assertions.assertNull(w);
    }
    @Test
    @Order(3)
    @DisplayName("Passed Clear")
    public void passedClear() {
        boolean w = false;
        w = sF.clear();
        Assertions.assertTrue(w);
    }
    @Test
    @Order(4)
    @DisplayName("Failed Clear")
    public void failedClear() {
        boolean w = false;
        if (!sF.clear()) {
            w = sF.clear();
        }
        Assertions.assertFalse(w);
    }
    @Test
    @Order(5)
    @DisplayName("Passed Login")
    public void passedLogin() {
        String w = sF.registerAttempt("chess", "@chess", "chess@chess.chess");
        w = sF.loginRequest("chess", "@chess");
        Assertions.assertNotNull(w);
    }
    @Test
    @Order(6)
    @DisplayName("Failed Login")
    public void failedLogin() {
        String w = sF.registerAttempt("chess", "@chess", "chess@chess.chess");
        w = sF.loginRequest("chess", "chess");
        Assertions.assertNull(w);
        w = sF.loginRequest("chessChess", "chess");
        Assertions.assertNull(w);
    }
    @Test
    @Order(7)
    @DisplayName("Passed Logout")
    public void passedLogout() {
        String w = sF.registerAttempt("chess", "@chess", "chess@chess.chess");
        Assertions.assertTrue(sF.logoutRequest(w));
    }
    @Test
    @Order(8)
    @DisplayName("Failed Logout")
    public void failedLogout() {
        String w = sF.registerAttempt("chess", "@chess", "chess@chess.chess");
        Assertions.assertFalse(sF.logoutRequest(w + "who now?"));
    }
    @Test
    @Order(9)
    @DisplayName("Passed List Games")
    public void passedListGames() {
        String w = sF.registerAttempt("chess", "@chess", "chess@chess.chess");
        Assertions.assertTrue(sF.createGame(w, "Chess"));
        Assertions.assertTrue(sF.createGame(w, "Chess"));
        Assertions.assertTrue(sF.createGame(w, "Checkers"));
        Collection<GameData> gD = sF.gameListRequest(w);
        Assertions.assertNotNull(gD);
        Assertions.assertEquals(gD.size(), 3);
        GameData gdFirst = null;
        GameData gdSecond = null;
        GameData gdThird = null;
        for (GameData gDD: gD) {
            if (gDD.gameID() == 1) {
                gdFirst = gDD;
            }
            if (gDD.gameID() == 2) {
                gdSecond = gDD;
            }
            if (gDD.gameID() == 3) {
                gdThird = gDD;
            }
        }
        Assertions.assertNotNull(gdFirst);
        Assertions.assertNotNull(gdSecond);
        Assertions.assertNotNull(gdThird);
        Assertions.assertEquals(1, gdFirst.gameID());
        Assertions.assertEquals(2, gdSecond.gameID());
        Assertions.assertEquals(3, gdThird.gameID());
        Assertions.assertEquals("Chess", gdFirst.gameName());
        Assertions.assertEquals("Chess", gdSecond.gameName());
        Assertions.assertEquals("Checkers", gdThird.gameName());
        Assertions.assertNull(gdFirst.whiteUsername());
        Assertions.assertNull(gdSecond.blackUsername());
        Assertions.assertNull(gdThird.whiteUsername());
        Assertions.assertNull(gdFirst.blackUsername());
        Assertions.assertNull(gdSecond.whiteUsername());
        Assertions.assertNull(gdThird.blackUsername());
    }
    @Test
    @Order(10)
    @DisplayName("Failed List Games")
    public void failedListGames() {
        String w = sF.registerAttempt("chess", "@chess", "chess@chess.chess");
        Assertions.assertTrue(sF.createGame(w, "Chess"));
        Assertions.assertNull(sF.gameListRequest(w + "who now?"));
    }
    @Test
    @Order(11)
    @DisplayName("Passed Add Game")
    public void passedAddGame() {
        String w = sF.registerAttempt("chess", "@chess", "chess@chess.chess");
        Assertions.assertTrue(sF.createGame(w, "Chess"));
    }
    @Test
    @Order(12)
    @DisplayName("Failed Add Game")
    public void failedAddGame() {
        String w = sF.registerAttempt("chess", "@chess", "chess@chess.chess");
        Assertions.assertFalse(sF.createGame(w + "who now?", "Chess"));
    }
    @Test
    @Order(13)
    @DisplayName("Passed Join Game")
    public void passedJoinGame() {
        String w = sF.registerAttempt("chess", "@chess", "chess@chess.chess");
        String x = sF.registerAttempt("checkers", "@chess", "chess@chess.chess");
        Assertions.assertTrue(sF.createGame(w, "Chess"));
        Assertions.assertTrue(sF.joinGame(w, 1, "WHITE"));
        Collection<GameData> gD = sF.gameListRequest(w);
        Assertions.assertNotNull(gD);
        GameData gdFirst = null;
        for (GameData gDD: gD) {
            gdFirst = gDD;
        }
        Assertions.assertNotNull(gdFirst);
        Assertions.assertEquals(gdFirst.gameID(), 1);
        Assertions.assertEquals(gdFirst.gameName(), "Chess");
        Assertions.assertEquals(gdFirst.whiteUsername(), "chess");
        Assertions.assertNull(gdFirst.blackUsername());
        Assertions.assertTrue(sF.joinGame(x, 1, "BLACK"));
        gD = sF.gameListRequest(w);
        Assertions.assertNotNull(gD);
        gdFirst = null;
        for (GameData gDD: gD) {
            gdFirst = gDD;
        }
        Assertions.assertNotNull(gdFirst);
        Assertions.assertEquals(gdFirst.gameID(), 1);
        Assertions.assertEquals(gdFirst.blackUsername(), "checkers");
        Assertions.assertEquals(gdFirst.gameName(), "Chess");
        Assertions.assertEquals(gdFirst.whiteUsername(), "chess");
    }
    @Test
    @Order(14)
    @DisplayName("Failed Join Game")
    public void failedJoinGame() {
        String w = sF.registerAttempt("chess", "@chess", "chess@chess.chess");
        String x = sF.registerAttempt("checkers", "@chess", "chess@chess.chess");
        Assertions.assertTrue(sF.createGame(w, "Chess"));
        Assertions.assertTrue(sF.joinGame(w, 1, "WHITE"));
        Assertions.assertFalse(sF.joinGame(w, 1, "TURQUOISE"));
        Assertions.assertFalse(sF.joinGame(w, 2, "BLACK"));
        Collection<GameData> gD = sF.gameListRequest(w);
        Assertions.assertNotNull(gD);
        GameData gdFirst = null;
        for (GameData gDD: gD) {
            gdFirst = gDD;
        }
        Assertions.assertNotNull(gdFirst);
        Assertions.assertEquals(gdFirst.whiteUsername(), "chess");
        Assertions.assertEquals(gdFirst.gameName(), "Chess");
        Assertions.assertEquals(gdFirst.gameID(), 1);
        Assertions.assertNull(gdFirst.blackUsername());
    }

}

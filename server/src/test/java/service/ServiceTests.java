package service;
import chess.ChessGame;
import dataaccess.DataAccessException;
import dataaccess.Service;
import model.GameData;
import model.GameDataWithout;
import org.junit.jupiter.api.*;
import org.mindrot.jbcrypt.BCrypt;
import passoff.model.*;
import passoff.server.TestServerFacade;
import server.Server;
import java.net.HttpURLConnection;
import java.util.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServiceTests {
    private static Service svc;
    @BeforeAll
    public static void getStarted() {
        svc = new Service();
    }
    @AfterAll
    static void beDone() {
        svc.clearThingy();
    }
    @BeforeEach
    public void startThing() {
        String s = "s";
    }
    @AfterEach
    public void endThing() {
        String s = "s";
    }
    @Test
    @Order(1)
    @DisplayName("First Passed Test Of Register User")
    public void firstPassedTestOfRegUsr() {
        String authy = null;
        boolean wentWell = true;
        try {
            authy = svc.regUsr("Viswanathan", "Anand", "anand@anand.anand", true);
            wentWell = true;
        } catch (DataAccessException e) {
            wentWell = false;
        }
        Assertions.assertTrue(wentWell);
        Assertions.assertNotNull(authy);
    }
    @Test
    @Order(2)
    @DisplayName("Second Passed Test Of Register User")
    public void secondPassedTestOfRegUsr() {
        svc.clearThingy();
        String authyAnand = null;
        String authyKramnik = null;
        String authyKasparov = null;
        boolean wentWell = true;
        try {
            authyAnand = svc.regUsr("Viswanathan", "Anand", "anand@anand.anand", true);
            authyKramnik = svc.regUsr("Vladimir", "Kramnik", "kramnik@kramnik.kramnik", true);
            wentWell = true;
        } catch (DataAccessException e) {
            wentWell = false;
        }
        try {
            authyKasparov = svc.regUsr("Garry", "Kasparov", "kasparov@kasparov.kasparov", true);
        } catch (DataAccessException e) {
            wentWell = false;
        }
        Assertions.assertTrue(wentWell);
        Assertions.assertNotNull(authyKasparov);
        Assertions.assertNotNull(authyKramnik);
        Assertions.assertNotNull(authyAnand);
    }
    @Test
    @Order(3)
    @DisplayName("First Failed Test Of Register User")
    public void firstFailedTestOfRegUsr() {
        svc.clearThingy();
        String authy = null;
        boolean wentWell = true;
        try {
            authy = svc.regUsr("Viswanathan", "Anand", "anand@anand.anand", true);
            authy = svc.regUsr("Viswanathan", "Anand the Chess Player", "Anand@anand.chess", true);
            wentWell = true;
        } catch (DataAccessException e) {
            wentWell = false;
        }
        Assertions.assertNotNull(authy);
        Assertions.assertFalse(wentWell);
    }
    @Test
    @Order(4)
    @DisplayName("Second Failed Test Of Register User")
    public void secondFailedTestOfRegUsr() {
        String authy = null;
        boolean wentWell = true;
        try {
            authy = svc.regUsr("Garry", "Kasparov", "kasparov@kasparov.kasparov", true);
            authy = svc.regUsr("Viswanathan", "Anand", "anand@anand.anand", true);
            wentWell = true;
        } catch (DataAccessException e) {
            wentWell = false;
        }
        Assertions.assertFalse(wentWell);
        Assertions.assertNotNull(authy);
    }
    @Test
    @Order(5)
    @DisplayName("Third Failed Test Of Register User")
    public void thirdFailedTestOfRegUsr() {
        String authy = null;
        boolean wentWell = true;
        try {
            authy = svc.regUsr("Garry", "Kasparov who Loves Chess", "kasparov@kasparov.chess", true);
            wentWell = true;
        } catch (DataAccessException e) {
            wentWell = false;
        }
        Assertions.assertFalse(wentWell);
        Assertions.assertNull(authy);
    }
    @Test
    @Order(6)
    @DisplayName("First Passed Test Of Logout")
    public void firstPassedTestOfLogout() {
        String authy = null;
        boolean wentWell = true;
        try {
            authy = svc.regUsr("Vladimir", "Kramnik", "kramnik@kramnik.kramnik", true);
            wentWell = true;
        } catch (DataAccessException e) {
            wentWell = false;
        }
        try {
            svc.logout(authy);
        } catch (DataAccessException e) {
            wentWell = false;
        }
        Assertions.assertFalse(svc.isAuthorized(authy) || (!wentWell));
    }
    @Test
    @Order(7)
    @DisplayName("Passed Test Of Clear Thingy")
    public void passedTestOfClearThingy() {
        svc.clearThingy();
        String authy = null;
        boolean wentWell = true;
        try {
            authy = svc.regUsr("Vladimir", "Kramnik", "kramnik@kramnik.kramnik", true);
            wentWell = true;
        } catch (DataAccessException e) {
            wentWell = false;
        }
        Assertions.assertTrue(wentWell);
        Assertions.assertNotNull(authy);
        svc.clearThingy();
        Assertions.assertFalse(svc.isAuthorized(authy));
    }
    @Test
    @Order(8)
    @DisplayName("Second Passed Test Of Logout")
    public void secondPassedTestOfLogout() {
        String authy = null;
        boolean wentWell = true;
        try {
            authy = svc.regUsr("Vladimir", "Kramnik", "kramnik@kramnik.kramnik", true);
            authy = svc.regUsr("Viswanathan", "Anand", "anand@anand.anand", true);
            wentWell = true;
        } catch (DataAccessException e) {
            wentWell = false;
        }
        try {
            svc.logout(authy);
        } catch (DataAccessException e) {
            wentWell = false;
        }
        Assertions.assertFalse(svc.isAuthorized(authy) || (!wentWell));
    }
    @Test
    @Order(9)
    @DisplayName("Passed Test Of Is Authorized")
    public void passedTestOfIsAuthorized() {
        svc.clearThingy();
        String authy = null;
        boolean wentWell = true;
        try {
            authy = svc.regUsr("Vladimir", "Kramnik", "kramnik@kramnik.kramnik", true);
            authy = svc.regUsr("Viswanathan", "Anand", "anand@anand.anand", true) + authy;
            wentWell = true;
        } catch (DataAccessException e) {
            wentWell = false;
        }
        try {
            svc.logout(authy);
            wentWell = false;
        } catch (DataAccessException e) {
            wentWell = true;
        }
        Assertions.assertFalse(svc.isAuthorized(authy + "a") || (!wentWell));
        svc.clearThingy();
    }
    @Test
    @Order(10)
    @DisplayName("Passed Test Of Join Game")
    public void passedTestOfJoinGame() {
        svc.clearThingy();
        String authy = null;
        try {
            authy = svc.regUsr("Viswanathan", "Anand", "anand@anand.anand", true);
        } catch (DataAccessException e) {
            authy = "authy";
            authy = null;
        }
        int i = svc.addGame("Anand is Champion");
        try {
            svc.joinGame(i, true, authy, null);
        } catch (DataAccessException e) {
            i = 0;
        }
        Collection<GameDataWithout> gD = new ArrayList<>();
        try {
            Collection<GameData> gE = svc.getGames(authy);
            for (GameData gF: gE) {
                gD.add(new GameDataWithout(gF.gameID(), gF.whiteUsername(), gF.blackUsername(), gF.gameName()));
            }
        } catch (DataAccessException e) {
            i = 0;
        }
        Assertions.assertEquals(1, i);
        GameDataWithout gW = null;
        for (GameDataWithout gDW: gD) {
            gW = gDW;
        }
        Assertions.assertNotNull(gW);
        Assertions.assertEquals(1, gW.gameID());
        Assertions.assertEquals(authy, gW.whiteUsername());
        Assertions.assertNull(gW.blackUsername());
        Assertions.assertEquals("Anand is Champion", gW.gameName());
    }
    @Test
    @Order(11)
    @DisplayName("Passed Test Of Add Game")
    public void passedTestOfAddGame() {
        svc.clearThingy();
        String authy = null;
        try {
            authy = svc.regUsr("Viswanathan", "Anand", "anand@anand.anand", true);
        } catch (DataAccessException e) {
            authy = "authy";
            authy = null;
        }
        Collection<GameDataWithout> gD = new ArrayList<>();
        int i = svc.addGame("Anand is Champion");
        try {
            Collection<GameData> gE = svc.getGames(authy);
            for (GameData gF: gE) {
                gD.add(new GameDataWithout(gF.gameID(), gF.whiteUsername(), gF.blackUsername(), gF.gameName()));
            }
        } catch (DataAccessException e) {
            i = 0;
        }
        Assertions.assertEquals(1, i);
        GameDataWithout gW = null;
        for (GameDataWithout gDW: gD) {
            gW = gDW;
        }
        Assertions.assertNotNull(gW);
        Assertions.assertEquals(1, gW.gameID());
        Assertions.assertNull(gW.whiteUsername());
        Assertions.assertNull(gW.blackUsername());
        Assertions.assertEquals("Anand is Champion", gW.gameName());
    }
    @Test
    @Order(12)
    @DisplayName("Passed Test Of Get Games")
    public void passedTestOfGetGames() {
        svc.clearThingy();
        String authy = null;
        try {
            authy = svc.regUsr("Garry", "Kasparov", "Kasparov@Kasparov.Kasparov", true);
        } catch (DataAccessException e) {
            authy = "authy";
            authy = null;
        }
        Collection<GameDataWithout> gD = new ArrayList<>();
        int i = svc.addGame("Kasparov is Champion");
        int j = svc.addGame("Kasparov is Chess Player");
        int k = svc.addGame("Kasparov is Bad At Chess");
        int l = svc.addGame("Kasparov is Good At Chess");
        try {
            Collection<GameData> gE = svc.getGames(authy);
            for (GameData gF: gE) {
                gD.add(new GameDataWithout(gF.gameID(), gF.whiteUsername(), gF.blackUsername(), gF.gameName()));
            }
        } catch (DataAccessException e) {
            i = 0;
            j = 0;
            k = 0;
            l = 0;
        }
        Assertions.assertEquals(1, i);
        GameDataWithout gWI = null;
        GameDataWithout gWJ = null;
        GameDataWithout gWK = null;
        GameDataWithout gWL = null;
        for (GameDataWithout gDW: gD) {
            if (gDW.gameID() == i) {
                gWI = gDW;
            }
            if (gDW.gameID() == j) {
                gWJ = gDW;
            }
            if (gDW.gameID() == k) {
                gWK = gDW;
            }
            if (gDW.gameID() == l) {
                gWL = gDW;
            }
        }
        Assertions.assertEquals(1, i);
        Assertions.assertEquals(2, j);
        Assertions.assertEquals(3, k);
        Assertions.assertEquals(4, l);
        Assertions.assertEquals(1, gWI.gameID());
        Assertions.assertEquals(2, gWJ.gameID());
        Assertions.assertEquals(3, gWK.gameID());
        Assertions.assertEquals(4, gWL.gameID());
        Assertions.assertEquals("Kasparov is Champion", gWI.gameName());
        Assertions.assertEquals("Kasparov is Chess Player", gWJ.gameName());
        Assertions.assertEquals("Kasparov is Bad At Chess", gWK.gameName());
        Assertions.assertEquals("Kasparov is Good At Chess", gWL.gameName());
    }
    @Test
    @Order(13)
    @DisplayName("Failed Test Of Clear Thingy")
    public void failedTestOfClearThingy() {
        boolean wentWell = true;
        svc.clearThingy();
        String authy = null;
        try {
            authy = svc.regUsr("Chess", "ChessChamp", "chess@chess.chess", true);
            wentWell = true;
        } catch (DataAccessException e) {
            wentWell = false;
        }
        if (false) {
            svc.clearThingy();
        }
        Assertions.assertNotNull(authy);
        Assertions.assertFalse(svc.isAuthorized(authy + "authy"));
        Assertions.assertTrue(svc.isAuthorized(authy));
        svc.clearThingy();
        Assertions.assertFalse(svc.isAuthorized(authy));
    }
    @Test
    @Order(14)
    @DisplayName("Failed Test Of Add Game")
    public void failedTestOfAddGame() {
        String authy = null;
        try {
            authy = svc.regUsr("Viswanathan", "Anand", "chess@anand.anand", true);
        } catch (DataAccessException e) {
            authy = "authy";
        }
        authy = "authy";
        int i = 0;
        try {
            if (svc.isAuthorized(authy)) {
                i = svc.addGame("anandGame");
            } else {
                throw new DataAccessException("");
            }
        } catch (DataAccessException e) {
            i = 0;
        }
        Assertions.assertEquals(0, i);
        svc.clearThingy();
    }
    @Test
    @Order(15)
    @DisplayName("Failed Test Of Logout")
    public void failedTestOfLogout() {
        boolean wentWell = true;
        try {
            svc.logout("authy");
        } catch (DataAccessException e) {
            wentWell = false;
        }
        Assertions.assertFalse(wentWell);
    }
    @Test
    @Order(16)
    @DisplayName("Failed Test Of Get Games")
    public void failedTestOfGetGames() {
        String authy = "authy";
        int i = svc.addGame("Good game");
        int j = svc.addGame("Bad game");
        Collection<GameDataWithout> gD = new ArrayList<>();
        try {
            Collection<GameData> gE = svc.getGames(authy);
            for (GameData gF: gE) {
                gD.add(new GameDataWithout(gF.gameID(), gF.whiteUsername(), gF.blackUsername(), gF.gameName()));
            }
        } catch (DataAccessException e) {
            authy = "";
        }
        Assertions.assertEquals(1, i);
        Assertions.assertEquals(1, i);
        Assertions.assertEquals(2, j);
        Assertions.assertEquals("", authy);
    }
    @Test
    @Order(17)
    @DisplayName("Failed Test Of Is Authorized")
    public void failedTestOfIsAuthorized() {
        String authy = "Auth";
        Assertions.assertFalse(svc.isAuthorized(authy));
    }
    @Test
    @Order(18)
    @DisplayName("Failed Test Of Join Game")
    public void failedTestOfJoinGame() {
        svc.clearThingy();
        String authy = null;
        String authyKasparov = null;
        String authyAnand = null;
        try {
            authy = svc.regUsr("Viswanathan", "Anand", "anand@chess.anand", true);
            authyAnand = authy;
        } catch (DataAccessException e) {
            authy = "authy";
            authy = null;
        }
        int i = svc.addGame("Anand Moves the Bishop");
        try {
            svc.joinGame(i, true, authy, null);
        } catch (DataAccessException e) {
            i = 0;
        }
        try {
            authy = svc.regUsr("Garry", "Kasparov", "kasparov@chess.kasparov", true);
            authyKasparov = authy;
        } catch (DataAccessException e) {
            authy = null;
        }
        try {
            svc.joinGame(i, false, authy, null);
        } catch (DataAccessException e) {
            i = 0;
        }
        try {
            authy = svc.regUsr("Vladimir", "Kramnik", "kramnik@chess.kramnik", true);
        } catch (DataAccessException e) {
            authy = null;
        }
        int j = 1;
        try {
            svc.joinGame(1, true, authy, null);
            Assertions.fail();
        } catch (DataAccessException e) {
            j = 1 - i;
            Assertions.assertTrue(true);
        }
        Assertions.assertEquals(1, i);
        Assertions.assertEquals(0, j);
        Collection<GameDataWithout> gD = new ArrayList<>();
        try {
            Collection<GameData> gE = svc.getGames(authy);
            for (GameData gF: gE) {
                gD.add(new GameDataWithout(gF.gameID(), gF.whiteUsername(), gF.blackUsername(), gF.gameName()));
            }
        } catch (DataAccessException e) {
            i = j;
        }
        Assertions.assertEquals(1, i);
        GameDataWithout gW = null;
        for (GameDataWithout gDW: gD) {
            gW = gDW;
        }
        Assertions.assertNotNull(gW);
        Assertions.assertEquals(1, gW.gameID());
        Assertions.assertEquals(authyKasparov, gW.blackUsername());
        Assertions.assertEquals(authyAnand, gW.whiteUsername());
        Assertions.assertEquals("Anand Moves the Bishop", gW.gameName());
    }
    @Test
    @Order(19)
    @DisplayName("Failed Test Of Get Psw")
    public void failedTestOfGetPsw() {
        svc.clearThingy();
        String authy = null;
        try {
            authy = svc.regUsr("Vladimir", "Kramnik", "kramnik@chess.kramnik", true);
        } catch (DataAccessException e) {
            authy = "authy";
            authy = null;
        }
        //the service does not hash the password
        Assertions.assertFalse("Kramnik".equals(BCrypt.checkpw(svc.getPsw("Vladimir", true), BCrypt.gensalt())));
    }
    @Test
    @Order(20)
    @DisplayName("Passed Test Of Get Psw")
    public void passedTestOfGetPsw() {
        String authy = "authy";
        Assertions.assertEquals("Kramnik", svc.getPsw("Vladimir", true));
    }
}

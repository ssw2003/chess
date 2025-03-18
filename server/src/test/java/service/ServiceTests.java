package service;
import chess.ChessGame;
import dataaccess.DataAccessException;
import dataaccess.Service;
import org.junit.jupiter.api.*;
import passoff.model.*;
import passoff.server.TestServerFacade;
import server.Server;
import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;
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
        Assertions.assertFalse(wentWell);
        Assertions.assertNotNull(authy);
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
    public void PassedTestOfIsAuthorized() {
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
}

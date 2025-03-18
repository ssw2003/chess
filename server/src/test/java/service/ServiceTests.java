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
        svc = new Service();
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
}

package service;
import chess.ChessGame;
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
}

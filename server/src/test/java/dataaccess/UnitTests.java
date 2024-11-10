package java.dataaccess;
import org.junit.jupiter.api.*;
import passoff.model.*;
import passoff.server.TestServerFacade;
import server.Server;
import java.net.HttpURLConnection;
import java.util.Locale;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UnitTests {

    private static TestUser eU;

    private static TestUser nU;

    private static TestUser kasparov;
    private static TestUser kramnik;
    private static TestUser anand;

    private static TestCreateRequest cR;

    private static TestServerFacade sF;
    private static Server sv;

    private String eA;

    @AfterAll
    static void stopServer() {
        sv.stop();
    }

    @BeforeAll
    public static void init() {
        sv = new Server();
        var port = sv.run(0);
        System.out.println("PORT " + port);

        sF = new TestServerFacade("localhost", Integer.toString(port));

        eU = new TestUser("Person", "Hash This!", "pErSoN");

        nU = new TestUser("chess", "mr_chess", "Chess!!!");

        kasparov = new TestUser("Garry Kasparov", "garry_kasparov", "kasparov@kasparov.kasparov");
        kramnik = new TestUser("Vladimir Kramnik", "vladimir_kramnik", "kramnik@kramnik.kramnik");
        anand = new TestUser("Viswanathan Anand", "viswanathan_anand", "anand@anand.anand");

        cR = new TestCreateRequest("testGame");
    }

    @BeforeEach
    public void setup() {
        sF.clear();
        TestAuthResult garryKasparov = sF.register(kasparov);
        TestAuthResult vladimirKramnik = sF.register(kramnik);
        TestAuthResult viswanathanAnand = sF.register(anand);
        eA = garryKasparov.getAuthToken();
    }

    @Test
    @Order(1)
    @DisplayName("Static Files")
    public void staticFiles() throws Exception {
        String htmlFromServer = sF.file("/").replaceAll("\r", "");
        Assertions.assertEquals(HttpURLConnection.HTTP_OK, sF.getStatusCode(),
                "Bad");
        Assertions.assertNotNull(htmlFromServer, "Bad");
        Assertions.assertTrue(htmlFromServer.contains("CHESS! YAY! LET'S PLAY"));
    }

    @Test
    @Order(2)
    @DisplayName("Kasparov is coming")
    public void wellGarryIsComing() {
        TestAuthResult lR = sF.login(kasparov);
        Assertions.assertEquals(HttpURLConnection.HTTP_OK, sF.getStatusCode(),
                "Bad");
        Assertions.assertFalse(lR.getMessage() != null &&
                        lR.getMessage().toLowerCase(Locale.ROOT).contains("error"),
                "Bad");
        Assertions.assertEquals(kasparov.getUsername(), lR.getUsername(),
                "This doesn't look like Kasparov");
        Assertions.assertNotNull(lR.getAuthToken(), "This doesn't look like Kasparov");
    }

    @Test
    @Order(3)
    @DisplayName("Creating the game")
    public void create_game() {
        TestCreateResult lR = sF.createGame(cR, "Kasparov is the champ!");
    }
    @Test
    @Order(4)
    @DisplayName("Joining the game")
    public void join_game() {
        TestResult lR = sF.joinPlayer(null, "Here comes Kramnik");
    }
    @Test
    @Order(5)
    @DisplayName("Anand wants to butt in")
    public void steal_game() {
        TestResult lR = sF.joinPlayer(null, "Anand will steal the game");
    }

}

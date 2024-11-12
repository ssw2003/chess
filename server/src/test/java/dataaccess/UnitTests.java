package dataaccess;

import org.junit.jupiter.api.*;
import server.AuthData;
import server.Server;
import server.UserData;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UnitTests {

    SQLThing sqlThing;
    UserData kasparov;
    UserData kramnik;
    UserData anand;
    //Server sv;

    //private static TestUser eU;

    //private static TestUser nU;

    //private static TestUser kasparov;
    //private static TestUser kramnik;
    //private static TestUser anand;

    //private static TestCreateRequest cR;

    //private static TestServerFacade sF;
    //private static Server sv;

    //private String eA;

    @AfterAll
    static void stopServer() {
        //sv.stop();
    }

    @BeforeAll
    public static void init() {

        //sv = new Server();
        //var port = sv.run(0);
        //System.out.println("PORT " + port);

        //sF = new TestServerFacade("localhost", Integer.toString(port));

        //eU = new TestUser("Person", "Hash This!", "pErSoN");

        //nU = new TestUser("chess", "mr_chess", "Chess!!!");

        //kasparov = new TestUser("Garry Kasparov", "garry_kasparov", "kasparov@kasparov.kasparov");
        //kramnik = new TestUser("Vladimir Kramnik", "vladimir_kramnik", "kramnik@kramnik.kramnik");
        //anand = new TestUser("Viswanathan Anand", "viswanathan_anand", "anand@anand.anand");

        //cR = new TestCreateRequest("testGame");
    }

    @BeforeEach
    public void setup() throws DataAccessException {
        sqlThing = new SQLThing();
        kasparov = new UserData("Garry Kasparov", "garry_kasparov", "kasparov@kasparov.kasparov");
        kramnik = new UserData("Vladimir Kramnik", "vladimir_kramnik", "kramnik@kramnik.kramnik");
        anand = new UserData("Viswanathan Anand", "viswanathan_anand", "anand@anand.anand");
    //sF.clear();
        //TestAuthResult garryKasparov = sF.register(kasparov);
        //TestAuthResult vladimirKramnik = sF.register(kramnik);
        //TestAuthResult viswanathanAnand = sF.register(anand);
        //eA = garryKasparov.getAuthToken();
    }

    //@Test
    //@Order(1)
    //@DisplayName("Static Files")
    //public void staticFiles() throws Exception {
    //    String htmlFromServer = sF.file("/").replaceAll("\r", "");
    //    Assertions.assertEquals(HttpURLConnection.HTTP_OK, sF.getStatusCode(),
    //            "Bad");
    //    Assertions.assertNotNull(htmlFromServer, "Bad");
    //    //Assertions.assertTrue(htmlFromServer.contains("CHESS! YAY! LET'S PLAY"));
    //    Assertions.assertTrue(htmlFromServer.contains("CS 240 Chess Server Web API"));
    //}

    //@Test
    //@Order(2)
    //@DisplayName("Garry Kasparov Logs In")
    //public void kasparovLogin() {
        //TestAuthResult lR = sF.login(kasparov);
        //assertHttpOk(lR);
        //Assertions.assertEquals(kasparov.getUsername(), lR.getUsername(),
        //                "Response did not give the same username as user");
        //Assertions.assertNotNull(lR.getAuthToken(), "This doesn't look like Kasparov");
        //Assertions.assertEquals(kasparov.getUsername(), lR.getUsername(),
        //        "Response did not give the same username as user");
        //Assertions.assertNotNull(lR.getAuthToken(), "Response did not return authentication String");
    //}
    //@Test
    //@Order(3)
    //@DisplayName("Garry Kasparov Logs In with Wrong Password")
    //public void loginWrongPassword() {
    //    //TestAuthResult lR = sF.login(new TestUser("Garry Kasparov", "gArRy-kAsPaRoV"));
    //    //assertHttpUnauthorized(lR);
    //    //assertAuthFieldsMissing(lR);
    //    Assertions.a
    //}

    //@Test
    //@Order(4)
    //@DisplayName("Creating the game")
    //public void create_game() {
    //    TestCreateResult lR = sF.createGame(cR, "Kasparov is the champ!");
    //}
    //@Test
    //@Order(5)
    //@DisplayName("Joining the game")
    //public void join_game() {
    //    TestResult lR = sF.joinPlayer(null, "Here comes Kramnik");
    //}
    //@Test
    //@Order(6)
    //@DisplayName("Anand wants to butt in")
    //public void steal_game() {
    //    TestResult lR = sF.joinPlayer(null, "Anand will steal the game");
    //}
    @Test
    @DisplayName("Auth Tokens")
    public void AuthTokens() throws DataAccessException {
        String s = sqlThing.generateAuthToken();
        Assertions.assertNotNull(s);
        Assertions.assertTrue(s.length() >= 32);
    }
    @Test
    @DisplayName("Kramnik Auth Tokens Passed")
    public void KramnikAuthTokensPassed() throws DataAccessException {
        AuthData authData = new AuthData("kRaMnIk", kramnik.username());
        sqlThing.CreateAuth(authData);
    }
    @Test
    @DisplayName("Get Kasparov From Database")
    public void GetKasparovFromDatabase() throws DataAccessException {
        //sv.
    }

}

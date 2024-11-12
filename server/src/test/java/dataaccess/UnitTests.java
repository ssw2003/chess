package dataaccess;

import chess.ChessGame;
import org.junit.jupiter.api.*;
import server.AuthData;
import server.GameData;
import server.UserData;

import java.util.ArrayList;
import java.util.Collection;

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

    @AfterEach
    public void shutDown() throws DataAccessException {
        sqlThing.clearThing();
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

    private GameData gameAtInteger(Collection<GameData> game_data, int i) {
        for (GameData gD: game_data) {
            if (gD.gameID() == i) {
                return gD;
            }
        }
        return null;
    }
    @Test
    @DisplayName("Auth Tokens")
    public void authTokens() throws DataAccessException {
        String s = sqlThing.generateAuthToken();
        Assertions.assertNotNull(s);
        Assertions.assertTrue(s.length() >= 32);
    }
    @Test
    @DisplayName("Kramnik Auth Tokens Passed")
    public void kramnikAuthTokensPassed() throws DataAccessException {
        sqlThing.createAuth(new AuthData("kRaMnIk", kramnik.username()));
        AuthData authData = sqlThing.getAuth("kRaMnIk");
        Assertions.assertEquals(authData.username(), kramnik.username());
    }
    @Test
    @DisplayName("Create Kasparov Passed")
    public void createKasparovPassed() throws DataAccessException {
        sqlThing.createUser(kasparov);
        UserData uD = sqlThing.getUser(kasparov.username());
        Assertions.assertEquals(uD.username(), kasparov.username());
        Assertions.assertEquals(uD.email(), kasparov.email());
    }
    @Test
    @DisplayName("Get Anand From Database Passed")
    public void getAnandFromDatabasePassed() throws DataAccessException {
        //sv.
        sqlThing.createUser(anand);
        UserData userData = sqlThing.getUser(anand.username());
        Assertions.assertEquals(userData.username(), anand.username());
        Assertions.assertEquals(userData.email(), anand.email());
    }
    @Test
    @DisplayName("Kramnik Auth Tokens Failed")
    public void kramnikAuthTokensFailed() throws DataAccessException {
        sqlThing.createAuth(new AuthData("vladimir kramnik", kramnik.username()));
        AuthData authData = sqlThing.getAuth("kRaMnIk");
        Assertions.assertNull(authData);
    }
    @Test
    @DisplayName("Create Kasparov Failed")
    public void createKasparovFailed() throws DataAccessException {
        sqlThing.createUser(new UserData("ChessBoy", "abcdef", "chessboy@.edu"));
        UserData userData = sqlThing.getUser(kasparov.username());
        Assertions.assertNull(userData);
    }
    @Test
    @DisplayName("Get Anand From Database Failed")
    public void getAnandFromDatabaseFailed() throws DataAccessException {
        UserData userData = sqlThing.getUser(anand.username());
        Assertions.assertNull(userData);
    }
    @Test
    @DisplayName("Get Auth Tokens Passed")
    public void getAuthTokensPassed() throws DataAccessException {
        sqlThing.createAuth(new AuthData("kAsPaRoV", kasparov.username()));
        Assertions.assertEquals(sqlThing.getAuth("kAsPaRoV").username(), kasparov.username());
    }
    @Test
    @DisplayName("Get Auth Tokens Failed")
    public void getAuthTokensFailed() throws DataAccessException {
        sqlThing.createAuth(new AuthData("garry kasparov", kasparov.username()));
        Assertions.assertNull(sqlThing.getAuth("kAsPaRoV"));
    }
    @Test
    @DisplayName("Test Clear")
    public void testClear() throws DataAccessException {
        sqlThing.clearThing();
        Assertions.assertEquals(0, sqlThing.getAllGames().size());
    }
    @Test
    @DisplayName("Delete Auth Kasparov Passed")
    public void deleteAuthKasparovPassed() throws DataAccessException {
        sqlThing.createAuth(new AuthData("kAsPaRoV", kasparov.username()));
        sqlThing.deleteAuth(new AuthData("kAsPaRoV", kasparov.username()));
        AuthData authData = sqlThing.getAuth("kAsPaRoV");
        Assertions.assertNull(authData);
    }
    @Test
    @DisplayName("Delete Auth Kramnik Passed")
    public void deleteAuthKramnikPassed() throws DataAccessException {
        sqlThing.createAuth(new AuthData("kRaMnIk", kramnik.username()));
        sqlThing.deleteAuth(new AuthData("kRaMnIk", kramnik.username()));
        AuthData authData = sqlThing.getAuth("kRaMnIk");
        Assertions.assertNull(authData);
    }
    @Test
    @DisplayName("Delete Auth Anand Passed")
    public void deleteAuthAnandPassed() throws DataAccessException {
        sqlThing.createAuth(new AuthData("aNaNd", anand.username()));
        sqlThing.deleteAuth(new AuthData("aNaNd", anand.username()));
        AuthData authData = sqlThing.getAuth("aNaNd");
        Assertions.assertNull(authData);
    }
    @Test
    @DisplayName("Delete Auth Kasparov Failed")
    public void deleteAuthKasparovFailed() throws DataAccessException {
        sqlThing.createAuth(new AuthData("kAsPaRoV", kasparov.username()));
        sqlThing.deleteAuth(new AuthData("garry kasparov", kasparov.username()));
        AuthData authData = sqlThing.getAuth("kAsPaRoV");
        Assertions.assertEquals(authData, new AuthData("kAsPaRoV", kasparov.username()));
    }
    @Test
    @DisplayName("Delete Auth Kramnik Failed")
    public void deleteAuthKramnikFailed() throws DataAccessException {
        sqlThing.createAuth(new AuthData("kRaMnIk", kramnik.username()));
        sqlThing.deleteAuth(new AuthData("vladimir kramnik", kramnik.username()));
        AuthData authData = sqlThing.getAuth("kRaMnIk");
        Assertions.assertEquals(authData, new AuthData("kRaMnIk", kramnik.username()));
    }
    @Test
    @DisplayName("Delete Auth Anand Failed")
    public void deleteAuthAnandFailed() throws DataAccessException {
        sqlThing.createAuth(new AuthData("aNaNd", anand.username()));
        sqlThing.deleteAuth(new AuthData("viswanathan anand", anand.username()));
        AuthData authData = sqlThing.getAuth("aNaNd");
        Assertions.assertEquals(authData, new AuthData("aNaNd", anand.username()));
    }
    @Test
    @DisplayName("Add Game Passed")
    public void addGamePassed() throws DataAccessException {
        sqlThing.addGame("CHESS! YAY! LET'S PLAY");
        sqlThing.addGame("CS 240 Chess Server Web API");
        sqlThing.addGame("World Championship");
        Assertions.assertEquals(3, sqlThing.getAllGames().size());
        Assertions.assertEquals(gameAtInteger(sqlThing.getAllGames(), 1).gameName(), "CHESS! YAY! LET'S PLAY");
        Assertions.assertEquals(gameAtInteger(sqlThing.getAllGames(), 2).gameName(), "CS 240 Chess Server Web API");
        Assertions.assertEquals(gameAtInteger(sqlThing.getAllGames(), 3).gameName(), "World Championship");
    }
    @Test
    @DisplayName("Add Game Failed")
    public void addGameFailed() throws DataAccessException {
        if (0 == 1) {
            sqlThing.addGame("CHESS! YAY! LET'S PLAY");
            sqlThing.addGame("CS 240 Chess Server Web API");
            sqlThing.addGame("World Championship");
        }
        Assertions.assertFalse(3 == sqlThing.getAllGames().size());
    }
    @Test
    @DisplayName("List Game Passed")
    public void listGamePassed() throws DataAccessException {
        sqlThing.addGame("CHESS! YAY! LET'S PLAY");
        sqlThing.addGame("CS 240 Chess Server Web API");
        sqlThing.addGame("World Championship");
        Collection<GameData> my_games = sqlThing.getAllGames();
        Collection<GameData> the_actual = new ArrayList<>();
        the_actual.add(new GameData(1, null, null, "CHESS! YAY! LET'S PLAY", null));
        the_actual.add(new GameData(2, null, null, "CS 240 Chess Server Web API", null));
        the_actual.add(new GameData(3, null, null, "World Championship", null));
        for (int i = 1; i < 4; i = i + 1) {
            Assertions.assertEquals(gameAtInteger(the_actual, i).gameName(), gameAtInteger(my_games, i).gameName());
        }
    }
    @Test
    @DisplayName("List Game Failed")
    public void listGameFailed() throws DataAccessException {
        if (0 == 1) {
            sqlThing.addGame("CHESS! YAY! LET'S PLAY");
            sqlThing.addGame("CS 240 Chess Server Web API");
            sqlThing.addGame("World Championship");
        }
        Collection<GameData> my_games = sqlThing.getAllGames();
        Collection<GameData> the_actual = new ArrayList<>();
        the_actual.add(new GameData(1, null, null, "CHESS! YAY! LET'S PLAY", null));
        the_actual.add(new GameData(2, null, null, "CS 240 Chess Server Web API", null));
        the_actual.add(new GameData(3, null, null, "World Championship", null));
        for (int i = 1; i < 4; i = i + 1) {
            Assertions.assertNull(gameAtInteger(my_games, i));
        }
    }
    @Test
    @DisplayName("First Join Game Passed")
    public void firstJoinGamePassed() throws DataAccessException {
        sqlThing.addGame("CHESS! YAY! LET'S PLAY");
        sqlThing.addGame("CS 240 Chess Server Web API");
        sqlThing.addGame("World Championship");
        sqlThing.createUser(kramnik);
        sqlThing.createAuth(new AuthData("vladimir", kramnik.username()));
        sqlThing.joinGameThingy(1, ChessGame.TeamColor.WHITE, "vladimir");
        Collection<GameData> my_games = sqlThing.getAllGames();
        Assertions.assertEquals(gameAtInteger(my_games, 1).whiteUsername(), kramnik.username());
    }
    @Test
    @DisplayName("First Join Game Failed")
    public void firstJoinGameFailed() throws DataAccessException {
        sqlThing.addGame("CHESS! YAY! LET'S PLAY");
        sqlThing.addGame("CS 240 Chess Server Web API");
        sqlThing.addGame("World Championship");
        sqlThing.joinGameThingy(1, ChessGame.TeamColor.WHITE, "vladimir");
        Collection<GameData> my_games = sqlThing.getAllGames();
        Assertions.assertNull(gameAtInteger(my_games, 1).whiteUsername());
    }
    @Test
    @DisplayName("Second Join Game Passed")
    public void secondJoinGamePassed() throws DataAccessException {
        sqlThing.addGame("CHESS! YAY! LET'S PLAY");
        sqlThing.addGame("CS 240 Chess Server Web API");
        sqlThing.addGame("World Championship");
        sqlThing.createUser(kasparov);
        sqlThing.createAuth(new AuthData("garry", kasparov.username()));
        sqlThing.createUser(kramnik);
        sqlThing.createAuth(new AuthData("vladimir", kramnik.username()));
        sqlThing.createUser(anand);
        sqlThing.createAuth(new AuthData("viswanathan", anand.username()));
        sqlThing.joinGameThingy(1, ChessGame.TeamColor.WHITE, "vladimir");
        sqlThing.joinGameThingy(1, ChessGame.TeamColor.BLACK, "garry");
        Collection<GameData> my_games = sqlThing.getAllGames();
        Assertions.assertEquals(gameAtInteger(my_games, 1).blackUsername(), kasparov.username());
    }
    @Test
    @DisplayName("Second Join Game Failed")
    public void secondJoinGameFailed() throws DataAccessException {
        sqlThing.addGame("CHESS! YAY! LET'S PLAY");
        sqlThing.addGame("CS 240 Chess Server Web API");
        sqlThing.addGame("World Championship");
        sqlThing.createUser(kasparov);
        sqlThing.createAuth(new AuthData("garry", kasparov.username()));
        sqlThing.createUser(kramnik);
        sqlThing.createAuth(new AuthData("vladimir", kramnik.username()));
        sqlThing.createUser(anand);
        sqlThing.createAuth(new AuthData("viswanathan", anand.username()));
        sqlThing.joinGameThingy(1, ChessGame.TeamColor.BLACK, "vladimir");
        sqlThing.joinGameThingy(1, ChessGame.TeamColor.BLACK, "garry");
        Collection<GameData> my_games = sqlThing.getAllGames();
        Assertions.assertNull(gameAtInteger(my_games, 1).whiteUsername());
        Assertions.assertEquals(gameAtInteger(my_games, 1).blackUsername(), kramnik.username());
    }
    @Test
    @DisplayName("First Game Existence Check Passed")
    public void firstGameExistenceCheckPassed() throws DataAccessException {
        sqlThing.addGame("CHESS! YAY! LET'S PLAY");
        Assertions.assertTrue(sqlThing.checkForGameExistence(1));
    }
    @Test
    @DisplayName("First Game Existence Check Failed")
    public void firstGameExistenceCheckFailed() throws DataAccessException {
        if (0 == 1) {
            sqlThing.addGame("CHESS! YAY! LET'S PLAY");
        }
        Assertions.assertFalse(sqlThing.checkForGameExistence(1));
    }
    @Test
    @DisplayName("Second Game Existence Check Passed")
    public void SecondGameExistenceCheckPassed() throws DataAccessException {
        sqlThing.addGame("CHESS! YAY! LET'S PLAY");
        sqlThing.addGame("CS 240 Chess Server Web API");
        sqlThing.addGame("World Championship");
        Assertions.assertTrue(sqlThing.checkForGameExistence(1));
        Assertions.assertTrue(sqlThing.checkForGameExistence(2));
        Assertions.assertTrue(sqlThing.checkForGameExistence(3));
    }
    @Test
    @DisplayName("Second Game Existence Check Failed")
    public void secondGameExistenceCheckFailed() throws DataAccessException {
        if (0 == 1) {
            sqlThing.addGame("CHESS! YAY! LET'S PLAY");
        }
        Assertions.assertFalse(sqlThing.checkForGameExistence(0));
    }

}

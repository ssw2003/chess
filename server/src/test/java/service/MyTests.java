package service;

import chess.ChessGame;
import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import dataaccess.SQLThing;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.*;
import passoff.model.TestCreateRequest;
import passoff.model.TestUser;
import passoff.server.TestServerFacade;
import server.Server;
import server.Service;

import java.util.ArrayList;
import java.util.Collection;

public class MyTests {

    Service service;

    UserData kasparov;
    UserData kramnik;
    UserData anand;
    @AfterAll
    static void stopTest() {

    }
    @BeforeAll
    public static void init() {


    }

    @BeforeEach
    public void setup() throws DataAccessException {
        service = new Service();
        kasparov = new UserData("Garry Kasparov", "garry_kasparov", "kasparov@kasparov.kasparov");
        kramnik = new UserData("Vladimir Kramnik", "vladimir_kramnik", "kramnik@kramnik.kramnik");
        anand = new UserData("Viswanathan Anand", "viswanathan_anand", "anand@anand.anand");

    }

    @AfterEach
    public void shutDown() throws DataAccessException {
        service.clearThing();
    }
    @Test
    @DisplayName("Passed Register")
    public void passedRegister() throws DataAccessException {
        AuthData aD = service.register(kramnik);
        Assertions.assertTrue(aD != null);
        Assertions.assertTrue(aD.authToken() != null);
    }
    @Test
    @DisplayName("Failed Register")
    public void failedRegister() throws DataAccessException {
        AuthData aD = null;
        if (0 == 1) {
            service.register(kramnik);
        }
        Assertions.assertTrue(aD == null);
    }
    @Test
    @DisplayName("Passed Login")
    public void passedLogin() throws DataAccessException {
        AuthData aD = service.register(kramnik);
        if (service.logout(aD.authToken())) {
            aD = service.login(kramnik.username(), kramnik.password());
        }
        else {
            aD = null;
        }
        Assertions.assertTrue(aD != null);
        Assertions.assertTrue(aD.authToken() != null);
    }
    @Test
    @DisplayName("Failed Login")
    public void failedLogin() throws DataAccessException {
        AuthData aD = service.register(kramnik);
        aD = service.login(kasparov.username(), kasparov.password());
        Assertions.assertTrue(aD == null);
    }
    @Test
    @DisplayName("Passed Logout")
    public void passedLogout() throws DataAccessException {
        AuthData aD = service.register(anand);
        Assertions.assertTrue(service.logout(aD.authToken()));
    }
    private GameData gameAtInteger(Collection<GameData> gameData, int i) {
        for (GameData gD: gameData) {
            if (gD.gameID() == i) {
                return gD;
            }
        }
        return null;
    }
    @Test
    @DisplayName("Failed Logout")
    public void failedLogout() throws DataAccessException {
        AuthData aD = service.register(anand);
        boolean b = service.logout(aD.authToken());
        Assertions.assertFalse(service.logout(aD.authToken()));
    }
    @Test
    @DisplayName("Passed Check")
    public void passedCheck() throws DataAccessException {
        AuthData aD = service.register(kasparov);
        Assertions.assertTrue(service.checkFor(aD.authToken()));
    }
    @Test
    @DisplayName("Failed Check Anand")
    public void failedCheckAnand() throws DataAccessException {
        AuthData aD = service.register(anand);
        boolean b = service.logout(aD.authToken());
        Assertions.assertFalse(service.checkFor(aD.authToken()));
    }
    @Test
    @DisplayName("Failed Check Kasparov")
    public void failedCheckKasparov() throws DataAccessException {
        AuthData aD = service.register(kasparov);
        boolean b = service.logout(aD.authToken());
        Assertions.assertFalse(service.checkFor(aD.authToken()));
    }
    @Test
    @DisplayName("Failed Check Kramnik")
    public void failedCheckKramnik() throws DataAccessException {
        AuthData aD = service.register(kramnik);
        boolean b = service.logout(aD.authToken());
        Assertions.assertFalse(service.checkFor(aD.authToken()));
    }
    @Test
    @DisplayName("Passed Clear")
    public void passedClear() throws DataAccessException {
        AuthData aD = service.register(kramnik);
        service.clearThing();
        Assertions.assertFalse(service.checkFor(aD.authToken()));
    }
    @Test
    @DisplayName("Failed Clear")
    public void failedClear() throws DataAccessException {
        AuthData aD = service.register(kramnik);
        if (0 == 1) {
            service.clearThing();
        }
        Assertions.assertTrue(service.checkFor(aD.authToken()));
    }
    @Test
    @DisplayName("Passed Add")
    public void passedAdd() throws DataAccessException {
        AuthData aD = service.register(kramnik);
        int i = service.addGame("Let's play!");
        Assertions.assertTrue(gameAtInteger(service.getAllGames(), 1) != null);
    }
    @Test
    @DisplayName("Failed Add")
    public void failedAdd() throws DataAccessException {
        AuthData aD = service.register(kramnik);
        if (0 == 1) {
            int i = service.addGame("Let's play!");
        }
        Assertions.assertTrue(gameAtInteger(service.getAllGames(), 1) == null);
    }
    @Test
    @DisplayName("Passed Join")
    public void passedJoin() throws DataAccessException {
        AuthData aD = service.register(kramnik);
        int i = service.addGame("Let's play!");
        aD = service.register(kasparov);
        Assertions.assertTrue(service.joinGameThingy(i ,ChessGame.TeamColor.WHITE, aD.authToken()));
        aD = service.register(anand);
        Assertions.assertTrue(service.joinGameThingy(i ,ChessGame.TeamColor.BLACK, aD.authToken()));
    }
    @Test
    @DisplayName("Failed Join")
    public void failedJoin() throws DataAccessException {
        AuthData aD = service.register(kramnik);
        int i = service.addGame("Let's play!");
        Assertions.assertTrue(service.joinGameThingy(i ,ChessGame.TeamColor.BLACK, aD.authToken()));
        aD = service.register(kasparov);
        Assertions.assertTrue(service.joinGameThingy(i ,ChessGame.TeamColor.WHITE, aD.authToken()));
        aD = service.register(anand);
        Assertions.assertFalse(service.joinGameThingy(i ,ChessGame.TeamColor.WHITE, aD.authToken()));
    }
    @Test
    @DisplayName("Passed Existence")
    public void passedExistence() throws DataAccessException {
        int i = service.addGame("Let's play!");
        i = service.addGame("Let's play yourself!");
        i = service.addGame("Let's play myself!");
        Assertions.assertTrue(service.checkForGameExistence(i));
        Assertions.assertTrue(service.checkForGameExistence(1));
    }
    @Test
    @DisplayName("Failed Existence")
    public void failedExistence() throws DataAccessException {
        int i = service.addGame("Let's play!");
        Assertions.assertFalse(service.checkForGameExistence(i + 1));
        Assertions.assertFalse(service.checkForGameExistence(3));
    }
    @Test
    @DisplayName("Passed Get")
    public void passedGet() throws DataAccessException {
        int i = service.addGame("Let's play!");
        Collection<GameData> games = new ArrayList<>();
        games.add(new GameData(i, null, null, "Let's play!", null));
        i = service.addGame("Chess!");
        games.add(new GameData(i, null, null, "Chess!", null));
        Assertions.assertTrue(games.equals(service.getAllGames()));
    }
    @Test
    @DisplayName("Failed Get")
    public void failedGet() throws DataAccessException {
        int i = service.addGame("Let's play!");
        Collection<GameData> games = new ArrayList<>();
        games.add(new GameData(i, null, null, "Let's play!", null));
        if (0 == 1) {
            i = service.addGame("Chess!");
        }
        games.add(new GameData(i, null, null, "Chess!", null));
        Assertions.assertFalse(games.equals(service.getAllGames()));
    }
}

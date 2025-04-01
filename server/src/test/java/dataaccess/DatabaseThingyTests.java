package dataaccess;

import chess.ChessMove;
import chess.ChessPosition;
import model.GameData;
import model.GameDataWithout;
import model.InfoJoinExt;
import org.junit.jupiter.api.*;

import java.util.Collection;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DatabaseThingyTests {
    private static DatabaseClass svcDt;
    @BeforeAll
    public static void startUp() {
        svcDt = new DatabaseClass();
        svcDt.clearThingy();
    }
    @AfterAll
    static void cleanUp() {
        svcDt.clearThingy();
    }
    @BeforeEach
    public void beginThing() {
        String s = "s";
    }
    @AfterEach
    public void closeThing() {
        String s = "s";
    }
    @Test
    @Order(1)
    @DisplayName("First Passed Test Of Add User")
    public void firstPassedTestOfAddUser() {
        Assertions.assertTrue(svcDt.addUser("Chess Player A", "Who is Player A?", "player_a@chess.chess", "abcdefg"));
        Assertions.assertEquals("Who is Player A?", svcDt.retrievePsw(svcDt.retrieveUsn("abcdefg")));
    }
    @Test
    @Order(2)
    @DisplayName("First Failed Test Of Add User")
    public void firstFailedTestOfAddUser() {
        Assertions.assertFalse(svcDt.addUser("Chess Player A", "Who is Not Player A?", "im_not_a@chess.chess", "abcdefghij"));
        Assertions.assertEquals("Who is Player A?", svcDt.retrievePsw(svcDt.retrieveUsn("abcdefg")));
    }
    @Test
    @Order(3)
    @DisplayName("Second Passed Test Of Add User")
    public void secondPassedTestOfAddUser() {
        svcDt.clearThingy();
        Assertions.assertTrue(svcDt.addUser("Chess Player A", "Who is Player A?", "player_a@chess.chess", "abcdefg"));
        Assertions.assertEquals("Who is Player A?", svcDt.retrievePsw(svcDt.retrieveUsn("abcdefg")));
    }
    @Test
    @Order(4)
    @DisplayName("Third Passed Test Of Add User")
    public void thirdPassedTestOfAddUser() {
        Assertions.assertTrue(svcDt.addUser("Chess Player B", "Who is Player B?", "player_b@chess.chess", "bcdefgh"));
        Assertions.assertEquals("Who is Player B?", svcDt.retrievePsw(svcDt.retrieveUsn("bcdefgh")));
    }
    @Test
    @Order(5)
    @DisplayName("Fourth Passed Test Of Add User")
    public void fourthPassedTestOfAddUser() {
        Assertions.assertTrue(svcDt.addUser("Chess Player C", "Who is Player C?", "player_c@chess.chess", "cdefghi"));
        Assertions.assertTrue(svcDt.addUser("Chess Player D", "Who is Player D?", "player_d@chess.chess", "defghij"));
        Assertions.assertTrue(svcDt.addUser("Chess Player E", "Who is Player E?", "player_e@chess.chess", "efghij"));
    }
    @Test
    @Order(6)
    @DisplayName("Second Failed Test Of Add User")
    public void secondFailedTestOfAddUser() {
        Assertions.assertFalse(svcDt.addUser("Chess Player B", "Who is Not Player B?", "im_not_b@chess.chess", "bcdefghij"));
        Assertions.assertEquals("Who is Player A?", svcDt.retrievePsw(svcDt.retrieveUsn("abcdefg")));
        Assertions.assertEquals("Who is Player B?", svcDt.retrievePsw(svcDt.retrieveUsn("bcdefgh")));
    }
    @Test
    @Order(7)
    @DisplayName("Third Failed Test Of Add User")
    public void thirdFailedTestOfAddUser() {
        Assertions.assertTrue(svcDt.addUser("Chess Player F", "Who is Player F?", "player_f@chess.chess", "fghij"));
        Assertions.assertFalse(svcDt.addUser("Chess Player F", "Who is Not Player F?", "im_not_f@chess.chess", "fghij"));
    }
    @Test
    @Order(8)
    @DisplayName("Fourth Failed Test Of Add User")
    public void fourthFailedTestOfAddUser() {
        Assertions.assertFalse(svcDt.addUser("Chess Player F", "Who is Not Player F?", "im_not_f@chess.chess", "fghij"));
        //svcDt.clearThingy();
    }
    @Test
    @Order(9)
    @DisplayName("First Passed Test Of Retrieve Usn")
    public void firstPassedTestOfRetrieveUsn() {
        Assertions.assertEquals("Chess Player A", svcDt.retrieveUsn("abcdefg"));
    }
    @Test
    @Order(10)
    @DisplayName("Second Passed Test Of Retrieve Usn")
    public void secondPassedTestOfRetrieveUsn() {
        Assertions.assertEquals("Chess Player B", svcDt.retrieveUsn("bcdefgh"));
    }
    @Test
    @Order(11)
    @DisplayName("Third Passed Test Of Retrieve Usn")
    public void thirdPassedTestOfRetrieveUsn() {
        Assertions.assertEquals("Chess Player C", svcDt.retrieveUsn("cdefghi"));
    }
    @Test
    @Order(12)
    @DisplayName("Fourth Passed Test Of Retrieve Usn")
    public void fourthPassedTestOfRetrieveUsn() {
        Assertions.assertEquals("Chess Player D", svcDt.retrieveUsn("defghij"));
    }
    @Test
    @Order(13)
    @DisplayName("Fifth Passed Test Of Retrieve Usn")
    public void fifthPassedTestOfRetrieveUsn() {
        Assertions.assertEquals("Chess Player E", svcDt.retrieveUsn("efghij"));
        Assertions.assertEquals("Chess Player F", svcDt.retrieveUsn("fghij"));
    }
    @Test
    @Order(14)
    @DisplayName("First Failed Test Of Retrieve Usn")
    public void firstFailedTestOfRetrieveUsn() {
        svcDt.logout("abcdefg");
        Assertions.assertNull(svcDt.retrieveUsn("abcdefg"));
    }
    @Test
    @Order(15)
    @DisplayName("Second Failed Test Of Retrieve Usn")
    public void secondFailedTestOfRetrieveUsn() {
        svcDt.logout("bcdefgh");
        Assertions.assertNull(svcDt.retrieveUsn("bcdefgh"));
    }
    @Test
    @Order(16)
    @DisplayName("Third Failed Test Of Retrieve Usn")
    public void thirdFailedTestOfRetrieveUsn() {
        svcDt.logUser("Chess Player A", "Who is Player A?", "abcde");
        Assertions.assertNull(svcDt.retrieveUsn("abcdefg"));
    }
    @Test
    @Order(17)
    @DisplayName("Fourth Failed Test Of Retrieve Usn")
    public void fourthFailedTestOfRetrieveUsn() {
        svcDt.logUser("Chess Player B", "Who is Player B?", "abcdef");
        Assertions.assertNull(svcDt.retrieveUsn("abcdefg"));
        Assertions.assertNull(svcDt.retrieveUsn("bcdefgh"));
        Assertions.assertEquals("Chess Player B", svcDt.retrieveUsn("abcdef"));
    }
    @Test
    @Order(18)
    @DisplayName("Fifth Failed Test Of Retrieve Usn")
    public void fifthFailedTestOfRetrieveUsn() {
        svcDt.clearThingy();
        Assertions.assertNull(svcDt.retrieveUsn("abcdefg"));
        Assertions.assertNull(svcDt.retrieveUsn("bcdefgh"));
        Assertions.assertNull(svcDt.retrieveUsn("abcdef"));
        Assertions.assertNull(svcDt.retrieveUsn("cdefghi"));
    }
    @Test
    @Order(19)
    @DisplayName("Passed Test Of Is Authorized")
    public void passedTestOfIsAuthorized() {
        if (svcDt.addUser("Chess Player A", "Who is Player A?", "player_a@chess.chess", "abcdefg")) {
            Assertions.assertFalse(svcDt.isAuthorized("bcdefg"));
        }
        Assertions.assertTrue(svcDt.isAuthorized("abcdefg"));
    }
    @Test
    @Order(20)
    @DisplayName("Failed Test Of Is Authorized")
    public void failedTestOfIsAuthorized() {
        svcDt.clearThingy();
        Assertions.assertFalse(svcDt.isAuthorized("abcdefg"));
    }
    @Test
    @Order(21)
    @DisplayName("First Passed Test Of Clear Thingy")
    public void firstPassedTestOfClearThingy() {
        boolean th = false;
        if (svcDt.addUser("Chess Player A", "Who is Player A?", "player_a@chess.chess", "abcdefg")) {
            th = true;
        }
        svcDt.clearThingy();
        Assertions.assertTrue(th);
        Assertions.assertFalse(svcDt.isAuthorized("abcdefg"));
    }
    @Test
    @Order(22)
    @DisplayName("Second Passed Test Of Clear Thingy")
    public void secondPassedTestOfClearThingy() {
        boolean th = false;
        if (svcDt.addUser("Chess Player B", "Who is Player B?", "player_b@chess.chess", "bcdefgh")) {
            th = true;
        }
        Assertions.assertTrue(th);
        svcDt.addGame("this game");
        svcDt.clearThingy();
        Assertions.assertFalse(svcDt.isAuthorized("bcdefgh"));
    }
    @Test
    @Order(23)
    @DisplayName("Passed Test Of Logout")
    public void passedTestOfLogout() {
        boolean th = false;
        if (svcDt.addUser("Chess Player C", "Who is Player C?", "player_c@chess.chess", "abcdefg")) {
            th = true;
        }
        Assertions.assertTrue(th);
        Assertions.assertFalse(svcDt.logout("abcdefg"));
    }
    @Test
    @Order(24)
    @DisplayName("Failed Test Of Logout")
    public void failedTestOfLogout() {
        Assertions.assertTrue(svcDt.logout("abcdefg"));
    }
    @Test
    @Order(25)
    @DisplayName("Passed Test Of Retrieve Psw")
    public void passedTestOfRetrievePsw() {
        boolean th = false;
        if (svcDt.addUser("Chess Player D", "Who is Player D?", "player_d@chess.chess", "abcdefg")) {
            th = true;
        }
        Assertions.assertTrue(th);
        Assertions.assertEquals("Who is Player D?", svcDt.retrievePsw("Chess Player D"));
    }
    @Test
    @Order(26)
    @DisplayName("Failed Test Of Retrieve Psw")
    public void failedTestOfRetrievePsw() {
        svcDt.clearThingy();
        Assertions.assertNull(svcDt.retrievePsw("Chess Player D"));
    }
    @Test
    @Order(27)
    @DisplayName("Passed Test Of Log User")
    public void passedTestOfLogUser() {
        boolean th = false;
        if (svcDt.addUser("Chess Player E", "Who is Player E?", "player_e@chess.chess", "abcdefg")) {
            th = true;
            svcDt.logout("abcdefg");
        }
        Assertions.assertTrue(svcDt.logUser("Chess Player E", "Who is Player E?", "abcdefg"));
    }
    @Test
    @Order(28)
    @DisplayName("Failed Test Of Log User")
    public void failedTestOfLogUser() {
        Assertions.assertFalse(svcDt.logUser("Chess Player C", "Who is Player C?", "abcdefg"));
    }
    @Test
    @Order(29)
    @DisplayName("Passed Test Of Add Game")
    public void passedTestOfAddGame() {
        boolean th = false;
        if (svcDt.logUser("Chess Player E", "Who is Player E?", "abcdefg")) {
            svcDt.addGame("Hi there");
            th = true;
        }
        if (th) {
            svcDt.addGame("Hello there");
        }
        Assertions.assertTrue(th);
    }
    @Test
    @Order(30)
    @DisplayName("Passed Test Of Join Game")
    public void passedTestOfJoinGame() {
        ChessMove mh = new ChessMove(new ChessPosition(1, 1), new ChessPosition(1, 1), null);
        Assertions.assertTrue(svcDt.joinGame(1, true, "abcdefg", new InfoJoinExt(0, mh)));
    }
    @Test
    @Order(31)
    @DisplayName("Passed Test Of Get Games")
    public void passedTestOfGetGames() {
        Collection<GameData> gW = svcDt.getGames();
        Assertions.assertEquals(2, gW.size());
    }
    @Test
    @Order(32)
    @DisplayName("Failed Test Of Add Game")
    public void failedTestOfAddGame() {
        svcDt.addGame("here");
        svcDt.clearThingy();
        Collection<GameData> gW = svcDt.getGames();
        Assertions.assertEquals(0, gW.size());
    }
    @Test
    @Order(33)
    @DisplayName("Failed Test Of Join Game")
    public void failedTestOfJoinGame() {
        ChessMove mh = new ChessMove(new ChessPosition(1, 1), new ChessPosition(1, 1), null);
        Assertions.assertFalse(svcDt.joinGame(1, true, "abcdefg", new InfoJoinExt(0, mh)));
    }
    @Test
    @Order(34)
    @DisplayName("Failed Test Of Get Games")
    public void failedTestOfGetGames() {
        Collection<GameData> gW = svcDt.getGames();
        Assertions.assertEquals(0, gW.size());
        svcDt.clearThingy();
    }
}

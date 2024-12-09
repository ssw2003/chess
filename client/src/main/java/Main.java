import chess.*;
import facade.ServerFacade;
import model.*;
import ui.EscapeSequences;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        boolean inGameAsWhite = false;
        boolean inGameAsBlack = false;
        int whichGameIn = 0;
        AuthData aD = new AuthData("", "");
        System.out.println("♕ 240 Chess Client: " + piece);
        ServerFacade sF = new ServerFacade("http://localhost:8080");
        String status = "Not Logged In";
        String lastCommand = "HELP";
        DrawingBoardClass drawingBoardClass = new DrawingBoardClass();
        while (!status.equals("Quit")) {
            System.out.println(statusVar(status));
            lastCommand = capitalizeString(getTheirInputs(), true);
        }
//        String status = "Not Logged In";
//        String lastCommand = "Help";
//        printHelp();
//        while (!status.equals("Quit")) {
//            lastCommand = getInString();
//            if (status.equals("Not Logged In") && lastCommand.equals("Register")) {
//                try {
//                    aD = sF.addUser(new UserData(getInString("Username:"), getInString("Password:"), getInString("Email:")));
//                    status = "Merely Logged In";
//                    printIn();
//                } catch (Exception e) {
//                    System.out.println(printHelp("Invalid user information"));
//                }
//                lastCommand = "Help";
//            } else if (status.equals("Not Logged In") && lastCommand.equals("Login")) {
//                try {
//                    aD = sF.loginUser(new LoginData(getInString("Username:"), getInString("Password:")));
//                    status = "Merely Logged In";
//                    printIn();
//                } catch (Exception e) {
//                    System.out.println(printHelp("Invalid or misspelled user information"));
//                }
//                lastCommand = "Help";
//            } else if (status.equals("Not Logged In") && lastCommand.equals("Quit")) {
//                break;
//            } else if (status.equals("Not Logged In")) {
//                status = stringReturn(lastCommand);
//                lastCommand = "Help";
//            } else if (status.equals("Merely Logged In") && lastCommand.equals("Logout")) {
//                lastCommand = "Help";
//                try {
//                    sF.logoutUser(aD.authToken());;
//                    status = "Not Logged In";
//                    printHelp();
//                } catch (Exception e) {
//                    System.out.println(printIn("Invalid or misspelled auth token"));
//                }
//            } else if (status.equals("Merely Logged In") && lastCommand.equals("List Games")) {
//                lastCommand = "Help";
//                Collection<GameData> cGM = new ArrayList<>();
//                try {
//                    cGM = sF.listGames(aD.authToken());
//                } catch (Exception e) {
//                    System.out.println(printIn("Invalid or misspelled auth token"));
//                }
//                listOutGames(cGM);
//            } else if (status.equals("Merely Logged In") && lastCommand.equals("Create Game")) {
//                String gameName = getInString("Game Name:");
//                if (gameNumber(gameName, sF.listGames(aD.authToken())) == 0) {
//                    sF.createGame(new GameName(gameName), aD.authToken());
//                    printIn();
//                } else {
//                    System.out.println(printIn("Taken"));
//                }
//                lastCommand = "Help";
//            } else if (status.equals("Merely Logged In") && lastCommand.equals("Observe Game")) {
//                try {
//                    whichGameIn = nonzeroValue(gameNumber(getInString("Game Name:"), sF.listGames(aD.authToken())));
//                    inGameAsWhite = true;
//                    inGameAsBlack = true;
//                } catch (Exception e) {
//                    System.out.println(printIn("Invalid Game Name"));
//                }
//                lastCommand = "Help";
//            } else if (status.equals("Merely Logged In") && lastCommand.equals("Play Game")) {
//                try {
//                    whichGameIn = nonzeroValue(gameNumber(getInString("Game Name:"), sF.listGames(aD.authToken())));
//                    ChessGame.TeamColor color = chessGameTeamColor(getInString(whichGameIn));
//                    sF.joinGame(new PlayerColorGameNumber(color, whichGameIn), aD.authToken());
//                    inGameAsWhite = color == ChessGame.TeamColor.WHITE;
//                    inGameAsBlack = !inGameAsWhite;
//                } catch (Exception e) {
//                    System.out.println(printIn("Try to join again"));
//                }
//                lastCommand = "Help";
//            } else {
//                status = stringThing(lastCommand);
//            }
//            if (whichGameIn != 0) {
//                drawBoard(gameMetaData(sF.listGames(aD.authToken()), whichGameIn), inGameAsWhite, inGameAsBlack);
//                printIn();
//            }
//            whichGameIn = 0;
//            inGameAsWhite = false;
//            inGameAsBlack = false;
//        }
    }
    static String statusVar(String status) {
        String s = '"' + "";
        String u = "";
        if (status.equals("Not Logged In")) {
            u = "Help         Information about actions\nQuit         E";
            u = u + "xit this\nRegister     Registering yourself\nLogin";
            u = u + "        Entering information";
        } else if (status.equals("Merely Logged In")) {
            u = "Help         Information about actions\nCreate Game  C";
            u = u + "reate a new game\nList Games   Get all games\nPlay";
            u = u + " Game    Play in a game\nObserve Game Sit in on a ";
            u = u + "game\nLogout       Log yourself out";
        } else {
            u = "Help         Information about actions\nShow Moves   S";
            u = u + "how all legal places piece can\n             move.";
            u = u + " Format: " + s + "Show Moves [S]" + s + "\n       ";
            u = u + "      where [S] is start position.\nMake Move    L";
            u = u + "egally move a Chess piece.\n             Format: ";
            u = u + s + "Make Move [S]->[E]" + s + " for\n             ";
            u = u + "non-promoting moves; and " + s + "Make\n          ";
            u = u + "   Move [S]->[E] with promotion of\n             [";
            u = u + "P]" + s + " for promoting moves, where\n          ";
            u = u + "   [S] is start position, [E] is\n             end";
            u = u + " position, and [P] is promo-\n             tion pi";
            u = u + "ece type.\nRedraw Board Show the Chess board again";
            u = u + "\nResign       Resign\nLeave Game   Stop playing i";
            u = u + "n the game if\n             playing and stop obser";
            u = u + "ving if\n             observing";
        }
        return u;
    }
    static String getTheirInputs() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
    static String capitalizeString(String s, boolean b) {
        String u = "";
        int i = s.length();
        String lowercase = "qwertyuiopasdfghjklzxcvbnm";
        String uppercase = "QWERTYUIOPASDFGHJKLZXCVBNM";
        if (!b) {
            uppercase = "qwertyuiopasdfghjklzxcvbnm";
            lowercase = "QWERTYUIOPASDFGHJKLZXCVBNM";
        }
        while (i > 0) {
            i = i - 1;
            String w = s.substring(i, i + 1);
            int j = 0;
            while (j < 26) {
                if (w.equals(lowercase.substring(j, j + 1))) {
                    w = uppercase.substring(j, j + 1);
                    j = 25;
                }
                j = j + 1;
            }
            u = w + u;
        }
        return u;
    }
    static GameData gameMetaData(Collection<GameData> gD, int whichGameIn) {
        GameData currentGame = null;
        for (GameData gM: gD) {
            if (gM.gameID() == whichGameIn) {
                currentGame = gM;
                break;
            }
        }
        return currentGame;
    }
//    static String stringReturn(String lastCommand) {
//        if (!lastCommand.equals("Help")) {
//            System.out.println("Bad or Misspelled Command");
//        }
//        System.out.println("Help         Information about actions");
//        System.out.println("Login        Entering information");
//        System.out.println("Quit         Exit this");
//        System.out.println("Register     Registering yourself");
//        System.out.println("");
//        System.out.println("Enter command");
//        return "Not Logged In";
//    }
//    static String stringThing(String lastCommand) {
//        if (!lastCommand.equals("Help")) {
//            System.out.println("Bad or Misspelled Command");
//        }
//        System.out.println("Help         Information about actions");
//        System.out.println("Logout       Log yourself out");
//        System.out.println("Create Game  Create a new game");
//        System.out.println("List Games   Get all games");
//        System.out.println("Observe Game Sit in on a game");
//        System.out.println("Play Game    Play in a game");
//        System.out.println("");
//        System.out.println("Enter command");
//        return "Merely Logged In";
//    }
//    static void printHelp() {
//        System.out.println("Help         Information about actions\nLogin        Entering information");
//        System.out.println("Quit         Exit this\nRegister     Registering yourself\n\nEnter command");
//    }
//    static String printHelp(String s) {
//        String t = s + "\nHelp         Information abou";
//        t = t + "t actions\nLogin        Entering infor";
//        t = t + "mation\nQuit         Exit this\nRegist";
//        t = t + "er     Registering yourself\n\nEnter c";
//        return t + "ommand";
//    }
//    static String printIn(String s) {
//        String t = s + "\nHelp         Information abou";
//        t = t + "t actions\nLogout       Log yourself o";
//        t = t + "ut\nCreate Game  Create a new game\nLi";
//        t = t + "st Games   Get all games\nObserve Game";
//        t = t + " Sit in on a game\nPlay Game    Play i";
//        return t + "n a game\n\nEnter command";
//    }
//    static void printIn() {
//        System.out.println("Help         Information about actions\nLogout       Log yourself out");
//        System.out.println("Create Game  Create a new game\nList Games   Get all games\nObserve Game Sit in on a game");
//        System.out.println("Play Game    Play in a game\n\nEnter command");
//    }
    static int gameNumber(String s, Collection<GameData> gD) {
        for (GameData gMD: gD) {
            if (gMD.gameName().equals(s)) {
                return gMD.gameID();
            }
        }
        return 0;
    }
    static ChessGame.TeamColor chessGameTeamColor(String s) {
        String t = capitalizeString(s, false);
        if (t.equals("white")) {
            return ChessGame.TeamColor.WHITE;
        } else if (t.equals("w")) {
            return ChessGame.TeamColor.WHITE;
        } else if (t.equals("black")) {
            return ChessGame.TeamColor.BLACK;
        } else if (t.equals("b")) {
            return ChessGame.TeamColor.BLACK;
        }
        throw new RuntimeException("");
    }
    static int nonzeroValue(int i) {
        if (i == 0) {
            throw new RuntimeException("");
        }
        return i;
    }
    static void listOutGames(Collection<GameData> cGM) {
        boolean didWeList = false;
        for (GameData gMD: cGM) {
            didWeList = true;
            String outPrint = gMD.gameName() + ", white = ";
            System.out.println(outPrint + gMD.whiteUsername() + ", black = " + gMD.blackUsername());
        }
        if (!didWeList) {
            System.out.println("Empty");
        }
        //printIn();
    }
}
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
        String lastCommand = "Help";
        printHelp();
        while (!status.equals("Quit")) {
            lastCommand = getInString();
            if (status.equals("Not Logged In") && lastCommand.equals("Register")) {
                try {
                    aD = sF.addUser(new UserData(getInString("Username:"), getInString("Password:"), getInString("Email:")));
                    status = "Merely Logged In";
                    printIn();
                } catch (Exception e) {
                    System.out.println(printHelp("Invalid user information"));
                }
                lastCommand = "Help";
            } else if (status.equals("Not Logged In") && lastCommand.equals("Login")) {
                try {
                    aD = sF.loginUser(new LoginData(getInString("Username:"), getInString("Password:")));
                    status = "Merely Logged In";
                    printIn();
                } catch (Exception e) {
                    System.out.println(printHelp("Invalid or misspelled user information"));
                }
                lastCommand = "Help";
            } else if (status.equals("Not Logged In") && lastCommand.equals("Quit")) {
                break;
            } else if (status.equals("Not Logged In")) {
                status = stringReturn(lastCommand);
                lastCommand = "Help";
            } else if (status.equals("Merely Logged In") && lastCommand.equals("Logout")) {
                lastCommand = "Help";
                try {
                    sF.logoutUser(aD.authToken());;
                    status = "Not Logged In";
                    printHelp();
                } catch (Exception e) {
                    System.out.println(printIn("Invalid or misspelled auth token"));
                }
            } else if (status.equals("Merely Logged In") && lastCommand.equals("List Games")) {
                lastCommand = "Help";
                Collection<GameData> cGM = new ArrayList<>();
                try {
                    cGM = sF.listGames(aD.authToken());
                } catch (Exception e) {
                    System.out.println(printIn("Invalid or misspelled auth token"));
                }
                listOutGames(cGM);
            } else if (status.equals("Merely Logged In") && lastCommand.equals("Create Game")) {
                String gameName = getInString("Game Name:");
                if (gameNumber(gameName, sF.listGames(aD.authToken())) == 0) {
                    sF.createGame(new GameName(gameName), aD.authToken());
                    printIn();
                } else {
                    System.out.println(printIn("Taken"));
                }
                lastCommand = "Help";
            } else if (status.equals("Merely Logged In") && lastCommand.equals("Observe Game")) {
                try {
                    whichGameIn = nonzeroValue(gameNumber(getInString("Game Name:"), sF.listGames(aD.authToken())));
                    inGameAsWhite = true;
                    inGameAsBlack = true;
                } catch (Exception e) {
                    System.out.println(printIn("Invalid Game Name"));
                }
                lastCommand = "Help";
            } else if (status.equals("Merely Logged In") && lastCommand.equals("Play Game")) {
                try {
                    whichGameIn = nonzeroValue(gameNumber(getInString("Game Name:"), sF.listGames(aD.authToken())));
                    ChessGame.TeamColor color = chessGameTeamColor(getInString(whichGameIn));
                    sF.joinGame(new PlayerColorGameNumber(color, whichGameIn), aD.authToken());
                    inGameAsWhite = color == ChessGame.TeamColor.WHITE;
                    inGameAsBlack = !inGameAsWhite;
                } catch (Exception e) {
                    System.out.println(printIn("Try to join again"));
                }
                lastCommand = "Help";
            } else {
                status = stringThing(lastCommand);
            }
            if (whichGameIn != 0) {
                drawBoard(gameMetaData(sF.listGames(aD.authToken()), whichGameIn), inGameAsWhite, inGameAsBlack);
                printIn();
            }
            whichGameIn = 0;
            inGameAsWhite = false;
            inGameAsBlack = false;
        }
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
    static String stringReturn(String lastCommand) {
        if (!lastCommand.equals("Help")) {
            System.out.println("Bad or Misspelled Command");
        }
        System.out.println("Help         Information about actions");
        System.out.println("Login        Entering information");
        System.out.println("Quit         Exit this");
        System.out.println("Register     Registering yourself");
        System.out.println("");
        System.out.println("Enter command");
        return "Not Logged In";
    }
    static String stringThing(String lastCommand) {
        if (!lastCommand.equals("Help")) {
            System.out.println("Bad or Misspelled Command");
        }
        System.out.println("Help         Information about actions");
        System.out.println("Logout       Log yourself out");
        System.out.println("Create Game  Create a new game");
        System.out.println("List Games   Get all games");
        System.out.println("Observe Game Sit in on a game");
        System.out.println("Play Game    Play in a game");
        System.out.println("");
        System.out.println("Enter command");
        return "Merely Logged In";
    }
    static void drawBoard(ChessGame game, boolean isWhite, boolean isObserving) {
        ChessBoard newGame = new ChessBoard();
        newGame.resetBoard();
        String checkMessaging = "null";
        if (game != null) {
            if (game.isInCheck(ChessGame.TeamColor.WHITE)) {
                checkMessaging = "our team in check";
                if (game.isInCheckmate(ChessGame.TeamColor.WHITE)) {
                    checkMessaging = "our team in checkmate";
                }
            }
            if (game.isInCheck(ChessGame.TeamColor.BLACK)) {
                checkMessaging = "their team in check";
                if (game.isInCheckmate(ChessGame.TeamColor.BLACK)) {
                    checkMessaging = "their team in checkmate";
                }
            }
            newGame = game.clone().getBoard();
        }
        if (!isWhite) {
            if (checkMessaging.equals("our team in check")) {
                checkMessaging = "their team in check";
            } else if (checkMessaging.equals("their team in check")) {
                checkMessaging = "our team in check";
            }
            if (checkMessaging.equals("our team in checkmate")) {
                checkMessaging = "their team in checkmate";
            } else if (checkMessaging.equals("their team in checkmate")) {
                checkMessaging = "our team in checkmate";
            }
            newGame = reverseBoard(newGame);
        }
        String messageColoring = "null";
        if (checkMessaging.equals("our team in check") || checkMessaging.equals("our team in checkmate")) {
            messageColoring = "red";
        }
        if (checkMessaging.equals("their team in check") || checkMessaging.equals("their team in checkmate")) {
            messageColoring = "green";
        }
        if (isObserving && !messageColoring.equals("null")) {
            messageColoring = "blue";
        }
        int bigNumber = 15;
        if (isObserving) {
            if (checkMessaging.equals("our team in check")) {
                checkMessaging = "White's in Check";
                bigNumber = 7;
            }
            if (checkMessaging.equals("our team in checkmate")) {
                checkMessaging = "Checkmate—Black Wins";
                bigNumber = 5;
            }
            if (checkMessaging.equals("their team in check")) {
                checkMessaging = "Black's in Check";
                bigNumber = 7;
            }
            if (checkMessaging.equals("their team in checkmate")) {
                checkMessaging = "Checkmate—White Wins";
                bigNumber = 5;
            }
        }
        if (checkMessaging.equals("our team in check") || checkMessaging.equals("their team in check")) {
            checkMessaging = "Check!";
            bigNumber = 12;
        }
        if (checkMessaging.equals("our team in checkmate") || checkMessaging.equals("their team in checkmate")) {
            checkMessaging = "Checkmate—White Wins";
            bigNumber = 5;
            if (messageColoring.equals("red") == isWhite) {
                checkMessaging = "Checkmate—Black Wins";
            }
        }
        if (checkMessaging.equals("null")) {
            checkMessaging = "";
        }
        String[] chars = new String[38];
        int i = 0;
        while (i < 30) {
            chars[i] = " ";
            i = i + 1;
        }
        i = 0;
        if (!isWhite) {
            i = 1;
        }
        chars[4 + 21 * i] = "a";
        chars[7 + 15 * i] = "b";
        chars[10 + 9 * i] = "c";
        chars[13 + 3 * i] = "d";
        chars[16 - 3 * i] = "e";
        chars[19 - 9 * i] = "f";
        chars[22 - 15 * i] = "g";
        chars[25 - 21 * i] = "h";
        chars[37 - 7 * i] = " 1 ";
        chars[36 - 5 * i] = " 2 ";
        chars[35 - 3 * i] = " 3 ";
        chars[34 - i] = " 4 ";
        chars[33 + i] = " 5 ";
        chars[32 + 3 * i] = " 6 ";
        chars[31 + 5 * i] = " 7 ";
        chars[30 + 7 * i] = " 8 ";
        i = 1;
        while (i < 30) {
            chars[0] = chars[0] + chars[i];
            i = i + 1;
        }
        i = 8;
        System.out.print(EscapeSequences.SET_BG_COLOR_YELLOW);
        System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
        System.out.print(chars[0]);
        chars[0] = " ";
        System.out.print(EscapeSequences.RESET_BG_COLOR);
        System.out.print(EscapeSequences.RESET_TEXT_COLOR);
        while (i > 0) {
            System.out.print("\n");
            System.out.print(EscapeSequences.SET_BG_COLOR_YELLOW);
            System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
            System.out.print(chars[38 - i]);
            System.out.print(EscapeSequences.RESET_BG_COLOR);
            System.out.print(EscapeSequences.RESET_TEXT_COLOR);
            for (int j = 1; j < 9; j++) {
                if (i % 2 == j % 2) {
                    System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREEN);
                } else {
                    System.out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
                }
                if (newGame.getPiece(new ChessPosition(i, j)) == null) {
                    System.out.print(EscapeSequences.SET_TEXT_COLOR_BLUE);
                } else if (newGame.getPiece(new ChessPosition(i, j)).getTeamColor() == ChessGame.TeamColor.WHITE) {
                    System.out.print(EscapeSequences.SET_TEXT_COLOR_BLUE);
                } else {
                    System.out.print(EscapeSequences.SET_TEXT_COLOR_BLACK);
                }
                if (newGame.getPiece(new ChessPosition(i, j)) == null) {
                    System.out.print("   ");
                } else if (newGame.getPiece(new ChessPosition(i, j)).getPieceType() == ChessPiece.PieceType.PAWN) {
                    System.out.print(" P ");
                } else if (newGame.getPiece(new ChessPosition(i, j)).getPieceType() == ChessPiece.PieceType.KING) {
                    System.out.print(" K ");
                } else if (newGame.getPiece(new ChessPosition(i, j)).getPieceType() == ChessPiece.PieceType.KNIGHT) {
                    System.out.print(" N ");
                } else if (newGame.getPiece(new ChessPosition(i, j)).getPieceType() == ChessPiece.PieceType.QUEEN) {
                    System.out.print(" Q ");
                } else if (newGame.getPiece(new ChessPosition(i, j)).getPieceType() == ChessPiece.PieceType.BISHOP) {
                    System.out.print(" B ");
                } else {
                    System.out.print(" R ");
                }
                System.out.print(EscapeSequences.RESET_BG_COLOR);
                System.out.print(EscapeSequences.RESET_TEXT_COLOR);
            }
            System.out.print(EscapeSequences.SET_BG_COLOR_YELLOW);
            System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
            System.out.print(chars[38 - i]);
            System.out.print(EscapeSequences.RESET_BG_COLOR);
            System.out.print(EscapeSequences.RESET_TEXT_COLOR);
            i = i - 1;
        }
        i = bigNumber;
        chars[30] = "";
        chars[31] = "";
        while (i > 0) {
            i = i - 1;
            chars[30] = chars[i] + chars[30];
            chars[31] = chars[31] + chars[29 - i];
        }
        System.out.print("\n");
        System.out.print(EscapeSequences.SET_BG_COLOR_YELLOW);
        System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
        System.out.print(chars[30]);
        System.out.print(EscapeSequences.RESET_BG_COLOR);
        System.out.print(EscapeSequences.RESET_TEXT_COLOR);
        if (messageColoring.equals("red")) {
            System.out.print(EscapeSequences.SET_BG_COLOR_RED);
        } else if (messageColoring.equals("green")) {
            System.out.print(EscapeSequences.SET_BG_COLOR_GREEN);
        }  else if (messageColoring.equals("blue")) {
            System.out.print(EscapeSequences.SET_BG_COLOR_BLUE);
        }  else {
            System.out.print(EscapeSequences.SET_BG_COLOR_RED);
        }
        System.out.print(EscapeSequences.SET_TEXT_COLOR_WHITE);
        System.out.print(checkMessaging);
        System.out.print(EscapeSequences.RESET_BG_COLOR);
        System.out.print(EscapeSequences.RESET_TEXT_COLOR);
        System.out.print(EscapeSequences.SET_BG_COLOR_YELLOW);
        System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
        System.out.print(chars[31]);
        System.out.print(EscapeSequences.RESET_BG_COLOR);
        System.out.print(EscapeSequences.RESET_TEXT_COLOR);
        System.out.print("\n");
    }
    static void drawBoard(GameData game, boolean a, boolean b) {
        if (game != null) {
            if (a || b) {
                drawBoard(game.game(), true, false);
                drawBoard(game.game(), false, false);
            }
        }
    }
    static String getInString() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
    static String getInString(String s) {
        System.out.println(s);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
    static String getInString(int i) {
        if (i == 0) {
            throw new RuntimeException("");
        }
        System.out.println("Color:");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
    static ChessBoard reverseBoard(ChessBoard cB) {
        ChessBoard nB = new ChessBoard();
        nB.resetBoard();
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                ChessPiece chessPiece = cB.getPiece(new ChessPosition(i, j));
                nB.addPiece(new ChessPosition(9 - i, 9 - j), chessPiece);
            }
        }
        return nB.clone();
    }
    
    static void printHelp() {
        System.out.println("Help         Information about actions\nLogin        Entering information");
        System.out.println("Quit         Exit this\nRegister     Registering yourself\n\nEnter command");
    }
    static String printHelp(String s) {
        String t = s + "\nHelp         Information abou";
        t = t + "t actions\nLogin        Entering infor";
        t = t + "mation\nQuit         Exit this\nRegist";
        t = t + "er     Registering yourself\n\nEnter c";
        return t + "ommand";
    }
    static String printIn(String s) {
        String t = s + "\nHelp         Information abou";
        t = t + "t actions\nLogout       Log yourself o";
        t = t + "ut\nCreate Game  Create a new game\nLi";
        t = t + "st Games   Get all games\nObserve Game";
        t = t + " Sit in on a game\nPlay Game    Play i";
        return t + "n a game\n\nEnter command";
    }
    static void printIn() {
        System.out.println("Help         Information about actions\nLogout       Log yourself out");
        System.out.println("Create Game  Create a new game\nList Games   Get all games\nObserve Game Sit in on a game");
        System.out.println("Play Game    Play in a game\n\nEnter command");
    }
    static int gameNumber(String s, Collection<GameData> gD) {
        for (GameData gMD: gD) {
            if (gMD.gameName().equals(s)) {
                return gMD.gameID();
            }
        }
        return 0;
    }
    static ChessGame.TeamColor chessGameTeamColor(String s) {
        if (s.equals("WHITE") || s.equals("White") || s.equals("white") || s.equals("W") || s.equals("w")) {
            return ChessGame.TeamColor.WHITE;
        }
        if (s.equals("BLACK") || s.equals("Black") || s.equals("black") || s.equals("B") || s.equals("b")) {
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
        printIn();
    }
}
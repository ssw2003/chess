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
    static void drawBoard(ChessGame yourGame, boolean isWhite, boolean isObserving, ChessPosition theChessPosition) {
        String boardSize = "medium";
        ChessBoard newGame = new ChessBoard();
        newGame.resetBoard();
        ChessPosition chessPosition = theChessPosition;
        String checkMessaging = "null";
        ChessGame game = new ChessGame();
        if (yourGame != null) {
            game = yourGame.clone();
        }
        if (game.isInCheck(ChessGame.TeamColor.WHITE)) {
            checkMessaging = "our team in check";
            if (game.isInCheckmate(ChessGame.TeamColor.WHITE)) { checkMessaging = "our team in checkmate"; }
        }
        if (game.isInCheck(ChessGame.TeamColor.BLACK)) {
            checkMessaging = "their team in check";
            if (game.isInCheckmate(ChessGame.TeamColor.BLACK)) { checkMessaging = "their team in checkmate"; }
        }
        newGame = game.clone().getBoard();
        if (!game.abortGame("Question").equals("Set")) {
            checkMessaging = "White Resigns—Black Wins";
            if (game.getBlackResigns().getTeamColor() == ChessGame.TeamColor.BLACK) { checkMessaging = "Black Resigns—White Wins"; }
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
            if (chessPosition != null) {
                chessPosition = new ChessPosition(9 - chessPosition.getRow(), 9 - chessPosition.getColumn());
            }
        }
        String messageColoring = "null";
        if (checkMessaging.equals("our team in check") || checkMessaging.equals("our team in checkmate")) { messageColoring = "red"; }
        if (checkMessaging.equals("their team in check") || checkMessaging.equals("their team in checkmate")) { messageColoring = "green"; }
        if (isObserving && !messageColoring.equals("null")) { messageColoring = "blue"; }
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
            if (messageColoring.equals("red") == isWhite) { checkMessaging = "Checkmate—Black Wins"; }
        }
        if (checkMessaging.equals("null")) { checkMessaging = ""; }
        if (checkMessaging.equals("White Resigns—Black Wins") || checkMessaging.equals("Black Resigns—White Wins")) {
            bigNumber = 3;
            messageColoring = "green";
            if (checkMessaging.equals("White Resigns—Black Wins") == isWhite) { messageColoring = "red"; }
            if (isObserving) { messageColoring = "blue"; }
        }
        String[] colorBackGroundsEquals = new String[90];
        String[] labels = new String[8];
        labels = new String[] { "h", "g", "f", "e", "d", "c", "b", "a" };
        if (isWhite) {
            labels = new String[] { "a", "b", "c", "d", "e", "f", "g", "h" };
        }
        int heights = 0;
        while (heights < 10) {
            colorBackGroundsEquals[heights] = "yellow";
            heights = heights + 1;
        }
        while (heights <= 89) {
            colorBackGroundsEquals[heights] = "gray";
            if ((heights - 1) % 10 > 7) {
                colorBackGroundsEquals[heights] = "yellow";
            } else if (((heights % 10) + (heights / 10)) % 2 == 1) {
                colorBackGroundsEquals[heights] = "green";
            }
            heights = heights + 1;
        }
        if (chessPosition == null) {
            Void();
        } else if (newGame.getPiece(chessPosition) == null) {
            Void();
        } else {
            Collection<ChessMove> vM = game.validMoves(theChessPosition);
            for (ChessMove move: vM) {
                ChessPosition mv = move.getEndPosition();
                if (!isWhite) { mv = new ChessPosition(9 - mv.getRow(), 9 - mv.getColumn()); }
                heights = 90 + mv.getColumn() - mv.getRow() * 10;
                colorBackGroundsEquals[heights] = "white";
                if (colorBackGroundsEquals[heights].equals("green")) { colorBackGroundsEquals[heights] = "magenta"; }
            }
        }
        String[] enhancedLabels = new String[90];
        String[] textingColors = new String[90];
        heights = 0;
        while (heights < 90) {
            textingColors[heights] = "magenta";
            heights = heights + 1;
        }
        heights = 11;
        while (heights < 90) {
            textingColors[heights] = "blue";
            if (newGame.getPiece(new ChessPosition(9 - heights / 10, heights % 10)) == null) {
                Void();
            } else if (newGame.getPiece(new ChessPosition(9 - heights / 10, heights % 10)).getTeamColor() == ChessGame.TeamColor.BLACK) {
                textingColors[heights] = "black";
            }
            heights = heights + 1;
            if ((heights - 1) % 10 > 7) {
                heights = heights + 2;
            }
        }
        heights = 0;
        enhancedLabels[0] = " ";
        enhancedLabels[9] = " ";
        while (heights < 8) {
            enhancedLabels[heights + 1] = labels[heights];
            heights = heights + 1;
            enhancedLabels[10 * heights] = "" + heights;
            if (isWhite) {
                enhancedLabels[10 * heights] = "" + (9 - heights);
            }
            enhancedLabels[10 * heights + 9] = enhancedLabels[10 * heights];
        }
        heights = 11;
        while (heights < 90) {
            textingColors[heights] = "blue";
            ChessPiece gP = newGame.getPiece(new ChessPosition(9 - heights / 10, heights % 10));
            enhancedLabels[heights] = "B";
            if (gP == null) {
                enhancedLabels[heights] = " ";
            } else if (gP.getPieceType() == ChessPiece.PieceType.PAWN) {
                enhancedLabels[heights] = "P";
            } else if (gP.getPieceType() == ChessPiece.PieceType.KING) {
                enhancedLabels[heights] = "K";
            } else if (gP.getPieceType() == ChessPiece.PieceType.QUEEN) {
                enhancedLabels[heights] = "Q";
            } else if (gP.getPieceType() == ChessPiece.PieceType.ROOK) {
                enhancedLabels[heights] = "R";
            } else if (gP.getPieceType() == ChessPiece.PieceType.KNIGHT) {
                enhancedLabels[heights] = "N";
            }
            heights = heights + 1;
            if ((heights - 1) % 10 > 7) {
                heights = heights + 2;
            }
        }
        heights = 0;
        while (heights < 90) {
            if (colorBackGroundsEquals[heights].equals("yellow")) {
                colorBackGroundsEquals[heights] = EscapeSequences.SET_BG_COLOR_YELLOW;
            } else if (colorBackGroundsEquals[heights].equals("green")) {
                colorBackGroundsEquals[heights] = EscapeSequences.SET_BG_COLOR_DARK_GREEN;
            } else if (colorBackGroundsEquals[heights].equals("gray")) {
                colorBackGroundsEquals[heights] = EscapeSequences.SET_BG_COLOR_LIGHT_GREY;
            } else if (colorBackGroundsEquals[heights].equals("magenta")) {
                colorBackGroundsEquals[heights] = EscapeSequences.SET_BG_COLOR_MAGENTA;
            } else {
                colorBackGroundsEquals[heights] = EscapeSequences.SET_BG_COLOR_WHITE;
            }
            if (textingColors[heights].equals("magenta")) {
                textingColors[heights] = EscapeSequences.SET_TEXT_COLOR_MAGENTA;
            } else if (textingColors[heights].equals("blue")) {
                textingColors[heights] = EscapeSequences.SET_TEXT_COLOR_BLUE;
            } else {
                colorBackGroundsEquals[heights] = EscapeSequences.SET_BG_COLOR_BLACK;
            }
            heights = heights + 1;
        }
        heights = 13;
        if (boardSize.equals("big")) {
            heights = 39;
        } else if (boardSize.equals("medium")) {
            heights = 37;
        }
        int j = 0;
        int k = 0;
        String ke = "";
        int i = 1;
        while (i < heights % 10) {
            ke = ke + " ";
            i = i + 2;
        }
        i = 0;
        while (i < 9) {
            j = 20;
            while (j < heights) {
                k = 10 * i;
                while (k < 10 * i + 10) {
                    System.out.print(textingColors[k]);
                    System.out.print(colorBackGroundsEquals[k]);
                    System.out.print(ke + ke + " ");
                    System.out.print(EscapeSequences.RESET_TEXT_COLOR);
                    System.out.print(EscapeSequences.RESET_BG_COLOR);
                    k = k + 1;
                }
                j = j + 20;
                System.out.print("\n");
            }
            k = 10 * i;
            while (k < 10 * i + 10) {
                System.out.print(colorBackGroundsEquals[k]);
                System.out.print(textingColors[k]);
                System.out.print(ke + enhancedLabels[k] + ke);
                System.out.print(EscapeSequences.RESET_BG_COLOR);
                System.out.print(EscapeSequences.RESET_TEXT_COLOR);
                k = k + 1;
            }
            System.out.print("\n");
            if (heights > 20) {
                k = 0;
                while (k < 10) {
                    System.out.print(textingColors[10 * i + k]);
                    System.out.print(colorBackGroundsEquals[10 * i + k]);
                    System.out.print(ke + ke + " ");
                    System.out.print(EscapeSequences.RESET_BG_COLOR);
                    System.out.print(EscapeSequences.RESET_TEXT_COLOR);
                    k = k + 1;
                }
            }
            i = i + 1;
            System.out.print("\n");
        }
        bigNumber = ((heights - 3) % 10) * 5 + bigNumber;
        labels = new String[270];
        i = 0;
        while (i < 270) {
            labels[i] = " ";
            i = i + 1;
        }
        i = 0;
        while (i < 10) {
            labels[(heights % 10) * i + ((heights % 10) / 2) + (heights / 20) * 90] = enhancedLabels[i];
            i = i + 1;
        }
        j = (heights / 10) - 1;
        k = (heights % 10) * 10;
        i = 0;
        while (i < j) {
            ke = "";
            j = k + 90 * i;
            while (j > 90 * i) {
                j = j - 1;
                ke = labels[j] + ke;
            }
            i = i + 1;
            j = (heights / 10) - 1;
            System.out.print(EscapeSequences.SET_BG_COLOR_YELLOW);
            System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
            System.out.print(ke);
            System.out.print(EscapeSequences.RESET_BG_COLOR);
            System.out.print(EscapeSequences.RESET_TEXT_COLOR);
            System.out.print("\n");
        }
        i = j * 90;
        k = k + i - bigNumber;
        j = j * 90 + bigNumber;
        ke = "";
        while (i < j) {
            ke = ke + labels[i];
            i = i + 1;
        }
        j = k + bigNumber;
        System.out.print(EscapeSequences.SET_BG_COLOR_YELLOW);
        System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
        System.out.print(ke);
        if (i == k) {
            Void();
        } else {
            System.out.print(EscapeSequences.RESET_BG_COLOR);
            System.out.print(EscapeSequences.RESET_TEXT_COLOR);
            if (messageColoring.equals("red")) {
                System.out.print(EscapeSequences.SET_BG_COLOR_RED);
            } else if (messageColoring.equals("green")) {
                System.out.print(EscapeSequences.SET_BG_COLOR_GREEN);
            } else if (messageColoring.equals("blue")) {
                System.out.print(EscapeSequences.SET_BG_COLOR_BLUE);
            }
            if (!messageColoring.equals("null")) {
                System.out.print(EscapeSequences.SET_TEXT_COLOR_WHITE);
            }
            System.out.print(checkMessaging);
            System.out.print(EscapeSequences.RESET_BG_COLOR);
            System.out.print(EscapeSequences.RESET_TEXT_COLOR);
            System.out.print(EscapeSequences.SET_BG_COLOR_YELLOW);
            System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
        }
        ke = "";
        while (k < j) {
            ke = ke + labels[k];
            k = k + 1;
        }
        System.out.print(ke);
        System.out.print(EscapeSequences.RESET_BG_COLOR);
        System.out.print(EscapeSequences.RESET_TEXT_COLOR);
        System.out.print("\n");
    }
    static void drawBoard(GameData game, boolean a, boolean b) {
        if (game != null) {
            if (a || b) {
                drawBoard(game.game(), true, false, null);
                drawBoard(game.game(), false, false, null);
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
    static void Void() {
        boolean v = false;
    }
}
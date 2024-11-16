import chess.*;
import facade.ServerFacade;
import model.*;
import ui.EscapeSequences;

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
        System.out.println("Help         Information about actions");
        System.out.println("Login        Entering information");
        System.out.println("Quit         Exit this");
        System.out.println("Register     Registering yourself");
        while (!status.equals("Quit")) {
            lastCommand = getInString();
            if (status.equals("Not Logged In") && lastCommand.equals("Register")) {
                String username = getInString("Username:");
                String password = getInString("Password:");
                String email = getInString("Email:");
                aD = sF.addUser(new UserData(username, password, email));
                status = "Merely Logged In";
                lastCommand = "Help";
            } else if (status.equals("Not Logged In") && lastCommand.equals("Login")) {
                String username = getInString("Username:");
                String password = getInString("Password:");
                aD = sF.loginUser(new LoginData(username, password));
                status = "Merely Logged In";
                lastCommand = "Help";
            } else if (status.equals("Not Logged In") && lastCommand.equals("Quit")) {
                break;
            } else if (status.equals("Not Logged In")) {
                status = stringReturn(lastCommand);
                lastCommand = "Help";
            } else if (status.equals("Merely Logged In") && lastCommand.equals("Logout")) {
                status = "Not Logged In";
                lastCommand = "Help";
                sF.logoutUser(aD.authToken());;
            } else if (status.equals("Merely Logged In") && lastCommand.equals("List Games")) {
                lastCommand = "Help";
                Collection<GameData> cGM = sF.listGames(aD.authToken());
                for (GameData gMD: cGM) {
                    System.out.println(gMD.gameID() + gMD.gameName() + gMD.whiteUsername() + gMD.blackUsername());
                }
            } else if (status.equals("Merely Logged In") && lastCommand.equals("Create Game")) {
                String gameName = "";
                gameName = getInString("Game Name:");
                sF.createGame(new GameName(gameName), aD.authToken());
                lastCommand = "Help";
            } else if (status.equals("Merely Logged In") && lastCommand.equals("Observe Game")) {
                int gameNumber = getInInt(getInString("Game Number:"));
                whichGameIn = gameNumber;
                inGameAsWhite = true;
                inGameAsBlack = true;
                lastCommand = "Help";
            } else if (status.equals("Merely Logged In") && lastCommand.equals("Play Game")) {
                int gameNumber = getInInt(getInString("Game Number:"));
                String color = getInString("Color:");
                if (Objects.equals(color, "WHITE") || Objects.equals(color, "White") || Objects.equals(color, "white")
                        || Objects.equals(color, "W") || Objects.equals(color, "w")) {
                    sF.joinGame(new PlayerColorGameNumber(ChessGame.TeamColor.WHITE, gameNumber), aD.authToken());
                    whichGameIn = gameNumber;
                    inGameAsWhite = true;
                    inGameAsBlack = false;
                }
                else if (Objects.equals(color, "BLACK") || Objects.equals(color, "Black") || Objects.equals(color, "black")
                        || Objects.equals(color, "B") || Objects.equals(color, "b")) {
                    sF.joinGame(new PlayerColorGameNumber(ChessGame.TeamColor.BLACK, gameNumber), aD.authToken());
                    whichGameIn = gameNumber;
                    inGameAsBlack = true;
                    inGameAsWhite = false;
                }
                else {
                    System.out.println("Try to join again");
                }
                lastCommand = "Help";
            } else {
                status = stringThing(lastCommand);
            }
            if (whichGameIn != 0) {
                GameData gD = gameMetaData(sF.listGames(aD.authToken()), whichGameIn);
                if (gD == null) {
                    inGameAsWhite = false;
                    inGameAsBlack = false;
                }
                if (inGameAsWhite) {
                    drawBoard(gD.game(), true);
                }
                if (inGameAsBlack) {
                    drawBoard(gD.game(), false);
                }
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
        return "Merely Logged In";
    }
    static void drawBoard(ChessGame game, boolean isWhite) {
        ChessBoard newGame = new ChessBoard();
        newGame.resetBoard();
        //newGame.r
        if (game != null) {
            newGame = game.clone().getBoard();
        }
        if (!isWhite) {
            newGame = reverseBoard(newGame);
        }
        if (isWhite) {
            System.out.print(EscapeSequences.RESET_BG_COLOR);
            System.out.print(EscapeSequences.RESET_TEXT_COLOR);
            System.out.printf(" abcdefgh ");
            System.out.print(EscapeSequences.RESET_BG_COLOR);
            System.out.print(EscapeSequences.RESET_TEXT_COLOR);
            System.out.printf("\n");
            for (int i = 0; i < 8; i++) {
                String myString = "";
                System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
                System.out.print(EscapeSequences.SET_BG_COLOR_YELLOW);
                System.out.printf("" + (8 - i));
                printRow(newGame, 8 - i);
                System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
                System.out.print(EscapeSequences.SET_BG_COLOR_YELLOW);
                System.out.printf("" + (8 - i));
                System.out.print(EscapeSequences.RESET_BG_COLOR);
                System.out.print(EscapeSequences.RESET_TEXT_COLOR);
                System.out.printf("\n");
            }
            System.out.print(EscapeSequences.RESET_BG_COLOR);
            System.out.print(EscapeSequences.RESET_TEXT_COLOR);
            System.out.printf(" abcdefgh ");
            System.out.print(EscapeSequences.RESET_BG_COLOR);
            System.out.print(EscapeSequences.RESET_TEXT_COLOR);
            System.out.printf("\n");
        }
        else {
            System.out.print(EscapeSequences.RESET_BG_COLOR);
            System.out.print(EscapeSequences.RESET_TEXT_COLOR);
            System.out.printf(" hgfedcba ");
            System.out.print(EscapeSequences.RESET_BG_COLOR);
            System.out.print(EscapeSequences.RESET_TEXT_COLOR);
            System.out.printf("\n");
            for (int i = 0; i < 8; i++) {
                String myString = "";
                System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
                System.out.print(EscapeSequences.SET_BG_COLOR_YELLOW);
                System.out.printf("" + (1 + i));
                printRow(newGame, 1 + i);
                System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
                System.out.print(EscapeSequences.SET_BG_COLOR_YELLOW);
                System.out.printf("" + (1 + i));
                System.out.print(EscapeSequences.RESET_BG_COLOR);
                System.out.print(EscapeSequences.RESET_TEXT_COLOR);
                System.out.printf("\n");
            }
            System.out.print(EscapeSequences.RESET_BG_COLOR);
            System.out.print(EscapeSequences.RESET_TEXT_COLOR);
            System.out.printf(" hgfedcba ");
            System.out.print(EscapeSequences.RESET_BG_COLOR);
            System.out.print(EscapeSequences.RESET_TEXT_COLOR);
            System.out.printf("\n");
            //System.p
        }
        System.out.print(EscapeSequences.RESET_BG_COLOR);
        System.out.print(EscapeSequences.RESET_TEXT_COLOR);
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
    static int getInInt(String sc) {
        for (int i = 0; i < sc.length(); i++) {
            if (sc.charAt(i) < '0' || sc.charAt(i) > '9') {
                return 0;
            }
        }
        if (sc.isEmpty()) {
            return 0;
        }
        if (sc.charAt(0) == '0') {
            return 0;
        }
        int j = 0;
        for (int i = 0; i < sc.length(); i++) {
            j = j * 10 + (sc.charAt(i) - '0');
        }
        return j;
    }
    static void printRow(ChessBoard cB, int row) {
        boolean isWhiteSquare = (row % 2 == 0);
        for (int i = 1; i < 9; i = i + 1 ) {
            if (isWhiteSquare) {
                System.out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
            }
            else {
                System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREEN);
            }
            ChessPiece cP = cB.getPiece(new ChessPosition(row, i));
            if (cP == null) {
                System.out.printf(" ");
            } else if (cP.getTeamColor() == ChessGame.TeamColor.WHITE) {
                System.out.print(EscapeSequences.SET_TEXT_COLOR_GREEN);
            } else {
                System.out.print(EscapeSequences.SET_TEXT_COLOR_BLUE);
            }
            if (cP != null) {
                if (cP.getPieceType() == ChessPiece.PieceType.KING) {
                    System.out.printf("K");
                } else if (cP.getPieceType() == ChessPiece.PieceType.KNIGHT) {
                    System.out.printf("N");
                } else if (cP.getPieceType() == ChessPiece.PieceType.BISHOP) {
                    System.out.printf("B");
                } else if (cP.getPieceType() == ChessPiece.PieceType.ROOK) {
                    System.out.printf("R");
                } else if (cP.getPieceType() == ChessPiece.PieceType.QUEEN) {
                    System.out.printf("Q");
                } else {
                    System.out.printf("P");
                }
            }
        }
    }
}
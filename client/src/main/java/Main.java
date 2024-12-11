import chess.*;
import com.google.gson.Gson;
import facade.DrawingBoardClass;
import facade.ServerFacade;
import model.*;
import websocket.commands.MoveMaker;
import websocket.commands.UserGameCommand;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        boolean inGameAsWhite = false;
        boolean inGameAsBlack = false;
        int wGI = 0;
        AuthData aD = new AuthData("", "");
        System.out.println("♕ 240 Chess Client: " + piece);
        ServerFacade sF = new ServerFacade("http://localhost:8080");
        String status = "Not Logged In";
        String lastCommand = "HELP";
        DrawingBoardClass dBC = new DrawingBoardClass();
        System.out.println("\n");
        DrawingBoardClass.Person color = DrawingBoardClass.Person.OBSERVER;
        MisterClient misterClient = null;
        ChessGame notificationGame = new ChessGame();
        try {
            misterClient = new MisterClient();
        } catch (Exception e) { Void(); }
        while (!status.equals("Quit")) {
            System.out.println(statusVar(status));
            lastCommand = capitalizeString(getTheirInputs(), true);
            RequestParse rP = new RequestParse(status, lastCommand);
            //7,8,9
            if (rP.getInteger() == 0) {
                Void();
            } else if (rP.getInteger() == 1) {
                status = "Quit";
            } else if (rP.getInteger() == 2) {
                try {
                    sF.logoutUser(aD.authToken());;
                } catch (Exception e) { System.out.println("Invalid or Misspelled Auth Token"); }
                status = "Not Logged In";
            } else if (rP.getInteger() == 3) {
                sF.createGame(new GameName(getTheirInputs("Game Name:")), aD.authToken());
            } else if (rP.getInteger() == 4) {
                System.out.println("Games:");
                Collection<GameData> gD = sF.listGames(aD.authToken());
                if (gD.isEmpty()) { System.out.println("  NULL"); }
                printOuts(gD);
            } else if (rP.getInteger() == 10) {
                String usN = getTheirInputs("Username:");
                String pas = getTheirInputs("Password:");
                String eMl = getTheirInputs("Email:");
                try {
                    aD = sF.addUser(new UserData(usN, pas, eMl));
                    status = "Merely Logged In";
                } catch (Exception e) { System.out.println("Invalid or Taken User Information"); }
            } else if (rP.getInteger() == 11) {
                String usN = getTheirInputs("Username:");
                String pas = getTheirInputs("Password:");
                try {
                    aD = sF.loginUser(new LoginData(usN, pas));
                    status = "Merely Logged In";
                } catch (Exception e) { System.out.println("Invalid or Misspelled User Information\n"); }
            } else if (rP.getInteger() == 14) {
                System.out.println("Bad or Misspelled Command\n");
            } else if (rP.getInteger() == 5 || rP.getInteger() == 6) {
                dBC.drawBoard(gameNumber(wGI, sF.listGames(aD.authToken())).game(), color, rP.getPosition(), DrawingBoardClass.Styling.MEDIUM);
            } else if (rP.getInteger() == 7) {
                ChessGame chessGame = gameNumber(wGI, sF.listGames(aD.authToken())).game();
                dBC.drawBoard(chessGame, color, null, DrawingBoardClass.Styling.MEDIUM);
                //boolean moved = true;
                //if (rP.getMove() == null) {
                //    System.out.println("That isn't a valid way to write a move\n");
                //    moved = false;
                //}
                var thingSerializer = new Gson();
                var thingToSerialize = new MoveMaker(UserGameCommand.CommandType.MAKE_MOVE, aD.authToken(), wGI, rP.getMove());
                var thingJson = thingSerializer.toJson(thingToSerialize);
                try {
                    misterClient.sn(thingJson);
                    //moved = false;
                } catch (Exception e) { System.out.println("Error"); }
                //if (moved) { System.out.println("Illegal move\n"); }
                chessGame = gameNumber(wGI, sF.listGames(aD.authToken())).game();
                dBC.drawBoard(chessGame, color, null, DrawingBoardClass.Styling.MEDIUM);
            } else if (rP.getInteger() == 8) {
                int resigns = 1;
                ChessGame chessGame = gameNumber(wGI, sF.listGames(aD.authToken())).game();
                dBC.drawBoard(chessGame, color, rP.getPosition(), DrawingBoardClass.Styling.MEDIUM);
                if (!chessGame.abortGame("Question").equals("Set")) {
                    System.out.println("Somebody has resigned already, so you can't\n");
                    resigns = 0;
                } else if (color == DrawingBoardClass.Person.OBSERVER) {
                    System.out.println("You are an observer. You can't move or resign\n");
                    resigns = 0;
                } else if (chessGame.isInCheckmate(chessGame.getTeamTurn()) || chessGame.isInStalemate(chessGame.getTeamTurn())) {
                    System.out.println("The game has finished, so you can't resign\n");
                    resigns = 0;
                }
                var thingSerializer = new Gson();
                var thingToSerialize = new UserGameCommand(UserGameCommand.CommandType.RESIGN, aD.authToken(), wGI);
                var thingJson = thingSerializer.toJson(thingToSerialize);
                boolean b = (resigns == 0);
                try {
                    misterClient.sn(thingJson);
                    resigns = nonzeroValue(resigns);
                } catch (Exception e) {
                    print(b);
                }
            } else if (rP.getInteger() == 9) {
                wGI = 0;
                status = "Merely Logged In";
                color = DrawingBoardClass.Person.OBSERVER;
                var thingSerializer = new Gson();
                var thingToSerialize = new UserGameCommand(UserGameCommand.CommandType.LEAVE, aD.authToken(), wGI);
                var thingJson = thingSerializer.toJson(thingToSerialize);
                try {
                    misterClient.sn(thingJson);
                } catch (Exception e) { Void(); }
            } else if (rP.getInteger() == 13 || rP.getInteger() == 12) {
                Collection<GameData> gD = sF.listGames(aD.authToken());
                int i = getTheirIntegerInputs(gD, rP.getInteger() == 12);
                try {
                    status = "Game UI";
                    wGI = gameAt(gD, nonzeroValue(i / 3)).gameID();
                    sF.joinGame(new PlayerColorGameNumber(pullColor(i % 3), wGI), aD.authToken(), rP.getInteger() == 12);
                    dBC.drawBoard(gameNumber(wGI, sF.listGames(aD.authToken())).game(), color, rP.getPosition(), DrawingBoardClass.Styling.MEDIUM);
                } catch (InvalidMoveException e) {
                    System.out.println("Try to join again\n");
                    status = "Merely Logged In";
                    i = 2;
                    wGI = 0;
                }
                color = DrawingBoardClass.Person.WHITE;
                if (i % 3 == 1) {
                    color = DrawingBoardClass.Person.BLACK;
                } else if (i % 3 == 2) { color = DrawingBoardClass.Person.OBSERVER; }
                try {
                    var thingSerializer = new Gson();
                    var thingToSerialize = new UserGameCommand(UserGameCommand.CommandType.CONNECT, aD.authToken(), wGI);
                    var thingJson = thingSerializer.toJson(thingToSerialize);
                    i = wGI = nonzeroValue(wGI);
                    misterClient.sn(thingJson);
                } catch (Exception e) { Void(); }
            }
        }
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
    static String getTheirInputs(String h) {
        System.out.println(h);
        return getTheirInputs();
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
    static GameData gameNumber(int i, Collection<GameData> gD) {
        for (GameData gMD: gD) {
            if (gMD.gameID() == i) {
                return gMD;
            }
        }
        return new GameData(1, "", "", "", new ChessGame());
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
    static void Void() {
        boolean v;
    }
    static boolean compareString(String s, String t) {
        int u = s.hashCode();
        int v = t.hashCode();
        if (u != v) {
            return (u > v);
        }
        u = s.length();
        v = t.length();
        int i = 0;
        while (i < u && i < v) {
            if (s.charAt(i) > t.charAt(i)) {
                return true;
            } else if (t.charAt(i) > s.charAt(i)) {
                return false;
            }
            i = i + 1;
        }
        if (u != v) {
            return (u > v);
        }
        return true;
    }
    static String compareStrings(String s, String t) {
        boolean cS = compareString(s, t);
        if (!cS) {
            return ">";
        }
        if (s.equals(t)) {
            return "=";
        }
        return "<";
    }
    static GameData gameAt(Collection<GameData> cGM, int i) throws InvalidMoveException {
        int j = 0;
        for (GameData gMD: cGM) {
            j = i;
            for (GameData gMDS: cGM) {
                String k = compareStrings(gMDS.gameName(), gMD.gameName());
                if (k.equals("<")) {
                    j = j - 1;
                } else if (k.equals("=") && gMDS.gameID() <= gMD.gameID()) {
                    j = j - 1;
                }
            }
            if (j == 0) {
                return gMD;
            }
            //String outPrint = gMD.gameName() + ", white = ";
            //System.out.println(outPrint + gMD.whiteUsername() + ", black = " + gMD.blackUsername());
        }
        throw new InvalidMoveException();
    }
    static void printOuts(Collection<GameData> cGD) {
        int varValue = 0;
        while (varValue < cGD.size()) {
            varValue++;
            GameData gA = new GameData(1, "null", "null", "null", new ChessGame());
            try {
                gA = gameAt(cGD, varValue);
            } catch (InvalidMoveException e) {
                Void();
            }
            String s = "  " + varValue + ". " + gA.gameName();
            s = s + ", white = [" + gA.whiteUsername() + "], black = [" + gA.blackUsername() + "]";
            System.out.println(s);
        }
    }
    static int getTheirIntegerInputs(Collection<GameData> gD, boolean wantsToPlay) {
        boolean wTP = false;
        System.out.println("Games:");
        if (gD.isEmpty()) {
            System.out.println("  Empty");
        }
        printOuts(gD);
        int wGI = 0;
        try {
            System.out.println("What is the game's number?");
            String theirInput = getTheirInputs();
            int i = 0;
            while (i < gD.size()) {
                i++;
                if ((i + "").equals(theirInput)) {
                    wGI = i;
                }
            }
            wGI = nonzeroValue(wGI);
            wTP = true;
            if (!wantsToPlay) {
                i = nonzeroValue(0);
            }
            System.out.println("Enter your Color");
            ChessGame.TeamColor tC = chessGameTeamColor(getTheirInputs());
            if (tC == ChessGame.TeamColor.WHITE) {
                return wGI * 3;
            }
            return wGI * 3 + 1;
        } catch (Exception e) {
            if (wTP && !wantsToPlay) {
                return wGI * 3 + 2;
            }
        }
        return 2;
    }
    static ChessGame.TeamColor pullColor(int i) {
        if (i == 0) {
            return ChessGame.TeamColor.WHITE;
        }
        return ChessGame.TeamColor.BLACK;
    }
    static void print(boolean b) {
        if (!b) {
            System.out.println("Illegal resign\n");
        }
    }
}
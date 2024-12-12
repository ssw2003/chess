import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.InvalidMoveException;
import com.google.gson.Gson;
import facade.DrawingBoardClass;
import facade.ServerFacade;
import model.*;
import websocket.commands.MoveMaker;
import websocket.commands.UserGameCommand;
import websocket.messages.GameOfChessThing;
import websocket.messages.NotificationFactor;
import websocket.messages.ServerMessage;

import java.util.Collection;
import java.util.Scanner;

public class Client implements NotificationHandler {
    boolean inGameAsWhite;
    boolean inGameAsBlack;
    int wGI;
    AuthData aD;
    ServerFacade sF;
    String status;
    String lastCommand;
    DrawingBoardClass dBC;
    DrawingBoardClass.Person color;
    MisterClient misterClient;
    ChessGame notificationGame;
    public void run() {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        inGameAsWhite = false;
        inGameAsBlack = false;
        wGI = 0;
        aD = new AuthData("", "");
        System.out.println("♕ 240 Chess Client: " + piece);
        sF = new ServerFacade("http://localhost:8080");
        status = "Not Logged In";
        lastCommand = "HELP";
        dBC = new DrawingBoardClass();
        System.out.println("\n");
        color = DrawingBoardClass.Person.OBSERVER;
        misterClient = null;
        notificationGame = new ChessGame();
        try {
            misterClient = new MisterClient(this);
        } catch (Exception e) {}
        while (!status.equals("Quit")) {
            System.out.println(statusVar(status));
            lastCommand = capitalizeString(getTheirInputs(), true);
            RequestParse rP = new RequestParse(status, lastCommand);
            if (rP.getInteger() == 0) {
                voidThing();
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
            } else if (rP.getInteger() == 6 || rP.getInteger() == 5) {
                drawBoard(dBC, gameNumber(wGI, sF.listGames(aD.authToken())).game(), color, rP.getPosition());
            } else if (rP.getInteger() == 7) {
                if (color == DrawingBoardClass.Person.OBSERVER) {
                    System.out.println("You are an Observer");
                } else if (!notificationGame.abortGame("Question").equals("Set")) {
                    System.out.println("Illegal Move from Resignation");
                } else if (notificationGame.getTeamTurn() != convert(color)) {
                    System.out.println("Illegal Move from it Not Being your Turn");
                } else {
                    var thingSerializer = new Gson();
                    var thingToSerialize = new MoveMaker(UserGameCommand.CommandType.MAKE_MOVE, aD.authToken(), wGI, rP.getMove());
                    var thingJson = thingSerializer.toJson(thingToSerialize);
                    try {
                        misterClient.sn(thingJson);
                    } catch (Exception e) {
                        System.out.println("Illegal Move");
                    }
                }
            } else if (rP.getInteger() == 8) {
                if (color == DrawingBoardClass.Person.OBSERVER) {
                    System.out.println("You are an Observer");
                } else if (!notificationGame.abortGame("Question").equals("Set")) {
                    System.out.println("Illegal Resign");
                } else {
                    var thingSerializer = new Gson();
                    var thingToSerialize = new UserGameCommand(UserGameCommand.CommandType.RESIGN, aD.authToken(), wGI);
                    var thingJson = thingSerializer.toJson(thingToSerialize);
                    try {
                        misterClient.sn(thingJson);
                    } catch (Exception e) {
                        System.out.println("Illegal Resign");
                    }
                }
            } else if (rP.getInteger() == 9) {
                var thingSerializer = new Gson();
                var thingToSerialize = new UserGameCommand(UserGameCommand.CommandType.LEAVE, aD.authToken(), wGI);
                var thingJson = thingSerializer.toJson(thingToSerialize);
                status = "Merely Logged In";
                try {
                    misterClient.sn(thingJson);
                    notificationGame = new ChessGame();
                    wGI = 0;
                    color = DrawingBoardClass.Person.OBSERVER;
                } catch (Exception e) {}
            } else if (rP.getInteger() == 13) {
                System.out.println("What is the game's number?");
                wGI = getTheirIntegerInputs(sF.listGames(aD.authToken()), getTheirInputs());
                if (wGI == 0) {
                    System.out.println("That is not the number of any game\nTry to join again\n");
                } else {
                    var thingSerializer = new Gson();
                    var thingToSerialize = new UserGameCommand(UserGameCommand.CommandType.CONNECT, aD.authToken(), wGI);
                    var thingJson = thingSerializer.toJson(thingToSerialize);
                    status = "Game UI";
                    try {
                        misterClient.sn(thingJson);
                    } catch (Exception e) { voidThing(); }
                }
            } else if (rP.getInteger() == 12) {
                System.out.println("What is the game's number?");
                wGI = getTheirIntegerInputs(sF.listGames(aD.authToken()), getTheirInputs());
                if (wGI == 0) {
                    System.out.println("That is not the number of any game\nTry to join again\n");
                } else {
                    System.out.println("Enter your Color");
                    try {
                        color = convert(chessGameTeamColor(getTheirInputs()));
                        String s = gameNumber(wGI, sF.listGames(aD.authToken())).whiteUsername();
                        if (color == DrawingBoardClass.Person.BLACK) {
                            s = gameNumber(wGI, sF.listGames(aD.authToken())).blackUsername();
                        }
                        if (s != null) {
                            System.out.println("Taken\nTry to join again\n");
                            wGI = 0;
                            color = DrawingBoardClass.Person.OBSERVER;
                            throw new RuntimeException();
                        }
                        sF.joinGame(new PlayerColorGameNumber(convert(color), wGI), aD.authToken());
                    } catch (Exception e) {
                        color = DrawingBoardClass.Person.OBSERVER;
                        if (wGI != 0) {
                            System.out.println("Bad or Misspelled Color\nTry to join again\n");
                        }
                        wGI = 0;
                    }
                }
                if (wGI != 0) {
                    var thingSerializer = new Gson();
                    var thingToSerialize = new UserGameCommand(UserGameCommand.CommandType.CONNECT, aD.authToken(), wGI);
                    var thingJson = thingSerializer.toJson(thingToSerialize);
                    status = "Game UI";
                    try {
                        misterClient.sn(thingJson);
                    } catch (Exception e) { voidThing(); }
                }
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
    static void voidThing() {
        boolean v;
    }
    static void drawBoard(DrawingBoardClass dbc, ChessGame cg, DrawingBoardClass.Person p, ChessPosition cp) {
        dbc.drawBoard(cg, p, cp, DrawingBoardClass.Styling.MEDIUM);
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
                voidThing();
            }
            String s = "  " + varValue + ". " + gA.gameName();
            s = s + ", white = [" + gA.whiteUsername() + "], black = [" + gA.blackUsername() + "]";
            System.out.println(s);
        }
    }
    static int getTheirIntegerInputs(Collection<GameData> gD, String theirInput) {
        int i = 0;
        int wGI = 0;
        while (i < gD.size()) {
            i++;
            if ((i + "").equals(theirInput)) {
                wGI = i;
            }
        }
        try {
            wGI = gameAt(gD, wGI).gameID();
        } catch (InvalidMoveException e) {
            wGI = 0;
        }
        return wGI;
    }
    static ChessGame.TeamColor convert(DrawingBoardClass.Person p) {
        if (p == null) {
            return null;
        } else if (p == DrawingBoardClass.Person.WHITE) {
            return ChessGame.TeamColor.WHITE;
        } else if (p == DrawingBoardClass.Person.BLACK) {
            return ChessGame.TeamColor.BLACK;
        }
        return null;
    }
    static DrawingBoardClass.Person convert(ChessGame.TeamColor p) {
        if (p == null) {
            return DrawingBoardClass.Person.OBSERVER;
        } else if (p == ChessGame.TeamColor.WHITE) {
            return DrawingBoardClass.Person.WHITE;
        }
        return DrawingBoardClass.Person.BLACK;
    }

    @Override
    public void notify(String s) {
        var registerRequest = new Gson().fromJson(s, ServerMessage.class);
        if (registerRequest.getServerMessageType() == ServerMessage.ServerMessageType.NOTIFICATION) {
            var rR = new Gson().fromJson(s, NotificationFactor.class);
            String lt = rR.getMessage().substring(0, 1);
            if (lt.equals("J")) {
                System.out.println(rR.getMessage().substring(2));
            } else if (lt.equals("L")) {
                System.out.println(rR.getMessage().substring(2));
            } else if (lt.equals("M")) {
                System.out.println(rR.getMessage().substring(2));
            } else if (lt.equals("R")) {
                System.out.println(rR.getMessage().substring(2));
                notificationGame = gameNumber(wGI, sF.listGames(aD.authToken())).game();
                drawBoard(dBC, notificationGame, color, null);
            }
        } else if (registerRequest.getServerMessageType() == ServerMessage.ServerMessageType.LOAD_GAME) {
            var rR = new Gson().fromJson(s, GameOfChessThing.class);
            notificationGame = rR.getGame();
            drawBoard(dBC, notificationGame, color, null);
        } else {
            System.out.println("Illegal move");
        }
    }
}

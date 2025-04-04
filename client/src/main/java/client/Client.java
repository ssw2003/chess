package client;

import chess.ChessGame;
import chess.ChessPosition;
import chess.InvalidMoveException;
import model.GameData;
import websocket.commands.UserGameCommand;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class Client {
    private ServerFacade sF;
    private Scanner getThing;
    private String authToken;
    private BoardDrawingClass bDC;
    private int wGI;
    private BoardDrawingClass.Role role;
    private MisterClient mC;
    public int run(int desiredPort) {
        sF = new ServerFacade("http://localhost:" + desiredPort);
        bDC = new BoardDrawingClass();
        wGI = 0;
        role = BoardDrawingClass.Role.WHITE;
        mC = null;
        runLoop();
        return desiredPort;
    }

    private void runLoop() {
        getThing = new Scanner(System.in);
        authToken = null;
        String height = "not logged in";
        int gameNumber = 0;
        while (!height.equals("quit")) {
            printPrompt(height);
            String theirInput = capitalizeLetters(getThing.nextLine());
            if (height.equals("not logged in")) {
                height = evaluateNotLoggedIn(theirInput);
            }
            else if (height.equals("logged in")) {
                height = evaluateLoggedIn(theirInput);
            }
            else {
                height = evaluateGame(theirInput);
            }
        }
    }

    private void printPrompt(String height) {
        if (height.equals("not logged in")) {
            System.out.println("Login\nRegister\nQuit\nHelp");
        }
        else {
            System.out.println("Play Game\nHelp\nLogout\nCreate Game\nList Games\nObserve Game");
        }
    }

    private String capitalizeLetters(String s) {
        String t = "";
        String u = "qwertyuiopasdfghjklzxcvbnm";
        String v = "QWERTYUIOPASDFGHJKLZXCVBNM";
        String w = "";
        for (int i = 0; i < s.length(); i++) {
            w = s.substring(i, i + 1);
            for (int j = 0; j < 26; j++) {
                if (u.substring(j, j + 1).equals(w)) {
                    w = v.substring(j, j + 1);
                    break;
                }
            }
            t = t + w;
        }
        return t;
    }



    private String evaluateLoggedIn(String iC) {
        if ((!iC.equals("PLAY GAME")) && (!iC.equals("CREATE GAME")) && (!iC.equals("OBSERVE GAME"))) {
            if ((!iC.equals("LIST GAMES")) && (!iC.equals("LOGOUT"))) {
                if (!iC.equals("HELP")) {
                    System.out.println("Bad command");
                }
                return "logged in";
            }
        }
        if (iC.equals("LOGOUT") || iC.equals("LIST GAMES")) {
            return evaluateOut(iC);
        }
        Collection<GameData> gD = null;
        if (iC.equals("PLAY GAME")) {
            System.out.println("Game to Play:");
            String gameNameCreate = getThing.nextLine();
            gD = sF.gameListRequest(authToken);
            if (gD == null) {
                gD = new ArrayList<>();
            }
            int joined = getInt(gameNameCreate, gD);
            System.out.println("Player Color:");
            gameNameCreate = getThing.nextLine();
            ChessGame.TeamColor tC = ChessGame.TeamColor.WHITE;
            if (capitalizeLetters(gameNameCreate).equals("BLACK")) {
                tC = ChessGame.TeamColor.BLACK;
            }
            role = BoardDrawingClass.Role.BLACK;
            if (tC == ChessGame.TeamColor.WHITE) {
                role = BoardDrawingClass.Role.WHITE;
            }
            wGI = joined;
            if (!sF.joinGame(authToken, joined, gameNameCreate)) {
                wGI = 0;
                role = BoardDrawingClass.Role.WHITE;
            }
            mC = new MisterClient(role);
            return "";
        }
        if (iC.equals("CREATE GAME")) {
            System.out.println("Game Name:");
            String gameNameCreate = getThing.nextLine();
            if (sF.createGame(authToken, gameNameCreate)) {
                System.out.println("Thank You");
            }
            else {
                System.out.println("Bad Auth Token");
            }
            return "logged in";
        }
        System.out.println("Game to Observe:");
        String gNC = getThing.nextLine();
        gD = sF.gameListRequest(authToken);
        if (gD == null) {
            gD = new ArrayList<>();
        }
        int joined = getInt(gNC, gD);
        role = BoardDrawingClass.Role.OBSERVER;
        wGI = joined;
        if (joined == 0) {
            role = BoardDrawingClass.Role.WHITE;
            wGI = 0;
        }
        mC = new MisterClient(role);
        return "";
    }
    private String evaluateOut(String iC) {
        if (iC.equals("LOGOUT")) {
            if (sF.logoutRequest(authToken)) {
                return "not logged in";
            }
            System.out.println("Bad Auth Token");
            return "logged in";
        }
        Collection<GameData> gamesListing = new ArrayList<>();
        boolean itWorked = false;
        try {
            gamesListing = sF.gameListRequest(authToken);
        } catch (Exception e) {
            System.out.println("Invalid Auth Token");
        }
        System.out.println("Games List:");
        if (gamesListing.isEmpty()) {
            System.out.println("   There are 0 games.");
        }
        int i = 1;
        ChessGame chGam = new ChessGame();
        try {
            chGam.attemptResign(ChessGame.TeamColor.WHITE);
            chGam.attemptResign(ChessGame.TeamColor.WHITE);
        } catch (InvalidMoveException e) {
            chGam = new ChessGame();
        }
        while (getInt(i + "", gamesListing) != 0) {
            int j = getInt(i + "", gamesListing);
            GameData gD = retrieveGame(j, gamesListing);
            if (gD == null) {
                gD = new GameData(j, null, null, "", chGam.clone());
            }
            String k = "   " + i + "-" + gD.gameName() + "; White: ";
            if (gD.whiteUsername() == null) {
                k = k + "[Nobody]";
            }
            else {
                k = k + gD.whiteUsername();
            }
            if (gD.blackUsername() == null) {
                k = k + "; Black: [Nobody]";
            }
            else {
                k = k + "; Black: " + gD.blackUsername();
            }
            i++;
            System.out.println(k);
        }
        return "logged in";
    }

    private String evaluateNotLoggedIn(String theirInput) {
        if (theirInput.equals("QUIT")) {
            return "quit";
        }
        if (!theirInput.equals("LOGIN") && !theirInput.equals("REGISTER")) {
            if (!theirInput.equals("HELP")) {
                System.out.println("Bad command");
            }
            return "not logged in";
        }
        String email = null;
        String username = null;
        String password = null;
        if (theirInput.equals("REGISTER")) {
            System.out.println("Email:");
            email = getThing.nextLine();
            System.out.println("Username:");
            username = getThing.nextLine();
            System.out.println("Password:");
            password = getThing.nextLine();
            authToken = sF.registerAttempt(username, password, email);
            if (authToken == null) {
                System.out.println("Taken");
                return "not logged in";
            }
            return "logged in";
        }
        System.out.println("Username:");
        username = getThing.nextLine();
        System.out.println("Password:");
        password = getThing.nextLine();
        authToken = sF.loginRequest(username, password);
        if (authToken == null) {
            System.out.println("Bad Password");
            return "not logged in";
        }
        return "logged in";
    }
    private int getGame(int g, Collection<GameData> cG) {
        for (GameData cGD: cG) {
            int j = g - 1;
            for (GameData myGame: cG) {
                if (cmpGames(myGame, cGD).equals("<")) {
                    j--;
                }
            }
            if (j == 0) {
                return cGD.gameID();
            }
        }
        return 0;
    }
    private int getInt(String m, Collection<GameData> cG) {
        int gameLen = cG.size();
        int i = 1;
        while (i <= gameLen) {
            String j = i + "";
            if (j.equals(m)) {
                return getGame(i, cG);
            }
            i++;
        }
        return 0;
    }
    private GameData retrieveGame(int i, Collection<GameData> cG) {
        for (GameData cGD: cG) {
            if (cGD.gameID() == i) {
                return cGD;
            }
        }
        return null;
    }

    private String cmpGames(GameData cGE, GameData cGD) {
        if (cGE.gameName().equals(cGD.gameName())) {
            if (cGE.gameID() < cGD.gameID()) {
                return "<";
            }
            if (cGE.gameID() == cGD.gameID()) {
                return "=";
            }
            return ">";
        }
        if (cGE.gameName().length() > cGD.gameName().length()) {
            return "<";
        }
        if (cGE.gameName().length() < cGD.gameName().length()) {
            return ">";
        }
        int i = 0;
        while (i < cGE.gameName().length()) {
            if (cGE.gameName().charAt(i) < cGD.gameName().charAt(i)) {
                return "<";
            }
            else if (cGE.gameName().charAt(i) > cGD.gameName().charAt(i)) {
                return ">";
            }
            i++;
        }
        return "=";
    }
    private void drawBoard(Collection<GameData> gD, int jd, ChessPosition ch, BoardDrawingClass.Role rl) {
        ChessGame cg = null;
        for (GameData gd: gD) {
            if (gd.gameID() == jd) {
                cg = gd.game().clone();
            }
        }
        bDC.dB(cg, ch, rl);
    }

    private String evaluateGame(String theirInput) {
        mC.sendNotification(UserGameCommand.CommandType.CONNECT, authToken, wGI, null);
        wGI = 0;
        role = BoardDrawingClass.Role.WHITE;
        mC = null;
        return "logged in";
    }
}

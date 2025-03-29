package client;

import chess.ChessGame;
import model.GameData;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class Client {
    private ServerFacade sF;
    private Scanner getThing;
    private String authToken;
    public int run(int desiredPort) {
        sF = new ServerFacade("http://localhost:" + desiredPort);
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

    private String evaluateGame(String theirInput) {
        return "logged in";
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
        if (iC.equals("LOGOUT")) {
            if (sF.logoutRequest(authToken)) {
                return "not logged in";
            }
            System.out.println("Bad Auth Token");
            return "logged in";
        }
        if (iC.equals("LIST GAMES")) {
            Collection<GameData> gamesListing = new ArrayList<>();
            boolean itWorked = false;
            try {
                gamesListing = sF.gameListRequest(authToken);
            } catch (Exception e) {
                System.out.println("Invalid Auth Token");
            }
            System.out.println("Games List:");
            if (gamesListing.isEmpty()) {
                System.out.println("Empty");
            }
            int i = 1;
            while (getInt(i + "", gamesListing) != 0) {
                int j = getInt(i + "", gamesListing);
                GameData gD = retrieveGame(j, gamesListing);
                if (gD == null) {
                    gD = new GameData(j, null, null, "", new ChessGame());
                }
                String k = i + " ";
                if (gD.whiteUsername() == null) {
                    k = k + "NULL";
                }
                else {
                    k = k + gD.whiteUsername();
                }
                if (gD.blackUsername() == null) {
                    k = k + " NULL";
                }
                else {
                    k = k + " " + gD.blackUsername();
                }
                k = k + " " + gD.gameName();
                i++;
                System.out.println(k);
            }
            return "logged in";
        }
        if (iC.equals("PLAY GAME")) {
            System.out.println("Game to Play:");
            //
            return "logged in";
        }
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
        if (g <= 0) {
            return 0;
        }
        if (g > cG.size()) {
            return 0;
        }
        for (GameData cGD: cG) {
            int i = 0;
            for (GameData cGE: cG) {
                if (!cmpGames(cGE, cGD).equals(">")) {
                    i++;
                }
            }
            if (i == g) {
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
        }
        return "=";
    }
}

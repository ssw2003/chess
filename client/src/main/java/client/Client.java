package client;

import model.GameData;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Scanner;

public class Client {
    private Scanner getThing;
    private String authToken;
//    private HttpURLConnection cn = null;
    public int run(int desiredPort) {
//        try {
//            URI url = new URI("http://localhost:" + desiredPort);
//            cn = (HttpURLConnection) url.toURL().openConnection();
//        } catch (Exception e) {
//            System.out.println("Bad");
//        }
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
            System.out.println("Login\nRegister\nQuit\nHelp\n");
        }
        else {
            System.out.println("Play Game\nHelp\nLogout\nCreate Game\nList Games\nObserve Game\n");
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
                    System.out.println("Bad command\n");
                }
                return "logged in";
            }
        }
        if (iC.equals("LOGOUT")) {
            boolean attemptWorked = false;
            //attempt to log out
            if (!attemptWorked) {
                System.out.println("Bad Auth Token\n");
                return "logged in";
            }
            return "not logged in";
        }
    }

    private String evaluateNotLoggedIn(String theirInput) {
        if (theirInput.equals("QUIT")) {
            return "quit";
        }
        if (!theirInput.equals("LOGIN") && !theirInput.equals("REGISTER")) {
            if (!theirInput.equals("HELP")) {
                System.out.println("Bad command\n");
            }
            return "not logged in";
        }
        String email = null;
        String username = null;
        String password = null;
        if (theirInput.equals("REGISTER")) {
            System.out.println("Email:\n");
            email = getThing.nextLine();
            System.out.println("Username:\n");
            username = getThing.nextLine();
            System.out.println("Password:\n");
            password = getThing.nextLine();
            authToken = null;
            //authToken = attempt to register [username, password, email]
            if (authToken == null) {
                System.out.println("Taken\n");
                return "not logged in";
            }
            return "logged in";
        }
        System.out.println("Username:\n");
        username = getThing.nextLine();
        System.out.println("Password:\n");
        password = getThing.nextLine();
        authToken = null;
        //authToken = attempt to log in [username, password]
        if (authToken == null) {
            System.out.println("Bad Password\n");
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

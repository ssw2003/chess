package client;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

public class Client {
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
        String height = "not logged in";
        String authToken = null;
        int gameNumber = 0;
        Scanner getThing = new Scanner(System.in);
        while (!height.equals("quit")) {
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
        if (theirInput.equals())
    }

    private String evaluateLoggedIn(String theirInput) {
    }

    private String evaluateNotLoggedIn(String theirInput) {
    }
}

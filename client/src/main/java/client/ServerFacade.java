package client;

import chess.ChessGame;
import chess.InvalidMoveException;
import com.google.gson.Gson;
import model.AuthData;
import model.UserData;
import model.UserPass;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ResponseCache;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;

public class ServerFacade {
    private String svurl;
    public ServerFacade(String url) {
        svurl = url;
    }
    private <T> T requests(String me, String pt, Object p, Class<T> re) throws InvalidMoveException {
        try {
            URL place = (new URI(svurl + pt)).toURL();
            HttpURLConnection huck = (HttpURLConnection) place.openConnection();
            huck.setRequestMethod(me);
            huck.setDoOutput(true);
            wB(huck, p);
            huck.connect();
            thrower(huck);
            return getBody(re, huck);
        } catch (Exception e) {
            throw new InvalidMoveException();
        }
    }
    private <T> T getBody(Class<T> p, HttpURLConnection huck) throws IOException {
        T thisThing = null;
        if (huck.getContentLength() < 0) {
            try (InputStream is = huck.getInputStream()) {
                InputStreamReader isr = new InputStreamReader(is);
                if (p != null) {
                    thisThing = new Gson().fromJson(isr, p);
                }
            }
        }
        return thisThing;
    }
    private void thrower(HttpURLConnection huck) throws IOException {
        var st = huck.getResponseCode();
        if (st / 100 != 2) {
            throw new IOException();
        }
    }
    private void wB(HttpURLConnection huck, Object p) throws IOException {
        if (p != null) {
            huck.addRequestProperty("Content-Type", "application/json");
            String rD = new Gson().toJson(p);
            try (OutputStream os = huck.getOutputStream()) {
                os.write(rD.getBytes());
            }
        }
    }

    public String loginRequest(String username, String password) {
        try {
            AuthData aD = requests("POST", "/session", new Gson().toJson(new UserPass(username, password)), AuthData.class);
            return aD.authToken();
        } catch (InvalidMoveException e) {
            return null;
        }
    }

    public String registerAttempt(String username, String password, String email) {
        try {
            AuthData aD = requests("POST", "/user", new Gson().toJson(new UserData(username, password, email)), AuthData.class);
            return aD.authToken();
        } catch (InvalidMoveException e) {
            return null;
        }
    }
}

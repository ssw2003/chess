package client;

import chess.ChessGame;
import chess.InvalidMoveException;
import com.google.gson.Gson;
import model.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class ServerFacade {
    private String svurl;
    public ServerFacade(String url) {
        svurl = url;
    }
    private <T> T requests(String me, String pt, String p, Class<T> re, String hd) throws InvalidMoveException {
        try {
            URL place = (new URI(svurl + pt)).toURL();
            HttpURLConnection huck = (HttpURLConnection) place.openConnection();
            huck.setRequestMethod(me);
            huck.setDoOutput(true);
            if (hd != null) {
                huck.addRequestProperty("authorization", hd);
            }
            wB(huck, p);
            huck.connect();
            thrower(huck);
            if (re == null) {
                return null;
            }
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
    private void wB(HttpURLConnection huck, String p) throws IOException {
        if (p != null) {
            try (OutputStream os = huck.getOutputStream()) {
                os.write(p.getBytes());
            }
        }
    }

    public String loginRequest(String username, String password) {
        try {
            AuthData aD = requests("POST", "/session", new Gson().toJson(new UserPass(username, password)), AuthData.class, null);
            return aD.authToken();
        } catch (InvalidMoveException e) {
            return null;
        }
    }
    public boolean logoutRequest(String headerField) {
        try {
            requests("DELETE", "/session", null, null, headerField);
            return true;
        } catch (InvalidMoveException e) {
            return false;
        }
    }

    public String registerAttempt(String username, String password, String email) {
        try {
            AuthData aD = requests("POST", "/user", new Gson().toJson(new UserData(username, password, email)), AuthData.class, null);
            return aD.authToken();
        } catch (InvalidMoveException e) {
            return null;
        }
    }

    public Collection<GameData> gameListRequest(String authToken) {
        Collection<GameData> aE = new ArrayList<>();
        try {
            ListGameDataWithout aD = requests("GET", "/game", null, ListGameDataWithout.class, authToken);
            for (GameDataWithout aG: aD.games()) {
                aE.add(new GameData(aG.gameID(), aG.whiteUsername(), aG.blackUsername(), aG.gameName(), new ChessGame()));
            }
        } catch (InvalidMoveException e) {
            return null;
        }
        return aE;
    }
    public boolean clear() {
        try {
            requests("DELETE", "/db", null, null, null);
            return true;
        } catch (InvalidMoveException e) {
            return false;
        }
    }
    public boolean createGame(String authToken, String gameName) {
        try {
            GameInteger i = requests("POST", "/game", new Gson().toJson(new GameNameData(gameName)), GameInteger.class, authToken);
            if (i.gameID() == 0) {
                return false;
            }
            return true;
        } catch (InvalidMoveException e) {
            return false;
        }
    }
    public boolean joinGame(String authToken, int gameName, String tC) {
        try {
            requests("PUT", "/game", new Gson().toJson(new ColorAndNumber(tC, gameName)), null, authToken);
            return true;
        } catch (InvalidMoveException e) {
            return false;
        }
    }
}

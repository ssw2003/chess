package facade;

import com.google.gson.Gson;
import model.*;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class ServerFacade {
    private final String url;
    public ServerFacade(String myUrl) {
        this.url = myUrl;
    }
    private static <T> T readFromBody(HttpURLConnection cn, Class<T> cT) throws IOException {
        if (cn.getContentLength() >= 0) {
            return null;
        }
        T res = null;
        try (InputStream rB = cn.getInputStream()) {
            InputStreamReader inputStreamReader = new InputStreamReader(rB);
            String body = new String(rB.readAllBytes());
            res = new Gson().fromJson(body, cT);
        }
        return res;
    }
    private static void writeToBody(HttpURLConnection cn, Object item) throws IOException {
        if (item == null) {
            return;
        }
        cn.addRequestProperty("Content-Type", "application/json");
        String rD = new Gson().toJson(item);
        try (OutputStream body = cn.getOutputStream()) {
            body.write(rD.getBytes());
        }
    }
    private <T> T requestSomethingBody(HttpURLConnection cn, String path, Object item, Class<T> res) {
        try {
            //cn.connect();
            cn.setDoOutput(true);
            writeToBody(cn, item);
            var gc = cn.getResponseCode();
            int cngc = gc;
            if (gc / 100 != 2) {
                String body = new String(cn.getErrorStream().readAllBytes());
                if (body.charAt(0) == '{') {
                    Map map = new Gson().fromJson(body, Map.class);
                    throw new IOException(cngc + (String)map.get("message"));
                }
                throw new IOException(cngc + body);
            }
            return readFromBody(cn, res);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void requestSomethingBody(HttpURLConnection cn, String path, Object item) {
        try {
            //cn.connect();
            cn.setDoOutput(true);
            writeToBody(cn, item);
            var gc = cn.getResponseCode();
            int cngc = gc;
            if (gc / 100 != 2) {
                throw new IOException(cngc + "");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void setHeader(HttpURLConnection cn, String newHeader) {
        cn.addRequestProperty("authorization", newHeader);
    }
    public AuthData addUser(UserData uD) {
        HttpURLConnection cn;
        try {
            URL newUrl = (new URI(url + "/user")).toURL();
            cn = (HttpURLConnection) newUrl.openConnection();
            cn.setRequestMethod("POST");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return this.requestSomethingBody(cn, url + "/user", uD, AuthData.class);
    }
    public AuthData loginUser(LoginData lD) {
        HttpURLConnection cn;
        try {
            URL newUrl = (new URI(url + "/session")).toURL();
            cn = (HttpURLConnection) newUrl.openConnection();
            cn.setRequestMethod("POST");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return this.requestSomethingBody(cn, url + "/session", lD, AuthData.class);
    }
    public void logoutUser(String aT) {
        HttpURLConnection cn;
        try {
            URL newUrl = (new URI(url + "/session")).toURL();
            cn = (HttpURLConnection) newUrl.openConnection();
            cn.setRequestMethod("DELETE");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.setHeader(cn, aT);
        requestSomethingBody(cn, url + "/session", null, Map.class);
    }
    public int createGame(GameName gN, String aT) {
        HttpURLConnection cn;
        try {
            URL newUrl = (new URI(url + "/game")).toURL();
            cn = (HttpURLConnection) newUrl.openConnection();
            cn.setRequestMethod("POST");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.setHeader(cn, aT);
        GameInteger r = this.requestSomethingBody(cn, url + "/game", gN, GameInteger.class);
        return r.gameID();
    }
    public void joinGame(PlayerColorGameNumber uD, String aT) {
        HttpURLConnection cn;
        try {
            URL newUrl = (new URI(url + "/game")).toURL();
            cn = (HttpURLConnection) newUrl.openConnection();
            cn.setRequestMethod("PUT");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.setHeader(cn, aT);
        this.requestSomethingBody(cn, url + "/game", uD, Map.class);
    }
    public Collection<GameData> listGames(String aT) {
        HttpURLConnection cn;
        try {
            URL newUrl = (new URI(url + "/game")).toURL();
            cn = (HttpURLConnection) newUrl.openConnection();
            cn.setRequestMethod("GET");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        setHeader(cn, aT);
        ListGamesResult lGR = this.requestSomethingBody(cn, url + "/game", null, ListGamesResult.class);
        return lGR.games();
    }
    public void clearThing() {
        HttpURLConnection cn;
        try {
            URL newUrl = (new URI(url + "/db")).toURL();
            cn = (HttpURLConnection) newUrl.openConnection();
            cn.setRequestMethod("DELETE");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.requestSomethingBody(cn, url + "/db", "");
    }
}

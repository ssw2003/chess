package facade;

import com.google.gson.Gson;
import model.*;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
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
            res = new Gson().fromJson(inputStreamReader, cT);
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
                throw new IOException(cngc + "");
            }
            return readFromBody(cn, res);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    void requestSomethingBody(HttpURLConnection cn, String path, Object item) {
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
        return this.requestSomethingBody(cn, url + "/game", gN, int.class);
    }
    public String joinGame(PlayerColorGameNumber uD, String aT) {
        HttpURLConnection cn;
        try {
            URL newUrl = (new URI(url + "/game")).toURL();
            cn = (HttpURLConnection) newUrl.openConnection();
            cn.setRequestMethod("PUT");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.setHeader(cn, aT);
        return this.requestSomethingBody(cn, url + "/game", uD, String.class);
    }
    public Collection<GameMetadata> listGames(PlayerColorGameNumber uD) {
        HttpURLConnection cn;
        try {
            URL newUrl = (new URI(url + "/game")).toURL();
            cn = (HttpURLConnection) newUrl.openConnection();
            cn.setRequestMethod("GET");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return this.requestSomethingBody(cn, url + "/game", uD, Collection.class);
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

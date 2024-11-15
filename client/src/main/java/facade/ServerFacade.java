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
    private <T> T requestSomethingBody(String post, String path, Object item, Class<T> res) {
        try {
            URL newUrl = (new URI(path)).toURL();
            HttpURLConnection cn = (HttpURLConnection) newUrl.openConnection();
            cn.setRequestMethod(post);
            cn.setDoOutput(true);
            writeToBody(cn, item);
            cn.connect();
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
    private String readFromHeader(HttpURLConnection cn) throws IOException {
        if (cn.getContentLength() >= 0) {
            return null;
        }
        String res = null;
        res = cn.getHeaderField();
        if (res == null) {
            throw new RuntimeException();
        }
        return res;
    }
    public AuthData addUser(UserData uD) {
        return this.requestSomethingBody("POST", url + "/user", uD, AuthData.class);
    }
    public AuthData loginUser(LoginData lD) {
        return this.requestSomethingBody("POST", url + "/session", lD, AuthData.class);
    }
    public String logoutUser(String aT) {
        return this.requestSomethingBody("DELETE", url + "/session", aT, String.class);
    }
    public int createGame(GameName gN) {
        return this.requestSomethingBody("POST", url + "/game", gN, int.class);
    }
    public String joinGame(PlayerColorGameNumber uD) {
        return this.requestSomethingBody("PUT", url + "/game", uD, String.class);
    }
    //public Collection<GameMetadata> listGames(PlayerColorGameNumber uD) {
    //    return this.requestSomething("GET", url + "/game", uD, Collection<GameMetadata>.class);
    //}
}

package facade;

import com.google.gson.Gson;
import model.AuthData;
import model.LoginData;
import model.UserData;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
import java.util.Map;

public class ServerFacade {
    private final String url;
    public ServerFacade(String myUrl) {
        this.url = myUrl;
    }
    private static <T> T readFrom(HttpURLConnection cn, Class<T> cT) throws IOException {
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
    private static void writeTo(HttpURLConnection cn, Object item) throws IOException {
        if (item == null) {
            return;
        }
        cn.addRequestProperty("Content-Type", "application/json");
        String rD = new Gson().toJson(item);
        try (OutputStream body = cn.getOutputStream()) {
            body.write(rD.getBytes());
        }
    }
    private <T> T requestSomething(String post, String path, Object item, Class<T> res) {
        try {
            URL newUrl = (new URI(path)).toURL();
            HttpURLConnection cn = (HttpURLConnection) newUrl.openConnection();
            cn.setRequestMethod(post);
            cn.setDoOutput(true);
            writeTo(cn, item);
            cn.connect();
            var gc = cn.getResponseCode();
            int cngc = gc;
            if (cngc / 100 != 2) {
                throw new IOException(cngc + "");
            }
            return readFrom(cn, res);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public AuthData addUser(UserData uD) {
        return this.requestSomething("POST", url + "/user", uD, AuthData.class);
    }
    public AuthData loginUser(LoginData lD) {
        return this.requestSomething("POST", url + "/session", lD, AuthData.class);
    }
    public String logoutUser(String uD) {
        return this.requestSomething("DELETE", url + "/session", uD, String.class);
    }
}

package client;

import chess.ChessGame;
import chess.InvalidMoveException;

import java.net.HttpURLConnection;
import java.net.ResponseCache;
import java.net.URI;
import java.net.URL;

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
        } catch (Exception e) {
            //
        }
    }
}

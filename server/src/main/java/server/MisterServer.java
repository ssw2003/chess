package server;

import chess.ChessMove;
import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import spark.Spark;
import websocket.commands.MakeMoveCommand;
import websocket.commands.UserGameCommand;
import websocket.messages.ErrorMessage;
import websocket.messages.ServerMessage;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Collection;
@WebSocket
public class MisterServer {
    private Collection<SessionAuthToken> ss;
    public MisterServer() {
        Spark.port(8080);
        Spark.webSocket("/ws", MisterServer.class);
    }
    @OnWebSocketMessage
    public void onMessage(Session sess, String s) throws Exception {
        UserGameCommand ugc = new Gson().fromJson(s, UserGameCommand.class);
        UserGameCommand.CommandType ct = ugc.getCommandType();
        int gameID = ugc.getGameID();
        String auth = ugc.getAuthToken();
        ChessMove cm = null;
        if (ct == UserGameCommand.CommandType.MAKE_MOVE) {
            cm = new Gson().fromJson(s, MakeMoveCommand.class).getMove();
        }
        if (gameID == 0) {
            ErrorMessage em = new ErrorMessage(ServerMessage.ServerMessageType.ERROR);
            em.setError("Error");
            sess.getBasicRemote().sendText(new Gson().toJson(em));
            return;
        }
        if ()
    }
    private int gameNum(Session sess) {
        for (SessionAuthToken sat: ss) {
            if (sat.session() == sess) {
                return sat.gameNumber();
            }
        }
        return 0;
    }
    private String authData(Session sess) {
        for (SessionAuthToken sat: ss) {
            if (sat.session() == sess) {
                return sat.authToken();
            }
        }
        return null;
    }
    private void sendIt(Session sat, boolean towardsMe, String g) {
        if (towardsMe) {
            try {
                sat.getBasicRemote().sendText(g);
            } catch (IOException e) {}
            return;
        }
        int gd = gameNum(sat);
        String at = authData(sat);
        for (SessionAuthToken st: ss) {
            if (st.gameNumber() == gd && !st.authToken().equals(at)) {
                try {
                    st.session().getBasicRemote().sendText(g);
                } catch (Exception e) {}
            }
        }
    }
}

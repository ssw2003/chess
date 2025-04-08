package server;

import chess.ChessGame;
import chess.ChessMove;
import com.google.gson.Gson;
import dataaccess.Service;
import model.GameData;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import spark.Spark;
import websocket.commands.MakeMoveCommand;
import websocket.commands.UserGameCommand;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Collection;
//@WebSocket
public class MisterServer {
//    private Collection<SessionAuthToken> ss;
//    private Service sve;
//    public MisterServer(Service sv) {
//        Spark.port(8080);
//        Spark.webSocket("/ws", MisterServer.class);
//        sve = sv;
//    }
//    @OnWebSocketMessage
//    public void onMessage(Session sess, String s) throws Exception {
//        UserGameCommand ugc = new Gson().fromJson(s, UserGameCommand.class);
//        UserGameCommand.CommandType ct = ugc.getCommandType();
//        int gameID = ugc.getGameID();
//        String auth = ugc.getAuthToken();
//        ChessMove cm = null;
//        if (ct == UserGameCommand.CommandType.MAKE_MOVE) {
//            cm = new Gson().fromJson(s, MakeMoveCommand.class).getMove();
//        }
//        if (gameID == 0) {
//            ErrorMessage em = new ErrorMessage(ServerMessage.ServerMessageType.ERROR);
//            em.setError("Error");
//            sess.getBasicRemote().sendText(new Gson().toJson(em));
//            return;
//        }
//        if (ct == UserGameCommand.CommandType.CONNECT) {
//            ss.add(new SessionAuthToken(sess, auth, gameID));
//            Collection<GameData> gd = sve.getGames(auth);
//            GameData cgcg = null;
//            for (GameData gdgd: gd) {
//                if (gdgd.gameID() == gameID) {
//                    cgcg = gdgd;
//                }
//            }
//            if (cgcg.game() == null) {
//                sendIt(sess, true, ServerMessage.ServerMessageType.LOAD_GAME, null, new ChessGame());
//                return;
//            }
//            sendIt(sess, true, ServerMessage.ServerMessageType.LOAD_GAME, null, cgcg.game());
//            String str = sve.getPsw(auth, false) + " joins as ";
//            if (cgcg.whiteUsername().equals(auth)) {
//                str = str + "white.";
//            } else if (cgcg.blackUsername().equals(auth)) {
//                str = str + "black.";
//            } else {
//                str = str + "observer.";
//            }
//            sendIt(sess, false, ServerMessage.ServerMessageType.NOTIFICATION, str, null);
//        }
//        else if (ct == UserGameCommand.CommandType.LEAVE) {
//            Collection<GameData> gd = sve.getGames(auth);
//            GameData cgcg = null;
//            for (GameData gdgd: gd) {
//                if (gameID == gdgd.gameID()) {
//                    cgcg = gdgd;
//                }
//            }
//            String str;
//            if (cgcg.whiteUsername().equals(auth)) {
//                str = "white.";
//            } else if (cgcg.blackUsername().equals(auth)) {
//                str = "black.";
//            } else {
//                str = "observer.";
//            } str = sve.getPsw(auth, false) + " leaves from " + str + ".";
//            sendIt(sess, false, ServerMessage.ServerMessageType.NOTIFICATION, str, null);
//            ss.remove(new SessionAuthToken(sess, auth, gameID));
//        }
//    }
//    private int gameNum(Session sess) {
//        for (SessionAuthToken sat: ss) {
//            if (sat.session() == sess) {
//                return sat.gameNumber();
//            }
//        }
//        return 0;
//    }
//    private String authData(Session sess) {
//        for (SessionAuthToken sat: ss) {
//            if (sat.session() == sess) {
//                return sat.authToken();
//            }
//        }
//        return null;
//    }
//    private void sendIt(Session sat, boolean towardsMe, ServerMessage.ServerMessageType smt, String ga, ChessGame cgm) {
//        String g;
//        Gson gson = new Gson();
//        if (smt == ServerMessage.ServerMessageType.ERROR) {
//            ErrorMessage em = new ErrorMessage(ServerMessage.ServerMessageType.ERROR);
//            em.setError(ga);
//            g = gson.toJson(em);
//        } else if (smt == ServerMessage.ServerMessageType.NOTIFICATION) {
//            NotificationMessage em = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION);
//            em.setNotification(ga);
//            g = gson.toJson(em);
//        } else {
//            LoadGameMessage em = new LoadGameMessage(smt);
//            em.setGame(cgm);
//            g = gson.toJson(em);
//
//        }
//        if (towardsMe) {
//            try {
//                sat.getBasicRemote().sendText(g);
//            } catch (IOException e) {}
//            return;
//        }
//        int gd = gameNum(sat);
//        String at = authData(sat);
//        for (SessionAuthToken st: ss) {
//            if (st.gameNumber() == gd && !st.authToken().equals(at)) {
//                try {
//                    st.session().getBasicRemote().sendText(g);
//                } catch (Exception e) {}
//            }
//        }
//    }
}

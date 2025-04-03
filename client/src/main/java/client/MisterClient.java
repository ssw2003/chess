package client;

import chess.ChessGame;
import chess.ChessMove;
import com.google.gson.Gson;
import websocket.commands.MakeMoveCommand;
import websocket.commands.UserGameCommand;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

import javax.websocket.ContainerProvider;
import javax.websocket.MessageHandler;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class MisterClient {
    private Session ss;
    private boolean b;
    private boolean c;
    public MisterClient() {
        WebSocketContainer wsc = ContainerProvider.getWebSocketContainer();
        b = false;
        c = false;
        try {
            ss = wsc.connectToServer(this, new URI("ws://localhost:8080/ws"));
        } catch (Exception e) {}
        ss.addMessageHandler(new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String s) {
                Gson gson = new Gson();
                ServerMessage sm = gson.fromJson(s, ServerMessage.class);
                String mssg = null;
                ChessGame cssg = null;
                if (sm.getServerMessageType() == ServerMessage.ServerMessageType.ERROR) {
                    ErrorMessage esm = gson.fromJson(s, ErrorMessage.class);
                    mssg = esm.getError();
                }
                else if (sm.getServerMessageType() == ServerMessage.ServerMessageType.NOTIFICATION) {
                    NotificationMessage esm = gson.fromJson(s, NotificationMessage.class);
                    mssg = esm.getNotification();
                }
                else {
                    LoadGameMessage lgm = gson.fromJson(s, LoadGameMessage.class);
                    cssg = lgm.getGame();
                }
                doThing(sm.getServerMessageType(), mssg, cssg);
                b = true;
            }
        });
    }

    private void doThing(ServerMessage.ServerMessageType serverMessageType, String mssg, ChessGame cssg) {
        if (serverMessageType == ServerMessage.ServerMessageType.ERROR) {
            System.out.println("Bad command");
        }
        else if (serverMessageType == ServerMessage.ServerMessageType.LOAD_GAME) {
            //
        }
        if (c) {
            try {
                ss.close();
            } catch (Exception e) {}
        }
    }



    public void sendNotification(UserGameCommand.CommandType p, String aT, int mv, ChessMove cmv) {
        try {
            Gson gson = new Gson();
            String s = gson.toJson(new UserGameCommand(p, aT, mv));
            if (p == UserGameCommand.CommandType.MAKE_MOVE) {
                MakeMoveCommand fmc = new MakeMoveCommand(p, aT, mv);
                fmc.setMove(cmv.clone());
                s = gson.toJson(fmc);
            }
            ss.getBasicRemote().sendText(s);
        } catch (IOException e) {}
    }
    public void stopIt() {
        try {
            c = true;
            if (!b) {
                throw new RuntimeException();
            }
            ss.close();
        } catch (Exception e) {}
    }
}

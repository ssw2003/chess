package client;

import chess.ChessMove;
import com.google.gson.Gson;
import websocket.commands.MakeMoveCommand;
import websocket.commands.UserGameCommand;

import javax.websocket.ContainerProvider;
import javax.websocket.MessageHandler;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class MisterClient {
    private Session ss;
    public MisterClient() {
        WebSocketContainer wsc = ContainerProvider.getWebSocketContainer();
        try {
            ss = wsc.connectToServer(this, new URI("ws://localhost:8080/ws"));
        } catch (Exception e) {}
        ss.addMessageHandler(new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String s) {
                doThing(s);
            }
        });
    }

    private void doThing(String s) {
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
            ss.close();
        } catch (Exception e) {}
    }
}

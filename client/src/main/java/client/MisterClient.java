package client;

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

    public void sendNotification(String p) {
        try {
            ss.getBasicRemote().sendText(p);
        } catch (IOException e) {}
    }
    public void stopIt() {
        try {
            ss.close();
        } catch (Exception e) {}
    }
}

import chess.InvalidMoveException;

import javax.websocket.*;
import java.net.URI;
import java.net.URISyntaxException;

public class MisterClient extends Endpoint {
    public static void main(String[] argv) throws Exception {
        //var mC = new MisterClient();
    }
    public Session ss;
    public MisterClient(NotificationHandler nh) throws Exception {
        URI u = new URI("ws://localhost:8080/ws");
        WebSocketContainer wsc = ContainerProvider.getWebSocketContainer();
        ss = wsc.connectToServer(this, u);
        ss.addMessageHandler(new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String ms) { nh.notify(ms); }
        });
    }
    public void sn(String ms) throws Exception {
        ss.getBasicRemote().sendText(ms);
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {

    }
}

import chess.InvalidMoveException;

import javax.websocket.*;
import java.net.URI;
import java.net.URISyntaxException;

public class MisterClient {
    public static void main(String[] argv) throws Exception {
        var mC = new MisterClient();
    }
    public Session ss;
    public MisterClient() throws Exception {
        URI u = new URI("ws://localhost:8080/ws");
        WebSocketContainer wsc = ContainerProvider.getWebSocketContainer();
        ss = wsc.connectToServer(this, u);
        ss.addMessageHandler(new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String ms) {
                System.out.println(ms);
            }
        });
    }
    public void sn(String ms) throws Exception { ss.getBasicRemote().sendText(ms); }
    public void whenMessageComes (Session ss, EndpointConfig ep) {}
}

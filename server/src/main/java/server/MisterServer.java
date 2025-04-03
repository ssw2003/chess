package server;

import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import spark.Spark;

import javax.websocket.Session;
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
        //
    }
}

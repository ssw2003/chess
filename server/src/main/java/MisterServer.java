import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import spark.Session;
import spark.Spark;

@WebSocket
public class MisterServer {
    public static void main(String[] argv) {
        Spark.port(8080);
        Spark.webSocket("/ws", MisterServer.class);
    }
    @OnWebSocketMessage
    public void takeCareOfIt(Session ss, String ms) {
        System.out.println(ms);
    }
}

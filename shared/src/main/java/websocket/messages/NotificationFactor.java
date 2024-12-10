package websocket.messages;

public class NotificationFactor extends ServerMessage {
    String message;
    public NotificationFactor(ServerMessageType type, String message) {
        super(type);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

package websocket.messages;

public class NotificationMessage extends ServerMessage {
    private String message;
    public NotificationMessage(ServerMessage.ServerMessageType type) {
        super(type);
    }
    public void setNotification(String n) {
        message = n;
    }
    public String getNotification() {
        return message;
    }
}

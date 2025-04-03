package websocket.messages;

public class NotificationMessage extends ServerMessage {
    private String notification;
    public NotificationMessage(ServerMessage.ServerMessageType type) {
        super(type);
    }
    public void setNotification(String n) {
        notification = n;
    }
    public String getNotification() {
        return notification;
    }
}

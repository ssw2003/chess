package websocket.messages;

public class ErrorMessage extends ServerMessage {
    private String notification;
    public ErrorMessage(ServerMessageType type) {
        super(type);
    }
    public void setError(String n) {
        notification = n;
    }
    public String getError() {
        return notification;
    }
}

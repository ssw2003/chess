package websocket.messages;

public class ErrorThing extends ServerMessage {
    String errorMessage;
    public ErrorThing(ServerMessageType type, String errorMessage) {
        super(type);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}

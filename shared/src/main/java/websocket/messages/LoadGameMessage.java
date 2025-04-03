package websocket.messages;

import chess.ChessGame;

public class LoadGameMessage extends ServerMessage {
    private ChessGame cg;
    public LoadGameMessage(ServerMessageType type) {
        super(type);
    }
    public void setGame(ChessGame ch) {
        cg = ch.clone();
    }
    public ChessGame getGame() {
        return cg.clone();
    }
}

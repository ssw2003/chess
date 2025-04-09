package websocket.messages;

import chess.ChessGame;

public class LoadGameMessage extends ServerMessage {
    private ChessGame game;
    public LoadGameMessage(ServerMessageType type) {
        super(type);
    }
    public void setGame(ChessGame ch) {
        game = ch.clone();
    }
    public ChessGame getGame() {
        return game.clone();
    }
}

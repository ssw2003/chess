package websocket.messages;

import chess.ChessGame;

public class GameOfChessThing extends ServerMessage {
    ChessGame game;
    public GameOfChessThing(ServerMessageType type, ChessGame game) {
        super(type);
        this.game = game.clone();
    }

    public ChessGame getGame() {
        return game.clone();
    }
}

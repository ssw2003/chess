package websocket.commands;

import chess.ChessMove;

public class MoveMaker extends UserGameCommand {
    private final ChessMove move;
    public MoveMaker(CommandType commandType, String authToken, Integer gameID, ChessMove cM) {
        super(commandType, authToken, gameID);
        move = cM.clone();
    }

    public ChessMove getMove() {
        return move;
    }
}

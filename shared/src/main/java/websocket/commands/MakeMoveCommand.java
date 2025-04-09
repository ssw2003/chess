package websocket.commands;

import chess.ChessMove;

public class MakeMoveCommand extends UserGameCommand {
    private ChessMove move;
    public MakeMoveCommand(CommandType commandType, String authToken, Integer gameID) {
        super(commandType, authToken, gameID);
    }
    public void setMove(ChessMove cM) {
        move = cM.clone();
    }
    public ChessMove getMove() {
        return move.clone();
    }
}

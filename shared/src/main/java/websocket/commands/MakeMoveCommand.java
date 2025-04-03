package websocket.commands;

import chess.ChessMove;

public class MakeMoveCommand extends UserGameCommand {
    private ChessMove chessMove;
    public MakeMoveCommand(CommandType commandType, String authToken, Integer gameID) {
        super(commandType, authToken, gameID);
    }
    public void setMove(ChessMove cM) {
        chessMove = cM.clone();
    }
    public ChessMove getMove() {
        return chessMove.clone();
    }
}

import chess.*;
import server.Server;

import java.util.logging.Handler;
import java.util.SortedSet;
import java.util.logging.LogRecord;

public class Main {
    public static void main(String[] args) {

        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Server: " + piece);
        Server s = new Server();
        int j = s.run(8080);
    }
}
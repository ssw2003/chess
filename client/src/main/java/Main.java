import chess.*;
import client.Client;

public class Main {
    public static void main(String[] args) throws Exception {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess client.Client: " + piece);
        Client cli = new Client();
        cli.run(8080);
    }
}
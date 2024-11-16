import chess.*;
import facade.ServerFacade;

public class Main {
    public static void main(String[] args) {
        //URI uri;
        //try {
        //    uri = new URI("http://localhost:8080");
        //} catch (URISyntaxException e) {}
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);
        ServerFacade sF = new ServerFacade("http://localhost:8080");
    }
}
import chess.*;
import server.GameData;
import server.Server;
import server.UserData;

import java.net.URI;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) {
        URI uri;
        try {
            uri = new URI("http://localhost:8080");
        } catch (URISyntaxException e) {}
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);
        String inputThing = "";
        boolean statusValueAboveLoggedIn = false;
        boolean inAndNotPlaying = true;
        //ff,ft,tt,tf
        UserData userData = null;
        GameData gameData = null;
        while (statusValueAboveLoggedIn || inAndNotPlaying) {
            //
        }
    }
}
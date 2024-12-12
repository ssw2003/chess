import chess.*;
import com.google.gson.Gson;
import facade.DrawingBoardClass;
import facade.ServerFacade;
import model.*;
import websocket.commands.MoveMaker;
import websocket.commands.UserGameCommand;

import java.util.*;

public class Main {
    public static void main(String[] args) {

        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Server: " + piece);
        Client s = new Client();
        s.run();
    }
}
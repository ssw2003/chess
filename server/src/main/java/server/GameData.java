package server;

import chess.ChessGame;
import java.util.SortedSet;

public record GameData(int gameID, String whiteUsername, String blackUsername, String gameName, ChessGame game) {
    //
}

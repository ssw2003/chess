package server;

import chess.ChessGame;

public record PlayerColorGameNumber(ChessGame.TeamColor playerColor, int gameID) {
}

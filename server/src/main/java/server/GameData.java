package server;

import chess.ChessGame;
import java.util.SortedSet;

public class GameData {
    private int game_id;
    private String white_username;
    private String black_username;
    private String game_name;
    private ChessGame the_game = new ChessGame();
    public GameData(int theGameID, String theGameName) {
        game_id = theGameID;
        game_name = theGameName;
        String white_username = null;
        String black_username = null;
        ChessGame the_game = new ChessGame();
    }
    public int gameID() {
        return game_id;
    }
    public String whiteUsername() {
        if (white_username == null) {
            return null;
        }
        return white_username;
    }
    public String blackUsername() {
        if (black_username == null) {
            return null;
        }
        return black_username;
    }
    public String gameName() {
        if (game_name == null) {
            return null;
        }
        return game_name;
    }
    public ChessGame game() {
        return the_game;
    }
    public void changeWhiteUsername(String s) {
        white_username = s;
    }
    public void changeBlackUsername(String s) {
        black_username = s;
    }
}

package dataaccess;

import chess.ChessGame;
import model.AuthData;
import model.GameData;
import model.GameDataWithout;
import model.UserData;

import java.util.ArrayList;
import java.util.Collection;

public interface DatabaseThingy {
    public int addGame(String gD);
    public boolean addUser(String usn, String psw, String eml, String aM);
    public boolean logout(String authrztn);
    public void clearThingy();
    public boolean isAuthorized(String authy);
    public Collection<GameDataWithout> getGames();
    public boolean joinGame(int ident, boolean isWhite, String authrztn);
    public String retrievePsw(String usn);
    public String retrieveUsn(String authToken);
    public boolean logUser(String usn, String psw, String aM);
}
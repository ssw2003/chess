package dataaccess;

import chess.ChessGame;
import model.*;

import java.util.ArrayList;
import java.util.Collection;

public interface DatabaseThingy {
    public int addGame(String gD);
    public boolean addUser(String usn, String psw, String eml, String aM);
    public boolean logout(String authrztn);
    public void clearThingy();
    public boolean isAuthorized(String authy);
    public Collection<GameData> getGames();
    public boolean joinGame(int ident, boolean isWhite, String authrztn, InfoJoinExt iJE);
    public String retrievePsw(String usn);
    public String retrieveUsn(String authToken);
    public boolean logUser(String usn, String psw, String aM);
}
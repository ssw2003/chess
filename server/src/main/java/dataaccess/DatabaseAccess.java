package dataaccess;
import java.util.Collection;

import chess.ChessGame;
import server.AuthData;
import server.GameData;
import server.UserData;

public interface DatabaseAccess {
    public String generateAuthToken() throws DataAccessException;
    public UserData getUser(String potentialUsername) throws DataAccessException;
    public void createUser(UserData userData) throws DataAccessException;
    public void createAuth(AuthData authData) throws DataAccessException;
    public AuthData getAuth(String authToken) throws DataAccessException;
    public void deleteAuth(AuthData ad) throws DataAccessException;
    public void clearThing() throws DataAccessException;
    public int addGame(String gameName) throws DataAccessException;
    public Collection<GameData> getAllGames() throws DataAccessException;
    public boolean joinGameThingy(int gameId, ChessGame.TeamColor gameColor, String myAuthData) throws DataAccessException;
    public boolean checkForGameExistence(int gameId) throws DataAccessException;
}

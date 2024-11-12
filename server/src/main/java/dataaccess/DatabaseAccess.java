package dataaccess;
import java.util.Collection;

import chess.ChessGame;
import server.AuthData;
import server.GameData;
import server.UserData;

public interface DatabaseAccess {
    public String generateAuthToken() throws DataAccessException;
    public UserData getUser(String potential_username) throws DataAccessException;
    public void createUser(UserData userData) throws DataAccessException;
    public void createAuth(AuthData authData) throws DataAccessException;
    public AuthData getAuth(String authToken) throws DataAccessException;
    public void deleteAuth(AuthData ad) throws DataAccessException;
    public void clearThing() throws DataAccessException;
    public int addGame(String game_name) throws DataAccessException;
    public Collection<GameData> getAllGames() throws DataAccessException;
    public boolean joinGameThingy(int game_id, ChessGame.TeamColor game_color, String my_auth_data) throws DataAccessException;
    public boolean checkForGameExistence(int game_id) throws DataAccessException;
}

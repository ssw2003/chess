package dataaccess;
import java.util.Collection;
import java.util.UUID;

import chess.ChessGame;
import server.AuthData;
import server.GameData;
import server.UserData;

public interface DatabaseAccess {
    public String generateAuthToken() throws DataAccessException;
    public UserData getUser(String potential_username) throws DataAccessException;
    public void CreateUser(UserData userData) throws DataAccessException;
    public void CreateAuth(AuthData authData) throws DataAccessException;
    public AuthData getAuth(String authToken) throws DataAccessException;
    public void deleteAuth(AuthData ad) throws DataAccessException;
    public void clear_thing() throws DataAccessException;
    public int add_game(String game_name) throws DataAccessException;
    public Collection<GameData> get_all_games() throws DataAccessException;
    public boolean join_game_thingy(int game_id, ChessGame.TeamColor game_color, String my_auth_data) throws DataAccessException;
    public boolean check_for_game_existence(int game_id) throws DataAccessException;
}

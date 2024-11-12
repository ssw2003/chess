package dataaccess;

import chess.ChessGame;
import server.AuthData;
import server.GameData;
import server.GameDataSet;
import server.UserData;

import java.util.*;

public class DataAccess implements DatabaseAccess {
    Collection<UserData> user_data_values = new ArrayList<>();
    GameDataSet game_data_values = new GameDataSet();
    Collection<AuthData> auth_data_values = new ArrayList<>();

    public DataAccess() {
        game_data_values = new GameDataSet();
    }
    public String generateAuthToken() {
        return UUID.randomUUID().toString();
    }
    public UserData getUser(String potential_username) {
        for (UserData p: user_data_values) {
            if (p.username().equals(potential_username)) {
                return p;
            }
        }
        return null;
    }
    public void createUser(UserData userData) {
        user_data_values.add(userData);
    }
    public void createAuth(AuthData authData) {
        auth_data_values.add(authData);
    }
    public AuthData getAuth(String authToken) {
        for (AuthData p: auth_data_values) {
            if (p.authToken().equals(authToken)) {
                return p;
            }
        }
        return null;
    }
    public void deleteAuth(AuthData ad) {
        auth_data_values.remove(ad);
    }
    public void clearThing() {
        user_data_values = new ArrayList<>();
        game_data_values = new GameDataSet();
        auth_data_values = new ArrayList<>();
    }
    public int addGame(String game_name) {
        int i = game_data_values.mySize() + 1;
        game_data_values.addGame(new GameData(i, null, null, game_name, null));
        return i;
    }
    public Collection<GameData> getAllGames() {
        Collection<GameData> all_my_games = new ArrayList<>();
        for (int i = 1; i < 1 + game_data_values.mySize(); i = i + 1) {
            all_my_games.add(game_data_values.getGame(i));
        }
        return all_my_games;
    }
    public boolean joinGameThingy(int game_id, ChessGame.TeamColor game_color, String my_auth_data) {
        if (!checkForGameExistence(game_id)) {
            return false;
        }
        GameData gd = game_data_values.getGame(game_id);
        if (game_color == ChessGame.TeamColor.WHITE) {
            if (gd.whiteUsername() != null) {
                return false;
            }
            gd = new GameData(gd.gameID(), getAuth(my_auth_data).username(), gd.blackUsername(), gd.gameName(), gd.game());
        }
        if (game_color == ChessGame.TeamColor.BLACK) {
            if (gd.blackUsername() != null) {
                return false;
            }
            gd = new GameData(gd.gameID(), gd.whiteUsername(), getAuth(my_auth_data).username(), gd.gameName(), gd.game());
        }
        game_data_values.changeGame(game_id, gd);
        return true;
    }

    public boolean checkForGameExistence(int game_id) {
        if (game_id < 1 || game_id > game_data_values.mySize()) {
            return false;
        }
        return true;
    }

}

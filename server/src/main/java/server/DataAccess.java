package server;

import chess.ChessGame;

import java.util.*;

public class DataAccess {
    Collection<UserData> user_data_values = new ArrayList<>();
    Collection<GameData> game_data_values = new ArrayList<>();
    Collection<AuthData> auth_data_values = new ArrayList<>();

    public DataAccess() {
        game_data_values = new ArrayList<>();
    }
    private String generateAuthToken() {
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
    public void CreateUser(UserData userData) {
        user_data_values.add(userData);
    }
    public void CreateAuth(AuthData authData) {
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
    public void clear_thing() {
        user_data_values = new ArrayList<>();
        game_data_values = new ArrayList<>();
        auth_data_values = new ArrayList<>();
    }
    public int add_game(String game_name) {
        int i = game_data_values.size() + 1;
        game_data_values.add(new GameData(i, game_name));
        return i;
    }
    public Collection<GameData> get_all_games() {
        Collection<GameData> all_the_games = new ArrayList<>();
        for (GameData gd: game_data_values) {
            all_the_games.add(gd);
        }
        return all_the_games;
    }
    public boolean join_game_thingy(int game_id, ChessGame.TeamColor game_color, String my_auth_data) {
        for (GameData gd: game_data_values) {
            if (gd.gameID() == game_id) {
                return join_game_thingy(gd, game_color, getAuth(my_auth_data).username());
            }
        }
        return false;
    }
    public boolean join_game_thingy(GameData game_id, ChessGame.TeamColor game_color, String my_person) {
        if (game_color == ChessGame.TeamColor.WHITE) {
            if (!(game_id.whiteUsername() == null)) {
                return false;
            }
            game_id.changeWhiteUsername(my_person);
            return true;
        }
        if (game_color == ChessGame.TeamColor.BLACK) {
            if (!(game_id.blackUsername() == null)) {
                return false;
            }
            game_id.changeBlackUsername(my_person);
            return true;
        }
        return false;
    }
    public boolean check_for_game_existence(int game_id) {
        for (GameData gd: game_data_values) {
            if (gd.gameID() == game_id) {
                return true;
            }
        }
        return false;
    }

}

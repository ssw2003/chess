package server;
import chess.ChessGame;

import java.util.Collection;
import java.util.SortedSet;
import java.util.UUID;

public class Service {
    DataAccess data_access = new DataAccess();
    public Service() {
        data_access = new DataAccess();
    }
    private String generateAuthToken() {
        return UUID.randomUUID().toString();
    }
    public AuthData register(UserData req) {
        if (data_access.getUser(req.username()) == null) {
            data_access.CreateUser(req);
            AuthData the_auth_data = new AuthData(generateAuthToken(), req.username());
            data_access.CreateAuth(the_auth_data);
            return the_auth_data;
        }
        return null;
    }
    public AuthData login(String u, String p) {
        UserData get_my_user = data_access.getUser(u);
        if (get_my_user != null) {
            if (get_my_user.password().equals(p)) {
                AuthData at = new AuthData(generateAuthToken(), u);
                data_access.CreateAuth(at);
                return at;
            }
        }
        return null;
    }
    public boolean logout(String ad) {
        AuthData get_my_user = data_access.getAuth(ad);
        if (get_my_user != null) {
            data_access.deleteAuth(get_my_user);
            return true;
        }
        return false;
    }
    public boolean check_for (String ad) {
        AuthData get_my_user = data_access.getAuth(ad);
        if (get_my_user != null) {
            return true;
        }
        return false;
    }
    public void clear_thing() {
        data_access.clear_thing();
    }
    public int add_game(String game_name) {
        return data_access.add_game(game_name);
    }
    public boolean join_game_thingy(int game_id, ChessGame.TeamColor game_color, String my_auth_data) {
        return data_access.join_game_thingy(game_id, game_color, my_auth_data);
    }
    public boolean check_for_game_existence(int game_id) {
        return data_access.check_for_game_existence(game_id);
    }
    public Collection<GameData> get_all_games() {
        return data_access.get_all_games();
    }
}

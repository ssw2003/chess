package server;
import chess.ChessGame;
import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import dataaccess.DatabaseAccess;

import java.util.ArrayList;
import java.util.Collection;

public class Service {
    DatabaseAccess data_access = new DataAccess();
    public Service() {
        data_access = new DataAccess();
    }
    private String generateAuthToken() {
        try {
            return data_access.generateAuthToken();
        } catch (DataAccessException e) {
            return null;
        }
    }
    public AuthData register(UserData req) {
        try {
            if (data_access.getUser(req.username()) == null) {
                data_access.CreateUser(req);
                AuthData the_auth_data = new AuthData(generateAuthToken(), req.username());
                data_access.CreateAuth(the_auth_data);
                return the_auth_data;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
    public AuthData login(String u, String p) {
        try {
            UserData get_my_user = data_access.getUser(u);
            if (get_my_user != null) {
                if (get_my_user.password().equals(p)) {
                    AuthData at = new AuthData(generateAuthToken(), u);
                    data_access.CreateAuth(at);
                    return at;
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
    public boolean logout(String ad) {
        try {
            AuthData get_my_user = data_access.getAuth(ad);
            if (get_my_user != null) {
                data_access.deleteAuth(get_my_user);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    public boolean check_for (String ad) {
        try {
            AuthData get_my_user = data_access.getAuth(ad);
            if (get_my_user != null) {
                return true;
            }
            return false;
        } catch (DataAccessException e) {
            return false;
        }
    }
    public void clear_thing() {
        try {
            data_access.clear_thing();
        } catch (DataAccessException e) {
            return;
        }
    }
    public int add_game(String game_name) {
        try {
            return data_access.add_game(game_name);
        } catch (DataAccessException e) {
            return 0;
        }
    }
    public boolean join_game_thingy(int game_id, ChessGame.TeamColor game_color, String my_auth_data) {
        try {
            return data_access.join_game_thingy(game_id, game_color, my_auth_data);
        } catch (DataAccessException e) {
            return false;
        }
    }
    public boolean check_for_game_existence(int game_id) {
        try {
            return data_access.check_for_game_existence(game_id);
        } catch (DataAccessException e) {
            return false;
        }
    }
    public Collection<GameData> get_all_games() {
        try {
            return data_access.get_all_games();
        } catch (DataAccessException e) {
            return new ArrayList<>();
        }
    }
}

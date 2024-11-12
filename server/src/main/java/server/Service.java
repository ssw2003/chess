package server;
import chess.ChessGame;
import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import dataaccess.DatabaseAccess;
import dataaccess.SQLThing;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.Collection;

public class Service {
    DatabaseAccess data_access = new DataAccess();
    public Service() {
        try {
            data_access = new SQLThing();
        } catch (Exception s) {
            //System.out.println(s.getMessage());
            String t = s.getMessage();
        }
    }
    private String generateAuthToken() {
        try {
            return data_access.generateAuthToken();
        } catch (DataAccessException e) {
            //System.out.println(e.getMessage());
            return null;
        }
    }
    public AuthData register(UserData req) {
        try {
            if (data_access.getUser(req.username()) == null) {
                data_access.createUser(req);
                AuthData the_auth_data = new AuthData(generateAuthToken(), req.username());
                data_access.createAuth(the_auth_data);
                return the_auth_data;
            }
            return null;
        } catch (Exception e) {
            //System.out.println(e.getMessage());
            return null;
        }
    }
    public AuthData login(String u, String p) {
        try {
            UserData get_my_user = data_access.getUser(u);
            if (get_my_user != null) {
                if (BCrypt.checkpw(p, get_my_user.password())) {
                    AuthData at = new AuthData(generateAuthToken(), u);
                    data_access.createAuth(at);
                    return at;
                }
            }
            return null;
        } catch (Exception e) {
            //System.out.println(e.getMessage());
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
            //System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean checkFor(String ad) {
        try {
            AuthData get_my_user = data_access.getAuth(ad);
            if (get_my_user != null) {
                return true;
            }
            return false;
        } catch (DataAccessException e) {
            //System.out.println(e.getMessage());
            return false;
        }
    }
    public void clearThing() {
        try {
            data_access.clearThing();
        } catch (DataAccessException e) {
            //System.out.println(e.getMessage());
            return;
        }
    }
    public int addGame(String game_name) {
        try {
            return data_access.addGame(game_name);
        } catch (DataAccessException e) {
            //System.out.println(e.getMessage());
            return 0;
        }
    }
    public boolean joinGameThingy(int game_id, ChessGame.TeamColor game_color, String my_auth_data) {
        try {

            return data_access.joinGameThingy(game_id, game_color, my_auth_data);

        } catch (DataAccessException e) {
            //System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean checkForGameExistence(int game_id) {
        try {
            return data_access.checkForGameExistence(game_id);
        } catch (DataAccessException e) {
            //System.out.println(e.getMessage());
            return false;
        }
    }
    public Collection<GameData> getAllGames() {
        try {
            return data_access.getAllGames();
        } catch (DataAccessException e) {
            //System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }
}

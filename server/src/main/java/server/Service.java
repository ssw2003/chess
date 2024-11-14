package server;
import chess.ChessGame;
import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import dataaccess.DatabaseAccess;
import dataaccess.SQLThing;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.Collection;

public class Service {
    DatabaseAccess dataAccess = new DataAccess();
    public Service() {
        try {
            dataAccess = new SQLThing();
        } catch (Exception s) {
            //System.out.println(s.getMessage());
            String t = s.getMessage();
        }
    }
    private String generateAuthToken() {
        try {
            return dataAccess.generateAuthToken();
        } catch (DataAccessException e) {
            //System.out.println(e.getMessage());
            return null;
        }
    }
    public AuthData register(UserData req) {
        try {
            if (dataAccess.getUser(req.username()) == null) {
                dataAccess.createUser(req);
                AuthData theAuthData = new AuthData(generateAuthToken(), req.username());
                dataAccess.createAuth(theAuthData);
                return theAuthData;
            }
            return null;
        } catch (Exception e) {
            //System.out.println(e.getMessage());
            return null;
        }
    }
    public AuthData login(String u, String p) {
        try {
            UserData getMyUser = dataAccess.getUser(u);
            if (getMyUser != null) {
                if (BCrypt.checkpw(p, getMyUser.password())) {
                    AuthData at = new AuthData(generateAuthToken(), u);
                    dataAccess.createAuth(at);
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
            AuthData getMyUser = dataAccess.getAuth(ad);
            if (getMyUser != null) {
                dataAccess.deleteAuth(getMyUser);
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
            AuthData getMyUser = dataAccess.getAuth(ad);
            if (getMyUser != null) {
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
            dataAccess.clearThing();
        } catch (DataAccessException e) {
            //System.out.println(e.getMessage());
            return;
        }
    }
    public int addGame(String gameName) {
        try {
            return dataAccess.addGame(gameName);
        } catch (DataAccessException e) {
            //System.out.println(e.getMessage());
            return 0;
        }
    }
    public boolean joinGameThingy(int gameId, ChessGame.TeamColor gameColor, String myAuthData) {
        try {

            return dataAccess.joinGameThingy(gameId, gameColor, myAuthData);

        } catch (DataAccessException e) {
            //System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean checkForGameExistence(int gameId) {
        try {
            return dataAccess.checkForGameExistence(gameId);
        } catch (DataAccessException e) {
            //System.out.println(e.getMessage());
            return false;
        }
    }
    public Collection<GameData> getAllGames() {
        try {
            return dataAccess.getAllGames();
        } catch (DataAccessException e) {
            //System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }
}

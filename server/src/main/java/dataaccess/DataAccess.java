package dataaccess;

import chess.ChessGame;
import model.AuthData;
import model.GameData;
import server.GameDataSet;
import model.UserData;

import java.util.*;

public class DataAccess implements DatabaseAccess {
    Collection<UserData> userDataValues = new ArrayList<>();
    GameDataSet gameDataValues = new GameDataSet();
    Collection<AuthData> authDataValues = new ArrayList<>();

    public DataAccess() {
        gameDataValues = new GameDataSet();
    }
    public String generateAuthToken() {
        return UUID.randomUUID().toString();
    }
    public UserData getUser(String potentialUsername) {
        for (UserData p: userDataValues) {
            if (p.username().equals(potentialUsername)) {
                return p;
            }
        }
        return null;
    }
    public void createUser(UserData userData) {
        userDataValues.add(userData);
    }
    public void createAuth(AuthData authData) {
        authDataValues.add(authData);
    }
    public AuthData getAuth(String authToken) {
        for (AuthData p: authDataValues) {
            if (p.authToken().equals(authToken)) {
                return p;
            }
        }
        return null;
    }
    public void deleteAuth(AuthData ad) {
        authDataValues.remove(ad);
    }
    public void clearThing() {
        userDataValues = new ArrayList<>();
        gameDataValues = new GameDataSet();
        authDataValues = new ArrayList<>();
    }
    public int addGame(String gameName) {
        int i = gameDataValues.mySize() + 1;
        gameDataValues.addGame(new GameData(i, null, null, gameName, null));
        return i;
    }
    public Collection<GameData> getAllGames() {
        Collection<GameData> allMyGames = new ArrayList<>();
        for (int i = 1; i < 1 + gameDataValues.mySize(); i = i + 1) {
            allMyGames.add(gameDataValues.getGame(i));
        }
        return allMyGames;
    }
    public boolean joinGameThingy(int gameId, ChessGame.TeamColor gameColor, String myAuthData) {
        if (!checkForGameExistence(gameId)) {
            return false;
        }
        GameData gd = gameDataValues.getGame(gameId);
        if (gameColor == ChessGame.TeamColor.WHITE) {
            if (gd.whiteUsername() != null) {
                return false;
            }
            gd = new GameData(gd.gameID(), getAuth(myAuthData).username(), gd.blackUsername(), gd.gameName(), gd.game());
        }
        if (gameColor == ChessGame.TeamColor.BLACK) {
            if (gd.blackUsername() != null) {
                return false;
            }
            gd = new GameData(gd.gameID(), gd.whiteUsername(), getAuth(myAuthData).username(), gd.gameName(), gd.game());
        }
        gameDataValues.changeGame(gameId, gd);
        return true;
    }

    public boolean checkForGameExistence(int gameId) {
        if (gameId < 1 || gameId > gameDataValues.mySize()) {
            return false;
        }
        return true;
    }

    @Override
    public void updateGame(int gameId, ChessGame cg) {
        gameDataValues.updateGame(gameId, cg);
    }

}

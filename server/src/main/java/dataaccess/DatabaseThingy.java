package dataaccess;

import model.AuthData;
import model.GameData;
import model.UserData;

import java.util.ArrayList;
import java.util.Collection;

public class DatabaseThingy {
    private Collection<AuthData> auths;
    private Collection<GameData> games;
    private Collection<UserData> users;
    public DatabaseThingy() {
        auths = new ArrayList<>();
        games = new ArrayList<>();
        users = new ArrayList<>();
    }
    public void addAuth(AuthData aD) {
        auths.add(new AuthData(aD.authToken(), aD.username()));
    }
    public void addGame(GameData gD) {
        games.add(new GameData(gD.gameID(), gD.whiteUsername(), gD.blackUsername(), gD.gameName(), gD.game().clone()));
    }
    public boolean addUser(UserData uD, String aM, boolean b) {
        for (AuthData a: auths) {
            if (a.username().equals(uD.username())) {
                return false;
            }
        }
        auths.add(new AuthData(aM, uD.username()));
        for (UserData u: users) {
            if (u.username().equals(uD.username())) {
                return false;
            }
        }
        if (!b) {
            return false;
        }
        users.add(new UserData(uD.username(), uD.password(), uD.email()));
        return true;
    }
}

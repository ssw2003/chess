package dataaccess;

import chess.ChessGame;
import model.GameData;
import model.GameDataWithout;
import model.UserData;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class DatabaseClass implements DatabaseThingy {
    public DatabaseClass() throws DataAccessException {
        tableStarter();
    }
    @Override
    public int addGame(String gD) {
        try (var cn = DatabaseManager.getConnection()) {
            var sqlAsk = "INSERT INTO games (gameName) VALUES (?)";
            try (var pS = cn.prepareStatement(sqlAsk)) {
                var i = pS.executeUpdate(gD);
                return i;
            }
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public boolean addUser(UserData uD, String aM, boolean b) {
        if (!b) {
            boolean c = false;
            for (UserData v: users) {
                if (v.username().equals(uD.username()) && v.password().equals(uD.password())) {
                    c = true;
                }
            }
            if (!c) {
                return true;
            }
            auths.add(new AuthData(aM, uD.username()));
            return false;
        }
        for (UserData u: users) {
            if (u.username().equals(uD.username())) {
                return false;
            }
        }
        auths.add(new AuthData(aM, uD.username()));
        users.add(new UserData(uD.username(), uD.password(), uD.email()));
        return true;
    }

    @Override
    public boolean logout(String authrztn) {
        boolean whatToReturn = true;
        if (true) {
            for (AuthData aD: auths) {
                if (aD.authToken().equals(authrztn)) {
                    whatToReturn = false;
                }
            }
        }
        Collection<AuthData> authDataCollection = new ArrayList<>();

        if (!whatToReturn) {

            for (AuthData aD: auths) {
                if (!aD.authToken().equals(authrztn)) {
                    authDataCollection.add(new AuthData(aD.authToken(), aD.username()));
                }
            }
        }
        if (!whatToReturn) {
            auths = new ArrayList<>();
            for (AuthData aD: authDataCollection) {
                auths.add(new AuthData(aD.authToken(), aD.username()));
            }
        }
        return whatToReturn;
    }

    @Override
    public void clearThingy() {

    }

    @Override
    public boolean isAuthorized(String authy) {
        return false;
    }

    @Override
    public Collection<GameDataWithout> getGames() {
        return List.of();
    }

    @Override
    public boolean joinGame(int ident, boolean isWhite, String authrztn) {
        return false;
    }
    private final String[] init = {
        """
        CREATE TABLE  IF NOT EXISTS auths (
        authToken VARCHAR(256) NOT NULL,
        username VARCHAR(256) NOT NULL
        )""", """
        CREATE TABLE  IF NOT EXISTS games (
        gameID INT AUTO_INCREMENT,
        whiteUsername VARCHAR(256),
        blackUsername VARCHAR(256),
        gameName VARCHAR(256),
        game TEXT,
        )""", """
        CREATE TABLE  IF NOT EXISTS users (
        username VARCHAR(256),
        password VARCHAR(256),
        email VARCHAR(256)
        """
    };
    private void tableStarter() throws DataAccessException {
        DatabaseManager.createDatabase();
        boolean needsThrowing = false;
        try (var cn = DatabaseManager.getConnection()) {
            for (var ask: init) {
                try (var pS = cn.prepareStatement(ask)) {
                    pS.executeUpdate();
                }
            }
        } catch (SQLException e) {
            needsThrowing = true;
        }
        if (needsThrowing) {
            throw new DataAccessException("");
        }
    }
}

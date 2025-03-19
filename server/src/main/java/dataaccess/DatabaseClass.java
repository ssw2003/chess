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
            var sqlAsk = "INSERT INTO games gameName VALUES " + gD;
            try (var pS = cn.prepareStatement(sqlAsk)) {
                var i = pS.executeUpdate();
                return i;
            }
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public boolean addUser(UserData uD, String aM, boolean b) {
//        if (!b) {
//            boolean c = false;
//            for (UserData v: users) {
//                if (v.username().equals(uD.username()) && v.password().equals(uD.password())) {
//                    c = true;
//                }
//            }
//            if (!c) {
//                return true;
//            }
//            auths.add(new AuthData(aM, uD.username()));
//            return false;
//        }
//        for (UserData u: users) {
//            if (u.username().equals(uD.username())) {
//                return false;
//            }
//        }
//        auths.add(new AuthData(aM, uD.username()));
//        users.add(new UserData(uD.username(), uD.password(), uD.email()));
        return true;
    }

    @Override
    public boolean logout(String authrztn) {
        try (var cn = DatabaseManager.getConnection()) {
            var sqlAsk = "DELETE FROM auths WHERE authToken = " + authrztn;
            try (var pS = cn.prepareStatement(sqlAsk)) {
                pS.executeUpdate();
            }
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    @Override
    public void clearThingy() {
        String stri = "stri";
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
        CREATE TABLE IF NOT EXISTS auths (
        authToken VARCHAR(256) NOT NULL,
        username VARCHAR(256) NOT NULL
        )""", """
        CREATE TABLE IF NOT EXISTS games (
        gameID INT AUTO_INCREMENT,
        whiteUsername VARCHAR(256),
        blackUsername VARCHAR(256),
        gameName VARCHAR(256) NOT NULL,
        game TEXT,
        PRIMARY KEY (gameID)
        )""", """
        CREATE TABLE IF NOT EXISTS users (
        username VARCHAR(256) NOT NULL,
        password VARCHAR(256) NOT NULL,
        email VARCHAR(256) NOT NULL
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

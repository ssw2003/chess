package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;
import model.GameDataWithout;
import model.UserData;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DatabaseClass implements DatabaseThingy {
    public DatabaseClass() {
        try {
            tableStarter();
        } catch (DataAccessException e) {}
    }
    @Override
    public int addGame(String gD) {
        try (var cn = DatabaseManager.getConnection()) {
            var sqlAsk = "INSERT INTO games (whiteUsername, blackUsername, gameName, game) VALUES (?, ?, ?, ?)";
            try (var pS = cn.prepareStatement(sqlAsk)) {
                pS.setString(1, null);
                pS.setString(2, null);
                pS.setString(3, gD);
                var gson = new Gson();
                pS.setString(4, gson.toJson(new ChessGame()));
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
//
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
            var sqlAsk = "DELETE FROM auths WHERE authToken = ?";
            try (var pS = cn.prepareStatement(sqlAsk)) {
                pS.setString(1, authrztn);
                pS.executeUpdate();
            }
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    @Override
    public void clearThingy() {
        try (var cn = DatabaseManager.getConnection()) {
            var sqlAsk = "TRUNCATE TABLE auths";
            try (var pS = cn.prepareStatement(sqlAsk)) {}
            sqlAsk = "TRUNCATE TABLE games";
            try (var pS = cn.prepareStatement(sqlAsk)) {}
            sqlAsk = "TRUNCATE TABLE users";
            try (var pS = cn.prepareStatement(sqlAsk)) {}
        } catch (Exception e) {}
    }

    @Override
    public boolean isAuthorized(String authy) {
        boolean isAuthorized = false;
        try (var cn = DatabaseManager.getConnection()) {
            var sqlAsk = "SELECT * FROM auths";
            try (var pS = cn.prepareStatement(sqlAsk)) {
                var i = pS.executeQuery();
                while (i.next()) {
                    isAuthorized = isAuthorized || (authy.equals(i.getString("authToken")));
                }
            }
        } catch (Exception e) {
            return false;
        }
        return isAuthorized;
    }

    @Override
    public Collection<GameDataWithout> getGames() {
        Collection<GameDataWithout> gDW = new ArrayList<>();
        try (var cn = DatabaseManager.getConnection()) {
            var sqlAsk = "SELECT * FROM games";
            try (var pS = cn.prepareStatement(sqlAsk)) {
                var i = pS.executeQuery();
                while (i.next()) {
                    int gID = i.getInt("gameID");
                    gDW.add(new GameDataWithout(gID, i.getString("whiteUsername"), i.getString("blackUsername"), i.getString("gameName")));
                }
                return gDW;
            }
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean joinGame(int ident, boolean isWhite, String authrztn) {
        String usnm = retrieveUsn(authrztn);
        if (usnm == null) {
            return false;
        }
        GameDataWithout gDW = null;
        try (var cn = DatabaseManager.getConnection()) {
            var sqlAsk = "SELECT * FROM games WHERE gameID = ?";
            try (var pS = cn.prepareStatement(sqlAsk)) {
                pS.setInt(1, ident);
                try (var i = pS.executeQuery()) {
                    String wt = i.getString("whiteUsername");
                    String bk = i.getString("blackUsername");
                    gDW = new GameDataWithout(ident, wt, bk, i.getString("gameName"));
                }
            }
        } catch (Exception e) {}
        if (gDW == null) {
            return false;
        }
        if (isWhite) {
            if (gDW.whiteUsername() != null) {
                return false;
            }
        }
        else {
            if (gDW.blackUsername() != null) {
                return false;
            }
        }
        try (var cn = DatabaseManager.getConnection()) {
            var sqlAsk = "UPDATE games SET blackUsername WHERE gameID = ?";
            if (isWhite) {
                sqlAsk = "UPDATE games SET whiteUsername WHERE gameID = ?";
            }
            try (var pS = cn.prepareStatement(sqlAsk)) {
                pS.setString(1, usnm);
                pS.executeUpdate();
            }
        } catch (Exception e) {}
        return true;
    }

    @Override
    public String retrievePsw(String usn) {
        try (var cn = DatabaseManager.getConnection()) {
            var sqlAsk = "SELECT password FROM users WHERE username = ?";
            try (var pS = cn.prepareStatement(sqlAsk)) {
                pS.setString(1, usn);
                try (var i = pS.executeQuery()) {
                    return i.getString("password");
                }
            }
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String retrieveUsn(String authToken) {
        try (var cn = DatabaseManager.getConnection()) {
            var sqlAsk = "SELECT username FROM auths WHERE authToken = ?";
            try (var pS = cn.prepareStatement(sqlAsk)) {
                pS.setString(1, authToken);
                try (var i = pS.executeQuery()) {
                    return i.getString("username");
                }
            }
        } catch (Exception e) {
            return null;
        }
    }

    private final String[] init = {
        """
        CREATE TABLE IF NOT EXISTS auths (
        authToken VARCHAR(256) NOT NULL,
        username VARCHAR(256) NOT NULL
        )""", """
        CREATE TABLE IF NOT EXISTS games (
        gameID INT NOT NULL AUTO_INCREMENT,
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

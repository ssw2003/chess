package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;
import model.GameDataWithout;
import model.UserData;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DatabaseClass implements DatabaseThingy {
    public DatabaseClass() {
        try {
            tableStarter();
        } catch (DataAccessException e) {
            String s = "s";
        }
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
                pS.executeUpdate();
                //var i = pS.executeUpdate();
                //return i;
            }
            sqlAsk = "SELECT gameID FROM games";
            int hgh = 0;
            try (var pS = cn.prepareStatement(sqlAsk)) {
                var i = pS.executeQuery();
                while (i.next()) {
                    hgh = goUp(i.getInt("gameID"), hgh);
                }
            }
            return hgh;
        } catch (Exception e) {
            return 0;
        }
    }

    private int goUp(int a, int b) {
        if (a > b) {
            return a;
        }
        return b;
    }

    @Override
    public boolean addUser(String usn, String psw, String eml, String aM) {
        boolean userIsIn = false;
        try (var cn = DatabaseManager.getConnection()) {
            var sqlAsk = "SELECT * FROM users WHERE username = ?";
            try (var pS = cn.prepareStatement(sqlAsk)) {
                pS.setString(1, usn);
                var i = pS.executeQuery();
                while (i.next()) {
                    userIsIn = true;
                }
            }
            if (userIsIn) {
                throw new DataAccessException("");
            }
        } catch (Exception e) {
            return false;
        }
        try (var cn = DatabaseManager.getConnection()) {
            var sqlAsk = "INSERT INTO auths (authToken, username) VALUES (?, ?)";
            try (var pS = cn.prepareStatement(sqlAsk)) {
                pS.setString(1, aM);
                pS.setString(2, usn);
                pS.executeUpdate();
            }
            sqlAsk = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
            try (var pS = cn.prepareStatement(sqlAsk)) {
                pS.setString(1, usn);
                pS.setString(2, psw);
                pS.setString(3, eml);
                pS.executeUpdate();
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean logout(String authrztn) {
        try (var cn = DatabaseManager.getConnection()) {
            boolean foundIt = false;
            var sqlAsk = "SELECT * FROM auths WHERE authToken = ?";
            try (var pS = cn.prepareStatement(sqlAsk)) {
                pS.setString(1, authrztn);
                var i = pS.executeQuery();
                while (i.next()) {
                    foundIt = true;
                }
            }
            if (!foundIt) {
                throw new DataAccessException("");
            }
            sqlAsk = "DELETE FROM auths WHERE authToken = ?";
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
            try (var pS = cn.prepareStatement(sqlAsk)) {
                pS.executeUpdate();
            }
            sqlAsk = "TRUNCATE TABLE games";
            try (var pS = cn.prepareStatement(sqlAsk)) {
                pS.executeUpdate();
            }
            sqlAsk = "TRUNCATE TABLE users";
            try (var pS = cn.prepareStatement(sqlAsk)) {
                pS.executeUpdate();
            }
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
                    i.next();
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
            var sqlAsk = "UPDATE games SET blackUsername = ? WHERE gameID = ?";
            if (isWhite) {
                sqlAsk = "UPDATE games SET whiteUsername = ? WHERE gameID = ?";
            }
            try (var pS = cn.prepareStatement(sqlAsk)) {
                pS.setString(1, usnm);
                pS.setInt(2, gDW.gameID());
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
                    i.next();
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
                    i.next();
                    return i.getString("username");
                }
            }
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean logUser(String usn, String psw, String aM) {
        if (!psw.equals(retrievePsw(usn))) {
            return false;
        }
        try (var cn = DatabaseManager.getConnection()) {
            var sqlAsk = "INSERT INTO auths (authToken, username) VALUES (?, ?)";
            try (var pS = cn.prepareStatement(sqlAsk)) {
                pS.setString(1, aM);
                pS.setString(2, usn);
                pS.executeUpdate();
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    private final String[] init = {"""
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
        )"""
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

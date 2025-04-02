package dataaccess;

import chess.ChessGame;
import chess.ChessMove;
import chess.InvalidMoveException;
import com.google.gson.Gson;
import model.GameData;
import model.GameDataWithout;
import model.InfoJoinExt;
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
    public Collection<GameData> getGames() {
        Collection<GameData> gDW = new ArrayList<>();
        try (var cn = DatabaseManager.getConnection()) {
            var sqlAsk = "SELECT * FROM games";
            try (var pS = cn.prepareStatement(sqlAsk)) {
                var i = pS.executeQuery();
                while (i.next()) {
                    int gID = i.getInt("gameID");
                    var cga = i.getString("game");
                    var gs = new Gson();
                    ChessGame cGM = gs.fromJson(cga, ChessGame.class);
                    gDW.add(new GameData(gID, i.getString("whiteUsername"), i.getString("blackUsername"), i.getString("gameName"), cGM.clone()));
                }
                return gDW;
            }
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean joinGame(int ident, boolean isWhite, String authrztn, InfoJoinExt iJE) {
        if (iJE.cmd() == 1) {
            if (isWhite) {
                return lvGmWh(ident, authrztn);
            }
            return lvGmBk(ident, authrztn);
        }
        if (iJE.cmd() == 2) {
            return rsGm(ident, isWhite, authrztn);
        }
        if (iJE.cmd() == 3) {
            return mkMv(ident, isWhite, authrztn, iJE.mv());
        }
        String usnm = authrztn;
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

    private boolean mkMv(int ident, boolean isWhite, String authrztn, ChessMove mv) {
        ChessGame gm = null;
        String usn = null;
        String busn = "blackUsername";
        ChessGame.TeamColor tU = ChessGame.TeamColor.BLACK;
        if (isWhite) {
            busn = "whiteUsername";
            tU = ChessGame.TeamColor.WHITE;
        }
        try (var cn = DatabaseManager.getConnection()) {
            var sqlAsk = "SELECT * FROM games WHERE gameID = ?";
            try (var pS = cn.prepareStatement(sqlAsk)) {
                pS.setInt(1, ident);
                try (var i = pS.executeQuery()) {
                    i.next();
                    ChessGame cga = new Gson().fromJson(i.getString("game"), ChessGame.class);
                    usn = i.getString(busn);
                    gm = cga.clone();
                }
            }
        } catch (Exception e) {}
        if (gm == null || mv == null || usn == null) {
            return false;
        }
        if ((!usn.equals(authrztn)) || tU != gm.getTeamTurn()) {
            return false;
        }
        try {
            gm.makeMove(mv);
        } catch (InvalidMoveException e) {
            return false;
        }
        try (var cn = DatabaseManager.getConnection()) {
            var sqlAsk = "UPDATE games SET game = ? WHERE gameID = ?";
            try (var pS = cn.prepareStatement(sqlAsk)) {
                ChessGame ga = gm.clone();
                pS.setString(1, new Gson().toJson(ga.clone()));
                pS.setInt(2, ident);
                pS.executeUpdate();
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private boolean rsGm(int ident, boolean isWhite, String authrztn) {
        ChessGame gm = null;
        String usnw = null;
        String busn = null;
        try (var cn = DatabaseManager.getConnection()) {
            var sqlAsk = "SELECT * FROM games WHERE gameID = ?";
            try (var pS = cn.prepareStatement(sqlAsk)) {
                pS.setInt(1, ident);
                try (var i = pS.executeQuery()) {
                    i.next();
                    ChessGame cga = new Gson().fromJson(i.getString("game"), ChessGame.class);
                    usnw = i.getString("whiteUsername");
                    busn = i.getString("blackUsername");
                    gm = cga.clone();
                }
            }
        } catch (Exception e) {}
        if (isWhite) {
            if (usnw == null) {
                return false;
            }
            else if (!usnw.equals(authrztn)) {
                return false;
            }
        }
        if (gm == null) {
            return false;
        }
        if (!isWhite) {
            if (busn == null) {
                return false;
            }
            else if (!busn.equals(authrztn)) {
                return false;
            }
        }
        ChessGame.TeamColor tC = ChessGame.TeamColor.BLACK;
        if (isWhite) {
            tC = ChessGame.TeamColor.WHITE;
        }
        try {
            gm.attemptResign(tC);
        } catch (InvalidMoveException e) {
            return false;
        }
        try (var cn = DatabaseManager.getConnection()) {
            var sqlAsk = "UPDATE games SET game = ? WHERE gameID = ?";
            try (var pS = cn.prepareStatement(sqlAsk)) {
                ChessGame ga = gm.clone();
                pS.setString(1, new Gson().toJson(ga));
                pS.setInt(2, ident);
                pS.executeUpdate();
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private boolean lvGmBk(int ident, String authrztn) {
        if (authrztn == null) {
            return false;
        }
        String bk = null;
        try (var cn = DatabaseManager.getConnection()) {
            var sqlAsk = "SELECT blackUsername FROM games WHERE gameID = ?";
            try (var pS = cn.prepareStatement(sqlAsk)) {
                pS.setInt(1, ident);
                try (var i = pS.executeQuery()) {
                    i.next();
                    bk = i.getString("blackUsername");
                }
            }
        } catch (Exception e) {}
        if (bk == null) {
            return false;
        }
        if (!bk.equals(authrztn)) {
            return false;
        }
        try (var cn = DatabaseManager.getConnection()) {
            var sqlAsk = "UPDATE games SET blackUsername = ? WHERE gameID = ?";
            try (var pS = cn.prepareStatement(sqlAsk)) {
                pS.setString(1, null);
                pS.setInt(2, ident);
                pS.executeUpdate();
            }
        } catch (Exception e) {}
        return true;
    }

    private boolean lvGmWh(int ident, String authrztn) {
        if (authrztn == null) {
            return false;
        }
        String wt = null;
        try (var cn = DatabaseManager.getConnection()) {
            try (var pS = cn.prepareStatement("SELECT * FROM games WHERE gameID = ?")) {
                pS.setInt(1, ident);
                try (var i = pS.executeQuery()) {
                    i.next();
                    wt = i.getString("whiteUsername");
                }
            }
        } catch (Exception e) {}
        if (wt == null) {
            return false;
        }
        if (!wt.equals(authrztn)) {
            return false;
        }
        try (var cn = DatabaseManager.getConnection()) {
            try (var pS = cn.prepareStatement("UPDATE games SET whiteUsername = ? WHERE gameID = ?")) {
                pS.setString(1, null);
                pS.setInt(2, ident);
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
        if (authToken == null) {
            return null;
        }
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

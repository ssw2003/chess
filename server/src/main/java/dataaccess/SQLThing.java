package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import org.mindrot.jbcrypt.BCrypt;
import server.AuthData;
import server.GameData;
import server.UserData;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class SQLThing implements DatabaseAccess {
    public SQLThing() throws  DataAccessException {
        startDatabase();
    }
    private void startDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (var potentialConnection = DatabaseManager.getConnection()) {
            for (var s: UserDataTable) {
                try (var ps = DatabaseManager.getConnection().prepareStatement(s)) {
                    ps.executeUpdate();
                }
            }
            for (var u: AuthDataTable) {
                try (var ps = DatabaseManager.getConnection().prepareStatement(u)) {
                    ps.executeUpdate();
                }
            }
            for (var t: GameDataTable) {
                try (var ps = DatabaseManager.getConnection().prepareStatement(t)) {
                    ps.executeUpdate();
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
    }
    private final String[] UserDataTable = {
            """
                create table if not exists user_data (
                `username` varchar(256) not null primary key,
                `password` varchar(256) not null,
                `email` varchar(256) not null
            )"""
    };
    private final String[] AuthDataTable = {
            """
                create table if not exists auth_data (
                `auth_token` varchar(256) not null primary key,
                `username` varchar(256) not null
            )"""
    };
    private final String[] GameDataTable = {
            """
                create table if not exists game_data (
                `game_id` int not null primary key auto_increment,
                `white_username` varchar(256),
                `black_username` varchar(256),
                `game_name` varchar(256) not null,
                `game` longtext
            )"""
    };

    @Override
    public String generateAuthToken() throws DataAccessException {
        return UUID.randomUUID().toString();
    }

    @Override
    public UserData getUser(String potentialUsername) throws DataAccessException {
        try (var cn = DatabaseManager.getConnection();
        var ps = cn.prepareStatement("SELECT username, password, email FROM user_data WHERE username=?")) {
            ps.setString(1, potentialUsername);
            try (var v = ps.executeQuery()) {
                while (v.next()) {
                    var un = v.getString("username");
                    var pw = v.getString("password");
                    var em = v.getString("email");
                    return new UserData(un, pw, em);
                }
            }
        } catch (SQLException dae) {
            return null;
        }
        return null;
    }

    @Override
    public void createUser(UserData userData) throws DataAccessException {
        try (var cn = DatabaseManager.getConnection();
        var ps = cn.prepareStatement("INSERT INTO user_data (username, password, email) VALUES(?, ?, ?)")) {
            ps.setString(1, userData.username());
            ps.setString(2, BCrypt.hashpw(userData.password(), BCrypt.gensalt()));
            ps.setString(3, userData.email());
            ps.executeUpdate();
        } catch (SQLException s) {
            throw new DataAccessException(s.getMessage());
        }
    }

    @Override
    public void createAuth(AuthData authData) throws DataAccessException {
        try (var cn = DatabaseManager.getConnection();
             var ps = cn.prepareStatement("INSERT INTO auth_data (auth_token, username) VALUES(?, ?)")) {
            ps.setString(1, authData.authToken());
            ps.setString(2, authData.username());
            ps.executeUpdate();
        } catch (SQLException s) {
            throw new DataAccessException(s.getMessage());
        }
    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        try (var cn = DatabaseManager.getConnection();
             var ps = cn.prepareStatement("SELECT auth_token, username FROM auth_data WHERE auth_token=?")) {
            ps.setString(1, authToken);
            try (var v = ps.executeQuery()) {
                if (v.next()) {
                    var at = v.getString("auth_token");
                    var un = v.getString("username");
                    return new AuthData(at, un);
                }
                return null;
            }
        } catch (SQLException dae) {
            throw new DataAccessException(dae.getMessage());
        }
    }

    @Override
    public void deleteAuth(AuthData ad) throws DataAccessException {
        try (var cn = DatabaseManager.getConnection();
             var ps = cn.prepareStatement("DELETE FROM auth_data WHERE auth_token=? AND username=?")) {
            ps.setString(1, ad.authToken());
            ps.setString(2, ad.username());
            ps.executeUpdate();
        } catch (SQLException dae) {
            throw new DataAccessException(dae.getMessage());
        }
    }

    @Override
    public void clearThing() throws DataAccessException {
        try (var cn = DatabaseManager.getConnection();
             var ps = cn.prepareStatement("TRUNCATE auth_data")) {
            ps.executeUpdate();
        } catch (SQLException dae) {
            throw new DataAccessException(dae.getMessage());
        }
        try (var cn = DatabaseManager.getConnection();
             var ps = cn.prepareStatement("TRUNCATE user_data")) {
            ps.executeUpdate();
        } catch (SQLException dae) {
            throw new DataAccessException(dae.getMessage());
        }
        try (var cn = DatabaseManager.getConnection();
             var ps = cn.prepareStatement("TRUNCATE game_data")) {
            ps.executeUpdate();
        } catch (SQLException dae) {
            throw new DataAccessException(dae.getMessage());
        }
    }

    @Override
    public int addGame(String gameName) throws DataAccessException {

        try (var cn = DatabaseManager.getConnection();
             var ps = cn.prepareStatement("INSERT INTO game_data (game_name) VALUES (?)", Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, gameName);
            ps.executeUpdate();
            var j = ps.getGeneratedKeys();
            var i = 0;
            if (j.next()) {
                i = j.getInt(1);
            }
            return i;
        } catch (SQLException dae) {
            throw new DataAccessException(dae.getMessage());
        }
    }

    @Override
    public Collection<GameData> getAllGames() throws DataAccessException {
        var allGames = new ArrayList<GameData>();
        try (var cn = DatabaseManager.getConnection();
             var ps = cn.prepareStatement("SELECT game_id, white_username, black_username, game_name, game FROM game_data")) {
            try (var v = ps.executeQuery()) {
                while (v.next()) {
                    var gid = v.getInt("game_id");
                    var wu = v.getString("white_username");
                    var bu = v.getString("black_username");
                    var gn = v.getString("game_name");
                    var g = v.getString("game");
                    var gameRequest = new Gson().fromJson(g, ChessGame.class);
                    allGames.add(new GameData(gid, wu, bu, gn, gameRequest));
                }
            }
        } catch (SQLException dae) {
            throw new DataAccessException(dae.getMessage());
        }
        return allGames;
    }

    @Override
    public boolean joinGameThingy(int gameId, ChessGame.TeamColor gameColor, String myAuthData) throws DataAccessException {
        try {
            if (getAuth(myAuthData) == null) {
                return false;
            }
        } catch (DataAccessException s){
            throw new DataAccessException(s.getMessage());
        }

        if (gameColor == ChessGame.TeamColor.WHITE) {
            try (var cn = DatabaseManager.getConnection();
                 var ps = cn.prepareStatement("SELECT white_username FROM game_data WHERE game_id=?")) {
                ps.setInt(1, gameId);
                try (var v = ps.executeQuery()) {
                    if (v.next()) {
                        if (v.getString("white_username") != null) {
                            return false;
                        }
                    }
                }
            } catch (SQLException dae) {
                throw new DataAccessException("Bad");
            }
            try (var cn = DatabaseManager.getConnection();
                 var ps = cn.prepareStatement("UPDATE game_data SET white_username=? WHERE game_id=?")) {
                ps.setString(1, getAuth(myAuthData).username());
                ps.setInt(2, gameId);
                ps.executeUpdate();
                return true;
            } catch (SQLException dae) {
                throw new DataAccessException(dae.getMessage());
            }
        }
        else {
            try (var cn = DatabaseManager.getConnection();
                 var ps = cn.prepareStatement("SELECT black_username FROM game_data WHERE game_id=?")) {
                ps.setInt(1, gameId);
                try (var v = ps.executeQuery()) {
                    if (v.next()) {
                        if (v.getString("black_username") != null) {
                            return false;
                        }
                    }
                }
            } catch (SQLException dae) {
                throw new DataAccessException(dae.getMessage());
            }
            try (var cn = DatabaseManager.getConnection();
                 var ps = cn.prepareStatement("UPDATE game_data SET black_username=? WHERE game_id=?")) {
                ps.setString(1, getAuth(myAuthData).username());
                ps.setInt(2, gameId);
                ps.executeUpdate();
                return true;
            } catch (SQLException dae) {
                throw new DataAccessException(dae.getMessage());
            }
        }
    }

    @Override
    public boolean checkForGameExistence(int gameId) throws DataAccessException {
        try (var cn = DatabaseManager.getConnection();
             var ps = cn.prepareStatement("SELECT game_id FROM game_data WHERE game_id=?")) {
            ps.setInt(1, gameId);
            try (var v = ps.executeQuery()) {
                if (v.next()) {
                    return true;
                }
                return false;
            }
        } catch (SQLException dae) {
            throw new DataAccessException(dae.getMessage());
        }
    }
}

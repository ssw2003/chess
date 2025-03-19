package dataaccess;

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
        return 0;
    }

    @Override
    public boolean addUser(UserData uD, String aM, boolean b) {
        return false;
    }

    @Override
    public boolean logout(String authrztn) {
        return false;
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

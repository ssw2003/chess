package dataaccess;

import chess.ChessGame;
import model.AuthData;
import model.GameData;
import model.GameDataWithout;
import model.UserData;

import java.util.ArrayList;
import java.util.Collection;

public interface DatabaseThingy {
    public int addGame(String gD);
    public boolean addUser(UserData uD, String aM, boolean b);
    public boolean logout(String authrztn);
    public void clearThingy();
    public boolean isAuthorized(String authy);
    public Collection<GameDataWithout> getGames();
    public boolean joinGame(int ident, boolean isWhite, String authrztn);
    public String retrievePsw(String usn);
}
//package dataaccess;
//
//import chess.ChessGame;
//import model.AuthData;
//import model.GameData;
//import model.GameDataWithout;
//import model.UserData;
//
//import java.util.ArrayList;
//import java.util.Collection;
//
//public interface DatabaseThingy {
//    private Collection<AuthData> auths;
//    private Collection<GameData> games;
//    private Collection<UserData> users;
//    public DatabaseThingy() {
//        auths = new ArrayList<>();
//        games = new ArrayList<>();
//        users = new ArrayList<>();
//    }
//    public int addGame(String gD) {
//        games.add(new GameData(games.size() + 1, null, null, gD, new ChessGame()));
//        return games.size();
//    }
//    public boolean addUser(UserData uD, String aM, boolean b) {
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
//        return true;
//    }
//
//    public boolean logout(String authrztn) {
//        boolean whatToReturn = true;
//        if (true) {
//            for (AuthData aD: auths) {
//                if (aD.authToken().equals(authrztn)) {
//                    whatToReturn = false;
//                }
//            }
//        }
//        Collection<AuthData> authDataCollection = new ArrayList<>();
//
//        if (!whatToReturn) {
//
//            for (AuthData aD: auths) {
//                if (!aD.authToken().equals(authrztn)) {
//                    authDataCollection.add(new AuthData(aD.authToken(), aD.username()));
//                }
//            }
//        }
//        if (!whatToReturn) {
//            auths = new ArrayList<>();
//            for (AuthData aD: authDataCollection) {
//                auths.add(new AuthData(aD.authToken(), aD.username()));
//            }
//        }
//        return whatToReturn;
//    }
//
//    public void clearThingy() {
//        auths = new ArrayList<>();
//        games = new ArrayList<>();
//        users = new ArrayList<>();
//    }
//
//    public boolean isAuthorized(String authy) {
//        for (AuthData aD: auths) {
//            if (aD.authToken().equals(authy)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public Collection<GameDataWithout> getGames() {
//        Collection<GameDataWithout> cGD = new ArrayList<>();
//        for (GameData i: games) {
//            cGD.add(new GameDataWithout(i.gameID(), i.whiteUsername(), i.blackUsername(), i.gameName()));
//        }
//        return cGD;
//    }
//    public boolean joinGame(int ident, boolean isWhite, String authrztn) {
//        String usnm = null;
//        for (AuthData aD: auths) {
//            if (aD.authToken().equals(authrztn)) {
//                usnm = aD.username();
//            }
//        }
//        if (usnm == null || ident <= 0 || ident > games.size()) {
//            return false;
//        }
//        Collection<GameData> cGD = new ArrayList<>();
//        for (GameData gaD: games) {
//            if (gaD.gameID() != ident) {
//                cGD.add(new GameData(gaD.gameID(), gaD.whiteUsername(), gaD.blackUsername(), gaD.gameName(), gaD.game()));
//            } else {
//                if ((gaD.whiteUsername() != null) && isWhite) {
//                    return false;
//                }
//                if ((gaD.blackUsername() != null) && (!isWhite)) {
//                    return false;
//                }
//                if (isWhite) {
//                    cGD.add(new GameData(gaD.gameID(), usnm, gaD.blackUsername(), gaD.gameName(), gaD.game()));
//                } else {
//                    cGD.add(new GameData(gaD.gameID(), gaD.whiteUsername(), usnm, gaD.gameName(), gaD.game()));
//                }
//            }
//        }
//        games = new ArrayList<>();
//        String m;
//        for (GameData data: cGD) {
//            games.add(new GameData(data.gameID(), data.whiteUsername(), data.blackUsername(), data.gameName(), data.game()));
//        }
//        return true;
//    }
//}
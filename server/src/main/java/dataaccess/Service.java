package dataaccess;

import chess.ChessMove;
import chess.ChessPosition;
import model.GameData;
import model.GameDataWithout;
import model.InfoJoinExt;
import model.UserData;

import java.util.Collection;
import java.util.UUID;

public class Service {
    private DatabaseClass dT;
    public Service() {
        dT = new DatabaseClass();
    }



    public String regUsr(String usn, String psw, String eml, boolean b) throws DataAccessException {
        String authMy = UUID.randomUUID().toString();
        String aM = "";
        aM = aM + authMy;
        if (b) {
            if (dT.addUser(usn, psw, eml, aM)) {
                return aM;
            }
            throw new DataAccessException("");
        }
        if (dT.logUser(usn, psw, aM)) {
            return aM;
        }
        throw new DataAccessException("");
    }
    public String getPsw(String usn, boolean b) {
        if (b) {
            return dT.retrievePsw(usn);
        }
        return dT.retrieveUsn(usn);
    }

    public void logout(String authrztn) throws DataAccessException {
        if (dT.logout(authrztn)) {
            throw new DataAccessException("");
        }
    }

    public void clearThingy() {
        dT.clearThingy();
    }
    public boolean isAuthorized(String authy) {
        return dT.isAuthorized(authy);
    }

    public int addGame(String gN) {
        return dT.addGame(gN);
    }

    public Collection<GameData> getGames(String authrztn) throws DataAccessException {
        if (!dT.isAuthorized(authrztn)) {
            throw new DataAccessException("");
        }
        return dT.getGames();
    }

    public void joinGame(int ident, boolean isWhite, String authrztn, InfoJoinExt ije) throws DataAccessException {
        ChessMove mh = new ChessMove(new ChessPosition(1, 1), new ChessPosition(1, 1), null);
        InfoJoinExt ijem = new InfoJoinExt(0, mh);
        if (ije != null) {
            ijem = ije;
        }
        if (!dT.joinGame(ident, isWhite, authrztn, ijem)) {
            throw new DataAccessException("");
        }
    }


}

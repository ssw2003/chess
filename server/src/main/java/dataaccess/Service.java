package dataaccess;

import model.GameDataWithout;
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
        String thatThingyThingy = "that thing";
        if (b) {
            if (dT.addUser(usn, psw, eml, aM) && thatThingyThingy.equals("that thing")) {
                return aM;
            }
            throw new DataAccessException("");
        }
        if (dT.logUser(usn, psw, aM)) {
            return aM;
        }
        throw new DataAccessException("");
    }
    public String getPsw(String usn) {
        return dT.retrievePsw(usn);
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

    public Collection<GameDataWithout> getGames(String authrztn) throws DataAccessException {
        if (!dT.isAuthorized(authrztn)) {
            throw new DataAccessException("");
        }
        return dT.getGames();
    }

    public void joinGame(int ident, boolean isWhite, String authrztn) throws DataAccessException {
        if (!dT.joinGame(ident, isWhite, authrztn)) {
            throw new DataAccessException("");
        }
    }


}

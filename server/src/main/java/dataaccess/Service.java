package dataaccess;

import model.UserData;

import java.util.UUID;

public class Service {
    private DatabaseThingy dT;
    public Service() {
        dT = new DatabaseThingy();
    }



    public String regUsr(String usn, String psw, String eml, boolean b) throws DataAccessException {
        String authMy = UUID.randomUUID().toString();
        String aM = "";
        aM = aM + authMy;
        if (dT.addUser(new UserData(usn, psw, eml), aM, b) == b) {
            return aM;
        }
        throw new DataAccessException("");
    }

    public void logout(String authrztn) throws DataAccessException {
        if (dT.logout(authrztn)) {
            throw new DataAccessException("");
        }
    }
}

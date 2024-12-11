package server;

import org.eclipse.jetty.websocket.api.Session;

public class ServerSession {
    private int gameID;
    private boolean going;
    Session session;
    public ServerSession(Session session, int gameID, boolean going) {
        this.gameID = gameID;
        this.going = going;
        this.session = session;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public boolean isGoing() {
        return going;
    }

    public void setGoing(boolean going) {
        this.going = going;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}

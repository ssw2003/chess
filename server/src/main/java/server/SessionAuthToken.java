package server;

import javax.websocket.Session;

public record SessionAuthToken(Session session, String authToken, int gameNumber) {
}

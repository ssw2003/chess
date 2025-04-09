package server;


public record SessionAuthToken(org.eclipse.jetty.websocket.api.Session session, String authToken, int gameNumber) {
}

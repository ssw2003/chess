package server;

import chess.ChessGame;
import chess.ChessMove;
import com.google.gson.Gson;
import com.google.gson.JsonNull;
import com.google.gson.JsonSyntaxException;
import com.sun.tools.javac.Main;
import dataaccess.DataAccessException;
import dataaccess.Service;
import model.*;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.mindrot.jbcrypt.BCrypt;
import spark.*;
import websocket.commands.MakeMoveCommand;
import websocket.commands.UserGameCommand;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@WebSocket
public class Server {
    private dataaccess.Service svc;
    private Collection<SessionAuthToken> sat;
    //private MisterServer ms;
    public Server() {
        svc = new Service();
        sat = new ArrayList<>();
    }

    public int run(int desiredPort) {
        //svc = new dataaccess.Service();
        Spark.port(desiredPort);
        //ms = new MisterServer(svc);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.

        //This line initializes the server and can be removed once you have a functioning endpoint
        //Spark.init();
        //Spark.webSocket("/ws", MisterServer.class);
        Spark.webSocket("/ws", Server.class);
        Spark.post("/user", this::goThingy);
        Spark.delete("/db", this::goThatThingy);
        Spark.get("/game", this::goThisThingy);
        Spark.post("/session", this::thingy);
        Spark.delete("/session", this::thatThingy);
        Spark.put("/game", this::thingyThing);
        Spark.post("/game", this::thisThingy);
        Spark.awaitInitialization();
        return Spark.port();
    }
    @OnWebSocketMessage
    public void onMessage(org.eclipse.jetty.websocket.api.Session ast, String s) throws Exception {
        UserGameCommand.CommandType uct = new Gson().fromJson(s, UserGameCommand.class).getCommandType();
        int tgid = new Gson().fromJson(s, UserGameCommand.class).getGameID();
        String aT = new Gson().fromJson(s, UserGameCommand.class).getAuthToken();
        ChessMove cM = null;
        if (uct == UserGameCommand.CommandType.MAKE_MOVE) {
            cM = new Gson().fromJson(s, MakeMoveCommand.class).getMove().clone();
        }
        if (uct == UserGameCommand.CommandType.CONNECT && tgid == 0) {
            ErrorMessage em = new ErrorMessage(ServerMessage.ServerMessageType.ERROR);
            em.setError("Error Bad Join");
            ast.getRemote().sendString(new Gson().toJson(em, ErrorMessage.class));
            return;
        }
        if (tgid == 0) {
            ErrorMessage em = new ErrorMessage(ServerMessage.ServerMessageType.ERROR);
            em.setError("Error");
            ast.getRemote().sendString(new Gson().toJson(em, ErrorMessage.class));
            return;
        }
        Collection<GameData> cgd = new ArrayList<>();
        try {
            cgd = svc.getGames(aT);
        } catch (DataAccessException e) {}
        String mssg = "observer";
        if (uct == UserGameCommand.CommandType.LEAVE) {
            for (GameData gdt: cgd) {
                if (gdt.gameID() == tgid && gdt.whiteUsername().equals(aT)) {
                    mssg = "white";
                }
                else if (gdt.gameID() == tgid && gdt.blackUsername().equals(aT)) {
                    mssg = "black";
                }
            }
            String mssge = mssg;
            mssg = svc.getPsw(aT, false) + " leaves from " + mssg;
            sendAll(false, ast, ServerMessage.ServerMessageType.NOTIFICATION, mssg, null);
            sat.remove(new SessionAuthToken(ast, aT, tgid));
            if (!mssge.equals("observer")) {
                svc.joinGame(tgid, mssge.equals("white"), aT, new InfoJoinExt(1, null));
            }
            return;
        }
        else if (uct == UserGameCommand.CommandType.CONNECT) {
            sat.add(new SessionAuthToken(ast, aT, tgid));
            ChessGame chg = null;
            for (GameData gdt: cgd) {
                if (gdt.gameID() == tgid && gdt.blackUsername().equals(aT)) {
                    mssg = "black";
                }
                else if (gdt.gameID() == tgid && gdt.whiteUsername().equals(aT)) {
                    mssg = "white";
                }
                if (gdt.gameID() == tgid) {
                    chg = gdt.game().clone();
                }
            }
            sendAll(false, ast, ServerMessage.ServerMessageType.NOTIFICATION, svc.getPsw(aT, false) + " joins as " + mssg, null);
            sendAll(true, ast, ServerMessage.ServerMessageType.LOAD_GAME, null, chg);
            return;
        }
        else if (uct == UserGameCommand.CommandType.RESIGN) {
            for (GameData gdt: cgd) {
                if (gdt.whiteUsername().equals(aT) && gdt.gameID() == tgid) {
                    mssg = "white";
                }
                else if (gdt.blackUsername().equals(aT) && gdt.gameID() == tgid) {
                    mssg = "black";
                }
            }
            boolean badRes = mssg.equals("observer");
            if (!badRes) {
                try {
                    svc.joinGame(tgid, mssg.equals("white"), aT, new InfoJoinExt(2, null));
                } catch (DataAccessException e) {
                    badRes = true;
                }
            }
            if (badRes) {
                sendAll(true, ast, ServerMessage.ServerMessageType.ERROR, "Error", null);
                return;
            }
            mssg = mssg + " (" + svc.getPsw(aT, false) + ") does the following action:\nresign";
            sendAll(true, ast, ServerMessage.ServerMessageType.NOTIFICATION, mssg, null);
            sendAll(false, ast, ServerMessage.ServerMessageType.NOTIFICATION, mssg, null);
            return;
        }
        for (GameData gdt: cgd) {
            if (gdt.gameID() == tgid && gdt.whiteUsername().equals(aT)) {
                mssg = "white";
            }
            else if (gdt.blackUsername().equals(aT) && gdt.gameID() == tgid) {
                mssg = "black";
            }
        }
        boolean badRes = mssg.equals("observer");
        if (!badRes) {
            try {
                svc.joinGame(tgid, mssg.equals("white"), aT, new InfoJoinExt(3, cM.clone()));
            } catch (DataAccessException e) {
                badRes = true;
            }
        }
        if (badRes) {
            sendAll(false == false, ast, ServerMessage.ServerMessageType.ERROR, "Error", null);
            return;
        }
        mssg = mssg + " (" + svc.getPsw(aT, false) + ") does the following action:\nmove ";
        mssg = mssg + "abcdefgh".charAt(cM.getStartPosition().getColumn() - 1);
        mssg = mssg + cM.getStartPosition().getRow() + " -> ";
        mssg = mssg + "abcdefgh".charAt(cM.getEndPosition().getColumn() - 1);
        mssg = mssg + cM.getEndPosition().getRow();
        if (cM.getPromotionPiece() != null) {
            mssg = mssg + " promoting to " + cM.getPromotionPiece();
        }
        ChessGame cgdg = new ChessGame();
        cgd = svc.getGames(aT);
        for (GameData gdt: cgd) {
            if (gdt.gameID() == tgid) {
                cgdg = gdt.game().clone();
            }
        }
        sendAll(true, ast, ServerMessage.ServerMessageType.LOAD_GAME, null, cgdg);
        sendAll(false, ast, ServerMessage.ServerMessageType.LOAD_GAME, null, cgdg);
        sendAll(false, ast, ServerMessage.ServerMessageType.NOTIFICATION, mssg, null);
        boolean sendCheck = false;
        if (cgdg.isInCheck(ChessGame.TeamColor.WHITE) || cgdg.isInCheck(ChessGame.TeamColor.BLACK)) {
            sendCheck = true;
        }
        else if (cgdg.isInStalemate(ChessGame.TeamColor.WHITE) || cgdg.isInStalemate(ChessGame.TeamColor.BLACK)) {
            sendCheck = true;
        }
        if (sendCheck) {
            sendAll(true, ast, ServerMessage.ServerMessageType.NOTIFICATION, "Ch", null);
            sendAll(false, ast, ServerMessage.ServerMessageType.NOTIFICATION, "Ch", null);
        }
    }

    private Object thisThingy(Request request, Response response) {
        //Create game
        var gson = new Gson();
        String authrztn = "";
        try {
            String json = request.headers("authorization");
            authrztn = json + authrztn;
        } catch (Exception exc) {
            var c = Map.of("message", "Error: bad request");
            response.status(400);
            return gson.toJson(c);
        }
        if (!svc.isAuthorized(authrztn)) {
            var c = Map.of("message", "Error: unauthorized");
            response.status(401);
            return gson.toJson(c);
        }
        String gN;
        try {
            var json = gson.fromJson(request.body(), GameNameData.class);
            gN = json.gameName();
            if (gN == null) {
                throw new DataAccessException("");
            }
        } catch (Exception exc) {
            response.status(400);
            var c = Map.of("message", "Error: bad request");
            return gson.toJson(c);
        }
        int i = svc.addGame(gN);
        var ce = Map.of("gameID", i);
        response.status(200);
        return gson.toJson(ce);
    }

    private Object thingyThing(Request request, Response response) {
        //Join game
        String authrztn = "";
        var gson = new Gson();
        try {
            String json = request.headers("authorization");
            authrztn = authrztn + json;
        } catch (Exception exc) {
            response.status(400);
            var c = Map.of("message", "Error: bad request");
            return gson.toJson(c);
        }
        if (!svc.isAuthorized(authrztn)) {
            response.status(401);
            var c = Map.of("message", "Error: unauthorized");
            return gson.toJson(c);
        }
        String pC;
        int ident;
        try {
            var json = gson.fromJson(request.body(), ColorAndNumber.class);
            pC = getColor(json.playerColor());
            ident = json.gameID();
            if (pC == null) {
                throw new DataAccessException("");
            }
            if (ident == 0) {
                throw new DataAccessException("");
            }
        } catch (Exception exc) {
            response.status(400);
            var c = Map.of("message", "Error: bad request");
            return gson.toJson(c);
        }
        var ce = Map.of();
        try {
            if (!pC.equals("OBSERVER")) {
                svc.joinGame(ident, pC.equals("WHITE"), authrztn, null);
            }
        } catch (DataAccessException e) {
            ce = Map.of("message", "Error: already taken");
            response.status(403);
            return gson.toJson(ce);
        }
        response.status(200);
        return gson.toJson(ce);
    }

    private Object thatThingy(Request request, Response response) {
        //LOGOUT
        var gson = new Gson();
        String authrztn = "";
        try {
            String json = request.headers("authorization");
            authrztn = authrztn + json;
        } catch (Exception exc) {
            response.status(400);
            var c = Map.of("message", "Error: bad request");
            return gson.toJson(c);
        }
        try {
            svc.logout(authrztn);
            var f = Map.of();
            response.status(200);
            return gson.toJson(f);
        } catch (Exception exc) {
            var e = Map.of("message", "Error: unauthorized");
            response.status(401);
            return gson.toJson(e);
        }
    }

    private Object thingy(Request request, Response response) {
        //LOGIN
        String usn;
        String psw;
        var gson = new Gson();
        try {
            var json = gson.fromJson(request.body(), UserPass.class);
            usn = json.username();
            psw = json.password();
            if (usn == null) {
                throw new DataAccessException("");
            }
            if (psw == null) {
                throw new DataAccessException("");
            }
        } catch (Exception exc) {
            response.status(400);
            var c = Map.of("message", "Error: bad request");
            return gson.toJson(c);
        }
        try {
            String pswRetrieved = svc.getPsw(usn, true);
            if (pswRetrieved == null) {
                throw new DataAccessException("");
            }
            if (!BCrypt.checkpw(psw, pswRetrieved)) {
                throw new DataAccessException("");
            }
            var f = Map.of("username", usn, "authToken", svc.regUsr(usn, pswRetrieved, "", false));
            response.status(200);
            return gson.toJson(f);
        } catch (Exception exc) {
            var e = Map.of("message", "Error: unauthorized");
            response.status(401);
            return gson.toJson(e);
        }
    }

    private Object goThisThingy(Request request, Response response) {

        //Games listing
        var gson = new Gson();
        String authrztn = "";
        try {
            String json = request.headers("authorization");
            authrztn = authrztn + json;
            if (authrztn == null) {
                throw new DataAccessException("");
            }
        } catch (Exception exc) {
            response.status(401);
            var c = Map.of("message", "Error: unauthorized");
            return gson.toJson(c);
        }
        try {
            var e = Map.of();
            Collection<GameData> cGD = svc.getGames(authrztn);
            Collection<GameDataWithout> cGE = new ArrayList<>();
            for (GameData gaDw: cGD) {
                String wu = svc.getPsw(gaDw.whiteUsername(), false);
                cGE.add(new GameDataWithout(gaDw.gameID(), wu, svc.getPsw(gaDw.blackUsername(), false), gaDw.gameName()));
            }
            var f = Map.of("games", cGE);
            response.status(200);
            return gson.toJson(f);
        } catch (Exception exc) {
            response.status(401);
            var c = Map.of("message", "Error: unauthorized");
            return gson.toJson(c);
        }
    }

    private Object goThatThingy(Request request, Response response) {
        //Clear
        var gson = new Gson();
        try {
            svc.clearThingy();
            response.status(200);
            var c = Map.of();
            return gson.toJson(c);
        } catch (Exception exc) {
            response.status(400);
            var c = Map.of("message", "Error: bad request");
            return gson.toJson(c);
        }
    }

    private Object goThingy(Request request, Response response) {
        //register
        String usn;
        String psw;
        String eml;
        var gson = new Gson();
        try {
            var json = gson.fromJson(request.body(), UserData.class);
            usn = json.username();
            psw = json.password();
            eml = json.email();
            if (usn == null) {
                throw new DataAccessException("");
            }
            if (psw == null) {
                throw new DataAccessException("");
            }
            if (eml == null) {
                throw new DataAccessException("");
            }
            psw = BCrypt.hashpw(psw, BCrypt.gensalt());
        } catch (Exception exc) {
            response.status(400);
            var c = Map.of("message", "Error: bad request");
            return gson.toJson(c);
        }
        try {
            var f = Map.of("username", usn, "authToken", svc.regUsr(usn, psw, eml, true));
            response.status(200);
            return gson.toJson(f);
        } catch (Exception exc) {
            var e = Map.of("message", "Error: already taken");
            response.status(403);
            return gson.toJson(e);
        }
    }

    private String getColor(String s) {
        if (s == null) {
            return null;
        }
        if (s.equals("OBSERVER")) {
            return "OBSERVER";
        }
        if (s.equals("W") || s.equals("w")) {
            return "WHITE";
        }
        if (s.equals("B") || s.equals("b")) {
            return "BLACK";
        }
        if (s.length() == 5) {
            String sa = s.substring(0, 1);
            String sb = s.substring(1, 2);
            String sc = s.substring(2, 3);
            String sd = s.substring(3, 4);
            String se = s.substring(4, 5);
            if (sa.equals("W") || sa.equals("w")) {
                if (!sb.equals("H") && !sb.equals("h")) {
                    return null;
                }
                if (!sc.equals("I") && !sc.equals("i")) {
                    return null;
                }
                if (!sd.equals("T") && !sd.equals("t")) {
                    return null;
                }
                if (!se.equals("E") && !se.equals("e")) {
                    return null;
                }
                return "WHITE";
            }
            if (sa.equals("B") || sa.equals("b")) {
                if (!sb.equals("L") && !sb.equals("l")) {
                    return null;
                }
                if (!sc.equals("A") && !sc.equals("a")) {
                    return null;
                }
                if (!sd.equals("C") && !sd.equals("c")) {
                    return null;
                }
                if (!se.equals("K") && !se.equals("k")) {
                    return null;
                }
                return "BLACK";
            }
            return null;
        }
        return null;
    }
    private void sendAll(boolean b, org.eclipse.jetty.websocket.api.Session a, ServerMessage.ServerMessageType smt, String mss, ChessGame cg) {
        String s;
        if (smt == ServerMessage.ServerMessageType.ERROR) {
            ErrorMessage em = new ErrorMessage(smt);
            em.setError(mss);
            s = new Gson().toJson(em, ErrorMessage.class);
        }
        else if (smt == ServerMessage.ServerMessageType.NOTIFICATION) {
            NotificationMessage nm = new NotificationMessage(smt);
            nm.setNotification(mss);
            s = new Gson().toJson(nm, NotificationMessage.class);
        }
        else {
            LoadGameMessage lgm = new LoadGameMessage(smt);
            lgm.setGame(cg.clone());
            s = new Gson().toJson(lgm, LoadGameMessage.class);
        }
        if (b) {
            try {
                a.getRemote().sendString(s);
            } catch (Exception e) {}
            return;
        }
        int gid = 0;
        for (SessionAuthToken pt: sat) {
            if (pt.session() == a) {
                gid = pt.gameNumber();
                break;
            }
        }
        for (SessionAuthToken pt: sat) {
            if (pt.gameNumber() == gid && gid != 0 && pt.session() != a) {
                try {
                    pt.session().getRemote().sendString(s);
                } catch (Exception e) {}
            }
        }
    }



    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}

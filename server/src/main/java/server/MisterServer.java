package server;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPiece;
import com.google.gson.Gson;
import dataaccess.DataAccessException;
import model.GameData;
import model.LoginData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import spark.Spark;
import websocket.commands.MoveMaker;
import websocket.commands.UserGameCommand;
import websocket.messages.ErrorThing;
import websocket.messages.GameOfChessThing;
import websocket.messages.NotificationFactor;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@WebSocket
public class MisterServer {
    Server sv;
    Collection<ServerSession> links;
    public MisterServer(Server sv) {
        this.sv = sv;
        links = new ArrayList<>();
    }
    @OnWebSocketMessage
    //public void takeCareOfIt(Session ss, String ms) {
    public void onMessage(Session ss, String ms) {
        var frommedJson = new Gson().fromJson(ms, UserGameCommand.class);
        boolean errorIsSent = false;
        String usN = "";
        ChessMove cM = null;
        int gameNumber = frommedJson.getGameID();;
        try {
            usN = sv.theService.dataAccess.getAuth(frommedJson.getAuthToken()).username();
            if (!sv.theService.dataAccess.checkForGameExistence(gameNumber)) {
                errorIsSent = true;
            }
        } catch (Exception e) {
            errorIsSent = true;
        }
        UserGameCommand.CommandType cT = frommedJson.getCommandType();
        if (frommedJson.getCommandType() == UserGameCommand.CommandType.MAKE_MOVE) {
            var fJ = new Gson().fromJson(ms, MoveMaker.class);
            cM = fJ.getMove();
        }
        if ((cT == UserGameCommand.CommandType.MAKE_MOVE) == (cM == null)) {
            errorIsSent = true;
        }
        if (cT == UserGameCommand.CommandType.RESIGN || cM != null) {
            try {
                Collection<GameData> cGs = sv.theService.dataAccess.getAllGames();
                GameData firstGame = gameData(cGs, gameNumber);
                if (!"Set".equals(firstGame.game().abortGame("Question"))) { throw new RuntimeException(); }
                if (cM != null) { firstGame.game().makeMove(cM); }
                ChessGame.TeamColor color;
                if (firstGame.whiteUsername().equals(usN)) {
                    color = ChessGame.TeamColor.WHITE;
                } else if (firstGame.blackUsername().equals(usN)) {
                    color = ChessGame.TeamColor.BLACK;
                } else {
                    throw new Exception();
                }
                if (cT == UserGameCommand.CommandType.RESIGN) {
                    firstGame.game().abortGame("Resign");
                    firstGame.game().setBlackResigns(new ChessPiece(color, ChessPiece.PieceType.KING));
                }
            } catch (Exception e) { errorIsSent = true; }
        }
        GameData cg = null;
        try {
            cg = gameData(sv.theService.getAllGames(), gameNumber);
        } catch (Exception e) {}
        if (errorIsSent) {
            try {
                var thingSerializer = new Gson();
                var thingToSerialize = new ErrorThing(ServerMessage.ServerMessageType.ERROR, "Error");
                var thingJson = thingSerializer.toJson(thingToSerialize);
                ss.getRemote().sendString(thingJson);
            } catch (IOException e) {}
        } else if (cT == UserGameCommand.CommandType.CONNECT) {
            //Connect
            try {
                var thingSerializer = new Gson();
                var thingToSerialize = new GameOfChessThing(ServerMessage.ServerMessageType.LOAD_GAME, cg.game());
                var thingJson = thingSerializer.toJson(thingToSerialize);
                ss.getRemote().sendString(thingJson);
                String cl = "observer";
                if (cg.whiteUsername().equals(usN)) {
                    cl = "white";
                } else if (cg.blackUsername().equals(usN)) {
                    cl = "black";
                }
                for (ServerSession ir: links) {
                    if (ir.getSession() != ss && ir.getGameID() == gameNumber && ir.isGoing()) {
                        var thingySerializer = new Gson();
                        var thingyToSerialize = new NotificationFactor(ServerMessage.ServerMessageType.NOTIFICATION, usN + " joined as " + cl);
                        var thingyJson = thingySerializer.toJson(thingyToSerialize);
                        ir.getSession().getRemote().sendString(thingyJson);
                    }
                }
                links.add(new ServerSession(ss, gameNumber, true));
            } catch (IOException e) {}
        } else if (cT == UserGameCommand.CommandType.LEAVE) {
            //Leave
            try {
                //System.out.println("Chp a");
                String cl = "observer";
                if (eqS(cg.whiteUsername(), usN)) {
                    cl = "white";
                } else if (eqS(cg.blackUsername(), usN)) {
                    cl = "black";
                }
                //System.out.println("Chp b");
                if (cl.equals("white")) {
                    sv.theService.dataAccess.joinGameThingy(gameNumber, ChessGame.TeamColor.WHITE, null);
                } else if (cl.equals("black")) {
                    sv.theService.dataAccess.joinGameThingy(gameNumber, ChessGame.TeamColor.BLACK, null);
                }
                //System.out.println("Chp c");
                //System.out.println(gameNumber);
                for (ServerSession ir: links) {
                    if (ir.getSession() != ss && ir.getGameID() == gameNumber && ir.isGoing()) {
                        var thingySerializer = new Gson();
                        var thingyToSerialize = new NotificationFactor(ServerMessage.ServerMessageType.NOTIFICATION, usN + " left from " + cl);
                        var thingyJson = thingySerializer.toJson(thingyToSerialize);
                        ir.getSession().getRemote().sendString(thingyJson);
                        //System.out.println(ir.getGameID());
                    }
                    if (ir.getSession() == ss) {
                        ir.setGoing(false);
                    }
                }
                //System.out.println("Chp d");
                //links.add(new ServerSession(ss, gameNumber, true));
                //System.out.println("Chp e");
            } catch (Exception e) {
                try {
                    var thingSerializer = new Gson();
                    var thingToSerialize = new ErrorThing(ServerMessage.ServerMessageType.ERROR, "Error");
                    var thingJson = thingSerializer.toJson(thingToSerialize);
                    ss.getRemote().sendString(thingJson);
                } catch (IOException ee) {}
            }
        } else if (cT == UserGameCommand.CommandType.MAKE_MOVE) {
            //Make move
            try {
                for (ServerSession ir: links) {
                    if (ir.getGameID() == gameNumber && ir.isGoing()) {
                        var thingySerializer = new Gson();
                        var thingyToSerialize = new GameOfChessThing(ServerMessage.ServerMessageType.LOAD_GAME, cg.game());
                        var thingyJson = thingySerializer.toJson(thingyToSerialize);
                        ir.getSession().getRemote().sendString(thingyJson);
                    }
                    if (ir.getSession() != ss && ir.getGameID() == gameNumber && ir.isGoing()) {
                        var thingySerializer = new Gson();
                        var thingyToSerialize = new NotificationFactor(ServerMessage.ServerMessageType.NOTIFICATION, usN + " made move " + cM.toString());
                        var thingyJson = thingySerializer.toJson(thingyToSerialize);
                        ir.getSession().getRemote().sendString(thingyJson);
                    }
                    if ((cg.game().isInCheck(cg.game().getTeamTurn()) ||
                            cg.game().isInCheck(cg.game().getTeamTurn())) &&
                            ir.getSession() != ss &&
                            ir.getGameID() == gameNumber &&
                            ir.isGoing()) {
                        var thingySerializer = new Gson();
                        var thingyToSerialize = new NotificationFactor(ServerMessage.ServerMessageType.NOTIFICATION, usN + " check " + cM.toString());
                        var thingyJson = thingySerializer.toJson(thingyToSerialize);
                        ir.getSession().getRemote().sendString(thingyJson);
                    }
                }
//                var thingSerializer = new Gson();
//                var thingToSerialize = new GameOfChessThing(ServerMessage.ServerMessageType.LOAD_GAME, cg.game());
//                var thingJson = thingSerializer.toJson(thingToSerialize);
//                ss.getRemote().sendString(thingJson);
            } catch (IOException e) {}
        } else {
            //Resign
            try {
                if (!cg.game().abortGame("Question").equals("Set")) {
                    throw new Exception();
                } else if (cg.game().isInCheckmate(cg.game().getTeamTurn()) || cg.game().isInStalemate(cg.game().getTeamTurn())) {
                    throw new Exception();
                }
            } catch (Exception e) {}
        }
        //sv.theService.dataAccess.joinGameThingy()
    }
    GameData gameData(Collection<GameData> cGs, int gameNumber) throws Exception {
        for (GameData gD: cGs) {
            if (gD.gameID() == gameNumber) {
                return gD;
            }
        }
        throw new Exception();
    }
    boolean eqS(String a, String b) {
        if (a == null) { return b == null; }
        if (b == null) { return false; }
        return a == b;
    }
}

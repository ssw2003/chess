package server;

import chess.ChessGame;
import chess.ChessMove;
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
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.util.Collection;

@WebSocket
public class MisterServer {
    Server sv;
    public MisterServer(Server sv) {
        this.sv = sv;
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
                ChessGame.TeamColor color = null;
                if (firstGame.whiteUsername().equals(usN)) {
                    color = ChessGame.TeamColor.WHITE;
                } else if (firstGame.whiteUsername().equals(usN)) {
                    color = ChessGame.TeamColor.BLACK;
                }
                if (cT == UserGameCommand.CommandType.RESIGN) {
                    firstGame.game().abortGame("Resign");
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
            } catch (IOException e) {}
        } else if (cT == UserGameCommand.CommandType.LEAVE) {
            //Leave
            try {
                if (cg.whiteUsername().equals(usN)) {
                    sv.theService.dataAccess.joinGameThingy(gameNumber, ChessGame.TeamColor.WHITE, null);
                } else if (cg.blackUsername().equals(usN)) {
                    sv.theService.dataAccess.joinGameThingy(gameNumber, ChessGame.TeamColor.BLACK, null);
                }
            } catch (Exception e) {}
        } else if (cT == UserGameCommand.CommandType.MAKE_MOVE) {
            //Make move
        } else {
            //Resign
            try {
                if (cg.whiteUsername().equals(usN)) {
                    sv.theService.dataAccess.joinGameThingy(gameNumber, ChessGame.TeamColor.WHITE, null);
                } else if (cg.blackUsername().equals(usN)) {
                    sv.theService.dataAccess.joinGameThingy(gameNumber, ChessGame.TeamColor.BLACK, null);
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
}

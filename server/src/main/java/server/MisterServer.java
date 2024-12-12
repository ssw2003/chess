package server;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPiece;
import com.google.gson.Gson;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
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
    public void onMessage(Session ss, String ms) {
        String mt = ms + "";
        var frommedJson = new Gson().fromJson(ms, UserGameCommand.class);
        boolean errorIsSent = false;
        String usN = "";
        ChessMove cM = null;
        int gameNumber = frommedJson.getGameID();;
        String color = "observer";
        GameData firstGame = null;
        try {
            usN = sv.theService.dataAccess.getAuth(frommedJson.getAuthToken()).username();
            firstGame = gameData(sv.theService.dataAccess.getAllGames(), gameNumber);
            if (firstGame.whiteUsername().equals(usN)) {
                color = "white";
            } else if (firstGame.blackUsername().equals(usN)) {
                color = "black";
            }
            if (!sv.theService.dataAccess.checkForGameExistence(gameNumber)) {
                errorIsSent = true;
            }
        } catch (Exception e) {
            errorIsSent = true;
        }
        UserGameCommand.CommandType cT = frommedJson.getCommandType();
        if (frommedJson.getCommandType() == UserGameCommand.CommandType.MAKE_MOVE) {
            var fJ = new Gson().fromJson(mt, MoveMaker.class);
            cM = fJ.getMove();
        }
        if ((cT == UserGameCommand.CommandType.MAKE_MOVE) == (cM == null)) {
            errorIsSent = true;
        }
        if (cT == UserGameCommand.CommandType.CONNECT) {
            try {
                if (errorIsSent) {
                    throw new RuntimeException();
                }
                links.add(new ServerSession(ss, gameNumber, true));
                var thingSerializer = new Gson();
                var thingToSerialize = new GameOfChessThing(ServerMessage.ServerMessageType.LOAD_GAME, firstGame.game());
                var thingJson = thingSerializer.toJson(thingToSerialize);
                ss.getRemote().sendString(thingJson);
                for (ServerSession lk: links) {
                    if (lk.getSession().isOpen() && lk.getSession() != ss && lk.getGameID() == gameNumber) {
                        var thingySerializer = new Gson();
                        var thingyToSerialize = new NotificationFactor(ServerMessage.ServerMessageType.NOTIFICATION, "J:" + usN + " connected as " + color);
                        var thingyJson = thingySerializer.toJson(thingyToSerialize);
                        lk.getSession().getRemote().sendString(thingyJson);
                    }
                }
            } catch (Exception e) {
                errorIsSent = true;
            }
        } else if (cT == UserGameCommand.CommandType.MAKE_MOVE) {
            try {
                if (color.equals("observer")) {
                    throw new RuntimeException();
                } else if (color.equals("white") == (firstGame.game().getTeamTurn() == ChessGame.TeamColor.BLACK)) {
                    throw new RuntimeException();
                } else if (!firstGame.game().abortGame("Question").equals("Set")) {
                    throw new RuntimeException();
                }
                ChessGame fg = firstGame.game().clone();
                fg.makeMove(cM);
                sv.theService.dataAccess.updateGame(gameNumber, fg);
                var thingSerializer = new Gson();
                var thingToSerialize = new GameOfChessThing(ServerMessage.ServerMessageType.LOAD_GAME, firstGame.game());
                var thingJson = thingSerializer.toJson(thingToSerialize);
                for (ServerSession lk: links) {
                    if (lk.getSession().isOpen() && lk.getGameID() == gameNumber) {
                        lk.getSession().getRemote().sendString(thingJson);
                    }
                }
                for (ServerSession lk: links) {
                    if (lk.getSession().isOpen() && lk.getSession() != ss && lk.getGameID() == gameNumber) {
                        var thingySerializer = new Gson();
                        String mv = convertMove(cM);
                        var thingyToSerialize = new NotificationFactor(ServerMessage.ServerMessageType.NOTIFICATION, "M:" + usN + " moved from " + mv);
                        var thingyJson = thingySerializer.toJson(thingyToSerialize);
                        lk.getSession().getRemote().sendString(thingyJson);
                    }
                }
                for (ServerSession lk: links) {
                    if (!firstGame.game().isInCheck(firstGame.game().getTeamTurn()) && !firstGame.game().isInStalemate(firstGame.game().getTeamTurn())) {
                        break;
                    }
                    if (lk.getSession().isOpen() && lk.getGameID() == gameNumber) {
                        var thingySerializer = new Gson();
                        var thingyToSerialize = new NotificationFactor(ServerMessage.ServerMessageType.NOTIFICATION, "C:");
                        var thingyJson = thingySerializer.toJson(thingyToSerialize);
                        lk.getSession().getRemote().sendString(thingyJson);
                    }
                }
            } catch (Exception e) {
                errorIsSent = true;
            }
        } else if (cT == UserGameCommand.CommandType.LEAVE) {
            try {
                if (color.equals("white")) {
                    sv.theService.dataAccess.joinGameThingy(gameNumber, ChessGame.TeamColor.WHITE, null);
                } else if (color.equals("black")) {
                    sv.theService.dataAccess.joinGameThingy(gameNumber, ChessGame.TeamColor.BLACK, null);
                }
                ServerSession ses = null;
                for (ServerSession ir: links) {
                    if (ir.getSession() == ss) {
                        ses = ir;
                        break;
                    }
                }
                ss.close();
                links.remove(ses);
                for (ServerSession ir: links) {
                    if (ir.getGameID() == gameNumber && ir.getSession().isOpen()) {
                        var thingySerializer = new Gson();
                        var thingyToSerialize = new NotificationFactor(ServerMessage.ServerMessageType.NOTIFICATION, "L:" + usN + " left from " + color);
                        var thingyJson = thingySerializer.toJson(thingyToSerialize);
                        ir.getSession().getRemote().sendString(thingyJson);
                    }
                }
            } catch (Exception e) {
                errorIsSent = true;
            }
        } else {
            try {
                if (color.equals("observer")) {
                    throw new RuntimeException();
                } else if (!firstGame.game().abortGame("Question").equals("Set")) {
                    throw new RuntimeException();
                }
                ChessPiece resignPiece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
                if (color.equals("black")) {
                    resignPiece = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
                }
                firstGame.game().abortGame("Abort");
                firstGame.game().setBlackResigns(resignPiece);
                sv.theService.dataAccess.updateGame(gameNumber, firstGame.game());
                for (ServerSession lk: links) {
                    if (lk.getSession().isOpen() && lk.getGameID() == gameNumber) {
                        var thingySerializer = new Gson();
                        var thingyToSerialize = new NotificationFactor(ServerMessage.ServerMessageType.NOTIFICATION, "R:" + usN + " resigned from " + color);
                        var thingyJson = thingySerializer.toJson(thingyToSerialize);
                        lk.getSession().getRemote().sendString(thingyJson);
                    }
                }
            } catch (Exception e) {
                errorIsSent = true;
            }
        }
        if (errorIsSent) {
            try {
                var thingSerializer = new Gson();
                var thingToSerialize = new ErrorThing(ServerMessage.ServerMessageType.ERROR, "Error");
                var thingJson = thingSerializer.toJson(thingToSerialize);
                ss.getRemote().sendString(thingJson);
            } catch (IOException e) {}
        }
    }
    GameData gameData(Collection<GameData> cGs, int gameNumber) throws Exception {
        for (GameData gD: cGs) {
            if (gD.gameID() == gameNumber) {
                return gD;
            }
        }
        throw new Exception();
    }
    private String convertMove(ChessMove mv) {
        String w = numberToLetter(mv.getStartPosition().getColumn());
        w = w + mv.getStartPosition().getRow();
        w = w + " to ";
        w = w + numberToLetter(mv.getEndPosition().getColumn());
        w = w + mv.getEndPosition().getRow();
        if (mv.getPromotionPiece() == null) {
            return w;
        }
        w = w + " making ";
        ChessPiece.PieceType pT = mv.getPromotionPiece();
        if (pT == ChessPiece.PieceType.QUEEN) {
            return w + "QUEEN";
        } else if (pT == ChessPiece.PieceType.BISHOP) {
            return w + "BISHOP";
        } else if (pT == ChessPiece.PieceType.KNIGHT) {
            return w + "KNIGHT";
        } else if (pT == ChessPiece.PieceType.ROOK) {
            return w + "ROOK";
        } else if (pT == ChessPiece.PieceType.KING) {
            return w + "KING";
        }
        return w + "PAWN";
    }
    private String numberToLetter(int s) {
        return switch (s) {
            case 1 -> "A";
            case 2 -> "B";
            case 3 -> "C";
            case 4 -> "D";
            case 5 -> "E";
            case 6 -> "F";
            case 7 -> "G";
            case 8 -> "H";
            default -> "";
        };
    }
}

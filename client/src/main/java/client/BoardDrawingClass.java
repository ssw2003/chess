package client;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;
import ui.EscapeSequences;

import java.util.Collection;

public class BoardDrawingClass {
    public BoardDrawingClass() {}
    public enum Role {
        WHITE,
        BLACK,
        OBSERVER
    }
    public void dB(ChessGame cG, ChessPosition cP, Role rl) {
        ChessGame ch = new ChessGame();
        if (cG != null) {
            ch = cG.clone();
        }
        System.out.println("\n");
        String p = "";
        int i = cG.getInfo(576) % 4;
        String mssg = "";
        int hfmssglen = 12;
        if (i >= 2) {
            mssg = "White Resigns—Black Wins";
        }
        else if (i == 1) {
            mssg = "Black Resigns—White Wins";
        }
        else {
            hfmssglen = 0;
            if (cG.isInStalemate(cG.getTeamTurn())) {
                mssg = "STALEMATE—Tie Game";
                hfmssglen = 9;
            }
            else if (cG.isInCheckmate(cG.getTeamTurn())) {
                hfmssglen = 10;
                mssg = "CHECKMATE—White Wins";
                if (cG.getTeamTurn() == ChessGame.TeamColor.WHITE) {
                    mssg = "CHECKMATE—Black Wins";
                }
            }
            else if (cG.isInCheck(cG.getTeamTurn())) {
                hfmssglen = 3;
                mssg = "Check!";
            }
        }
        i = 0;
        while (i < 330) {
            p = p + EscapeSequences.RESET_BG_COLOR;
            p = p + EscapeSequences.RESET_TEXT_COLOR;
            int j = getNum(i, rl);
            if (i + hfmssglen == 285) {
                if (rl == Role.OBSERVER || mssg.equals("") || mssg.equals("STALEMATE—Tie Game")) {
                    p = p + EscapeSequences.SET_BG_COLOR_BLUE;
                }
                else if ((rl == Role.WHITE) == (cG.getTeamTurn() == ChessGame.TeamColor.WHITE) && mssg.equals("Check!")) {
                    p = p + EscapeSequences.SET_BG_COLOR_RED;
                }
                else if (mssg.equals("Check!")) {
                    p = p + EscapeSequences.SET_BG_COLOR_GREEN;
                }
                else if ((mssg.equals("CHECKMATE—White Wins") || mssg.equals("Black Resigns—White Wins")) == (rl == Role.WHITE)) {
                    p = p + EscapeSequences.SET_BG_COLOR_GREEN;
                }
                else {
                    p = p + EscapeSequences.SET_BG_COLOR_RED;
                }
                p = p + EscapeSequences.SET_TEXT_COLOR_WHITE;
                p = p + mssg;
                i = 314 + hfmssglen;
            }
            else {
                if (textIsBlack(j, cG)) {
                    p = p + EscapeSequences.SET_TEXT_COLOR_BLACK;
                }
                else {
                    p = p + EscapeSequences.SET_TEXT_COLOR_WHITE;
                }
                int hg = highlights(j, cG, cP);
                if (hg == 0) {
                    p = p + EscapeSequences.SET_BG_COLOR_YELLOW;
                }
                else if (hg == 1) {
                    p = p + EscapeSequences.SET_BG_COLOR_LIGHT_GREY;
                }
                else if (hg == 2) {
                    p = p + EscapeSequences.SET_BG_COLOR_DARK_GREY;
                }
                else if (hg == 3) {
                    p = p + EscapeSequences.SET_BG_COLOR_DARK_GREEN;
                }
                else {
                    p = p + EscapeSequences.SET_BG_COLOR_MAGENTA;
                }
                p = p + textByte(j, cG);
            }
            p = p + EscapeSequences.RESET_BG_COLOR;
            p = p + EscapeSequences.RESET_TEXT_COLOR;
            i++;
            if (i % 30 == 0) {
                System.out.println(p);
                p = "";
            }
        }
        System.out.println("\n");
    }

    private int getNum(int i, Role rl) {
        if (rl == Role.BLACK) {
            int j = 299 - i;
            if (j < 0) {
                return j + 30;
            }
            return j;
        }
        return i;
    }

    private boolean textIsBlack(int x, ChessGame cG) {
        int r = x / 30;
        if (r > 8) {
            r = 0;
        }
        int c = x % 30;
        if (c % 3 != 1) {
            return true;
        }
        if (r == 0) {
            return true;
        }
        if (c == 1 || c == 28) {
            return true;
        }
        ChessPosition cQ = new ChessPosition(9 - r, c / 3);
        ChessPiece cP = cG.getBoard().getPiece(cQ);
        if (cP == null) {
            return true;
        }
        return cP.getTeamColor() == ChessGame.TeamColor.BLACK;
    }
    private String textByte(int x, ChessGame cG) {
        int r = x / 30;
        int c = x % 30;
        if (r > 8) {
            r = 0;
        }
        if (c % 3 != 1) {
            return " ";
        }
        c = c / 3;
        if (c == 9) {
            c = 0;
        }
        if (r != 0) {
            r = 9 - r;
        }
        if (c == 0) {
            if (r == 0) {
                return " ";
            }
            return "" + r;
        }
        if (r == 0) {
            return "labcdefgh".substring(c, c + 1);
        }
        ChessPiece bP = cG.getBoard().getPiece(new ChessPosition(r, c));
        if (bP == null) {
            return " ";
        }
        if (bP.getPieceType() == ChessPiece.PieceType.KING) {
            return "K";
        }
        if (bP.getPieceType() == ChessPiece.PieceType.QUEEN) {
            return "Q";
        }
        if (bP.getPieceType() == ChessPiece.PieceType.PAWN) {
            return "P";
        }
        if (bP.getPieceType() == ChessPiece.PieceType.ROOK) {
            return "R";
        }
        if (bP.getPieceType() == ChessPiece.PieceType.KNIGHT) {
            return "N";
        }
        return "B";
    }
    private int highlights(int x, ChessGame cG, ChessPosition cP) {
        int c = x % 30;
        int r = x / 30;
        if (r > 8) {
            r = 0;
        }
        if (c < 3 || c > 26) {
            r = 0;
        }
        if (r == 0) {
            return 0;
        }
        c = c / 3;
        r = 9 - r;
        int t = 1;
        if (c % 2 == r % 2) {
            t = 2;
        }
        if (cP == null) {
            return t;
        }
        ChessPosition cQ = new ChessPosition(r, c);
        if (cG.getBoard().getPiece(cP) == null) {
            return t;
        }
        Collection<ChessMove> cM = cG.validMoves(cP);
        for (ChessMove cN: cM) {
            if (cN.getEndPosition().equals(cQ)) {
                return 5 - t;
            }
        }
        return t;
    }
}

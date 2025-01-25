package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    ChessGame.TeamColor pC;
    PieceType pType;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        pC = pieceColor;
        pType = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pC;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return pType;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        if (board.getPiece(myPosition).getPieceType() == PieceType.PAWN) {
            if (board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {
                return pawnMoves(board, myPosition, 0);
            }
            return pawnMoves(board, myPosition, 1);
        }
        Collection<ChessMove> cM = new ArrayList<>();
        return cM;
    }
    private Collection<ChessMove> pawnMoves(ChessBoard board, ChessPosition myPosition, int cl) {
        Collection<ChessMove> cM = new ArrayList<>();
        int a = 5;
        int r = myPosition.getRow();
        int c = myPosition.getColumn();
        if (myPosition.getRow() == 2 + 5 * cl) {
            if (board.getPiece(new ChessPosition(r + 1 - 2 * cl, c)) == null) {
                if (board.getPiece(new ChessPosition(r + 2 - 4 * cl, c)) == null) {
                    cM.add(new ChessMove(myPosition, new ChessPosition(r + 2 - 4 * cl, c), null));
                }
            }
        }
        boolean cal = false;
        boolean caf = (board.getPiece(new ChessPosition(r + 1 - 2 * cl, c)) == null);
        boolean car = false;
        if (cl != 1) {
            ChessPosition cP = new ChessPosition(r + 1 - 2 * cl, c - 1);
            if (board.getPiece(cP) != null) {
                if ((board.getPiece(cP).getTeamColor() == ChessGame.TeamColor.BLACK) == (cl == 0)) {
                    cal = true;
                }
            }
        }
        if (cl != 8) {
            ChessPosition cP = new ChessPosition(r + 1 - 2 * cl, c + 1);
            if (board.getPiece(cP) != null) {
                if ((board.getPiece(cP).getTeamColor() == ChessGame.TeamColor.WHITE) == (cl == 1)) {
                    car = true;
                }
            }
        }
        r = r + 1 - 2 * cl;
        boolean canPromote = (myPosition.getRow() == 7 - 5 * cl);
        if (cal) {
            if (canPromote) {
                cM.add(new ChessMove(myPosition, new ChessPosition(r, c - 1), PieceType.QUEEN));
                cM.add(new ChessMove(myPosition, new ChessPosition(r, c - 1), PieceType.ROOK));
                cM.add(new ChessMove(myPosition, new ChessPosition(r, c - 1), PieceType.KNIGHT));
                cM.add(new ChessMove(myPosition, new ChessPosition(r, c - 1), PieceType.BISHOP));
            }
            else {
                cM.add(new ChessMove(myPosition, new ChessPosition(r, c - 1), null));
            }
        }
        if (caf) {
            if (canPromote) {
                cM.add(new ChessMove(myPosition, new ChessPosition(r, c), PieceType.QUEEN));
                cM.add(new ChessMove(myPosition, new ChessPosition(r, c), PieceType.ROOK));
                cM.add(new ChessMove(myPosition, new ChessPosition(r, c), PieceType.KNIGHT));
                cM.add(new ChessMove(myPosition, new ChessPosition(r, c), PieceType.BISHOP));
            }
            else {
                cM.add(new ChessMove(myPosition, new ChessPosition(r, c), null));
            }
        }
        if (car) {
            if (canPromote) {
                cM.add(new ChessMove(myPosition, new ChessPosition(r, c + 1), PieceType.QUEEN));
                cM.add(new ChessMove(myPosition, new ChessPosition(r, c + 1), PieceType.ROOK));
                cM.add(new ChessMove(myPosition, new ChessPosition(r, c + 1), PieceType.KNIGHT));
                cM.add(new ChessMove(myPosition, new ChessPosition(r, c + 1), PieceType.BISHOP));
            }
            else {
                cM.add(new ChessMove(myPosition, new ChessPosition(r, c + 1), null));
            }
        }
        return cM;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        ChessPiece ch = (ChessPiece) obj;
        return pC == ch.getTeamColor() && pType == ch.getPieceType();
    }

    @Override
    public int hashCode() {
        int i = 3;
        if (pType == ChessPiece.PieceType.PAWN) {
            i = 1;
        } else if (pType == ChessPiece.PieceType.KING) {
            i = 4;
        } else if (pType == ChessPiece.PieceType.QUEEN) {
            i = 5;
        } else if (pType == ChessPiece.PieceType.ROOK) {
            i = 2;
        } else if (pType == ChessPiece.PieceType.KNIGHT) {
            i = 0;
        }
        if (pC == ChessGame.TeamColor.BLACK) {
            return i;
        }
        return i + 6;
    }
    public ChessPiece clone() {
        return new ChessPiece(getTeamColor(), getPieceType());
    }
}

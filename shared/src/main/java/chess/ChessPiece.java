package chess;

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
        throw new RuntimeException("Not implemented");
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        throw new RuntimeException("Not implemented");
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

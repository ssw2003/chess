package chess;

/**
 * Represents moving a chess piece on a chessboard
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessMove {
    private ChessPosition sP;
    private ChessPosition eP;
    private ChessPiece.PieceType pP;

    public ChessMove(ChessPosition startPosition, ChessPosition endPosition,
                     ChessPiece.PieceType promotionPiece) {
        sP = startPosition.clone();
        eP = endPosition.clone();
        pP = null;
        if (promotionPiece != null) {
            pP = promotionPiece;
        }
    }

    /**
     * @return ChessPosition of starting location
     */
    public ChessPosition getStartPosition() {
        return sP.clone();
    }

    /**
     * @return ChessPosition of ending location
     */
    public ChessPosition getEndPosition() {
        return eP.clone();
    }

    /**
     * Gets the type of piece to promote a pawn to if pawn promotion is part of this
     * chess move
     *
     * @return Type of piece to promote a pawn to, or null if no promotion
     */
    public ChessPiece.PieceType getPromotionPiece() {
        if (pP == null) {
            return null;
        }
        return pP;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        ChessMove ch = (ChessMove) obj;
        if ((pP == null) != (ch.getPromotionPiece() == null)) {
            return false;
        }
        if (pP != null) {
            if (pP != ch.getPromotionPiece()) {
                return false;
            }
        }
        return (sP.equals(ch.getStartPosition())) && (eP.equals(ch.getEndPosition()));
    }

    @Override
    public int hashCode() {
        int i = 0;
        if (pP == null) {
            i = 1;
        } else if (pP == ChessPiece.PieceType.KING) {
            i = 2;
        } else if (pP == ChessPiece.PieceType.QUEEN) {
            i = 3;
        } else if (pP == ChessPiece.PieceType.ROOK) {
            i = 4;
        } else if (pP == ChessPiece.PieceType.KNIGHT) {
            i = 5;
        } else if (pP == ChessPiece.PieceType.BISHOP) {
            i = 6;
        }
        i = i * 64 + sP.hashCode();
        return i * 64 + eP.hashCode();
    }
    public ChessMove clone() {
        if (pP != null) {
            return new ChessMove(sP, eP, pP);
        }
        return new ChessMove(sP, eP, null);
    }
}

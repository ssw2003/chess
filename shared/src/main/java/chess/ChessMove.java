package chess;

/**
 * Represents moving a chess piece on a chessboard
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessMove {

    private ChessPosition myStartPosition;
    private ChessPosition myEndPosition;
    private ChessPiece.PieceType myPromotionPiece;
    public ChessMove(ChessPosition startPosition, ChessPosition endPosition,
                     ChessPiece.PieceType promotionPiece) {
        myStartPosition = startPosition.clone();
        myEndPosition = endPosition.clone();
        if (promotionPiece == null) {
            myPromotionPiece = null;
        } else {
            myPromotionPiece = promotionPiece;
        }
    }

    /**
     * @return ChessPosition of starting location
     */
    public ChessPosition getStartPosition() {
        return this.myStartPosition.clone();
    }

    /**
     * @return ChessPosition of ending location
     */
    public ChessPosition getEndPosition() {
        return this.myEndPosition.clone();
    }

    /**
     * Gets the type of piece to promote a pawn to if pawn promotion is part of this
     * chess move
     *
     * @return Type of piece to promote a pawn to, or null if no promotion
     */
    public ChessPiece.PieceType getPromotionPiece() {
        if (null == this.myPromotionPiece) {
            return null;
        }
        return this.myPromotionPiece;
    }
    public boolean equals(Object compareWith) {
        if (compareWith == null) {
            return false;
        }
        if (compareWith.getClass() != getClass()) {
            return false;
        }
        ChessMove myThing = (ChessMove) compareWith;
        if (myThing.getStartPosition().hashCode() != myStartPosition.hashCode()) {
            return false;
        }
        if (myThing.getEndPosition().hashCode() != myEndPosition.hashCode()) {
            return false;
        }
        if (myPromotionPiece == null) {
            if (null == myThing.getPromotionPiece()) {
                return true;
            }
            return false;
        }
        if (null == myThing.getPromotionPiece()) {
            return false;
        }
        if (myPromotionPiece != myThing.getPromotionPiece()) {
            return false;
        }
        return true;
    }
    public int hashCode() {
        int i = 2;
        if (myPromotionPiece == null) {
            i = 5;
        }
        else {
            if (myPromotionPiece == ChessPiece.PieceType.KNIGHT) {
                i = 1;
            }
            if (myPromotionPiece == ChessPiece.PieceType.PAWN) {
                i = 7;
            }
            if (myPromotionPiece == ChessPiece.PieceType.QUEEN) {
                i = 3;
            }
            if (myPromotionPiece == ChessPiece.PieceType.KING) {
                i = 0;
            }
            if (myPromotionPiece == ChessPiece.PieceType.BISHOP) {
                i = 4;
            }
        }
        int j = myEndPosition.hashCode();
        if (j > 63) {
            j = j - 64;
        }
        int k = myStartPosition.hashCode();
        if (k > 63) {
            k = k - 64;
        }
        return 4096 * i + 64 * j + k;
    }
    public ChessMove clone() {
        ChessMove clonedThing = new ChessMove(myStartPosition.clone(), myEndPosition.clone(), null);
        if (myPromotionPiece == null) {
            return clonedThing;
        }
        clonedThing = new ChessMove(myStartPosition.clone(), myEndPosition.clone(), myPromotionPiece);
        return clonedThing;
    }
}

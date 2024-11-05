package chess;

/**
 * Represents moving a chess piece on a chessboard
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessMove {

    private ChessPosition my_start_position;
    private ChessPosition my_end_position;
    private ChessPiece.PieceType my_promotion_piece;
    public ChessMove(ChessPosition startPosition, ChessPosition endPosition,
                     ChessPiece.PieceType promotionPiece) {
        my_start_position = startPosition.clone();
        my_end_position = endPosition.clone();
        if (promotionPiece == null) {
            my_promotion_piece = null;
        }else{
            my_promotion_piece = promotionPiece;
        }
    }

    /**
     * @return ChessPosition of starting location
     */
    public ChessPosition getStartPosition() {
        return this.my_start_position.clone();
    }

    /**
     * @return ChessPosition of ending location
     */
    public ChessPosition getEndPosition() {
        return this.my_end_position.clone();
    }

    /**
     * Gets the type of piece to promote a pawn to if pawn promotion is part of this
     * chess move
     *
     * @return Type of piece to promote a pawn to, or null if no promotion
     */
    public ChessPiece.PieceType getPromotionPiece() {
        if (null == this.my_promotion_piece) {
            return null;
        }
        return this.my_promotion_piece;
    }
    public boolean equals(Object compare_with) {
        if (compare_with == null) {
            return false;
        }
        if (compare_with.getClass() != getClass()) {
            return false;
        }
        ChessMove my_thing = (ChessMove) compare_with;
        if (my_thing.getStartPosition().hashCode() != my_start_position.hashCode()) {
            return false;
        }
        if (my_thing.getEndPosition().hashCode() != my_end_position.hashCode()) {
            return false;
        }
        if (my_promotion_piece == null) {
            if (null == my_thing.getPromotionPiece()) {
                return true;
            }
            return false;
        }
        if (null == my_thing.getPromotionPiece()) {
            return false;
        }
        if (my_promotion_piece != my_thing.getPromotionPiece()) {
            return false;
        }
        return true;
    }
    public int hashCode() {
        int i = 2;
        if (my_promotion_piece == null) {
            i = 5;
        }
        else {
            if (my_promotion_piece == ChessPiece.PieceType.KNIGHT) {
                i = 1;
            }
            if (my_promotion_piece == ChessPiece.PieceType.PAWN) {
                i = 7;
            }
            if (my_promotion_piece == ChessPiece.PieceType.QUEEN) {
                i = 3;
            }
            if (my_promotion_piece == ChessPiece.PieceType.KING) {
                i = 0;
            }
            if (my_promotion_piece == ChessPiece.PieceType.BISHOP) {
                i = 4;
            }
        }
        int j = my_end_position.hashCode();
        if (j > 63) {
            j = j - 64;
        }
        int k = my_start_position.hashCode();
        if (k > 63) {
            k = k - 64;
        }
        return 4096 * i + 64 * j + k;
    }
    public ChessMove clone() {
        ChessMove cloned_thing = new ChessMove(my_start_position.clone(), my_end_position.clone(), null);
        if (my_promotion_piece == null) {
            return cloned_thing;
        }
        cloned_thing = new ChessMove(my_start_position.clone(), my_end_position.clone(), my_promotion_piece);
        return cloned_thing;
    }
}

package chess;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {

    private ChessPiece[] chessPieceGrid;
    public ChessBoard() {
        chessPieceGrid = new ChessPiece[64];
        for (int i = 0; i < 64; i = i + 1) {
            chessPieceGrid[i] = null;
        }
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        int theRow = position.getRow() - 1;
        int theColumn = position.getColumn() - 1;
        if (piece == null) {
            chessPieceGrid[theColumn * 8 + theRow] = null;
        }
        else {
            chessPieceGrid[theColumn * 8 + theRow] = piece.clone();
        }
    }
    public ChessPiece getPiece(ChessPosition position) {
        int theRow = position.getRow() - 1;
        int positionColumn = position.getColumn() - 1;
        try {
            return chessPieceGrid[positionColumn * 8 + theRow];
        } catch (NullPointerException ex) {
            return null;
        }
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        int i = 0;
        while (i < 64) {
            chessPieceGrid[i] = null;
            i = i + 1;
        }
        int theRow = 1;
        int positionColumn = 0;
        while (positionColumn < 8) {
            chessPieceGrid[positionColumn * 8 + theRow] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
            positionColumn++;
        }
        theRow = 6;
        while (positionColumn > 0) {
            positionColumn = positionColumn - 1;
            chessPieceGrid[positionColumn * 8 + theRow] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        }
        chessPieceGrid[0] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        chessPieceGrid[8] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        chessPieceGrid[16] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        chessPieceGrid[24] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);
        chessPieceGrid[32] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING);
        chessPieceGrid[40] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        chessPieceGrid[48] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        chessPieceGrid[56] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        chessPieceGrid[7] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        chessPieceGrid[15] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        chessPieceGrid[23] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        chessPieceGrid[31] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN);
        chessPieceGrid[39] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);
        chessPieceGrid[47] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        chessPieceGrid[55] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        chessPieceGrid[63] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
    }
    public boolean equals(Object compareWith) {
        if (compareWith == null) {
            return false;
        }
        if (compareWith.getClass() != getClass()) {
            return false;
        }
        ChessBoard myThing = (ChessBoard) compareWith;
        int i = 0;
        while (i < 64) {
            ChessPosition myChessPosition = new ChessPosition((i % 8) + 1, (i / 8) + 1);
            ChessPiece thatChessPiece = myThing.getPiece(myChessPosition);
            ChessPiece thisChessPiece = this.getPiece(myChessPosition);
            if (thisChessPiece == null) {
                return thatChessPiece == null;
            }
            else if (thatChessPiece == null) {
                return false;
            }
            else if (thatChessPiece.equals(thisChessPiece) == false) {
                return false;
            }
            i = 1 + i;
        }
        return true;
    }
    public ChessBoard clone() {
        ChessBoard clonedThing = new ChessBoard();
        int i = 0;
        while (i < 64) {
            ChessPosition myChessPosition = new ChessPosition((i % 8) + 1, (i / 8) + 1);
            ChessPiece thisChessPiece = this.getPiece(myChessPosition);
            if (thisChessPiece == null) {
                clonedThing.addPiece(myChessPosition, null);
            }
            else {
                clonedThing.addPiece(myChessPosition, thisChessPiece);
            }
            i = 1 + i;
        }
        return clonedThing;
    }
    public int hashCode() {
        if (getPiece(new ChessPosition(3, 7)) == null) {
            return 36;
        }
        return getPiece(new ChessPosition(3, 7)).hashCode();
    }
}

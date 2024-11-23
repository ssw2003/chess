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

    private ChessGame.TeamColor myColor;
    private ChessPiece.PieceType myType;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        myColor = pieceColor;
        myType = type;
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
        return myColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public ChessPiece.PieceType getPieceType() {
        String ch = "ch";
        return myType;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    private Collection<ChessMove> theMoves(ChessBoard board,ChessPosition m, ChessPosition[] chessPositions, ChessGame.TeamColor color) {
        Collection<ChessMove> moves = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            if (!chessPositions[i].equals(m)) {
                if (board.getPiece(chessPositions[i]) == null) {
                    moves.add(new ChessMove(m, chessPositions[i], null));
                } else if (board.getPiece(chessPositions[i]).getTeamColor() != color) {
                    moves.add(new ChessMove(m, chessPositions[i], null));
                }
            }
        }
        return moves;
    }
    private Collection<ChessMove> kingMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color) {
        ChessPosition[] chessPositions = new ChessPosition[9];
        int k = 0;
        for (int i = myPosition.getRow() - 1; i <= myPosition.getRow() + 1; i++) {
            for (int j = myPosition.getColumn() - 1; j <= myPosition.getColumn() + 1; j++) {
                if (i == 0 || i == 9 || j == 0 || j == 9) {
                    chessPositions[k] = myPosition.clone();
                }
                else {
                    chessPositions[k] = new ChessPosition(i, j);
                }
                k++;
            }
        }
        return theMoves(board, myPosition, chessPositions, color);
    }
    private Collection<ChessMove> knightMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color) {
        ChessPosition[] chessPositions = new ChessPosition[9];
        int[] rows = new int[9];
        int[] columns = new int[9];
        rows[3] = myPosition.getRow();
        columns[3] = myPosition.getColumn();
        rows[0] = rows[3] - 2;
        rows[1] = rows[3] - 2;
        rows[2] = rows[3] - 1;
        rows[4] = rows[3] - 1;
        rows[5] = rows[3] + 1;
        rows[6] = rows[3] + 1;
        rows[7] = rows[3] + 2;
        rows[8] = rows[3] + 2;
        columns[0] = columns[3] - 1;
        columns[1] = columns[3] + 1;
        columns[2] = columns[3] + 2;
        columns[4] = columns[3] - 2;
        columns[5] = columns[3] - 2;
        columns[6] = columns[3] + 2;
        columns[7] = columns[3] + 1;
        columns[8] = columns[3] - 1;
        int k = 0;
        while (k < 9) {
            if (rows[k] < 1 || rows[k] > 8 || columns[k] < 1 || columns[k] > 8) {
                chessPositions[k] = (myPosition.clone());
            }
            else {
                chessPositions[k] = new ChessPosition(rows[k], columns[k]);
            }
            k = k + 1;
        }
        return theMoves(board, myPosition, chessPositions, color);
    }
    private Collection<ChessMove> loopMoves(int[] directions, ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color) {
        Collection<ChessMove> moves = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            int r = (directions[i] / 3) - 1;
            int c = 1 - (directions[i] % 3);
            if (directions[i] == 4) {
                r = 46;
            }
            int s = r + myPosition.getRow();
            int d = c + myPosition.getColumn();
            while (s > 0 && s < 9 && d > 0 && d < 9) {
                if (board.getPiece(new ChessPosition(s, d)) == null) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(s, d), null));
                } else if (board.getPiece(new ChessPosition(s, d)).getTeamColor() == color) {
                    break;
                } else {
                    moves.add(new ChessMove(myPosition, new ChessPosition(s, d), null));
                    break;
                }
                s = s + r;
                d = d + c;
            }
        }
        return moves;
    }
    private Collection<ChessMove> pawnMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color) {
        boolean canAttackLeft = false;
        Collection<ChessMove> moves = new ArrayList<>();
        boolean canAttackRight = false;
        boolean canAdvance = false;
        boolean canDoubleAdvance = false;
        boolean canPromote = (myPosition.getRow() == 7);
        if (color == ChessGame.TeamColor.BLACK) {
            canPromote = (myPosition.getRow() == 2);
        }
        canDoubleAdvance = (myPosition.getRow() == 2);
        if (color == ChessGame.TeamColor.BLACK) {
            canDoubleAdvance = (myPosition.getRow() == 7);
        }
        ChessPosition newPosition;
        if (canDoubleAdvance) {
            newPosition = new ChessPosition(4, myPosition.getColumn());
            if (color == ChessGame.TeamColor.BLACK) {
                newPosition = new ChessPosition(5, myPosition.getColumn());
            }
            if (board.getPiece(newPosition) != null) {
                canDoubleAdvance = false;
            }
        }
        if (myPosition.getColumn() != 1) {
            newPosition = new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() - 1);
            if (color == ChessGame.TeamColor.BLACK) {
                newPosition = new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() - 1);
            }
            if (board.getPiece(newPosition) != null) {
                if (board.getPiece(newPosition).getTeamColor() != color) {
                    canAttackLeft = true;
                }
            }
        }
        if (myPosition.getColumn() != 8) {
            newPosition = new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() + 1);
            if (color == ChessGame.TeamColor.BLACK) {
                newPosition = new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() + 1);
            }
            if (board.getPiece(newPosition) != null) {
                if (board.getPiece(newPosition).getTeamColor() != color) {
                    canAttackRight = true;
                }
            }
        }
        newPosition = new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn());
        if (color == ChessGame.TeamColor.BLACK) {
            newPosition = new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn());
        }
        canAdvance = board.getPiece(newPosition) == null;
        canDoubleAdvance = canAdvance && canDoubleAdvance;
        Collection<ChessPiece> types = new ArrayList<>();
        if (canPromote) {
            types.add(new ChessPiece(ChessGame.TeamColor.WHITE, PieceType.QUEEN));
            types.add(new ChessPiece(ChessGame.TeamColor.WHITE, PieceType.KNIGHT));
            types.add(new ChessPiece(ChessGame.TeamColor.WHITE, PieceType.ROOK));
            types.add(new ChessPiece(ChessGame.TeamColor.WHITE, PieceType.BISHOP));
        }
        else {
            types.add(new ChessPiece(ChessGame.TeamColor.WHITE, PieceType.KING));
        }
        Collection<ChessPosition> positions = new ArrayList<>();
        int i = -1;
        if (color == ChessGame.TeamColor.BLACK) {
            i = 1;
        }
        if (canAttackRight) {
            positions.add(new ChessPosition(myPosition.getRow() - i, myPosition.getColumn() + 1));
        }
        if (canAttackLeft) {
            positions.add(new ChessPosition(myPosition.getRow() - i, myPosition.getColumn() - 1));
        }
        if (canAdvance) {
            positions.add(new ChessPosition(myPosition.getRow() - i, myPosition.getColumn()));
        }
        if (canDoubleAdvance) {
            positions.add(new ChessPosition(myPosition.getRow() - i * 2, myPosition.getColumn()));
        }
        for (ChessPosition p: positions) {
            for (ChessPiece q: types) {
                if (q.getPieceType() == PieceType.KING) {
                    moves.add(new ChessMove(myPosition, p, null));
                }
                else {
                    moves.add(new ChessMove(myPosition, p, q.getPieceType()));
                }
            }
        }
        return  moves;
    }
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        if (board.getPiece(myPosition).getPieceType() == PieceType.KING) {
            return kingMoves(board, myPosition, board.getPiece(myPosition).getTeamColor());
        }
        if (board.getPiece(myPosition).getPieceType() == PieceType.KNIGHT) {
            return knightMoves(board, myPosition, board.getPiece(myPosition).getTeamColor());
        }
        if (board.getPiece(myPosition).getPieceType() != PieceType.PAWN) {
            int[] directions = new int[8];
            if (board.getPiece(myPosition).getPieceType() == PieceType.ROOK) {
                directions = new int[]{1, 3, 5, 7, 4, 4, 4, 4};
            }
            if (board.getPiece(myPosition).getPieceType() == PieceType.BISHOP) {
                directions = new int[]{0, 2, 6, 8, 4, 4, 4, 4};
            }
            if (board.getPiece(myPosition).getPieceType() == PieceType.QUEEN) {
                directions = new int[]{0, 1, 2, 3, 5, 6, 7, 8};
            }
            return loopMoves(directions, board, myPosition, board.getPiece(myPosition).getTeamColor());
        }
        return pawnMoves(board, myPosition, board.getPiece(myPosition).getTeamColor());
    }
    public boolean equals(Object compareWith) {
        if (compareWith == null) {
            return false;
        }
        if (compareWith.getClass() != getClass()) {
            return false;
        }
        ChessPiece myThing = (ChessPiece) compareWith;
        return (myThing.getPieceType() == myType && myThing.getTeamColor() == myColor);
    }
    public int hashCode() {
        int theColor = 23;
        int firstPhoneme = 63;
        if (myType == ChessPiece.PieceType.KING) {
            firstPhoneme = 11;
        }
        if (myType == ChessPiece.PieceType.QUEEN) {
            firstPhoneme = 17;
        }
        if (myType == ChessPiece.PieceType.BISHOP) {
            firstPhoneme = 2;
        }
        if (myType == ChessPiece.PieceType.KNIGHT) {
            firstPhoneme = 14;
        }
        if (myType == ChessPiece.PieceType.ROOK) {
            firstPhoneme = 18;
        }
        if (myType == ChessPiece.PieceType.PAWN) {
            firstPhoneme = 16;
        }
        if (myColor == ChessGame.TeamColor.BLACK) {
            theColor = 2;
        }
        return firstPhoneme + theColor;
    }
    public ChessPiece clone() {
        ChessPiece clonedThing = new ChessPiece(myColor, myType);
        return clonedThing;
    }
    public String toString() {
        int i = 0;
        if (this.getPieceType() == PieceType.PAWN) {
            i = 5;
        }
        if (this.getPieceType() == PieceType.QUEEN) {
            i = 1;
        }
        if (this.getPieceType() == PieceType.ROOK) {
            i = 2;
        }
        if (this.getPieceType() == PieceType.KNIGHT) {
            i = 3;
        }
        if (this.getPieceType() == PieceType.BISHOP) {
            i = 4;
        }
        if (this.getTeamColor() == ChessGame.TeamColor.BLACK) {
            i = i + 6;
        }
        String[] s = new String[12];
        s = new String[]{ "♔", "♕", "♗", "♘", "♖", "♙", "♚", "♛", "♝", "♞", "♜", "♟" };
        return s[i];
    }
}

package chess;

/**
 * Represents a single square position on a chess board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPosition {
    private int r;
    private int c;

    public ChessPosition(int row, int col) {
        r = row;
        c = col;
    }

    /**
     * @return which row this position is in
     * 1 codes for the bottom row
     */
    public int getRow() {
        return r;
    }

    /**
     * @return which column this position is in
     * 1 codes for the left row
     */
    public int getColumn() {
        return c;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        ChessPosition ch = (ChessPosition) obj;
        return (r == ch.getRow() && c == ch.getColumn());
    }

    @Override
    public int hashCode() {
        return r + c * 8 - 9;
    }
    public ChessPosition clone() {
        return new ChessPosition(r, c);
    }
}

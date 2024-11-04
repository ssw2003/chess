package chess;

/**
 * Represents a single square position on a chess board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPosition {

    public ChessPosition(int row, int col) {
        private int my_row = row;
        private int my_column = col;
    }

    /**
     * @return which row this position is in
     * 1 codes for the bottom row
     */
    public int getRow() {
        return this.my_row;
        throw new RuntimeException("Not implemented");
    }

    /**
     * @return which column this position is in
     * 1 codes for the left row
     */
    public int getColumn() {
        return this.my_column;
        throw new RuntimeException("Not implemented");
    }

    public boolean equals(Object compare_with) {
        if (compare_with == null) {
            return false;
        }
        if (compare_with.getClass() != getClass()) {
            return false;
        }
        ChessPosition my_thing = (ChessPosition) compare_with;
        if (my_thing.getRow() != my_row) {
            return false;
        }
        if (my_thing.getColumn() != my_column) {
            return false;
        }
        return true;
    }
    public int hashCode() {
        return my_row * 8 + my_column;
    }
    public ChessPosition clone() {
        ChessPosition cloned_thing = new ChessPosition(my_row, my_column);
        return cloned_thing;
    }
}

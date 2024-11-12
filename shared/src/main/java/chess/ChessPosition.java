package chess;

/**
 * Represents a single square position on a chess board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPosition {

    private int myRow;
    private int myColumn;
    public ChessPosition(int row, int col) {
        myRow = row;
        myColumn = col;
    }

    /**
     * @return which row this position is in
     * 1 codes for the bottom row
     */
    public int getRow() {
        return this.myRow;
    }

    /**
     * @return which column this position is in
     * 1 codes for the left row
     */
    public int getColumn() {
        return this.myColumn;
    }

    public boolean equals(Object compare_with) {
        if (compare_with == null) {
            return false;
        }
        if (compare_with.getClass() != getClass()) {
            return false;
        }
        ChessPosition my_thing = (ChessPosition) compare_with;
        if (my_thing.getRow() != myRow) {
            return false;
        }
        if (my_thing.getColumn() != myColumn) {
            return false;
        }
        return true;
    }
    public int hashCode() {
        return myRow * 8 + myColumn;
    }
    public ChessPosition clone() {
        ChessPosition cloned_thing = new ChessPosition(myRow, myColumn);
        return cloned_thing;
    }
}

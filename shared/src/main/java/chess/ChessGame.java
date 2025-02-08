package chess;

import java.util.Collection;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private TeamColor theTurn;
    private ChessBoard theBoard;
    private boolean whiteKingRookMoved;
    private boolean whiteQueenRookMoved;
    private boolean blackKingRookMoved;
    private boolean blackQueenRookMoved;
    private int lastMove;

    public ChessGame() {
        theTurn = TeamColor.WHITE;
        theBoard = new ChessBoard();
        theBoard.resetBoard();
        whiteKingRookMoved = false;
        whiteQueenRookMoved = false;
        blackQueenRookMoved = false;
        blackKingRookMoved = false;
        lastMove = 0;

    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return theTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        theTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        theBoard = board.clone();
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return theBoard.clone();
    }
    private boolean convertBoolean(int m) {
        return (m % 2 == 1);
    }
    private int convertBoolean(boolean m) {
        if (m) {
            return 1;
        }
        return 0;
    }
    public int getInfo(int m) {
        if (m == 144) {
            int f = convertBoolean(whiteKingRookMoved) * 8 + 4 * convertBoolean(whiteQueenRookMoved);
            f = f + convertBoolean(blackKingRookMoved) * 2 + convertBoolean(blackQueenRookMoved);
            return 16 * lastMove + f;
        }
        lastMove = m / 16;
        whiteKingRookMoved = convertBoolean(m / 8);
        whiteQueenRookMoved = convertBoolean(m / 4);
        blackKingRookMoved = convertBoolean(m / 2);
        blackQueenRookMoved = convertBoolean(m);
        return 144;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        ChessGame ch = (ChessGame) obj;
        if (ch.getInfo(144) != getInfo(144)) {
            return false;
        }
        if (ch.getTeamTurn() != theTurn) {
            return false;
        }
        return ch.getBoard().equals(theBoard);
    }

    @Override
    public int hashCode() {
        return 67;
    }
    public ChessGame clone() {
        ChessGame cg = new ChessGame();
        cg.setBoard(theBoard);
        if (cg.getInfo(getInfo(144)) == 144) {
            cg.setTeamTurn(theTurn);
        }
        return cg;
    }
}

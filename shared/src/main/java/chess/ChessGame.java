package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private ChessGame.TeamColor whoseTurnIsIt;
    private ChessBoard theBoard = new ChessBoard();
    public ChessGame() {
whoseTurnIsIt = ChessGame.TeamColor.WHITE;
theBoard.resetBoard();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return whoseTurnIsIt;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        whoseTurnIsIt = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    // /**
    //  * Gets a valid moves for a piece at the given location
    //  *
    //  * @param startPosition the piece to get valid moves for
    //  * @return Set of valid moves for requested piece, or null if no piece at
    //  * startPosition
    //  */
    private boolean getIsCheck(ChessBoard thatBoard, ChessGame.TeamColor theirColor) {
        for (int i = 0; i < 64; i = i + 1) {
            ChessPosition newPlace = new ChessPosition(1 + (i % 8), 8 - (i / 8));
            ChessPiece newPiece = thatBoard.getPiece(newPlace);
            Collection<ChessMove> newMoves = new ArrayList<>();
            if (newPiece != null) {
                if (newPiece.getTeamColor() != theirColor) {
                    newMoves = newPiece.pieceMoves(thatBoard, newPlace);
                    for (ChessMove move: newMoves) {
                        newPiece = thatBoard.getPiece(move.getEndPosition());
                        if (newPiece != null) {
                            if (newPiece.getTeamColor() == theirColor &&
                            newPiece.getPieceType() == ChessPiece.PieceType.KING) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        Collection<ChessMove> chessMoves = new ArrayList<>();
        if (theBoard.getPiece(startPosition) == null) {
            return null;
        }

        Collection<ChessMove> possibleChessMoves = theBoard.getPiece(startPosition).pieceMoves(theBoard, startPosition);
        for (ChessMove move: possibleChessMoves) {
            ChessBoard myNewBoardThingy = theBoard.clone();
            myNewBoardThingy.addPiece(move.getEndPosition(), myNewBoardThingy.getPiece(startPosition));
            myNewBoardThingy.addPiece(startPosition, null);
            if (!getIsCheck(myNewBoardThingy, theBoard.getPiece(startPosition).getTeamColor())) {
                chessMoves.add(move.clone());
            }
        }
        return chessMoves;
    }
    private Collection<ChessMove> validMovesWithColor(ChessGame.TeamColor teamColor) {
        Collection<ChessMove> theseChessMoves = new ArrayList<>();
        for (int i = 0; i < 64; i = i + 1) {
            ChessPosition positionThingy = new ChessPosition((i % 8) + 1, 8 - (i / 8));
            if (theBoard.getPiece(positionThingy) != null) {
                if (theBoard.getPiece(positionThingy).getTeamColor() == teamColor) {
                    Collection<ChessMove> thoseChessMoves = validMoves(positionThingy);
                    for (ChessMove move: thoseChessMoves) {
                        theseChessMoves.add(move);
                    }
                }
            }
        }
        return theseChessMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        InvalidMoveException p = new InvalidMoveException();
        if (theBoard.getPiece(move.getStartPosition()) == null) {
            throw p;
        }
        if (theBoard.getPiece(move.getStartPosition()).getTeamColor() != whoseTurnIsIt) {
            throw p;
        }
        Collection<ChessMove> possible_chess_moves = validMovesWithColor(whoseTurnIsIt);
        boolean isBadMove = true;
        for (ChessMove potentialMove: possible_chess_moves) {
            if (potentialMove.equals(move)) {
                isBadMove = false;
                break;
            }
        }
        if (isBadMove) {
            throw p;
        }
        theBoard.addPiece(move.getEndPosition(), theBoard.getPiece(move.getStartPosition()));
        if (move.getPromotionPiece() != null) {
            theBoard.addPiece(move.getEndPosition(), new ChessPiece(whoseTurnIsIt, move.getPromotionPiece()));
        }
        theBoard.addPiece(move.getStartPosition(), null);
        if (getTeamTurn() == ChessGame.TeamColor.WHITE) {
            setTeamTurn(ChessGame.TeamColor.BLACK);
        }
        else {
            setTeamTurn(ChessGame.TeamColor.WHITE);
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        return getIsCheck(theBoard, teamColor);
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        return isInCheck(teamColor) && validMovesWithColor(teamColor).isEmpty();
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        return (!isInCheck(teamColor)) && validMovesWithColor(teamColor).isEmpty();
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
    public ChessGame clone() {
        ChessGame cg = new ChessGame();
        cg.setTeamTurn(whoseTurnIsIt);
        cg.setBoard(theBoard);
        return cg;
    }
    public boolean equals(Object compareWith) {
        if (compareWith == null) {
            return false;
        }
        if (compareWith.getClass() != getClass()) {
            return false;
        }
        ChessGame myThing = (ChessGame) compareWith;
        if (myThing.getTeamTurn() != whoseTurnIsIt) {
            return false;
        }
        return myThing.getBoard().equals(theBoard);
    }
    public int hashCode() {
        int i = theBoard.hashCode();
        if (whoseTurnIsIt == ChessGame.TeamColor.WHITE) {
            if (i == 2147483646) {
                return -2147483613;
            }
            if (i == 2147483647) {
                return -2147483612;
            }
            if (i == -2147483648) {
                return -2147483611;
            }
            if (i == -2147483647) {
                return -2147483610;
            }
            if (i == -2147483646) {
                return -2147483609;
            }
            if (i == 2147483613) {
                return -2147483646;
            }
            if (i == 2147483612) {
                return -2147483647;
            }
            if (i == 2147483611) {
                return -2147483648;
            }
            if (i == 2147483610) {
                return 2147483647;
            }
            if (i == 2147483609) {
                return 2147483646;
            }
            if (i > 2147483611) {
                return (i - 2147483629) - 2147483630;
            }
            return i + 37;
        }
        return i;
    }
}

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
    private boolean whiteKingHasMoved;
    private boolean blackKingHasMoved;
    private boolean whiteKingRookHasMoved;
    private boolean whiteQueenRookHasMoved;
    private boolean blackKingRookHasMoved;
    private boolean blackQueenRookHasMoved;
    private int whichPawnsHaveDoubleMoved;
    public ChessGame() {
whoseTurnIsIt = ChessGame.TeamColor.WHITE;
theBoard.resetBoard();
whiteKingHasMoved = false;
        blackKingHasMoved = false;
        whiteKingRookHasMoved = false;
        whiteQueenRookHasMoved = false;
        blackKingRookHasMoved = false;
        blackQueenRookHasMoved = false;
        whichPawnsHaveDoubleMoved = 0;
    }

    public int setHasMoved(int i) {
        int j;
        if (i == 64) {
            j = 0;
            if (whiteKingHasMoved) {
                j = j + 32;
            }
            if (whiteKingRookHasMoved) {
                j = j + 16;
            }
            if (whiteQueenRookHasMoved) {
                j = j + 8;
            }
            if (blackKingHasMoved) {
                j = j + 4;
            }
            if (blackKingRookHasMoved) {
                j = j + 2;
            }
            if (blackQueenRookHasMoved) {
                j = j + 1;
            }
            return j;
        }
        whiteKingHasMoved = (i > 31);
        whiteKingRookHasMoved = (i % 32 > 15);
        whiteQueenRookHasMoved = (i % 16 > 7);
        blackKingHasMoved = (i % 8 > 3);
        blackKingRookHasMoved = (i % 4 > 1);
        blackQueenRookHasMoved = (i % 2 == 1);
        return 64;
    }
    public int setHasMovedPawn(int i) {
        if (i == 9) {
            return whichPawnsHaveDoubleMoved;
        }
        whichPawnsHaveDoubleMoved = i;
        return 9;
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
    private boolean getMyIsCheck(Collection<ChessMove> newMoves, ChessBoard thatBoard, ChessGame.TeamColor theirColor) {
        for (ChessMove move: newMoves) {
            ChessPiece newPiece = thatBoard.getPiece(move.getEndPosition());
            if (newPiece != null) {
                if (newPiece.getTeamColor() == theirColor &&
                        newPiece.getPieceType() == ChessPiece.PieceType.KING) {
                    return true;
                }
            }
        }
        return false;
    }
    private boolean getIsCheck(ChessBoard thatBoard, ChessGame.TeamColor theirColor) {
        for (int i = 0; i < 64; i = i + 1) {
            ChessPosition newPlace = new ChessPosition(1 + (i % 8), 8 - (i / 8));
            ChessPiece newPiece = thatBoard.getPiece(newPlace);
            Collection<ChessMove> newMoves = new ArrayList<>();
            if (newPiece != null) {
                if (newPiece.getTeamColor() != theirColor) {
                    newMoves = newPiece.pieceMoves(thatBoard, newPlace);
                    if (getMyIsCheck(newMoves, thatBoard, theirColor)) {
                        return true;
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
        if (whichPawnsHaveDoubleMoved != 0) {
            TeamColor color = theBoard.getPiece(startPosition).getTeamColor();
            if (startPosition.getRow() == 5 && (startPosition.getColumn() == whichPawnsHaveDoubleMoved + 1 || startPosition.getColumn() == whichPawnsHaveDoubleMoved - 1) && color == TeamColor.WHITE) {
                chessMoves.add(new ChessMove(startPosition, new ChessPosition(6, whichPawnsHaveDoubleMoved), null));
            }
            if (startPosition.getRow() == 4 && (startPosition.getColumn() == whichPawnsHaveDoubleMoved + 1 || startPosition.getColumn() == whichPawnsHaveDoubleMoved - 1) && color == TeamColor.BLACK) {
                chessMoves.add(new ChessMove(startPosition, new ChessPosition(3, whichPawnsHaveDoubleMoved), null));
            }
        }
        if (startPosition.equals(new ChessPosition(1, 5)) && !whiteKingHasMoved && !whiteQueenRookHasMoved) {
            if (theBoard.getPiece(new ChessPosition(1, 4)) == null && theBoard.getPiece(new ChessPosition(1, 3)) == null && theBoard.getPiece(new ChessPosition(1, 2)) == null) {
                if (!getIsCheck(theBoard, TeamColor.WHITE)) {
                    ChessBoard thisBoard = theBoard.clone();
                    ChessBoard thatBoard = theBoard.clone();
                    thisBoard.addPiece(new ChessPosition(1, 5), null);
                    thisBoard.addPiece(new ChessPosition(1, 4), new ChessPiece(TeamColor.WHITE, ChessPiece.PieceType.KING));
                    thisBoard.addPiece(new ChessPosition(1, 3), new ChessPiece(TeamColor.WHITE, ChessPiece.PieceType.BISHOP));
                    thisBoard.addPiece(new ChessPosition(1, 1), null);
                    thatBoard.addPiece(new ChessPosition(1, 5), null);
                    thatBoard.addPiece(new ChessPosition(1, 4), new ChessPiece(TeamColor.WHITE, ChessPiece.PieceType.BISHOP));
                    thatBoard.addPiece(new ChessPosition(1, 3), new ChessPiece(TeamColor.WHITE, ChessPiece.PieceType.KING));
                    thatBoard.addPiece(new ChessPosition(1, 1), null);
                    if (!getIsCheck(thisBoard, TeamColor.WHITE) && !getIsCheck(thatBoard, TeamColor.WHITE)) {
                        chessMoves.add(new ChessMove(new ChessPosition(1, 5), new ChessPosition(1, 3), null));
                    }
                }
            }
        }
        if (startPosition.equals(new ChessPosition(1, 5)) && !whiteKingHasMoved && !whiteKingRookHasMoved) {
            if (theBoard.getPiece(new ChessPosition(1, 6)) == null && theBoard.getPiece(new ChessPosition(1, 7)) == null) {
                if (!getIsCheck(theBoard, TeamColor.WHITE)) {
                    ChessBoard thisBoard = theBoard.clone();
                    ChessBoard thatBoard = theBoard.clone();
                    thisBoard.addPiece(new ChessPosition(1, 5), null);
                    thisBoard.addPiece(new ChessPosition(1, 6), new ChessPiece(TeamColor.WHITE, ChessPiece.PieceType.KING));
                    thisBoard.addPiece(new ChessPosition(1, 7), new ChessPiece(TeamColor.WHITE, ChessPiece.PieceType.BISHOP));
                    thisBoard.addPiece(new ChessPosition(1, 8), null);
                    thatBoard.addPiece(new ChessPosition(1, 5), null);
                    thatBoard.addPiece(new ChessPosition(1, 6), new ChessPiece(TeamColor.WHITE, ChessPiece.PieceType.BISHOP));
                    thatBoard.addPiece(new ChessPosition(1, 7), new ChessPiece(TeamColor.WHITE, ChessPiece.PieceType.KING));
                    thatBoard.addPiece(new ChessPosition(1, 8), null);
                    if (!getIsCheck(thisBoard, TeamColor.WHITE) && !getIsCheck(thatBoard, TeamColor.WHITE)) {
                        chessMoves.add(new ChessMove(new ChessPosition(1, 5), new ChessPosition(1, 7), null));
                    }
                }
            }
        }
        if (startPosition.equals(new ChessPosition(8, 5)) && !blackKingHasMoved && !blackQueenRookHasMoved) {
            if (theBoard.getPiece(new ChessPosition(8, 4)) == null && theBoard.getPiece(new ChessPosition(8, 3)) == null && theBoard.getPiece(new ChessPosition(8, 2)) == null) {
                if (!getIsCheck(theBoard, TeamColor.BLACK)) {
                    ChessBoard thisBoard = theBoard.clone();
                    ChessBoard thatBoard = theBoard.clone();
                    thisBoard.addPiece(new ChessPosition(8, 5), null);
                    thisBoard.addPiece(new ChessPosition(8, 4), new ChessPiece(TeamColor.BLACK, ChessPiece.PieceType.KING));
                    thisBoard.addPiece(new ChessPosition(8, 3), new ChessPiece(TeamColor.BLACK, ChessPiece.PieceType.BISHOP));
                    thisBoard.addPiece(new ChessPosition(8, 1), null);
                    thatBoard.addPiece(new ChessPosition(8, 5), null);
                    thatBoard.addPiece(new ChessPosition(8, 4), new ChessPiece(TeamColor.BLACK, ChessPiece.PieceType.BISHOP));
                    thatBoard.addPiece(new ChessPosition(8, 3), new ChessPiece(TeamColor.BLACK, ChessPiece.PieceType.KING));
                    thatBoard.addPiece(new ChessPosition(8, 1), null);
                    if (!getIsCheck(thisBoard, TeamColor.BLACK) && !getIsCheck(thatBoard, TeamColor.BLACK)) {
                        chessMoves.add(new ChessMove(new ChessPosition(8, 5), new ChessPosition(8, 3), null));
                    }
                }
            }
        }
        if (startPosition.equals(new ChessPosition(8, 5)) && !blackKingHasMoved && !blackKingRookHasMoved) {
            if (theBoard.getPiece(new ChessPosition(8, 6)) == null && theBoard.getPiece(new ChessPosition(8, 7)) == null) {
                if (!getIsCheck(theBoard, TeamColor.BLACK)) {
                    ChessBoard thisBoard = theBoard.clone();
                    ChessBoard thatBoard = theBoard.clone();
                    thisBoard.addPiece(new ChessPosition(8, 5), null);
                    thisBoard.addPiece(new ChessPosition(8, 6), new ChessPiece(TeamColor.BLACK, ChessPiece.PieceType.KING));
                    thisBoard.addPiece(new ChessPosition(8, 7), new ChessPiece(TeamColor.BLACK, ChessPiece.PieceType.BISHOP));
                    thisBoard.addPiece(new ChessPosition(8, 8), null);
                    thatBoard.addPiece(new ChessPosition(8, 5), null);
                    thatBoard.addPiece(new ChessPosition(8, 6), new ChessPiece(TeamColor.BLACK, ChessPiece.PieceType.BISHOP));
                    thatBoard.addPiece(new ChessPosition(8, 7), new ChessPiece(TeamColor.BLACK, ChessPiece.PieceType.KING));
                    thatBoard.addPiece(new ChessPosition(8, 8), null);
                    if (!getIsCheck(thisBoard, TeamColor.BLACK) && !getIsCheck(thatBoard, TeamColor.BLACK)) {
                        chessMoves.add(new ChessMove(new ChessPosition(8, 5), new ChessPosition(8, 7), null));
                    }
                }
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
        Collection<ChessMove> possibleChessMoves = validMovesWithColor(whoseTurnIsIt);
        boolean isBadMove = true;
        for (ChessMove potentialMove: possibleChessMoves) {
            if (potentialMove.equals(move)) {
                isBadMove = false;
                break;
            }
        }
        if (isBadMove) {
            throw p;
        }
        boolean ep = (theBoard.getPiece(move.getStartPosition()).getPieceType() == ChessPiece.PieceType.PAWN);
        ep = ep && (theBoard.getPiece(move.getEndPosition()) == null);
        theBoard.addPiece(move.getEndPosition(), theBoard.getPiece(move.getStartPosition()));
        if (move.getPromotionPiece() != null) {
            theBoard.addPiece(move.getEndPosition(), new ChessPiece(whoseTurnIsIt, move.getPromotionPiece()));
        }
        theBoard.addPiece(move.getStartPosition(), null);
        if (ep) {
            theBoard.addPiece(new ChessPosition(move.getStartPosition().getRow(), move.getEndPosition().getColumn()), null);
        }
        ep = theBoard.getPiece(move.getEndPosition()).getPieceType() == ChessPiece.PieceType.KING;
        ep = ep && ((move.getEndPosition().getColumn() - move.getStartPosition().getColumn() + 8) % 4 == 2);
        if (ep) {
            theBoard.addPiece(new ChessPosition(move.getStartPosition().getRow(), (move.getEndPosition().getColumn() + move.getStartPosition().getColumn()) / 2), new ChessPiece(whoseTurnIsIt, ChessPiece.PieceType.ROOK));
            theBoard.addPiece(new ChessPosition(move.getStartPosition().getRow(), ((move.getEndPosition().getColumn() + 3) / 4) * 7 - 6), null);
        }
        if (theBoard.getPiece(move.getEndPosition()).getPieceType() == ChessPiece.PieceType.KING) {
            if (whoseTurnIsIt == TeamColor.WHITE) {
                whiteKingHasMoved = true;
            }
            else {
                blackKingHasMoved = true;
            }
        }
        if (move.getStartPosition().equals(new ChessPosition(1, 1))) {
            whiteQueenRookHasMoved = true;
        } else if (move.getStartPosition().equals(new ChessPosition(1, 8))) {
            whiteKingRookHasMoved = true;
        } else if (move.getStartPosition().equals(new ChessPosition(8, 1))) {
            blackQueenRookHasMoved = true;
        } else if (move.getStartPosition().equals(new ChessPosition(8, 8))) {
            blackKingRookHasMoved = true;
        }
        whichPawnsHaveDoubleMoved = 0;
        if (theBoard.getPiece(move.getEndPosition()).getPieceType() == ChessPiece.PieceType.PAWN) {
            if (move.getStartPosition().getRow() == 2 || move.getStartPosition().getRow() == 7) {
                if (move.getEndPosition().getRow() == 4 || move.getEndPosition().getRow() == 5) {
                    whichPawnsHaveDoubleMoved = move.getEndPosition().getColumn();
                }
            }
        }
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
        whiteKingHasMoved = false;
        blackKingHasMoved = false;
        whiteKingRookHasMoved = false;
        whiteQueenRookHasMoved = false;
        blackKingRookHasMoved = false;
        blackQueenRookHasMoved = false;
        whichPawnsHaveDoubleMoved = 0;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        String ch = "ch";
        return theBoard.clone();
    }
    public ChessGame clone() {
        ChessGame cg = new ChessGame();
        cg.setTeamTurn(whoseTurnIsIt);
        cg.setBoard(theBoard);
        cg.setHasMoved(setHasMoved(64));
        cg.setHasMovedPawn(setHasMovedPawn(9));
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
        if (setHasMoved(64) != myThing.setHasMoved(64)) {
            return false;
        }
        if (setHasMovedPawn(9) != myThing.setHasMovedPawn(9)) {
            return false;
        }
        if (myThing.getTeamTurn() != whoseTurnIsIt) {
            return false;
        }
        return myThing.getBoard().equals(theBoard);
    }
    public int hashCode() {
        int i = theBoard.hashCode();
        if (whoseTurnIsIt == ChessGame.TeamColor.WHITE) {
            return i + 37;
        }
        return i;
    }
}

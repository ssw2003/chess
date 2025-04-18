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
    private TeamColor theTurn;
    private ChessBoard theBoard;
    private boolean whiteKingRookMoved;
    private boolean whiteQueenRookMoved;
    private boolean blackKingRookMoved;
    private boolean blackQueenRookMoved;
    private boolean whiteHasResigned;
    private boolean blackHasResigned;
    private int lastMove;

    public ChessGame() {
        theTurn = TeamColor.WHITE;
        theBoard = new ChessBoard();
        theBoard.resetBoard();
        whiteKingRookMoved = false;
        whiteQueenRookMoved = false;
        blackQueenRookMoved = false;
        blackKingRookMoved = false;
        whiteHasResigned = false;
        blackHasResigned = false;
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
        if (theBoard.getPiece(startPosition) == null) {
            return null;
        }
        Collection<ChessMove> cm = new ArrayList<>();
        boolean mC = false;
        if (theBoard.getPiece(startPosition).getPieceType() == ChessPiece.PieceType.PAWN) {
            if (theTurn == theBoard.getPiece(startPosition).getTeamColor()) {
                if ((startPosition.getColumn() - lastMove == 1 || lastMove - startPosition.getColumn() == 1) && lastMove != 0) {
                    mC = true;
                }
            }
        }
        if (mC) {
            mC = (startPosition.getRow() == 4);
            if (theTurn == TeamColor.WHITE) {
                mC = (startPosition.getRow() == 5);
            }
        }
        ChessBoard tempBoard = theBoard.clone();
        ChessGame tempGame = new ChessGame();
        if (mC) {
            tempBoard.addPiece(new ChessPosition(startPosition.getRow(), lastMove), null);
            tempBoard.addPiece(new ChessPosition(startPosition.getRow() * 3 - 9, lastMove), tempBoard.getPiece(startPosition));
            tempBoard.addPiece(startPosition, null);
            tempGame.setBoard(tempBoard);
            if (tempGame.isInCheck(theTurn)) {
                mC = false;
            }
        }
        if (mC) {
            cm.add(new ChessMove(startPosition, new ChessPosition(startPosition.getRow() * 3 - 9, lastMove), null));
        }
        mC = (theBoard.getPiece(startPosition).equals(new ChessPiece(TeamColor.WHITE, ChessPiece.PieceType.KING)));
        mC = mC && isEqu(new ChessPosition(1, 8), new ChessPiece(TeamColor.WHITE, ChessPiece.PieceType.ROOK));
        mC = mC && (!whiteKingRookMoved) && startPosition.equals(new ChessPosition(1, 5));
        mC = mC && theBoard.getPiece(new ChessPosition(1, 6)) == null && theBoard.getPiece(new ChessPosition(1, 7)) == null;
        if (mC) {
            tempBoard = theBoard.clone();
            tempBoard.addPiece(new ChessPosition(1, 5), new ChessPiece(TeamColor.WHITE, ChessPiece.PieceType.KING));
            tempBoard.addPiece(new ChessPosition(1, 6), new ChessPiece(TeamColor.WHITE, ChessPiece.PieceType.KING));
            tempBoard.addPiece(new ChessPosition(1, 7), new ChessPiece(TeamColor.WHITE, ChessPiece.PieceType.KING));
            tempBoard.addPiece(new ChessPosition(1, 8), null);
            tempGame.setBoard(tempBoard);
            if (!tempGame.isInCheck(TeamColor.WHITE)) {
                cm.add(new ChessMove(startPosition, new ChessPosition(1, 7), null));
            }
        }
        mC = (theBoard.getPiece(startPosition).equals(new ChessPiece(TeamColor.WHITE, ChessPiece.PieceType.KING)));
        mC = mC && isEqu(new ChessPosition(1, 1), new ChessPiece(TeamColor.WHITE, ChessPiece.PieceType.ROOK));
        mC = mC && (!whiteQueenRookMoved) && startPosition.equals(new ChessPosition(1, 5));
        mC = mC && theBoard.getPiece(new ChessPosition(1, 4)) == null && theBoard.getPiece(new ChessPosition(1, 3)) == null;
        mC = mC && theBoard.getPiece(new ChessPosition(1, 2)) == null;
        if (mC) {
            tempBoard = theBoard.clone();
            tempBoard.addPiece(new ChessPosition(1, 5), new ChessPiece(TeamColor.WHITE, ChessPiece.PieceType.KING));
            tempBoard.addPiece(new ChessPosition(1, 4), new ChessPiece(TeamColor.WHITE, ChessPiece.PieceType.KING));
            tempBoard.addPiece(new ChessPosition(1, 3), new ChessPiece(TeamColor.WHITE, ChessPiece.PieceType.KING));
            tempBoard.addPiece(new ChessPosition(1, 1), null);
            tempGame.setBoard(tempBoard);
            if (!tempGame.isInCheck(TeamColor.WHITE)) {
                cm.add(new ChessMove(startPosition, new ChessPosition(1, 3), null));
            }
        }
        mC = (theBoard.getPiece(startPosition).equals(new ChessPiece(TeamColor.BLACK, ChessPiece.PieceType.KING)));
        mC = mC && isEqu(new ChessPosition(8, 8), new ChessPiece(TeamColor.BLACK, ChessPiece.PieceType.ROOK));
        mC = mC && (!blackKingRookMoved) && startPosition.equals(new ChessPosition(8, 5));
        mC = mC && theBoard.getPiece(new ChessPosition(8, 6)) == null && theBoard.getPiece(new ChessPosition(8, 7)) == null;
        if (mC) {
            tempBoard = theBoard.clone();
            tempBoard.addPiece(new ChessPosition(8, 5), new ChessPiece(TeamColor.BLACK, ChessPiece.PieceType.KING));
            tempBoard.addPiece(new ChessPosition(8, 6), new ChessPiece(TeamColor.BLACK, ChessPiece.PieceType.KING));
            tempBoard.addPiece(new ChessPosition(8, 7), new ChessPiece(TeamColor.BLACK, ChessPiece.PieceType.KING));
            tempBoard.addPiece(new ChessPosition(8, 8), null);
            tempGame.setBoard(tempBoard);
            if (!tempGame.isInCheck(TeamColor.BLACK)) {
                cm.add(new ChessMove(startPosition, new ChessPosition(8, 7), null));
            }
        }
        mC = (theBoard.getPiece(startPosition).equals(new ChessPiece(TeamColor.BLACK, ChessPiece.PieceType.KING)));
        mC = mC && isEqu(new ChessPosition(8, 1), new ChessPiece(TeamColor.BLACK, ChessPiece.PieceType.ROOK));
        mC = mC && (!blackQueenRookMoved) && startPosition.equals(new ChessPosition(8, 5));
        mC = mC && theBoard.getPiece(new ChessPosition(8, 4)) == null && theBoard.getPiece(new ChessPosition(8, 3)) == null;
        mC = mC && theBoard.getPiece(new ChessPosition(8, 2)) == null;
        if (mC) {
            tempBoard = theBoard.clone();
            tempBoard.addPiece(new ChessPosition(8, 5), new ChessPiece(TeamColor.BLACK, ChessPiece.PieceType.KING));
            tempBoard.addPiece(new ChessPosition(8, 4), new ChessPiece(TeamColor.BLACK, ChessPiece.PieceType.KING));
            tempBoard.addPiece(new ChessPosition(8, 3), new ChessPiece(TeamColor.BLACK, ChessPiece.PieceType.KING));
            tempBoard.addPiece(new ChessPosition(8, 1), null);
            tempGame.setBoard(tempBoard);
            if (!tempGame.isInCheck(TeamColor.BLACK)) {
                cm.add(new ChessMove(startPosition, new ChessPosition(8, 3), null));
            }
        }
        return valMoves(cm, startPosition);
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        InvalidMoveException thrower = new InvalidMoveException();
        Collection<ChessMove> ccm = validMoves(move.getStartPosition());
        if (ccm == null || whiteHasResigned || blackHasResigned) {
            throw thrower;
        }
        boolean canMakeIt = false;
        for (ChessMove cm: ccm) {
            if (cm.equals(move)) {
                canMakeIt = true;
            }
        }
        if (!canMakeIt) {
            throw thrower;
        }
        if (theBoard.getPiece(move.getStartPosition()).getTeamColor() != theTurn) {
            throw thrower;
        }
        ChessPosition i = new ChessPosition(move.getStartPosition().getRow(), 1);
        ChessPosition j = new ChessPosition(move.getStartPosition().getRow(), 4);
        if (theBoard.getPiece(move.getStartPosition()).getPieceType() == ChessPiece.PieceType.KING) {
            if ((move.getStartPosition().getColumn() - move.getEndPosition().getColumn() + 8) % 4 == 2) {
                if (move.getEndPosition().getColumn() > 4) {
                    i = new ChessPosition(move.getStartPosition().getRow(), 8);
                    j = new ChessPosition(move.getStartPosition().getRow(), 6);
                }
                theBoard.addPiece(j, theBoard.getPiece(i));
                theBoard.addPiece(i, null);
            }
        }
        if (theBoard.getPiece(move.getStartPosition()).getPieceType() == ChessPiece.PieceType.PAWN) {
            if (move.getStartPosition().getColumn() != move.getEndPosition().getColumn()) {
                if (theBoard.getPiece(move.getEndPosition()) == null) {
                    theBoard.addPiece(new ChessPosition(move.getStartPosition().getRow(), move.getEndPosition().getColumn()), null);
                }
            }
        }
        lastMove = 0;
        if (theBoard.getPiece(move.getStartPosition()).getPieceType() == ChessPiece.PieceType.PAWN) {
            if ((move.getEndPosition().getRow() - move.getStartPosition().getRow() + 8) % 4 == 2) {
                lastMove = move.getEndPosition().getColumn();
            }
        }
        if (move.getStartPosition().equals(new ChessPosition(1, 1))) {
            whiteQueenRookMoved = true;
        }
        if (move.getStartPosition().equals(new ChessPosition(1, 5))) {
            whiteKingRookMoved = true;
            whiteQueenRookMoved = true;
        }
        if (move.getStartPosition().equals(new ChessPosition(1, 8))) {
            whiteKingRookMoved = true;
        }
        if (move.getStartPosition().equals(new ChessPosition(8, 1))) {
            blackQueenRookMoved = true;
        }
        if (move.getStartPosition().equals(new ChessPosition(8, 5))) {
            blackKingRookMoved = true;
            blackQueenRookMoved = true;
        }
        if (move.getStartPosition().equals(new ChessPosition(8, 8))) {
            blackKingRookMoved = true;
        }
        if (move.getEndPosition().equals(new ChessPosition(1, 1))) {
            whiteQueenRookMoved = true;
        }
        if (move.getEndPosition().equals(new ChessPosition(1, 5))) {
            whiteKingRookMoved = true;
            whiteQueenRookMoved = true;
        }
        if (move.getEndPosition().equals(new ChessPosition(1, 8))) {
            whiteKingRookMoved = true;
        }
        if (move.getEndPosition().equals(new ChessPosition(8, 1))) {
            blackQueenRookMoved = true;
        }
        if (move.getEndPosition().equals(new ChessPosition(8, 5))) {
            blackKingRookMoved = true;
            blackQueenRookMoved = true;
        }
        if (move.getEndPosition().equals(new ChessPosition(8, 8))) {
            blackKingRookMoved = true;
        }
        theBoard.addPiece(move.getEndPosition(), theBoard.getPiece(move.getStartPosition()));
        theBoard.addPiece(move.getStartPosition(), null);
        if (move.getPromotionPiece() != null) {
            theBoard.addPiece(move.getEndPosition(), new ChessPiece(theTurn, move.getPromotionPiece()));
        }
        boolean whitesTurn = theTurn == TeamColor.WHITE;
        if (whitesTurn) {
            theTurn = TeamColor.BLACK;
        } else {
            theTurn = TeamColor.WHITE;
        }
    }
    public void attemptResign(TeamColor tC) throws InvalidMoveException {
        InvalidMoveException thrower = new InvalidMoveException();
        if (whiteHasResigned || blackHasResigned) {
            throw thrower;
        }
        if (isInCheckmate(TeamColor.WHITE) || isInStalemate(TeamColor.BLACK)) {
            throw thrower;
        }
        if (isInCheckmate(TeamColor.BLACK) || isInStalemate(TeamColor.WHITE)) {
            throw thrower;
        }
        if (tC == TeamColor.WHITE) {
            whiteHasResigned = true;
        }
        else {
            blackHasResigned = true;
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        Collection<ChessPosition> squareThings = new ArrayList<>();
        ChessPosition psit;
        int i = 0;
        while (i < 64) {
            psit = new ChessPosition(8 - (i / 8), 1 + (i % 8));
            i = i + 1;
            if (theBoard.getPiece(psit) != null) {
                if (theBoard.getPiece(psit).getTeamColor() != teamColor) {
                    squareThings.add(psit.clone());
                }
            }
        }
        for (ChessPosition sT: squareThings) {
            ChessPiece ch = new ChessPiece(TeamColor.WHITE, ChessPiece.PieceType.KING);
            Collection<ChessMove> cm = ch.pieceMoves(theBoard, sT);
            for (ChessMove se: cm) {
                if (theBoard.getPiece(se.getEndPosition()) == null) {
                    boolean v = false;
                } else if (theBoard.getPiece(se.getEndPosition()).equals(new ChessPiece(teamColor, ChessPiece.PieceType.KING))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        return (isInCheckmateOrStalemate(teamColor) && isInCheck(teamColor));
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        return ((!isInCheck(teamColor)) && isInCheckmateOrStalemate(teamColor));
    }
    private boolean isInCheckmateOrStalemate(TeamColor teamColor) {
        int i = 0;
        while (i < 64) {
            ChessPosition cp = new ChessPosition((i % 8) + 1, (i / 8) + 1);
            if (validMoves(cp) == null) {
                i = i + 1;
            } else if (validMoves(cp).size() != 0 && theBoard.getPiece(cp).getTeamColor() == teamColor) {
                return false;
            } else {
                i = i + 1;
            }
        }
        return true;
    }
    private boolean isEqu(ChessPiece firstPiece, ChessPiece secondPiece) {
        if (firstPiece == null) {
            return (secondPiece == null);
        }
        if (secondPiece == null) {
            return false;
        }
        return firstPiece.equals(secondPiece);
    }
    private boolean isEqu(ChessPosition firstPiece, ChessPiece secondPiece) {
        return isEqu(theBoard.getPiece(firstPiece), secondPiece);
    }
    private boolean isEqu(ChessPiece firstPiece, ChessPosition secondPiece) {
        return isEqu(theBoard.getPiece(secondPiece), firstPiece);
    }
    private boolean isEqu(ChessPosition firstPiece, ChessPosition secondPiece) {
        return isEqu(theBoard.getPiece(firstPiece), theBoard.getPiece(secondPiece));
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        theBoard = board.clone();
        whiteQueenRookMoved = false;
        whiteKingRookMoved = whiteQueenRookMoved;
        blackQueenRookMoved = whiteQueenRookMoved;
        blackKingRookMoved = whiteQueenRookMoved;
        whiteHasResigned = whiteQueenRookMoved;
        blackHasResigned = whiteQueenRookMoved;
        lastMove = 0;
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
    private Collection<ChessMove> valMoves(Collection<ChessMove> beg, ChessPosition startPosition) {
        ChessPiece cm = new ChessPiece(TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        Collection<ChessMove> cms = cm.pieceMoves(theBoard, startPosition);
        Collection<ChessMove> ret = new ArrayList<>();
        for (ChessMove mv: beg) {
            ret.add(mv.clone());
        }
        for (ChessMove cn: cms) {
            ChessBoard cb = theBoard.clone();
            ChessGame cg = new ChessGame();
            cb.addPiece(cn.getEndPosition(), cb.getPiece(cn.getStartPosition()));
            cb.addPiece(cn.getStartPosition(), null);
            cg.setBoard(cb);
            if (!cg.isInCheck(theBoard.getPiece(startPosition).getTeamColor())) {
                ret.add(cn.clone());
            }
        }
        return ret;
    }
    private int convertBoolean(boolean m) {
        if (m) {
            return 1;
        }
        return 0;
    }
    public int getInfo(int m) {
        if (m == 576) {
            int f = convertBoolean(whiteKingRookMoved) * 32 + 16 * convertBoolean(whiteQueenRookMoved);
            f = f + convertBoolean(blackKingRookMoved) * 8 + convertBoolean(blackQueenRookMoved) * 4;
            f = f + convertBoolean(whiteHasResigned) * 2 + convertBoolean(blackHasResigned);
            return 64 * lastMove + f;
        }
        lastMove = m / 64;
        whiteKingRookMoved = convertBoolean(m / 32);
        whiteQueenRookMoved = convertBoolean(m / 16);
        blackKingRookMoved = convertBoolean(m / 8);
        blackQueenRookMoved = convertBoolean(m / 4);
        whiteHasResigned = convertBoolean(m / 2);
        blackHasResigned = convertBoolean(m);
        return 576;
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
        if (ch.getInfo(576) != getInfo(576)) {
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
        if (cg.getInfo(getInfo(576)) == 576) {
            cg.setTeamTurn(theTurn);
        }
        return cg;
    }
}

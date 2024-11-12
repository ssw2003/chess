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
        return myType;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        boolean canAddMove = true;
        Collection<ChessMove> chessMoves = new ArrayList<>();
        ChessPiece thePosition = board.getPiece(myPosition).clone();
        ChessPiece.PieceType theType = thePosition.getPieceType();
        int theColor = 23;
        int firstPhoneme = 63;
        int thatRow = myPosition.getRow();
        int thatColumn = myPosition.getColumn();
        if (theType == ChessPiece.PieceType.KING) {
            firstPhoneme = 11;
        }
        if (theType == ChessPiece.PieceType.QUEEN) {
            firstPhoneme = 17;
        }
        if (theType == ChessPiece.PieceType.BISHOP) {
            firstPhoneme = 2;
        }
        if (theType == ChessPiece.PieceType.KNIGHT) {
            firstPhoneme = 14;
        }
        if (theType == ChessPiece.PieceType.ROOK) {
            firstPhoneme = 18;
        }
        if (theType == ChessPiece.PieceType.PAWN) {
            firstPhoneme = 16;
        }
        if (thePosition.getTeamColor() == ChessGame.TeamColor.BLACK) {
            theColor = 2;
        }
        ChessPosition theNewPlace = new ChessPosition(1, 1);
        ChessMove theNewMove = new ChessMove(theNewPlace.clone(), theNewPlace.clone(), null);
        if (firstPhoneme == 18 || firstPhoneme == 17) {
            int i = thatRow + 1;
            int j = thatColumn;
            while (i < 9) {
                theNewPlace = new ChessPosition(i, j);
                if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                    if (board.getPiece(theNewPlace).getTeamColor() ==
                            board.getPiece(myPosition).getTeamColor()) {
                        break;
                    }
                }
                theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                chessMoves.add(theNewMove.clone());
                if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                    if (board.getPiece(theNewPlace).getTeamColor() !=
                            board.getPiece(myPosition).getTeamColor()) {
                        break;
                    }
                }
                i = i + 1;
            }
            i = thatRow - 1;
            j = thatColumn;
            while (i > 0) {
                theNewPlace = new ChessPosition(i, j);
                if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                    if (board.getPiece(theNewPlace).getTeamColor() ==
                            board.getPiece(myPosition).getTeamColor()) {
                        break;
                    }
                }
                theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                chessMoves.add(theNewMove.clone());
                if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                    if (board.getPiece(theNewPlace).getTeamColor() !=
                            board.getPiece(myPosition).getTeamColor()) {
                        break;
                    }
                }
                i = i - 1;
            }
            i = thatRow;
            j = thatColumn + 1;
            while (j < 9) {
                theNewPlace = new ChessPosition(i, j);
                if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                    if (board.getPiece(theNewPlace).getTeamColor() ==
                            board.getPiece(myPosition).getTeamColor()) {
                        break;
                    }
                }
                theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                chessMoves.add(theNewMove.clone());
                if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                    if (board.getPiece(theNewPlace).getTeamColor() !=
                            board.getPiece(myPosition).getTeamColor()) {
                        break;
                    }
                }
                j = j + 1;
            }
            j = thatColumn - 1;
            while (j > 0) {
                theNewPlace = new ChessPosition(i, j);
                if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                    if (board.getPiece(theNewPlace).getTeamColor() ==
                            board.getPiece(myPosition).getTeamColor()) {
                        break;
                    }
                }
                theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                chessMoves.add(theNewMove.clone());
                if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                    if (board.getPiece(theNewPlace).getTeamColor() !=
                            board.getPiece(myPosition).getTeamColor()) {
                        break;
                    }
                }
                j = j - 1;
            }
        }
        if (firstPhoneme == 17 || firstPhoneme == 2) {
            int i = thatRow + 1;
            int j = thatColumn + 1;
            while (i < 9 && j < 9) {
                theNewPlace = new ChessPosition(i, j);
                if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                    if (board.getPiece(theNewPlace).getTeamColor() ==
                    board.getPiece(myPosition).getTeamColor()) {
                        break;
                    }
                }
                theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                chessMoves.add(theNewMove.clone());
                if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                    if (board.getPiece(theNewPlace).getTeamColor() !=
                            board.getPiece(myPosition).getTeamColor()) {
                        break;
                    }
                }
                i = i + 1;
                j = j + 1;
            }
            i = thatRow + 1;
            j = thatColumn - 1;
            while (i < 9 && j > 0) {
                theNewPlace = new ChessPosition(i, j);
                if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                    if (board.getPiece(theNewPlace).getTeamColor() ==
                            board.getPiece(myPosition).getTeamColor()) {
                        break;
                    }
                }
                theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                chessMoves.add(theNewMove.clone());
                if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                    if (board.getPiece(theNewPlace).getTeamColor() !=
                            board.getPiece(myPosition).getTeamColor()) {
                        break;
                    }
                }
                i = i + 1;
                j = j - 1;
            }
            i = thatRow - 1;
            j = thatColumn - 1;
            while (i > 0 && j > 0) {
                theNewPlace = new ChessPosition(i, j);
                if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                    if (board.getPiece(theNewPlace).getTeamColor() ==
                            board.getPiece(myPosition).getTeamColor()) {
                        break;
                    }
                }
                theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                chessMoves.add(theNewMove.clone());
                if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                    if (board.getPiece(theNewPlace).getTeamColor() !=
                            board.getPiece(myPosition).getTeamColor()) {
                        break;
                    }
                }
                i = i - 1;
                j = j - 1;
            }
            i = thatRow - 1;
            j = thatColumn + 1;
            while (i > 0 && j < 9) {
                theNewPlace = new ChessPosition(i, j);
                if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                    if (board.getPiece(theNewPlace).getTeamColor() ==
                            board.getPiece(myPosition).getTeamColor()) {
                        break;
                    }
                }
                theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                chessMoves.add(theNewMove.clone());
                if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                    if (board.getPiece(theNewPlace).getTeamColor() !=
                            board.getPiece(myPosition).getTeamColor()) {
                        break;
                    }
                }
                i = i - 1;
                j = j + 1;
            }
        }
        if (firstPhoneme == 16) {
            boolean canAttackLeft = false;
            boolean canAttackRight = false;
            if (theColor == 2) {
                if (thatColumn != 1) {
                    if (board.getPiece(new ChessPosition(thatRow - 1, thatColumn - 1)) != null) {
                        if (board.getPiece(new ChessPosition(thatRow - 1, thatColumn - 1)).getTeamColor() == ChessGame.TeamColor.WHITE) {
                            canAttackRight = true;
                        }
                    }
                }
                if (thatColumn != 8) {
                    if (board.getPiece(new ChessPosition(thatRow - 1, thatColumn + 1)) != null) {
                        if (board.getPiece(new ChessPosition(thatRow - 1, thatColumn + 1)).getTeamColor() == ChessGame.TeamColor.WHITE) {
                            canAttackLeft = true;
                        }
                    }
                }
                if (thatRow != 2) {
                    if (canAttackRight) {
                        theNewPlace = new ChessPosition(thatRow - 1, thatColumn - 1);
                        theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                        if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                            if (board.getPiece(theNewPlace).getTeamColor() ==
                                    board.getPiece(myPosition).getTeamColor()) {
                                canAddMove = false;
                            }
                        }
                        if (canAddMove) {
                            chessMoves.add(theNewMove.clone());
                        }
                        canAddMove = true;
                    }
                    if (canAttackLeft) {
                        theNewPlace = new ChessPosition(thatRow - 1, thatColumn + 1);
                        theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                        if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                            if (board.getPiece(theNewPlace).getTeamColor() ==
                                    board.getPiece(myPosition).getTeamColor()) {
                                canAddMove = false;
                            }
                        }
                        if (canAddMove) {
                            chessMoves.add(theNewMove.clone());
                        }
                        canAddMove = true;
                    }
                    theNewPlace = new ChessPosition(thatRow - 1, thatColumn);
                    theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                    if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                        canAddMove = false;
                    }
                    if (canAddMove) {
                        chessMoves.add(theNewMove.clone());
                    }
                    canAddMove = true;
                }
                if (thatRow == 2) {
                    if (canAttackRight) {
                        theNewPlace = new ChessPosition(thatRow - 1, thatColumn - 1);
                        theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), ChessPiece.PieceType.QUEEN);
                        if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                            if (board.getPiece(theNewPlace).getTeamColor() ==
                                    board.getPiece(myPosition).getTeamColor()) {
                                canAddMove = false;
                            }
                        }
                        if (canAddMove) {
                            chessMoves.add(theNewMove.clone());
                        }
                        canAddMove = true;
                        theNewPlace = new ChessPosition(thatRow - 1, thatColumn - 1);
                        theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), ChessPiece.PieceType.BISHOP);
                        if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                            if (board.getPiece(theNewPlace).getTeamColor() ==
                                    board.getPiece(myPosition).getTeamColor()) {
                                canAddMove = false;
                            }
                        }
                        if (canAddMove) {
                            chessMoves.add(theNewMove.clone());
                        }
                        canAddMove = true;
                        theNewPlace = new ChessPosition(thatRow - 1, thatColumn - 1);
                        theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), ChessPiece.PieceType.KNIGHT);
                        if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                            if (board.getPiece(theNewPlace).getTeamColor() ==
                                    board.getPiece(myPosition).getTeamColor()) {
                                canAddMove = false;
                            }
                        }
                        if (canAddMove) {
                            chessMoves.add(theNewMove.clone());
                        }
                        canAddMove = true;
                        theNewPlace = new ChessPosition(thatRow - 1, thatColumn - 1);
                        theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), ChessPiece.PieceType.ROOK);
                        if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                            if (board.getPiece(theNewPlace).getTeamColor() ==
                                    board.getPiece(myPosition).getTeamColor()) {
                                canAddMove = false;
                            }
                        }
                        if (canAddMove) {
                            chessMoves.add(theNewMove.clone());
                        }
                        canAddMove = true;
                    }
                    if (canAttackLeft) {
                        theNewPlace = new ChessPosition(thatRow - 1, thatColumn + 1);
                        theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), ChessPiece.PieceType.QUEEN);
                        if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                            if (board.getPiece(theNewPlace).getTeamColor() ==
                                    board.getPiece(myPosition).getTeamColor()) {
                                canAddMove = false;
                            }
                        }
                        if (canAddMove) {
                            chessMoves.add(theNewMove.clone());
                        }
                        canAddMove = true;
                        theNewPlace = new ChessPosition(thatRow - 1, thatColumn + 1);
                        theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), ChessPiece.PieceType.BISHOP);
                        if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                            if (board.getPiece(theNewPlace).getTeamColor() ==
                                    board.getPiece(myPosition).getTeamColor()) {
                                canAddMove = false;
                            }
                        }
                        if (canAddMove) {
                            chessMoves.add(theNewMove.clone());
                        }
                        canAddMove = true;
                        theNewPlace = new ChessPosition(thatRow - 1, thatColumn + 1);
                        theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), ChessPiece.PieceType.KNIGHT);
                        if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                            if (board.getPiece(theNewPlace).getTeamColor() ==
                                    board.getPiece(myPosition).getTeamColor()) {
                                canAddMove = false;
                            }
                        }
                        if (canAddMove) {
                            chessMoves.add(theNewMove.clone());
                        }
                        canAddMove = true;
                        theNewPlace = new ChessPosition(thatRow - 1, thatColumn + 1);
                        theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), ChessPiece.PieceType.ROOK);
                        if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                            if (board.getPiece(theNewPlace).getTeamColor() ==
                                    board.getPiece(myPosition).getTeamColor()) {
                                canAddMove = false;
                            }
                        }
                        if (canAddMove) {
                            chessMoves.add(theNewMove.clone());
                        }
                        canAddMove = true;
                    }
                    theNewPlace = new ChessPosition(thatRow - 1, thatColumn);
                    theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), ChessPiece.PieceType.QUEEN);
                    if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                        canAddMove = false;
                    }
                    if (canAddMove) {
                        chessMoves.add(theNewMove.clone());
                    }
                    canAddMove = true;
                    theNewPlace = new ChessPosition(thatRow - 1, thatColumn);
                    theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), ChessPiece.PieceType.BISHOP);
                    if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                        canAddMove = false;
                    }
                    if (canAddMove) {
                        chessMoves.add(theNewMove.clone());
                    }
                    canAddMove = true;
                    theNewPlace = new ChessPosition(thatRow - 1, thatColumn);
                    theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), ChessPiece.PieceType.KNIGHT);
                    if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                        canAddMove = false;
                    }
                    if (canAddMove) {
                        chessMoves.add(theNewMove.clone());
                    }
                    canAddMove = true;
                    theNewPlace = new ChessPosition(thatRow - 1, thatColumn);
                    theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), ChessPiece.PieceType.ROOK);
                    if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                        canAddMove = false;
                    }
                    if (canAddMove) {
                        chessMoves.add(theNewMove.clone());
                    }
                    canAddMove = true;
                }
                if (thatRow == 7) {
                    theNewPlace = new ChessPosition(5, thatColumn);
                    theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                    if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                        canAddMove = false;
                    }
                    if (board.getPiece(new ChessPosition(6 ,thatColumn)) != null) {
                        canAddMove = false;
                    }
                    if (canAddMove) {
                        chessMoves.add(theNewMove.clone());
                    }
                    canAddMove = true;
                }
            }
            if (theColor == 23) {
                if (thatColumn != 8) {
                    if (board.getPiece(new ChessPosition(thatRow + 1, thatColumn + 1)) != null) {
                        if (board.getPiece(new ChessPosition(thatRow + 1, thatColumn + 1)).getTeamColor() == ChessGame.TeamColor.BLACK) {
                            canAttackRight = true;
                        }
                    }
                }
                if (thatColumn != 1) {
                    if (board.getPiece(new ChessPosition(thatRow + 1, thatColumn - 1)) != null) {
                        if (board.getPiece(new ChessPosition(thatRow + 1, thatColumn - 1)).getTeamColor() == ChessGame.TeamColor.BLACK) {
                            canAttackLeft = true;
                        }
                    }
                }
                if (thatRow != 7) {
                    if (canAttackRight) {
                        theNewPlace = new ChessPosition(thatRow + 1, thatColumn + 1);
                        theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                        if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                            if (board.getPiece(theNewPlace).getTeamColor() ==
                                    board.getPiece(myPosition).getTeamColor()) {
                                canAddMove = false;
                            }
                        }
                        if (canAddMove) {
                            chessMoves.add(theNewMove.clone());
                        }
                        canAddMove = true;
                    }
                    if (canAttackLeft) {
                        theNewPlace = new ChessPosition(thatRow + 1, thatColumn - 1);
                        theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                        if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                            if (board.getPiece(theNewPlace).getTeamColor() ==
                                    board.getPiece(myPosition).getTeamColor()) {
                                canAddMove = false;
                            }
                        }
                        if (canAddMove) {
                            chessMoves.add(theNewMove.clone());
                        }
                        canAddMove = true;
                    }
                    theNewPlace = new ChessPosition(thatRow + 1, thatColumn);
                    theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                    if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                        canAddMove = false;
                    }
                    if (canAddMove) {
                        chessMoves.add(theNewMove.clone());
                    }
                    canAddMove = true;
                }
                if (thatRow == 7) {
                    if (canAttackRight) {
                        theNewPlace = new ChessPosition(thatRow + 1, thatColumn + 1);
                        theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), ChessPiece.PieceType.QUEEN);
                        if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                            if (board.getPiece(theNewPlace).getTeamColor() ==
                                    board.getPiece(myPosition).getTeamColor()) {
                                canAddMove = false;
                            }
                        }
                        if (canAddMove) {
                            chessMoves.add(theNewMove.clone());
                        }
                        canAddMove = true;
                        theNewPlace = new ChessPosition(thatRow + 1, thatColumn + 1);
                        theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), ChessPiece.PieceType.BISHOP);
                        if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                            if (board.getPiece(theNewPlace).getTeamColor() ==
                                    board.getPiece(myPosition).getTeamColor()) {
                                canAddMove = false;
                            }
                        }
                        if (canAddMove) {
                            chessMoves.add(theNewMove.clone());
                        }
                        canAddMove = true;
                        theNewPlace = new ChessPosition(thatRow + 1, thatColumn + 1);
                        theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), ChessPiece.PieceType.KNIGHT);
                        if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                            if (board.getPiece(theNewPlace).getTeamColor() ==
                                    board.getPiece(myPosition).getTeamColor()) {
                                canAddMove = false;
                            }
                        }
                        if (canAddMove) {
                            chessMoves.add(theNewMove.clone());
                        }
                        canAddMove = true;
                        theNewPlace = new ChessPosition(thatRow + 1, thatColumn + 1);
                        theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), ChessPiece.PieceType.ROOK);
                        if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                            if (board.getPiece(theNewPlace).getTeamColor() ==
                                    board.getPiece(myPosition).getTeamColor()) {
                                canAddMove = false;
                            }
                        }
                        if (canAddMove) {
                            chessMoves.add(theNewMove.clone());
                        }
                        canAddMove = true;
                    }
                    if (canAttackLeft) {
                        theNewPlace = new ChessPosition(thatRow + 1, thatColumn - 1);
                        theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), ChessPiece.PieceType.QUEEN);
                        if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                            if (board.getPiece(theNewPlace).getTeamColor() ==
                                    board.getPiece(myPosition).getTeamColor()) {
                                canAddMove = false;
                            }
                        }
                        if (canAddMove) {
                            chessMoves.add(theNewMove.clone());
                        }
                        canAddMove = true;
                        theNewPlace = new ChessPosition(thatRow + 1, thatColumn - 1);
                        theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), ChessPiece.PieceType.BISHOP);
                        if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                            if (board.getPiece(theNewPlace).getTeamColor() ==
                                    board.getPiece(myPosition).getTeamColor()) {
                                canAddMove = false;
                            }
                        }
                        if (canAddMove) {
                            chessMoves.add(theNewMove.clone());
                        }
                        canAddMove = true;
                        theNewPlace = new ChessPosition(thatRow + 1, thatColumn - 1);
                        theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), ChessPiece.PieceType.KNIGHT);
                        if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                            if (board.getPiece(theNewPlace).getTeamColor() ==
                                    board.getPiece(myPosition).getTeamColor()) {
                                canAddMove = false;
                            }
                        }
                        if (canAddMove) {
                            chessMoves.add(theNewMove.clone());
                        }
                        canAddMove = true;
                        theNewPlace = new ChessPosition(thatRow + 1, thatColumn - 1);
                        theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), ChessPiece.PieceType.ROOK);
                        if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                            if (board.getPiece(theNewPlace).getTeamColor() ==
                                    board.getPiece(myPosition).getTeamColor()) {
                                canAddMove = false;
                            }
                        }
                        if (canAddMove) {
                            chessMoves.add(theNewMove.clone());
                        }
                        canAddMove = true;
                    }
                    theNewPlace = new ChessPosition(thatRow + 1, thatColumn);
                    theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), ChessPiece.PieceType.QUEEN);
                    if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                        canAddMove = false;
                    }
                    if (canAddMove) {
                        chessMoves.add(theNewMove.clone());
                    }
                    canAddMove = true;
                    theNewPlace = new ChessPosition(thatRow + 1, thatColumn);
                    theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), ChessPiece.PieceType.BISHOP);
                    if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                        canAddMove = false;
                    }
                    if (canAddMove) {
                        chessMoves.add(theNewMove.clone());
                    }
                    canAddMove = true;
                    theNewPlace = new ChessPosition(thatRow + 1, thatColumn);
                    theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), ChessPiece.PieceType.KNIGHT);
                    if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                        canAddMove = false;
                    }
                    if (canAddMove) {
                        chessMoves.add(theNewMove.clone());
                    }
                    canAddMove = true;
                    theNewPlace = new ChessPosition(thatRow + 1, thatColumn);
                    theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), ChessPiece.PieceType.ROOK);
                    if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                        canAddMove = false;
                    }
                    if (canAddMove) {
                        chessMoves.add(theNewMove.clone());
                    }
                    canAddMove = true;
                }
                if (thatRow == 2) {
                    theNewPlace = new ChessPosition(4, thatColumn);
                    theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                    if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                        canAddMove = false;
                    }
                    if (board.getPiece(new ChessPosition(3 ,thatColumn)) != null) {
                        canAddMove = false;
                    }
                    if (canAddMove) {
                        chessMoves.add(theNewMove.clone());
                    }
                    canAddMove = true;
                }
            }
        }
        if (firstPhoneme == 14) {
            int i = thatRow + 1;
            int j = thatColumn + 2;
            if (i > 0 && i < 9 && j > 0 && j < 9) {
                theNewPlace = new ChessPosition(i, j);
                theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                    if (board.getPiece(theNewPlace).getTeamColor() ==
                            board.getPiece(myPosition).getTeamColor()) {
                        canAddMove = false;
                    }
                }
                if (canAddMove) {
                    chessMoves.add(theNewMove.clone());
                }
                canAddMove = true;
            }
            j = thatColumn - 2;
            if (i > 0 && i < 9 && j > 0 && j < 9) {
                theNewPlace = new ChessPosition(i, j);
                theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                    if (board.getPiece(theNewPlace).getTeamColor() ==
                            board.getPiece(myPosition).getTeamColor()) {
                        canAddMove = false;
                    }
                }
                if (canAddMove) {
                    chessMoves.add(theNewMove.clone());
                }
                canAddMove = true;
            }
            i = thatRow - 1;
            if (i > 0 && i < 9 && j > 0 && j < 9) {
                theNewPlace = new ChessPosition(i, j);
                theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                    if (board.getPiece(theNewPlace).getTeamColor() ==
                            board.getPiece(myPosition).getTeamColor()) {
                        canAddMove = false;
                    }
                }
                if (canAddMove) {
                    chessMoves.add(theNewMove.clone());
                }
                canAddMove = true;
            }
            j = thatColumn + 2;
            if (i > 0 && i < 9 && j > 0 && j < 9) {
                theNewPlace = new ChessPosition(i, j);
                theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                    if (board.getPiece(theNewPlace).getTeamColor() ==
                            board.getPiece(myPosition).getTeamColor()) {
                        canAddMove = false;
                    }
                }
                if (canAddMove) {
                    chessMoves.add(theNewMove.clone());
                }
                canAddMove = true;
            }
            i = thatRow + 2;
            j = thatColumn - 1;
            if (i > 0 && i < 9 && j > 0 && j < 9) {
                theNewPlace = new ChessPosition(i, j);
                theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                    if (board.getPiece(theNewPlace).getTeamColor() ==
                            board.getPiece(myPosition).getTeamColor()) {
                        canAddMove = false;
                    }
                }
                if (canAddMove) {
                    chessMoves.add(theNewMove.clone());
                }
                canAddMove = true;
            }
            i = thatRow - 2;
            if (i > 0 && i < 9 && j > 0 && j < 9) {
                theNewPlace = new ChessPosition(i, j);
                theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                    if (board.getPiece(theNewPlace).getTeamColor() ==
                            board.getPiece(myPosition).getTeamColor()) {
                        canAddMove = false;
                    }
                }
                if (canAddMove) {
                    chessMoves.add(theNewMove.clone());
                }
                canAddMove = true;
            }
            j = thatColumn + 1;
            if (i > 0 && i < 9 && j > 0 && j < 9) {
                theNewPlace = new ChessPosition(i, j);
                theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                    if (board.getPiece(theNewPlace).getTeamColor() ==
                            board.getPiece(myPosition).getTeamColor()) {
                        canAddMove = false;
                    }
                }
                if (canAddMove) {
                    chessMoves.add(theNewMove.clone());
                }
                canAddMove = true;
            }
            i = thatRow + 2;
            if (i > 0 && i < 9 && j > 0 && j < 9) {
                theNewPlace = new ChessPosition(i, j);
                theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                    if (board.getPiece(theNewPlace).getTeamColor() ==
                            board.getPiece(myPosition).getTeamColor()) {
                        canAddMove = false;
                    }
                }
                if (canAddMove) {
                    chessMoves.add(theNewMove.clone());
                }
                canAddMove = true;
            }
        }
        if (firstPhoneme == 11) {
            if (thatRow == 1) {
                if (thatColumn == 1) {
                    theNewPlace = new ChessPosition(1, 2);
                    theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);

                    if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                        if (board.getPiece(theNewPlace).getTeamColor() ==
                                board.getPiece(myPosition).getTeamColor()) {
                            canAddMove = false;
                        }
                    }
                    if (canAddMove) {
                        chessMoves.add(theNewMove.clone());
                    }
                    canAddMove = true;
                    theNewPlace = new ChessPosition(2, 2);
                    theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                    if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                        if (board.getPiece(theNewPlace).getTeamColor() ==
                                board.getPiece(myPosition).getTeamColor()) {
                            canAddMove = false;
                        }
                    }
                    if (canAddMove) {
                        chessMoves.add(theNewMove.clone());
                    }
                    canAddMove = true;
                    theNewPlace = new ChessPosition(2, 1);
                    theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                    if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                        if (board.getPiece(theNewPlace).getTeamColor() ==
                                board.getPiece(myPosition).getTeamColor()) {
                            canAddMove = false;
                        }
                    }
                    if (canAddMove) {
                        chessMoves.add(theNewMove.clone());
                    }
                    canAddMove = true;
                }
                if (thatColumn == 8) {
                    theNewPlace = new ChessPosition(1, 7);
                    theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                    if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                        if (board.getPiece(theNewPlace).getTeamColor() ==
                                board.getPiece(myPosition).getTeamColor()) {
                            canAddMove = false;
                        }
                    }
                    if (canAddMove) {
                        chessMoves.add(theNewMove.clone());
                    }
                    canAddMove = true;
                    theNewPlace = new ChessPosition(2, 7);
                    theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                    if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                        if (board.getPiece(theNewPlace).getTeamColor() ==
                                board.getPiece(myPosition).getTeamColor()) {
                            canAddMove = false;
                        }
                    }
                    if (canAddMove) {
                        chessMoves.add(theNewMove.clone());
                    }
                    canAddMove = true;
                    theNewPlace = new ChessPosition(2, 8);
                    theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                    if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                        if (board.getPiece(theNewPlace).getTeamColor() ==
                                board.getPiece(myPosition).getTeamColor()) {
                            canAddMove = false;
                        }
                    }
                    if (canAddMove) {
                        chessMoves.add(theNewMove.clone());
                    }
                    canAddMove = true;
                }
                if (thatColumn > 1 && thatColumn < 8) {
                    theNewPlace = new ChessPosition(1, thatColumn - 1);
                    theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                    if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                        if (board.getPiece(theNewPlace).getTeamColor() ==
                                board.getPiece(myPosition).getTeamColor()) {
                            canAddMove = false;
                        }
                    }
                    if (canAddMove) {
                        chessMoves.add(theNewMove.clone());
                    }
                    canAddMove = true;
                    theNewPlace = new ChessPosition(2, thatColumn - 1);
                    theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                    if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                        if (board.getPiece(theNewPlace).getTeamColor() ==
                                board.getPiece(myPosition).getTeamColor()) {
                            canAddMove = false;
                        }
                    }
                    if (canAddMove) {
                        chessMoves.add(theNewMove.clone());
                    }
                    canAddMove = true;
                    theNewPlace = new ChessPosition(2, thatColumn);
                    theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                    if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                        if (board.getPiece(theNewPlace).getTeamColor() ==
                                board.getPiece(myPosition).getTeamColor()) {
                            canAddMove = false;
                        }
                    }
                    if (canAddMove) {
                        chessMoves.add(theNewMove.clone());
                    }
                    canAddMove = true;
                    theNewPlace = new ChessPosition(2, thatColumn + 1);
                    theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                    if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                        if (board.getPiece(theNewPlace).getTeamColor() ==
                                board.getPiece(myPosition).getTeamColor()) {
                            canAddMove = false;
                        }
                    }
                    if (canAddMove) {
                        chessMoves.add(theNewMove.clone());
                    }
                    canAddMove = true;
                    theNewPlace = new ChessPosition(1, thatColumn + 1);
                    theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                    if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                        if (board.getPiece(theNewPlace).getTeamColor() ==
                                board.getPiece(myPosition).getTeamColor()) {
                            canAddMove = false;
                        }
                    }
                    if (canAddMove) {
                        chessMoves.add(theNewMove.clone());
                    }
                    canAddMove = true;
                }
            }
            if (thatRow == 8) {
                if (thatColumn == 1) {
                    theNewPlace = new ChessPosition(8, 2);
                    theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                    if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                        if (board.getPiece(theNewPlace).getTeamColor() ==
                                board.getPiece(myPosition).getTeamColor()) {
                            canAddMove = false;
                        }
                    }
                    if (canAddMove) {
                        chessMoves.add(theNewMove.clone());
                    }
                    canAddMove = true;
                    theNewPlace = new ChessPosition(7, 2);
                    theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                    if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                        if (board.getPiece(theNewPlace).getTeamColor() ==
                                board.getPiece(myPosition).getTeamColor()) {
                            canAddMove = false;
                        }
                    }
                    if (canAddMove) {
                        chessMoves.add(theNewMove.clone());
                    }
                    canAddMove = true;
                    theNewPlace = new ChessPosition(7, 1);
                    theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                    if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                        if (board.getPiece(theNewPlace).getTeamColor() ==
                                board.getPiece(myPosition).getTeamColor()) {
                            canAddMove = false;
                        }
                    }
                    if (canAddMove) {
                        chessMoves.add(theNewMove.clone());
                    }
                    canAddMove = true;
                }
                if (thatColumn == 8) {
                    theNewPlace = new ChessPosition(8, 7);
                    theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                    if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                        if (board.getPiece(theNewPlace).getTeamColor() ==
                                board.getPiece(myPosition).getTeamColor()) {
                            canAddMove = false;
                        }
                    }
                    if (canAddMove) {
                        chessMoves.add(theNewMove.clone());
                    }
                    canAddMove = true;
                    theNewPlace = new ChessPosition(7, 7);
                    theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                    if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                        if (board.getPiece(theNewPlace).getTeamColor() ==
                                board.getPiece(myPosition).getTeamColor()) {
                            canAddMove = false;
                        }
                    }
                    if (canAddMove) {
                        chessMoves.add(theNewMove.clone());
                    }
                    canAddMove = true;
                    theNewPlace = new ChessPosition(7, 8);
                    theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                    if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                        if (board.getPiece(theNewPlace).getTeamColor() ==
                                board.getPiece(myPosition).getTeamColor()) {
                            canAddMove = false;
                        }
                    }
                    if (canAddMove) {
                        chessMoves.add(theNewMove.clone());
                    }
                    canAddMove = true;
                }
                if (thatColumn > 1 && thatColumn < 8) {
                    theNewPlace = new ChessPosition(8, thatColumn - 1);
                    theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                    if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                        if (board.getPiece(theNewPlace).getTeamColor() ==
                                board.getPiece(myPosition).getTeamColor()) {
                            canAddMove = false;
                        }
                    }
                    if (canAddMove) {
                        chessMoves.add(theNewMove.clone());
                    }
                    canAddMove = true;
                    theNewPlace = new ChessPosition(7, thatColumn - 1);
                    theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                    if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                        if (board.getPiece(theNewPlace).getTeamColor() ==
                                board.getPiece(myPosition).getTeamColor()) {
                            canAddMove = false;
                        }
                    }
                    if (canAddMove) {
                        chessMoves.add(theNewMove.clone());
                    }
                    canAddMove = true;
                    theNewPlace = new ChessPosition(7, thatColumn);
                    theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                    if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                        if (board.getPiece(theNewPlace).getTeamColor() ==
                                board.getPiece(myPosition).getTeamColor()) {
                            canAddMove = false;
                        }
                    }
                    if (canAddMove) {
                        chessMoves.add(theNewMove.clone());
                    }
                    canAddMove = true;
                    theNewPlace = new ChessPosition(7, thatColumn + 1);
                    theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                    if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                        if (board.getPiece(theNewPlace).getTeamColor() ==
                                board.getPiece(myPosition).getTeamColor()) {
                            canAddMove = false;
                        }
                    }
                    if (canAddMove) {
                        chessMoves.add(theNewMove.clone());
                    }
                    canAddMove = true;
                    theNewPlace = new ChessPosition(8, thatColumn + 1);
                    theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                    if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                        if (board.getPiece(theNewPlace).getTeamColor() ==
                                board.getPiece(myPosition).getTeamColor()) {
                            canAddMove = false;
                        }
                    }
                    if (canAddMove) {
                        chessMoves.add(theNewMove.clone());
                    }
                    canAddMove = true;
                }
            }
            if (thatRow > 1 && thatRow < 8) {
                if (thatColumn == 1) {
                    theNewPlace = new ChessPosition(thatRow + 1, 1);
                    theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                    if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                        if (board.getPiece(theNewPlace).getTeamColor() ==
                                board.getPiece(myPosition).getTeamColor()) {
                            canAddMove = false;
                        }
                    }
                    if (canAddMove) {
                        chessMoves.add(theNewMove.clone());
                    }
                    canAddMove = true;
                    theNewPlace = new ChessPosition(thatRow + 1, 2);
                    theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                    if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                        if (board.getPiece(theNewPlace).getTeamColor() ==
                                board.getPiece(myPosition).getTeamColor()) {
                            canAddMove = false;
                        }
                    }
                    if (canAddMove) {
                        chessMoves.add(theNewMove.clone());
                    }
                    canAddMove = true;
                    theNewPlace = new ChessPosition(thatRow, 2);
                    theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                    if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                        if (board.getPiece(theNewPlace).getTeamColor() ==
                                board.getPiece(myPosition).getTeamColor()) {
                            canAddMove = false;
                        }
                    }
                    if (canAddMove) {
                        chessMoves.add(theNewMove.clone());
                    }
                    canAddMove = true;
                    theNewPlace = new ChessPosition(thatRow - 1, 2);
                    theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                    if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                        if (board.getPiece(theNewPlace).getTeamColor() ==
                                board.getPiece(myPosition).getTeamColor()) {
                            canAddMove = false;
                        }
                    }
                    if (canAddMove) {
                        chessMoves.add(theNewMove.clone());
                    }
                    canAddMove = true;
                    theNewPlace = new ChessPosition(thatRow - 1, 1);
                    theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                    if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                        if (board.getPiece(theNewPlace).getTeamColor() ==
                                board.getPiece(myPosition).getTeamColor()) {
                            canAddMove = false;
                        }
                    }
                    if (canAddMove) {
                        chessMoves.add(theNewMove.clone());
                    }
                    canAddMove = true;
                }
                if (thatColumn == 8) {
                    theNewPlace = new ChessPosition(thatRow + 1, 8);
                    theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                    if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                        if (board.getPiece(theNewPlace).getTeamColor() ==
                                board.getPiece(myPosition).getTeamColor()) {
                            canAddMove = false;
                        }
                    }
                    if (canAddMove) {
                        chessMoves.add(theNewMove.clone());
                    }
                    canAddMove = true;
                    theNewPlace = new ChessPosition(thatRow + 1, 7);
                    theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                    if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                        if (board.getPiece(theNewPlace).getTeamColor() ==
                                board.getPiece(myPosition).getTeamColor()) {
                            canAddMove = false;
                        }
                    }
                    if (canAddMove) {
                        chessMoves.add(theNewMove.clone());
                    }
                    canAddMove = true;
                    theNewPlace = new ChessPosition(thatRow, 7);
                    theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                    if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                        if (board.getPiece(theNewPlace).getTeamColor() ==
                                board.getPiece(myPosition).getTeamColor()) {
                            canAddMove = false;
                        }
                    }
                    if (canAddMove) {
                        chessMoves.add(theNewMove.clone());
                    }
                    canAddMove = true;
                    theNewPlace = new ChessPosition(thatRow - 1, 7);
                    theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                    if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                        if (board.getPiece(theNewPlace).getTeamColor() ==
                                board.getPiece(myPosition).getTeamColor()) {
                            canAddMove = false;
                        }
                    }
                    if (canAddMove) {
                        chessMoves.add(theNewMove.clone());
                    }
                    canAddMove = true;
                    theNewPlace = new ChessPosition(thatRow - 1, 8);
                    theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                    if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                        if (board.getPiece(theNewPlace).getTeamColor() ==
                                board.getPiece(myPosition).getTeamColor()) {
                            canAddMove = false;
                        }
                    }
                    if (canAddMove) {
                        chessMoves.add(theNewMove.clone());
                    }
                    canAddMove = true;
                }
                if (thatColumn > 1 && thatColumn < 8) {
                    theNewPlace = new ChessPosition(thatRow + 1, thatColumn);
                    theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                    if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                        if (board.getPiece(theNewPlace).getTeamColor() ==
                                board.getPiece(myPosition).getTeamColor()) {
                            canAddMove = false;
                        }
                    }
                    if (canAddMove) {
                        chessMoves.add(theNewMove.clone());
                    }
                    canAddMove = true;
                    theNewPlace = new ChessPosition(thatRow + 1, thatColumn + 1);
                    theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                    if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                        if (board.getPiece(theNewPlace).getTeamColor() ==
                                board.getPiece(myPosition).getTeamColor()) {
                            canAddMove = false;
                        }
                    }
                    if (canAddMove) {
                        chessMoves.add(theNewMove.clone());
                    }
                    canAddMove = true;
                    theNewPlace = new ChessPosition(thatRow, thatColumn + 1);
                    theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                    if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                        if (board.getPiece(theNewPlace).getTeamColor() ==
                                board.getPiece(myPosition).getTeamColor()) {
                            canAddMove = false;
                        }
                    }
                    if (canAddMove) {
                        chessMoves.add(theNewMove.clone());
                    }
                    canAddMove = true;
                    theNewPlace = new ChessPosition(thatRow - 1, thatColumn + 1);
                    theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                    if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                        if (board.getPiece(theNewPlace).getTeamColor() ==
                                board.getPiece(myPosition).getTeamColor()) {
                            canAddMove = false;
                        }
                    }
                    if (canAddMove) {
                        chessMoves.add(theNewMove.clone());
                    }
                    canAddMove = true;
                    theNewPlace = new ChessPosition(thatRow - 1, thatColumn);
                    theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                    if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                        if (board.getPiece(theNewPlace).getTeamColor() ==
                                board.getPiece(myPosition).getTeamColor()) {
                            canAddMove = false;
                        }
                    }
                    if (canAddMove) {
                        chessMoves.add(theNewMove.clone());
                    }
                    canAddMove = true;
                    theNewPlace = new ChessPosition(thatRow - 1, thatColumn - 1);
                    theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                    if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                        if (board.getPiece(theNewPlace).getTeamColor() ==
                                board.getPiece(myPosition).getTeamColor()) {
                            canAddMove = false;
                        }
                    }
                    if (canAddMove) {
                        chessMoves.add(theNewMove.clone());
                    }
                    canAddMove = true;
                    theNewPlace = new ChessPosition(thatRow, thatColumn - 1);
                    theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                    if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                        if (board.getPiece(theNewPlace).getTeamColor() ==
                                board.getPiece(myPosition).getTeamColor()) {
                            canAddMove = false;
                        }
                    }
                    if (canAddMove) {
                        chessMoves.add(theNewMove.clone());
                    }
                    canAddMove = true;
                    theNewPlace = new ChessPosition(thatRow + 1, thatColumn - 1);
                    theNewMove = new ChessMove(myPosition.clone(), theNewPlace.clone(), null);
                    if (board.getPiece(theNewPlace) != null && board.getPiece(myPosition) != null) {
                        if (board.getPiece(theNewPlace).getTeamColor() ==
                                board.getPiece(myPosition).getTeamColor()) {
                            canAddMove = false;
                        }
                    }
                    if (canAddMove) {
                        chessMoves.add(theNewMove.clone());
                    }
                    canAddMove = true;
                }
            }
        }
        return chessMoves;
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
}

package chess;

import java.util.Collection;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        private ChessGame.TeamColor my_color = pieceColor;
        private ChessPiece.PieceType my_type = type;
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
        return my_color;
        throw new RuntimeException("Not implemented");
    }

    /**
     * @return which type of chess piece this piece is
     */
    public ChessPiece.PieceType getPieceType() {
        return my_type;
        throw new RuntimeException("Not implemented");
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> chess_moves = Collection.of();
        ChessPiece the_position = board.getPiece(myPosition).clone();
        ChessPiece.PieceType the_type = the_position.getPieceType();
        int the_color = 23;
        int first_phoneme = 63;
        int that_row = myPosition.getRow();
        int that_column = myPosition.getColumn();
        if (the_type == ChessPiece.PieceType.KING) {
            first_phoneme = 11;
        }
        if (the_type == ChessPiece.PieceType.QUEEN) {
            first_phoneme = 17;
        }
        if (the_type == ChessPiece.PieceType.BISHOP) {
            first_phoneme = 2;
        }
        if (the_type == ChessPiece.PieceType.KNIGHT) {
            first_phoneme = 14;
        }
        if (the_type == ChessPiece.PieceType.ROOK) {
            first_phoneme = 18;
        }
        if (the_type == ChessPiece.PieceType.PAWN) {
            first_phoneme = 16;
        }
        if (the_position.getTeamColor() == ChessGame.TeamColor.BLACK) {
            the_color = 2;
        }
        ChessPosition the_new_place = new ChessPosition(1, 1);
        ChessMove the_new_move = new ChessMove(the_new_place.clone(), the_new_place.clone());
        if (firstPhoneme == 18 || firstPhoneme == 17) {
            int i = 1;
            while (i < 9) {
                if (that_row != i) {
                    the_new_place = new ChessPosition(i, that_column);
                    the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                    chess_moves.add(the_new_move.clone());
                }
                i = i + 1
            }
            while (i > 1) {
                i = i - 1
                if (that_column != i) {
                    the_new_place = new ChessPosition(that_row, i);
                    the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                    chess_moves.add(the_new_move.clone());
                }
            }
        }
        if (firstPhoneme == 17 || firstPhoneme == 2) {
            int i = that_row + 1;
            int j = that_column + 1;
            while (i < 9 && j < 9) {
                the_new_place = new ChessPosition(i, j);
                the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                chess_moves.add(the_new_move.clone());
                i = i + 1
                j = j + 1
            }
            i = that_row + 1;
            j = that_column - 1;
            while (i < 9 && j > 0) {
                the_new_place = new ChessPosition(i, j);
                the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                chess_moves.add(the_new_move.clone());
                i = i + 1
                j = j - 1
            }
            i = that_row - 1;
            j = that_column - 1;
            while (i > 0 && j > 0) {
                the_new_place = new ChessPosition(i, j);
                the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                chess_moves.add(the_new_move.clone());
                i = i - 1
                j = j - 1
            }
            i = that_row - 1;
            j = that_column + 1;
            while (i > 0 && j < 9) {
                the_new_place = new ChessPosition(i, j);
                the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                chess_moves.add(the_new_move.clone());
                i = i - 1
                j = j + 1
            }
        }
        if (firstPhoneme == 16) {
            boolean can_attack_left = false;
            boolean can_attack_right = false;
            if (the_color == 2) {
                if (that_column != 1) {
                    if (board.getPiece(ChessPosition(that_row - 1, that_column - 1)) != null) {
                        if (board.getPiece(ChessPosition(that_row - 1, that_column - 1)).getTeamColor() == ChessGame.TeamColor.BLACK) {
                            can_attack_right = true;
                        }
                    }
                }
                if (that_column != 8) {
                    if (board.getPiece(ChessPosition(that_row - 1, that_column + 1)) != null) {
                        if (board.getPiece(ChessPosition(that_row - 1, that_column + 1)).getTeamColor() == ChessGame.TeamColor.BLACK) {
                            can_attack_left = true;
                        }
                    }
                }
                if (that_row != 2) {
                    if (can_attack_right == true) {
                        the_new_place = new ChessPosition(that_row - 1, that_column - 1);
                        the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                        chess_moves.add(the_new_move.clone());
                    }
                    if (can_attack_left == true) {
                        the_new_place = new ChessPosition(that_row - 1, that_column + 1);
                        the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                        chess_moves.add(the_new_move.clone());
                    }
                    the_new_place = new ChessPosition(that_row - 1, that_column);
                    the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                    chess_moves.add(the_new_move.clone());
                }
                if (that_row == 2) {
                    if (can_attack_right == true) {
                        the_new_place = new ChessPosition(that_row - 1, that_column - 1);
                        the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone(), ChessPiece.PieceType.QUEEN);
                        chess_moves.add(the_new_move.clone());
                        the_new_place = new ChessPosition(that_row - 1, that_column - 1);
                        the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone(), ChessPiece.PieceType.BISHOP);
                        chess_moves.add(the_new_move.clone());
                        the_new_place = new ChessPosition(that_row - 1, that_column - 1);
                        the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone(), ChessPiece.PieceType.KNIGHT);
                        chess_moves.add(the_new_move.clone());
                        the_new_place = new ChessPosition(that_row - 1, that_column - 1);
                        the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone(), ChessPiece.PieceType.ROOK);
                        chess_moves.add(the_new_move.clone());
                    }
                    if (can_attack_left == true) {
                        the_new_place = new ChessPosition(that_row - 1, that_column + 1);
                        the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone(), ChessPiece.PieceType.QUEEN);
                        chess_moves.add(the_new_move.clone());
                        the_new_place = new ChessPosition(that_row - 1, that_column + 1);
                        the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone(), ChessPiece.PieceType.BISHOP);
                        chess_moves.add(the_new_move.clone());
                        the_new_place = new ChessPosition(that_row - 1, that_column + 1);
                        the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone(), ChessPiece.PieceType.KNIGHT);
                        chess_moves.add(the_new_move.clone());
                        the_new_place = new ChessPosition(that_row - 1, that_column + 1);
                        the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone(), ChessPiece.PieceType.ROOK);
                        chess_moves.add(the_new_move.clone());
                    }
                    the_new_place = new ChessPosition(that_row - 1, that_column);
                    the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone(), ChessPiece.PieceType.QUEEN);
                    chess_moves.add(the_new_move.clone());
                    the_new_place = new ChessPosition(that_row - 1, that_column);
                    the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone(), ChessPiece.PieceType.BISHOP);
                    chess_moves.add(the_new_move.clone());
                    the_new_place = new ChessPosition(that_row - 1, that_column);
                    the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone(), ChessPiece.PieceType.KNIGHT);
                    chess_moves.add(the_new_move.clone());
                    the_new_place = new ChessPosition(that_row - 1, that_column);
                    the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone(), ChessPiece.PieceType.ROOK);
                    chess_moves.add(the_new_move.clone());
                }
                if (that_row == 7) {
                    the_new_place = new ChessPosition(5, that_column);
                    the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                    chess_moves.add(the_new_move.clone());
                }
            }
            if (the_color == 23) {
                if (that_column != 8) {
                    if (board.getPiece(ChessPosition(that_row + 1, that_column + 1)) != null) {
                        if (board.getPiece(ChessPosition(that_row + 1, that_column + 1)).getTeamColor() == ChessGame.TeamColor.WHITE) {
                            can_attack_right = true;
                        }
                    }
                }
                if (that_column != 1) {
                    if (board.getPiece(ChessPosition(that_row + 1, that_column - 1)) != null) {
                        if (board.getPiece(ChessPosition(that_row + 1, that_column - 1)).getTeamColor() == ChessGame.TeamColor.WHITE) {
                            can_attack_left = true;
                        }
                    }
                }
                if (that_row != 7) {
                    if (can_attack_right == true) {
                        the_new_place = new ChessPosition(that_row + 1, that_column + 1);
                        the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                        chess_moves.add(the_new_move.clone());
                    }
                    if (can_attack_left == true) {
                        the_new_place = new ChessPosition(that_row + 1, that_column - 1);
                        the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                        chess_moves.add(the_new_move.clone());
                    }
                    the_new_place = new ChessPosition(that_row + 1, that_column);
                    the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                    chess_moves.add(the_new_move.clone());
                }
                if (that_row == 7) {
                    if (can_attack_right == true) {
                        the_new_place = new ChessPosition(that_row + 1, that_column + 1);
                        the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone(), ChessPiece.PieceType.QUEEN);
                        chess_moves.add(the_new_move.clone());
                        the_new_place = new ChessPosition(that_row + 1, that_column + 1);
                        the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone(), ChessPiece.PieceType.BISHOP);
                        chess_moves.add(the_new_move.clone());
                        the_new_place = new ChessPosition(that_row + 1, that_column + 1);
                        the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone(), ChessPiece.PieceType.KNIGHT);
                        chess_moves.add(the_new_move.clone());
                        the_new_place = new ChessPosition(that_row + 1, that_column + 1);
                        the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone(), ChessPiece.PieceType.ROOK);
                        chess_moves.add(the_new_move.clone());
                    }
                    if (can_attack_left == true) {
                        the_new_place = new ChessPosition(that_row + 1, that_column - 1);
                        the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone(), ChessPiece.PieceType.QUEEN);
                        chess_moves.add(the_new_move.clone());
                        the_new_place = new ChessPosition(that_row + 1, that_column - 1);
                        the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone(), ChessPiece.PieceType.BISHOP);
                        chess_moves.add(the_new_move.clone());
                        the_new_place = new ChessPosition(that_row + 1, that_column - 1);
                        the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone(), ChessPiece.PieceType.KNIGHT);
                        chess_moves.add(the_new_move.clone());
                        the_new_place = new ChessPosition(that_row + 1, that_column - 1);
                        the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone(), ChessPiece.PieceType.ROOK);
                        chess_moves.add(the_new_move.clone());
                    }
                    the_new_place = new ChessPosition(that_row + 1, that_column);
                    the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone(), ChessPiece.PieceType.QUEEN);
                    chess_moves.add(the_new_move.clone());
                    the_new_place = new ChessPosition(that_row + 1, that_column);
                    the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone(), ChessPiece.PieceType.BISHOP);
                    chess_moves.add(the_new_move.clone());
                    the_new_place = new ChessPosition(that_row + 1, that_column);
                    the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone(), ChessPiece.PieceType.KNIGHT);
                    chess_moves.add(the_new_move.clone());
                    the_new_place = new ChessPosition(that_row + 1, that_column);
                    the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone(), ChessPiece.PieceType.ROOK);
                    chess_moves.add(the_new_move.clone());
                }
                if (that_row == 2) {
                    the_new_place = new ChessPosition(4, that_column);
                    the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                    chess_moves.add(the_new_move.clone());
                }
            }
        }
        if (firstPhoneme == 14) {
            int i = that_row + 1;
            int j = that_column + 2;
            if (i > 0 && i < 9 && j > 0 && j < 9) {
                the_new_place = new ChessPosition(i, j);
                the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                chess_moves.add(the_new_move.clone());
            }
            j = that_column - 2;
            if (i > 0 && i < 9 && j > 0 && j < 9) {
                the_new_place = new ChessPosition(i, j);
                the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                chess_moves.add(the_new_move.clone());
            }
            i = that_row - 1;
            if (i > 0 && i < 9 && j > 0 && j < 9) {
                the_new_place = new ChessPosition(i, j);
                the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                chess_moves.add(the_new_move.clone());
            }
            j = that_column + 2;
            if (i > 0 && i < 9 && j > 0 && j < 9) {
                the_new_place = new ChessPosition(i, j);
                the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                chess_moves.add(the_new_move.clone());
            }
            i = that_row + 2;
            j = that_column - 1;
            if (i > 0 && i < 9 && j > 0 && j < 9) {
                the_new_place = new ChessPosition(i, j);
                the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                chess_moves.add(the_new_move.clone());
            }
            i = that_row - 2;
            if (i > 0 && i < 9 && j > 0 && j < 9) {
                the_new_place = new ChessPosition(i, j);
                the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                chess_moves.add(the_new_move.clone());
            }
            j = that_column + 1;
            if (i > 0 && i < 9 && j > 0 && j < 9) {
                the_new_place = new ChessPosition(i, j);
                the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                chess_moves.add(the_new_move.clone());
            }
            i = that_row + 2;
            if (i > 0 && i < 9 && j > 0 && j < 9) {
                the_new_place = new ChessPosition(i, j);
                the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                chess_moves.add(the_new_move.clone());
            }
        }
        if (firstPhoneme == 11) {
            if (that_row == 1) {
                if (that_column == 1) {
                    the_new_place = new ChessPosition(1, 2);
                    the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                    chess_moves.add(the_new_move.clone());
                    the_new_place = new ChessPosition(2, 2);
                    the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                    chess_moves.add(the_new_move.clone());
                    the_new_place = new ChessPosition(2, 1);
                    the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                    chess_moves.add(the_new_move.clone());
                }
                if (that_column == 8) {
                    the_new_place = new ChessPosition(1, 7);
                    the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                    chess_moves.add(the_new_move.clone());
                    the_new_place = new ChessPosition(2, 7);
                    the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                    chess_moves.add(the_new_move.clone());
                    the_new_place = new ChessPosition(2, 8);
                    the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                    chess_moves.add(the_new_move.clone());
                }
                if (that_column > 1 && that_column < 8) {
                    the_new_place = new ChessPosition(1, that_column - 1);
                    the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                    chess_moves.add(the_new_move.clone());
                    the_new_place = new ChessPosition(2, that_column - 1);
                    the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                    chess_moves.add(the_new_move.clone());
                    the_new_place = new ChessPosition(2, that_column);
                    the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                    chess_moves.add(the_new_move.clone());
                    the_new_place = new ChessPosition(2, that_column + 1);
                    the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                    chess_moves.add(the_new_move.clone());
                    the_new_place = new ChessPosition(1, that_column + 1);
                    the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                    chess_moves.add(the_new_move.clone());
                }
            }
            if (that_row == 8) {
                if (that_column == 1) {
                    the_new_place = new ChessPosition(8, 2);
                    the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                    chess_moves.add(the_new_move.clone());
                    the_new_place = new ChessPosition(7, 2);
                    the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                    chess_moves.add(the_new_move.clone());
                    the_new_place = new ChessPosition(7, 1);
                    the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                    chess_moves.add(the_new_move.clone());
                }
                if (that_column == 8) {
                    the_new_place = new ChessPosition(8, 7);
                    the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                    chess_moves.add(the_new_move.clone());
                    the_new_place = new ChessPosition(7, 7);
                    the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                    chess_moves.add(the_new_move.clone());
                    the_new_place = new ChessPosition(7, 8);
                    the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                    chess_moves.add(the_new_move.clone());
                }
                if (that_column > 1 && that_column < 8) {
                    the_new_place = new ChessPosition(8, that_column - 1);
                    the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                    chess_moves.add(the_new_move.clone());
                    the_new_place = new ChessPosition(7, that_column - 1);
                    the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                    chess_moves.add(the_new_move.clone());
                    the_new_place = new ChessPosition(7, that_column);
                    the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                    chess_moves.add(the_new_move.clone());
                    the_new_place = new ChessPosition(7, that_column + 1);
                    the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                    chess_moves.add(the_new_move.clone());
                    the_new_place = new ChessPosition(8, that_column + 1);
                    the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                    chess_moves.add(the_new_move.clone());
                }
            }
            if (that_row > 1 && that_row < 8) {
                if (that_column == 1) {
                    the_new_place = new ChessPosition(that_row + 1, 1);
                    the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                    chess_moves.add(the_new_move.clone());
                    the_new_place = new ChessPosition(that_row + 1, 2);
                    the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                    chess_moves.add(the_new_move.clone());
                    the_new_place = new ChessPosition(that_row, 2);
                    the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                    chess_moves.add(the_new_move.clone());
                    the_new_place = new ChessPosition(that_row - 1, 2);
                    the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                    chess_moves.add(the_new_move.clone());
                    the_new_place = new ChessPosition(that_row - 1, 1);
                    the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                    chess_moves.add(the_new_move.clone());
                }
                if (that_column == 8) {
                    the_new_place = new ChessPosition(that_row + 1, 8);
                    the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                    chess_moves.add(the_new_move.clone());
                    the_new_place = new ChessPosition(that_row + 1, 7);
                    the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                    chess_moves.add(the_new_move.clone());
                    the_new_place = new ChessPosition(that_row, 7);
                    the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                    chess_moves.add(the_new_move.clone());
                    the_new_place = new ChessPosition(that_row - 1, 7);
                    the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                    chess_moves.add(the_new_move.clone());
                    the_new_place = new ChessPosition(that_row - 1, 8);
                    the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                    chess_moves.add(the_new_move.clone());
                }
                if (that_column > 1 && that_column < 8) {
                    the_new_place = new ChessPosition(that_row + 1, that_column);
                    the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                    chess_moves.add(the_new_move.clone());
                    the_new_place = new ChessPosition(that_row + 1, that_column + 1);
                    the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                    chess_moves.add(the_new_move.clone());
                    the_new_place = new ChessPosition(that_row, that_column + 1);
                    the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                    chess_moves.add(the_new_move.clone());
                    the_new_place = new ChessPosition(that_row - 1, that_column + 1);
                    the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                    chess_moves.add(the_new_move.clone());
                    the_new_place = new ChessPosition(that_row - 1, that_column);
                    the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                    chess_moves.add(the_new_move.clone());
                    the_new_place = new ChessPosition(that_row - 1, that_column - 1);
                    the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                    chess_moves.add(the_new_move.clone());
                    the_new_place = new ChessPosition(that_row, that_column - 1);
                    the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                    chess_moves.add(the_new_move.clone());
                    the_new_place = new ChessPosition(that_row + 1, that_column - 1);
                    the_new_move = new ChessMove(myPosition.clone(), the_new_place.clone());
                    chess_moves.add(the_new_move.clone());
                }
            }
        }
        return chess_moves;
        throw new RuntimeException("Not implemented");
    }
    public boolean equals(Object compare_with) {
        if (compare_with == null) {
            return false;
        }
        if (compare_with.getClass() != getClass()) {
            return false;
        }
        ChessPiece my_thing = (ChessPiece) compare_with;
        if (my_thing.getTeamColor() != my_color) {
            return false;
        }
        if (my_thing.getPieceType() != my_type) {
            return false;
        }
        return true;
    }
    public int hashCode() {
        int the_color = 23;
        int first_phoneme = 63;
        if (my_type == ChessPiece.PieceType.KING) {
            first_phoneme = 11;
        }
        if (my_type == ChessPiece.PieceType.QUEEN) {
            first_phoneme = 17;
        }
        if (my_type == ChessPiece.PieceType.BISHOP) {
            first_phoneme = 2;
        }
        if (my_type == ChessPiece.PieceType.KNIGHT) {
            first_phoneme = 14;
        }
        if (my_type == ChessPiece.PieceType.ROOK) {
            first_phoneme = 18;
        }
        if (my_type == ChessPiece.PieceType.PAWN) {
            first_phoneme = 16;
        }
        if (my_color == ChessGame.TeamColor.BLACK) {
            the_color = 2;
        }
        return first_phoneme + the_color;
    }
    public ChessPiece clone() {
        ChessPiece cloned_thing = new ChessPiece(my_color, my_type);
        return cloned_thing;
    }
}

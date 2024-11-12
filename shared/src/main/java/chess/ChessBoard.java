package chess;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {

    private ChessPiece piece_at_a_1;
    private ChessPiece piece_at_a_2;
    private ChessPiece piece_at_a_3;
    private ChessPiece piece_at_a_4;
    private ChessPiece piece_at_a_5;
    private ChessPiece piece_at_a_6;
    private ChessPiece piece_at_a_7;
    private ChessPiece piece_at_a_8;
    private ChessPiece piece_at_b_1;
    private ChessPiece piece_at_b_2;
    private ChessPiece piece_at_b_3;
    private ChessPiece piece_at_b_4;
    private ChessPiece piece_at_b_5;
    private ChessPiece piece_at_b_6;
    private ChessPiece piece_at_b_7;
    private ChessPiece piece_at_b_8;
    private ChessPiece piece_at_c_1;
    private ChessPiece piece_at_c_2;
    private ChessPiece piece_at_c_3;
    private ChessPiece piece_at_c_4;
    private ChessPiece piece_at_c_5;
    private ChessPiece piece_at_c_6;
    private ChessPiece piece_at_c_7;
    private ChessPiece piece_at_c_8;
    private ChessPiece piece_at_d_1;
    private ChessPiece piece_at_d_2;
    private ChessPiece piece_at_d_3;
    private ChessPiece piece_at_d_4;
    private ChessPiece piece_at_d_5;
    private ChessPiece piece_at_d_6;
    private ChessPiece piece_at_d_7;
    private ChessPiece piece_at_d_8;
    private ChessPiece piece_at_e_1;
    private ChessPiece piece_at_e_2;
    private ChessPiece piece_at_e_3;
    private ChessPiece piece_at_e_4;
    private ChessPiece piece_at_e_5;
    private ChessPiece piece_at_e_6;
    private ChessPiece piece_at_e_7;
    private ChessPiece piece_at_e_8;
    private ChessPiece piece_at_f_1;
    private ChessPiece piece_at_f_2;
    private ChessPiece piece_at_f_3;
    private ChessPiece piece_at_f_4;
    private ChessPiece piece_at_f_5;
    private ChessPiece piece_at_f_6;
    private ChessPiece piece_at_f_7;
    private ChessPiece piece_at_f_8;
    private ChessPiece piece_at_g_1;
    private ChessPiece piece_at_g_2;
    private ChessPiece piece_at_g_3;
    private ChessPiece piece_at_g_4;
    private ChessPiece piece_at_g_5;
    private ChessPiece piece_at_g_6;
    private ChessPiece piece_at_g_7;
    private ChessPiece piece_at_g_8;
    private ChessPiece piece_at_h_1;
    private ChessPiece piece_at_h_2;
    private ChessPiece piece_at_h_3;
    private ChessPiece piece_at_h_4;
    private ChessPiece piece_at_h_5;
    private ChessPiece piece_at_h_6;
    private ChessPiece piece_at_h_7;
    private ChessPiece piece_at_h_8;
    public String toString() {
        String new_string = "";
        for (int i = 8; i > 0; i = i - 1) {
            new_string = String.join("",new_string,"|");
            for (int j = 1; j < 9; j = j + 1) {
                ChessPiece chess_piece = this.getPiece(new ChessPosition(i, j));
                if (chess_piece == null) {
                    new_string = String.join("",new_string," ");
                } else if (chess_piece.getTeamColor() == ChessGame.TeamColor.BLACK) {
                    if (chess_piece.getPieceType() == ChessPiece.PieceType.KING) {
                        new_string = String.join("",new_string,"k");
                    } else if (chess_piece.getPieceType() == ChessPiece.PieceType.QUEEN) {
                        new_string = String.join("",new_string,"q");
                    } else if (chess_piece.getPieceType() == ChessPiece.PieceType.KNIGHT) {
                        new_string = String.join("",new_string,"n");
                    } else if (chess_piece.getPieceType() == ChessPiece.PieceType.PAWN) {
                        new_string = String.join("",new_string,"p");
                    } else if (chess_piece.getPieceType() == ChessPiece.PieceType.BISHOP) {
                        new_string = String.join("",new_string,"b");
                    } else {
                        new_string = String.join("",new_string,"r");
                    }
                } else {
                    if (chess_piece.getPieceType() == ChessPiece.PieceType.KING) {
                        new_string = String.join("",new_string,"K");
                    } else if (chess_piece.getPieceType() == ChessPiece.PieceType.QUEEN) {
                        new_string = String.join("",new_string,"Q");
                    } else if (chess_piece.getPieceType() == ChessPiece.PieceType.KNIGHT) {
                        new_string = String.join("",new_string,"N");
                    } else if (chess_piece.getPieceType() == ChessPiece.PieceType.PAWN) {
                        new_string = String.join("",new_string,"P");
                    } else if (chess_piece.getPieceType() == ChessPiece.PieceType.BISHOP) {
                        new_string = String.join("",new_string,"B");
                    } else {
                        new_string = String.join("",new_string,"R");
                    }
                }
                new_string = String.join("",new_string,"|");
            }
            if (i != 1) {
                new_string = String.join("",new_string,"\n");
            }
        }
        return new_string;
    }
    public ChessBoard() {
        piece_at_a_1 = null;
        piece_at_a_2 = null;
        piece_at_a_3 = null;
        piece_at_a_4 = null;
        piece_at_a_5 = null;
        piece_at_a_6 = null;
        piece_at_a_7 = null;
        piece_at_a_8 = null;
        piece_at_b_1 = null;
        piece_at_b_2 = null;
        piece_at_b_3 = null;
        piece_at_b_4 = null;
        piece_at_b_5 = null;
        piece_at_b_6 = null;
        piece_at_b_7 = null;
        piece_at_b_8 = null;
        piece_at_c_1 = null;
        piece_at_c_2 = null;
        piece_at_c_3 = null;
        piece_at_c_4 = null;
        piece_at_c_5 = null;
        piece_at_c_6 = null;
        piece_at_c_7 = null;
        piece_at_c_8 = null;
        piece_at_d_1 = null;
        piece_at_d_2 = null;
        piece_at_d_3 = null;
        piece_at_d_4 = null;
        piece_at_d_5 = null;
        piece_at_d_6 = null;
        piece_at_d_7 = null;
        piece_at_d_8 = null;
        piece_at_e_1 = null;
        piece_at_e_2 = null;
        piece_at_e_3 = null;
        piece_at_e_4 = null;
        piece_at_e_5 = null;
        piece_at_e_6 = null;
        piece_at_e_7 = null;
        piece_at_e_8 = null;
        piece_at_f_1 = null;
        piece_at_f_2 = null;
        piece_at_f_3 = null;
        piece_at_f_4 = null;
        piece_at_f_5 = null;
        piece_at_f_6 = null;
        piece_at_f_7 = null;
        piece_at_f_8 = null;
        piece_at_g_1 = null;
        piece_at_g_2 = null;
        piece_at_g_3 = null;
        piece_at_g_4 = null;
        piece_at_g_5 = null;
        piece_at_g_6 = null;
        piece_at_g_7 = null;
        piece_at_g_8 = null;
        piece_at_h_1 = null;
        piece_at_h_2 = null;
        piece_at_h_3 = null;
        piece_at_h_4 = null;
        piece_at_h_5 = null;
        piece_at_h_6 = null;
        piece_at_h_7 = null;
        piece_at_h_8 = null;
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        int the_row = position.getRow();
        int the_column = position.getColumn();
        if (piece == null) {
            if (the_column < 5) {
                if (the_column < 3) {
                    if (the_column == 1) {
                        if (the_row < 5) {
                            if (the_row < 3) {
                                if (the_row == 1) {
                                    piece_at_a_1 = null;
                                }
                                else {
                                    piece_at_a_2 = null;
                                }
                            }
                            else {
                                if (the_row == 3) {
                                    piece_at_a_3 = null;
                                }
                                else {
                                    piece_at_a_4 = null;
                                }
                            }
                        }
                        else {
                            if (the_row < 7) {
                                if (the_row == 5) {
                                    piece_at_a_5 = null;
                                }
                                else {
                                    piece_at_a_6 = null;
                                }
                            }
                            else {
                                if (the_row == 7) {
                                    piece_at_a_7 = null;
                                }
                                else {
                                    piece_at_a_8 = null;
                                }
                            }
                        }
                    }
                    else {
                        if (the_row < 5) {
                            if (the_row < 3) {
                                if (the_row == 1) {
                                    piece_at_b_1 = null;
                                }
                                else {
                                    piece_at_b_2 = null;
                                }
                            }
                            else {
                                if (the_row == 3) {
                                    piece_at_b_3 = null;
                                }
                                else {
                                    piece_at_b_4 = null;
                                }
                            }
                        }
                        else {
                            if (the_row < 7) {
                                if (the_row == 5) {
                                    piece_at_b_5 = null;
                                }
                                else {
                                    piece_at_b_6 = null;
                                }
                            }
                            else {
                                if (the_row == 7) {
                                    piece_at_b_7 = null;
                                }
                                else {
                                    piece_at_b_8 = null;
                                }
                            }
                        }
                    }
                }
                else {
                    if (the_column == 3) {
                        if (the_row < 5) {
                            if (the_row < 3) {
                                if (the_row == 1) {
                                    piece_at_c_1 = null;
                                }
                                else {
                                    piece_at_c_2 = null;
                                }
                            }
                            else {
                                if (the_row == 3) {
                                    piece_at_c_3 = null;
                                }
                                else {
                                    piece_at_c_4 = null;
                                }
                            }
                        }
                        else {
                            if (the_row < 7) {
                                if (the_row == 5) {
                                    piece_at_c_5 = null;
                                }
                                else {
                                    piece_at_c_6 = null;
                                }
                            }
                            else {
                                if (the_row == 7) {
                                    piece_at_c_7 = null;
                                }
                                else {
                                    piece_at_c_8 = null;
                                }
                            }
                        }
                    }
                    else {
                        if (the_row < 5) {
                            if (the_row < 3) {
                                if (the_row == 1) {
                                    piece_at_d_1 = null;
                                }
                                else {
                                    piece_at_d_2 = null;
                                }
                            }
                            else {
                                if (the_row == 3) {
                                    piece_at_d_3 = null;
                                }
                                else {
                                    piece_at_d_4 = null;
                                }
                            }
                        }
                        else {
                            if (the_row < 7) {
                                if (the_row == 5) {
                                    piece_at_d_5 = null;
                                }
                                else {
                                    piece_at_d_6 = null;
                                }
                            }
                            else {
                                if (the_row == 7) {
                                    piece_at_d_7 = null;
                                }
                                else {
                                    piece_at_d_8 = null;
                                }
                            }
                        }
                    }
                }
            }
            else {
                if (the_column < 7) {
                    if (the_column == 5) {
                        if (the_row < 5) {
                            if (the_row < 3) {
                                if (the_row == 1) {
                                    piece_at_e_1 = null;
                                }
                                else {
                                    piece_at_e_2 = null;
                                }
                            }
                            else {
                                if (the_row == 3) {
                                    piece_at_e_3 = null;
                                }
                                else {
                                    piece_at_e_4 = null;
                                }
                            }
                        }
                        else {
                            if (the_row < 7) {
                                if (the_row == 5) {
                                    piece_at_e_5 = null;
                                }
                                else {
                                    piece_at_e_6 = null;
                                }
                            }
                            else {
                                if (the_row == 7) {
                                    piece_at_e_7 = null;
                                }
                                else {
                                    piece_at_e_8 = null;
                                }
                            }
                        }
                    }
                    else {
                        if (the_row < 5) {
                            if (the_row < 3) {
                                if (the_row == 1) {
                                    piece_at_f_1 = null;
                                }
                                else {
                                    piece_at_f_2 = null;
                                }
                            }
                            else {
                                if (the_row == 3) {
                                    piece_at_f_3 = null;
                                }
                                else {
                                    piece_at_f_4 = null;
                                }
                            }
                        }
                        else {
                            if (the_row < 7) {
                                if (the_row == 5) {
                                    piece_at_f_5 = null;
                                }
                                else {
                                    piece_at_f_6 = null;
                                }
                            }
                            else {
                                if (the_row == 7) {
                                    piece_at_f_7 = null;
                                }
                                else {
                                    piece_at_f_8 = null;
                                }
                            }
                        }
                    }
                }
                else {
                    if (the_column == 7) {
                        if (the_row < 5) {
                            if (the_row < 3) {
                                if (the_row == 1) {
                                    piece_at_g_1 = null;
                                }
                                else {
                                    piece_at_g_2 = null;
                                }
                            }
                            else {
                                if (the_row == 3) {
                                    piece_at_g_3 = null;
                                }
                                else {
                                    piece_at_g_4 = null;
                                }
                            }
                        }
                        else {
                            if (the_row < 7) {
                                if (the_row == 5) {
                                    piece_at_g_5 = null;
                                }
                                else {
                                    piece_at_g_6 = null;
                                }
                            }
                            else {
                                if (the_row == 7) {
                                    piece_at_g_7 = null;
                                }
                                else {
                                    piece_at_g_8 = null;
                                }
                            }
                        }
                    }
                    else {
                        if (the_row < 5) {
                            if (the_row < 3) {
                                if (the_row == 1) {
                                    piece_at_h_1 = null;
                                }
                                else {
                                    piece_at_h_2 = null;
                                }
                            }
                            else {
                                if (the_row == 3) {
                                    piece_at_h_3 = null;
                                }
                                else {
                                    piece_at_h_4 = null;
                                }
                            }
                        }
                        else {
                            if (the_row < 7) {
                                if (the_row == 5) {
                                    piece_at_h_5 = null;
                                }
                                else {
                                    piece_at_h_6 = null;
                                }
                            }
                            else {
                                if (the_row == 7) {
                                    piece_at_h_7 = null;
                                }
                                else {
                                    piece_at_h_8 = null;
                                }
                            }
                        }
                    }
                }
            }
            return;
        }
        if (the_column < 5) {
            if (the_column < 3) {
                if (the_column == 1) {
                    if (the_row < 5) {
                        if (the_row < 3) {
                            if (the_row == 1) {
                                piece_at_a_1 = piece.clone();
                            }
                            else {
                                piece_at_a_2 = piece.clone();
                            }
                        }
                        else {
                            if (the_row == 3) {
                                piece_at_a_3 = piece.clone();
                            }
                            else {
                                piece_at_a_4 = piece.clone();
                            }
                        }
                    }
                    else {
                        if (the_row < 7) {
                            if (the_row == 5) {
                                piece_at_a_5 = piece.clone();
                            }
                            else {
                                piece_at_a_6 = piece.clone();
                            }
                        }
                        else {
                            if (the_row == 7) {
                                piece_at_a_7 = piece.clone();
                            }
                            else {
                                piece_at_a_8 = piece.clone();
                            }
                        }
                    }
                }
                else {
                    if (the_row < 5) {
                        if (the_row < 3) {
                            if (the_row == 1) {
                                piece_at_b_1 = piece.clone();
                            }
                            else {
                                piece_at_b_2 = piece.clone();
                            }
                        }
                        else {
                            if (the_row == 3) {
                                piece_at_b_3 = piece.clone();
                            }
                            else {
                                piece_at_b_4 = piece.clone();
                            }
                        }
                    }
                    else {
                        if (the_row < 7) {
                            if (the_row == 5) {
                                piece_at_b_5 = piece.clone();
                            }
                            else {
                                piece_at_b_6 = piece.clone();
                            }
                        }
                        else {
                            if (the_row == 7) {
                                piece_at_b_7 = piece.clone();
                            }
                            else {
                                piece_at_b_8 = piece.clone();
                            }
                        }
                    }
                }
            }
            else {
                if (the_column == 3) {
                    if (the_row < 5) {
                        if (the_row < 3) {
                            if (the_row == 1) {
                                piece_at_c_1 = piece.clone();
                            }
                            else {
                                piece_at_c_2 = piece.clone();
                            }
                        }
                        else {
                            if (the_row == 3) {
                                piece_at_c_3 = piece.clone();
                            }
                            else {
                                piece_at_c_4 = piece.clone();
                            }
                        }
                    }
                    else {
                        if (the_row < 7) {
                            if (the_row == 5) {
                                piece_at_c_5 = piece.clone();
                            }
                            else {
                                piece_at_c_6 = piece.clone();
                            }
                        }
                        else {
                            if (the_row == 7) {
                                piece_at_c_7 = piece.clone();
                            }
                            else {
                                piece_at_c_8 = piece.clone();
                            }
                        }
                    }
                }
                else {
                    if (the_row < 5) {
                        if (the_row < 3) {
                            if (the_row == 1) {
                                piece_at_d_1 = piece.clone();
                            }
                            else {
                                piece_at_d_2 = piece.clone();
                            }
                        }
                        else {
                            if (the_row == 3) {
                                piece_at_d_3 = piece.clone();
                            }
                            else {
                                piece_at_d_4 = piece.clone();
                            }
                        }
                    }
                    else {
                        if (the_row < 7) {
                            if (the_row == 5) {
                                piece_at_d_5 = piece.clone();
                            }
                            else {
                                piece_at_d_6 = piece.clone();
                            }
                        }
                        else {
                            if (the_row == 7) {
                                piece_at_d_7 = piece.clone();
                            }
                            else {
                                piece_at_d_8 = piece.clone();
                            }
                        }
                    }
                }
            }
        }
        else {
            if (the_column < 7) {
                if (the_column == 5) {
                    if (the_row < 5) {
                        if (the_row < 3) {
                            if (the_row == 1) {
                                piece_at_e_1 = piece.clone();
                            }
                            else {
                                piece_at_e_2 = piece.clone();
                            }
                        }
                        else {
                            if (the_row == 3) {
                                piece_at_e_3 = piece.clone();
                            }
                            else {
                                piece_at_e_4 = piece.clone();
                            }
                        }
                    }
                    else {
                        if (the_row < 7) {
                            if (the_row == 5) {
                                piece_at_e_5 = piece.clone();
                            }
                            else {
                                piece_at_e_6 = piece.clone();
                            }
                        }
                        else {
                            if (the_row == 7) {
                                piece_at_e_7 = piece.clone();
                            }
                            else {
                                piece_at_e_8 = piece.clone();
                            }
                        }
                    }
                }
                else {
                    if (the_row < 5) {
                        if (the_row < 3) {
                            if (the_row == 1) {
                                piece_at_f_1 = piece.clone();
                            }
                            else {
                                piece_at_f_2 = piece.clone();
                            }
                        }
                        else {
                            if (the_row == 3) {
                                piece_at_f_3 = piece.clone();
                            }
                            else {
                                piece_at_f_4 = piece.clone();
                            }
                        }
                    }
                    else {
                        if (the_row < 7) {
                            if (the_row == 5) {
                                piece_at_f_5 = piece.clone();
                            }
                            else {
                                piece_at_f_6 = piece.clone();
                            }
                        }
                        else {
                            if (the_row == 7) {
                                piece_at_f_7 = piece.clone();
                            }
                            else {
                                piece_at_f_8 = piece.clone();
                            }
                        }
                    }
                }
            }
            else {
                if (the_column == 7) {
                    if (the_row < 5) {
                        if (the_row < 3) {
                            if (the_row == 1) {
                                piece_at_g_1 = piece.clone();
                            }
                            else {
                                piece_at_g_2 = piece.clone();
                            }
                        }
                        else {
                            if (the_row == 3) {
                                piece_at_g_3 = piece.clone();
                            }
                            else {
                                piece_at_g_4 = piece.clone();
                            }
                        }
                    }
                    else {
                        if (the_row < 7) {
                            if (the_row == 5) {
                                piece_at_g_5 = piece.clone();
                            }
                            else {
                                piece_at_g_6 = piece.clone();
                            }
                        }
                        else {
                            if (the_row == 7) {
                                piece_at_g_7 = piece.clone();
                            }
                            else {
                                piece_at_g_8 = piece.clone();
                            }
                        }
                    }
                }
                else {
                    if (the_row < 5) {
                        if (the_row < 3) {
                            if (the_row == 1) {
                                piece_at_h_1 = piece.clone();
                            }
                            else {
                                piece_at_h_2 = piece.clone();
                            }
                        }
                        else {
                            if (the_row == 3) {
                                piece_at_h_3 = piece.clone();
                            }
                            else {
                                piece_at_h_4 = piece.clone();
                            }
                        }
                    }
                    else {
                        if (the_row < 7) {
                            if (the_row == 5) {
                                piece_at_h_5 = piece.clone();
                            }
                            else {
                                piece_at_h_6 = piece.clone();
                            }
                        }
                        else {
                            if (the_row == 7) {
                                piece_at_h_7 = piece.clone();
                            }
                            else {
                                piece_at_h_8 = piece.clone();
                            }
                        }
                    }
                }
            }
        }
        return;
    }

    //**
    // * Gets a chess piece on the chessboard
    // *
    // * @param position The position to get the piece from
    // * @return Either the piece at the position, or null if no piece is at that
    // * position
    // */
    private int integerHash(int q) {
        int byte_0 = 0;
        int byte_1 = 0;
        int byte_2 = 0;
        int byte_3 = 0;
        int current_byte_0 = 4 * byte_0;
        int current_byte_1 = 4 * byte_1;
        int current_byte_2 = 4 * byte_2;
        int current_byte_3 = 4 * byte_3 + 6;
        int i = 0;
        if (q == -2147483648) {
            byte_0 = 128;
        }
        else if (q == -2147483647) {
            byte_0 = 128;
            byte_3 = 1;
        }
        else if (q == 2147483647) {
            byte_0 = 127;
            byte_1 = 255;
            byte_2 = 255;
            byte_3 = 255;
        }
        else if (q == -2147483646) {
            byte_0 = 128;
            byte_3 = 2;
        }
        else if (q == 2147483646) {
            byte_0 = 127;
            byte_1 = 255;
            byte_2 = 255;
            byte_3 = 254;
        }
        else {
            i = q;
            if (q < 0) {
                i = -1 - q;
            }
            byte_0 = (i / 65536) / 256;
            byte_1 = (i / 65536) % 256;
            byte_2 = (i / 256) % 256;
            byte_3 = i % 256;
            if (q < 0) {
                byte_0 = 255 - byte_0;
                byte_1 = 255 - byte_1;
                byte_2 = 255 - byte_2;
                byte_3 = 255 - byte_3;
            }
        }
        i = 12;
        while (i > 0) {
            i = i - 1;
            current_byte_0 = 4 * byte_0;
            current_byte_1 = 4 * byte_1;
            current_byte_2 = 4 * byte_2;
            current_byte_3 = 4 * byte_3 + 6;
            current_byte_0 = current_byte_0 * byte_3 + current_byte_1 * byte_2 + current_byte_2 * byte_1 + current_byte_3 * byte_0;
            current_byte_1 = current_byte_1 * byte_3 + current_byte_2 * byte_2 + current_byte_3 * byte_1;
            current_byte_2 = current_byte_2 * byte_3 + current_byte_3 * byte_2;
            current_byte_3 = current_byte_3 * byte_3 + 3;
            current_byte_2 = current_byte_2 + (current_byte_3 / 256);
            current_byte_1 = current_byte_1 + (current_byte_2 / 256);
            current_byte_0 = current_byte_0 + (current_byte_1 / 256);
            current_byte_0 = current_byte_0 % 256;
            current_byte_1 = current_byte_1 % 256;
            current_byte_2 = current_byte_2 % 256;
            current_byte_3 = current_byte_3 % 256;
            current_byte_0 = current_byte_0 * byte_3 + current_byte_1 * byte_2 + current_byte_2 * byte_1 + current_byte_3 * byte_0;
            current_byte_1 = current_byte_1 * byte_3 + current_byte_2 * byte_2 + current_byte_3 * byte_1;
            current_byte_2 = current_byte_2 * byte_3 + current_byte_3 * byte_2;
            current_byte_3 = current_byte_3 * byte_3;
            current_byte_2 = current_byte_2 + (current_byte_3 / 256);
            current_byte_1 = current_byte_1 + (current_byte_2 / 256);
            current_byte_0 = current_byte_0 + (current_byte_1 / 256);
            byte_0 = current_byte_0 % 256;
            byte_1 = current_byte_1 % 256;
            byte_2 = current_byte_2 % 256;
            byte_3 = current_byte_3 % 256;
        }
        if (byte_0 == 128 && byte_1 == 0 && byte_2 == 0 && byte_3 < 3) {
            if (byte_3 == 0) {
                return -2147483648;
            }
            if (byte_3 == 1) {
                return -2147483647;
            }
            return -2147483646;
        }
        if (byte_0 == 127 && byte_1 == 255 && byte_2 == 255 && byte_3 > 253) {
            if (byte_3 == 254) {
                return 2147483646;
            }
            return 2147483647;
        }
        if (byte_0 > 127) {
            byte_0 = byte_0 * 256 + byte_1 - 65536;
            byte_0 = byte_0 * 256 + byte_2;
            return ((byte_0 + 16) * 256 + byte_3) - 4096;
        }
        return (256 * byte_0 + byte_1) * 65536 + byte_2 * 256 + byte_3;
    }
    public ChessPiece getPiece(ChessPosition position) {
        int the_row = position.getRow();
        int the_column = position.getColumn();
        try {
            if (the_row < 5) {
                if (the_row < 3) {
                    if (the_row == 1) {
                        if (the_column < 5) {
                            if (the_column < 3) {
                                if (the_column == 1) {
                                    return piece_at_a_1.clone();
                                }
                                return piece_at_b_1.clone();
                            }
                            if (the_column == 3) {
                                return piece_at_c_1.clone();
                            }
                            return piece_at_d_1.clone();
                        }
                        if (the_column < 7) {
                            if (the_column == 5) {
                                return piece_at_e_1.clone();
                            }
                            return piece_at_f_1.clone();
                        }
                        if (the_column == 7) {
                            return piece_at_g_1.clone();
                        }
                        return piece_at_h_1.clone();
                    }
                    if (the_column < 5) {
                        if (the_column < 3) {
                            if (the_column == 1) {
                                return piece_at_a_2.clone();
                            }
                            return piece_at_b_2.clone();
                        }
                        if (the_column == 3) {
                            return piece_at_c_2.clone();
                        }
                        return piece_at_d_2.clone();
                    }
                    if (the_column < 7) {
                        if (the_column == 5) {
                            return piece_at_e_2.clone();
                        }
                        return piece_at_f_2.clone();
                    }
                    if (the_column == 7) {
                        return piece_at_g_2.clone();
                    }
                    return piece_at_h_2.clone();
                }
                if (the_row == 3) {
                    if (the_column < 5) {
                        if (the_column < 3) {
                            if (the_column == 1) {
                                return piece_at_a_3.clone();
                            }
                            return piece_at_b_3.clone();
                        }
                        if (the_column == 3) {
                            return piece_at_c_3.clone();
                        }
                        return piece_at_d_3.clone();
                    }
                    if (the_column < 7) {
                        if (the_column == 5) {
                            return piece_at_e_3.clone();
                        }
                        return piece_at_f_3.clone();
                    }
                    if (the_column == 7) {
                        return piece_at_g_3.clone();
                    }
                    return piece_at_h_3.clone();
                }
                if (the_column < 5) {
                    if (the_column < 3) {
                        if (the_column == 1) {
                            return piece_at_a_4.clone();
                        }
                        return piece_at_b_4.clone();
                    }
                    if (the_column == 3) {
                        return piece_at_c_4.clone();
                    }
                    return piece_at_d_4.clone();
                }
                if (the_column < 7) {
                    if (the_column == 5) {
                        return piece_at_e_4.clone();
                    }
                    return piece_at_f_4.clone();
                }
                if (the_column == 7) {
                    return piece_at_g_4.clone();
                }
                return piece_at_h_4.clone();
            }
            if (the_row < 7) {
                if (the_row == 5) {
                    if (the_column < 5) {
                        if (the_column < 3) {
                            if (the_column == 1) {
                                return piece_at_a_5.clone();
                            }
                            return piece_at_b_5.clone();
                        }
                        if (the_column == 3) {
                            return piece_at_c_5.clone();
                        }
                        return piece_at_d_5.clone();
                    }
                    if (the_column < 7) {
                        if (the_column == 5) {
                            return piece_at_e_5.clone();
                        }
                        return piece_at_f_5.clone();
                    }
                    if (the_column == 7) {
                        return piece_at_g_5.clone();
                    }
                    return piece_at_h_5.clone();
                }
                if (the_column < 5) {
                    if (the_column < 3) {
                        if (the_column == 1) {
                            return piece_at_a_6.clone();
                        }
                        return piece_at_b_6.clone();
                    }
                    if (the_column == 3) {
                        return piece_at_c_6.clone();
                    }
                    return piece_at_d_6.clone();
                }
                if (the_column < 7) {
                    if (the_column == 5) {
                        return piece_at_e_6.clone();
                    }
                    return piece_at_f_6.clone();
                }
                if (the_column == 7) {
                    return piece_at_g_6.clone();
                }
                return piece_at_h_6.clone();
            }
            if (the_row == 7) {
                if (the_column < 5) {
                    if (the_column < 3) {
                        if (the_column == 1) {
                            return piece_at_a_7.clone();
                        }
                        return piece_at_b_7.clone();
                    }
                    if (the_column == 3) {
                        return piece_at_c_7.clone();
                    }
                    return piece_at_d_7.clone();
                }
                if (the_column < 7) {
                    if (the_column == 5) {
                        return piece_at_e_7.clone();
                    }
                    return piece_at_f_7.clone();
                }
                if (the_column == 7) {
                    return piece_at_g_7.clone();
                }
                return piece_at_h_7.clone();
            }
            if (the_column < 5) {
                if (the_column < 3) {
                    if (the_column == 1) {
                        return piece_at_a_8.clone();
                    }
                    return piece_at_b_8.clone();
                }
                if (the_column == 3) {
                    return piece_at_c_8.clone();
                }
                return piece_at_d_8.clone();
            }
            if (the_column < 7) {
                if (the_column == 5) {
                    return piece_at_e_8.clone();
                }
                return piece_at_f_8.clone();
            }
            if (the_column == 7) {
                return piece_at_g_8.clone();
            }
            return piece_at_h_8.clone();
        } catch (NullPointerException ex) {
            return null;
        }
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        piece_at_a_1 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        piece_at_a_2 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        piece_at_a_3 = null;
        piece_at_a_4 = null;
        piece_at_a_5 = null;
        piece_at_a_6 = null;
        piece_at_a_7 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        piece_at_a_8 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        piece_at_b_1 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        piece_at_b_2 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        piece_at_b_3 = null;
        piece_at_b_4 = null;
        piece_at_b_5 = null;
        piece_at_b_6 = null;
        piece_at_b_7 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        piece_at_b_8 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        piece_at_c_1 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        piece_at_c_2 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        piece_at_c_3 = null;
        piece_at_c_4 = null;
        piece_at_c_5 = null;
        piece_at_c_6 = null;
        piece_at_c_7 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        piece_at_c_8 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        piece_at_d_1 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);
        piece_at_d_2 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        piece_at_d_3 = null;
        piece_at_d_4 = null;
        piece_at_d_5 = null;
        piece_at_d_6 = null;
        piece_at_d_7 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        piece_at_d_8 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN);
        piece_at_e_1 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING);
        piece_at_e_2 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        piece_at_e_3 = null;
        piece_at_e_4 = null;
        piece_at_e_5 = null;
        piece_at_e_6 = null;
        piece_at_e_7 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        piece_at_e_8 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);
        piece_at_f_1 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        piece_at_f_2 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        piece_at_f_3 = null;
        piece_at_f_4 = null;
        piece_at_f_5 = null;
        piece_at_f_6 = null;
        piece_at_f_7 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        piece_at_f_8 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        piece_at_g_1 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        piece_at_g_2 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        piece_at_g_3 = null;
        piece_at_g_4 = null;
        piece_at_g_5 = null;
        piece_at_g_6 = null;
        piece_at_g_7 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        piece_at_g_8 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        piece_at_h_1 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        piece_at_h_2 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        piece_at_h_3 = null;
        piece_at_h_4 = null;
        piece_at_h_5 = null;
        piece_at_h_6 = null;
        piece_at_h_7 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        piece_at_h_8 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
    }
    public boolean equals(Object compareWith) {
        if (compareWith == null) {
            return false;
        }
        if (compareWith.getClass() != getClass()) {
            return false;
        }
        ChessBoard myThing = (ChessBoard) compareWith;
        int i = 0;
        while (i < 64) {
            ChessPosition myChessPosition = new ChessPosition((i % 8) + 1, (i / 8) + 1);
            ChessPiece thatChessPiece = myThing.getPiece(myChessPosition);
            ChessPiece thisChessPiece = this.getPiece(myChessPosition);
            if (thisChessPiece == null && thatChessPiece != null) {
                return false;
            }
            else if (thatChessPiece == null && thisChessPiece != null) {
                return false;
            }
            else if (thatChessPiece.equals(thisChessPiece) == false) {
                return false;
            }
            i = 1 + i;
        }
        return true;
    }
    public ChessBoard clone() {
        ChessBoard clonedThing = new ChessBoard();
        int i = 0;
        while (i < 64) {
            ChessPosition myChessPosition = new ChessPosition((i % 8) + 1, (i / 8) + 1);
            ChessPiece thisChessPiece = this.getPiece(myChessPosition);
            if (thisChessPiece == null) {
                clonedThing.addPiece(myChessPosition, null);
            }
            else {
                clonedThing.addPiece(myChessPosition, thisChessPiece);
            }
            i = 1 + i;
        }
        return clonedThing;
    }
    public int hashCode() {
        if (getPiece(new ChessPosition(3, 7)) == null) {
            return 36;
        }
        return getPiece(new ChessPosition(3, 7)).hashCode();
    }
}

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
    public boolean equals(Object compare_with) {
        if (compare_with == null) {
            return false;
        }
        if (compare_with.getClass() != getClass()) {
            return false;
        }
        ChessBoard my_thing = (ChessBoard) compare_with;
        int i = 0;
        while (i < 64) {
            ChessPosition my_chess_position = new ChessPosition((i % 8) + 1, (i / 8) + 1);
            ChessPiece that_chess_piece = my_thing.getPiece(my_chess_position);
            ChessPiece this_chess_piece = this.getPiece(my_chess_position);
            if (this_chess_piece == null) {
                if (that_chess_piece != null) {
                    return false;
                }
            }
            else if (that_chess_piece == null) {
                if (this_chess_piece != null) {
                    return false;
                }
            }
            else if (that_chess_piece.equals(this_chess_piece) == false) {
                return false;
            }
            i = 1 + i;
        }
        return true;
    }
    public ChessBoard clone() {
        ChessBoard cloned_thing = new ChessBoard();
        int i = 0;
        while (i < 64) {
            ChessPosition my_chess_position = new ChessPosition((i % 8) + 1, (i / 8) + 1);
            ChessPiece this_chess_piece = this.getPiece(my_chess_position);
            if (this_chess_piece == null) {
                cloned_thing.addPiece(my_chess_position, null);
            }
            else {
                cloned_thing.addPiece(my_chess_position, this_chess_piece);
            }
            i = 1 + i;
        }
        return cloned_thing;
    }
    public int hashCode() {
        int i = 0;
        int my_summation = 0;
        int i_0 = 0;
        int i_1 = 0;
        int i_2 = 0;
        int i_3 = 0;
        int i_4 = 0;
        int i_5 = 0;
        int i_6 = 0;
        int i_7 = 0;
        int i_8 = 0;
        int i_9 = 0;
        int i_10 = 0;
        int i_11 = 0;
        int i_12 = 0;
        int i_13 = 0;
        int i_14 = 0;
        int i_15 = 0;
        while (i < 64) {
            int j = 0;
            ChessPosition my_chess_position = new ChessPosition((i % 8) + 1, (i / 8) + 1);
            ChessPiece this_chess_piece = this.getPiece(my_chess_position);
            if (this_chess_piece != null) {
                j = 7;
                if (this_chess_piece.getTeamColor() == ChessGame.TeamColor.BLACK) {
                    j = 1;
                }
                if (this_chess_piece.getPieceType() == ChessPiece.PieceType.QUEEN) {
                    j = j + 1;
                }
                if (this_chess_piece.getPieceType() == ChessPiece.PieceType.PAWN) {
                    j = j + 2;
                }
                if (this_chess_piece.getPieceType() == ChessPiece.PieceType.BISHOP) {
                    j = j + 3;
                }
                if (this_chess_piece.getPieceType() == ChessPiece.PieceType.KNIGHT) {
                    j = j + 4;
                }
                if (this_chess_piece.getPieceType() == ChessPiece.PieceType.ROOK) {
                    j = j + 5;
                }
            }
            i = 1 + i;
            my_summation = 13 * my_summation + j;
            if (i % 4 == 0) {
                if (i < 34) {
                    if (i < 18) {
                        if (i < 10) {
                            if (i == 4) {
                                i_0 = integerHash(my_summation);
                            }
                            else {
                                i_1 = integerHash(my_summation);
                            }
                        }
                        else {
                            if (i == 12) {
                                i_2 = integerHash(my_summation);
                            }
                            else {
                                i_3 = integerHash(my_summation);
                            }
                        }
                    }
                    else {
                        if (i < 26) {
                            if (i == 20) {
                                i_4 = integerHash(my_summation);
                            }
                            else {
                                i_5 = integerHash(my_summation);
                            }
                        }
                        else {
                            if (i == 28) {
                                i_6 = integerHash(my_summation);
                            }
                            else {
                                i_7 = integerHash(my_summation);
                            }
                        }
                    }
                }
                else {
                    if (i < 50) {
                        if (i < 42) {
                            if (i == 36) {
                                i_8 = integerHash(my_summation);
                            }
                            else {
                                i_9 = integerHash(my_summation);
                            }
                        }
                        else {
                            if (i == 44) {
                                i_10 = integerHash(my_summation);
                            }
                            else {
                                i_11 = integerHash(my_summation);
                            }
                        }
                    }
                    else {
                        if (i < 58) {
                            if (i == 52) {
                                i_12 = integerHash(my_summation);
                            }
                            else {
                                i_13 = integerHash(my_summation);
                            }
                        }
                        else {
                            if (i == 60) {
                                i_14 = integerHash(my_summation);
                            }
                            else {
                                i_15 = integerHash(my_summation);
                            }
                        }
                    }
                }
                my_summation = 0;
            }
        }
        my_summation = 0;
        if (i_0 < -2147483645) {
            my_summation = my_summation + 32768;
        }
        else if (i_0 > 2147483645) {
            my_summation = my_summation + 32767;
        }
        else if (i_0 < 0) {
            my_summation = my_summation + (65535 - ((-1 - i_0) / 65536));
        }
        else {
            my_summation = my_summation + (i_0 / 65536);
        }
        if (i_1 < -2147483645) {
            my_summation = my_summation + 32768;
        }
        else if (i_1 > 2147483645) {
            my_summation = my_summation + 32767;
        }
        else if (i_1 < 0) {
            my_summation = my_summation + (65535 - ((-1 - i_1) / 65536));
        }
        else {
            my_summation = my_summation + (i_1 / 65536);
        }
        if (i_2 < -2147483645) {
            my_summation = my_summation + 32768;
        }
        else if (i_2 > 2147483645) {
            my_summation = my_summation + 32767;
        }
        else if (i_2 < 0) {
            my_summation = my_summation + (65535 - ((-1 - i_2) / 65536));
        }
        else {
            my_summation = my_summation + (i_2 / 65536);
        }
        if (i_3 < -2147483645) {
            my_summation = my_summation + 32768;
        }
        else if (i_3 > 2147483645) {
            my_summation = my_summation + 32767;
        }
        else if (i_3 < 0) {
            my_summation = my_summation + (65535 - ((-1 - i_3) / 65536));
        }
        else {
            my_summation = my_summation + (i_3 / 65536);
        }
        if (i_4 < -2147483645) {
            my_summation = my_summation + 32768;
        }
        else if (i_4 > 2147483645) {
            my_summation = my_summation + 32767;
        }
        else if (i_4 < 0) {
            my_summation = my_summation + (65535 - ((-1 - i_4) / 65536));
        }
        else {
            my_summation = my_summation + (i_4 / 65536);
        }
        if (i_5 < -2147483645) {
            my_summation = my_summation + 32768;
        }
        else if (i_5 > 2147483645) {
            my_summation = my_summation + 32767;
        }
        else if (i_5 < 0) {
            my_summation = my_summation + (65535 - ((-1 - i_5) / 65536));
        }
        else {
            my_summation = my_summation + (i_5 / 65536);
        }
        if (i_6 < -2147483645) {
            my_summation = my_summation + 32768;
        }
        else if (i_6 > 2147483645) {
            my_summation = my_summation + 32767;
        }
        else if (i_6 < 0) {
            my_summation = my_summation + (65535 - ((-1 - i_6) / 65536));
        }
        else {
            my_summation = my_summation + (i_6 / 65536);
        }
        if (i_7 < -2147483645) {
            my_summation = my_summation + 32768;
        }
        else if (i_7 > 2147483645) {
            my_summation = my_summation + 32767;
        }
        else if (i_7 < 0) {
            my_summation = my_summation + (65535 - ((-1 - i_7) / 65536));
        }
        else {
            my_summation = my_summation + (i_7 / 65536);
        }
        if (i_8 < -2147483645) {
            my_summation = my_summation + 32768;
        }
        else if (i_8 > 2147483645) {
            my_summation = my_summation + 32767;
        }
        else if (i_8 < 0) {
            my_summation = my_summation + (65535 - ((-1 - i_8) / 65536));
        }
        else {
            my_summation = my_summation + (i_8 / 65536);
        }
        if (i_9 < -2147483645) {
            my_summation = my_summation + 32768;
        }
        else if (i_9 > 2147483645) {
            my_summation = my_summation + 32767;
        }
        else if (i_9 < 0) {
            my_summation = my_summation + (65535 - ((-1 - i_9) / 65536));
        }
        else {
            my_summation = my_summation + (i_9 / 65536);
        }
        if (i_10 < -2147483645) {
            my_summation = my_summation + 32768;
        }
        else if (i_10 > 2147483645) {
            my_summation = my_summation + 32767;
        }
        else if (i_10 < 0) {
            my_summation = my_summation + (65535 - ((-1 - i_10) / 65536));
        }
        else {
            my_summation = my_summation + (i_10 / 65536);
        }
        if (i_11 < -2147483645) {
            my_summation = my_summation + 32768;
        }
        else if (i_11 > 2147483645) {
            my_summation = my_summation + 32767;
        }
        else if (i_11 < 0) {
            my_summation = my_summation + (65535 - ((-1 - i_11) / 65536));
        }
        else {
            my_summation = my_summation + (i_11 / 65536);
        }
        if (i_12 < -2147483645) {
            my_summation = my_summation + 32768;
        }
        else if (i_12 > 2147483645) {
            my_summation = my_summation + 32767;
        }
        else if (i_12 < 0) {
            my_summation = my_summation + (65535 - ((-1 - i_12) / 65536));
        }
        else {
            my_summation = my_summation + (i_12 / 65536);
        }
        if (i_13 < -2147483645) {
            my_summation = my_summation + 32768;
        }
        else if (i_13 > 2147483645) {
            my_summation = my_summation + 32767;
        }
        else if (i_13 < 0) {
            my_summation = my_summation + (65535 - ((-1 - i_13) / 65536));
        }
        else {
            my_summation = my_summation + (i_13 / 65536);
        }
        if (i_14 < -2147483645) {
            my_summation = my_summation + 32768;
        }
        else if (i_14 > 2147483645) {
            my_summation = my_summation + 32767;
        }
        else if (i_14 < 0) {
            my_summation = my_summation + (65535 - ((-1 - i_14) / 65536));
        }
        else {
            my_summation = my_summation + (i_14 / 65536);
        }
        if (i_15 < -2147483645) {
            my_summation = my_summation + 32768;
        }
        else if (i_15 > 2147483645) {
            my_summation = my_summation + 32767;
        }
        else if (i_15 < 0) {
            my_summation = my_summation + (65535 - ((-1 - i_15) / 65536));
        }
        else {
            my_summation = my_summation + (i_15 / 65536);
        }
        if (i_0 == -2147483648) {
            i_0 = 0;
        }
        else if (i_0 == -2147483647) {
            i_0 = 1;
        }
        else if (i_0 == -2147483646) {
            i_0 = 2;
        }
        else if (i_0 == 2147483646) {
            i_0 = 65534;
        }
        else if (i_0 == 2147483647) {
            i_0 = 65535;
        }
        else if (i_0 < 0) {
            i_0 = 65535 - ((-1 - i_0) % 65536);
        }
        else {
            i_0 = i_0 % 65536;
        }
        if (i_1 == -2147483648) {
            i_1 = 0;
        }
        else if (i_1 == -2147483647) {
            i_1 = 1;
        }
        else if (i_1 == -2147483646) {
            i_1 = 2;
        }
        else if (i_1 == 2147483646) {
            i_1 = 65534;
        }
        else if (i_1 == 2147483647) {
            i_1 = 65535;
        }
        else if (i_1 < 0) {
            i_1 = 65535 - ((-1 - i_1) % 65536);
        }
        else {
            i_1 = i_1 % 65536;
        }
        if (i_2 == -2147483648) {
            i_2 = 0;
        }
        else if (i_2 == -2147483647) {
            i_2 = 1;
        }
        else if (i_2 == -2147483646) {
            i_2 = 2;
        }
        else if (i_2 == 2147483646) {
            i_2 = 65534;
        }
        else if (i_2 == 2147483647) {
            i_2 = 65535;
        }
        else if (i_2 < 0) {
            i_2 = 65535 - ((-1 - i_2) % 65536);
        }
        else {
            i_2 = i_2 % 65536;
        }
        if (i_3 == -2147483648) {
            i_3 = 0;
        }
        else if (i_3 == -2147483647) {
            i_3 = 1;
        }
        else if (i_3 == -2147483646) {
            i_3 = 2;
        }
        else if (i_3 == 2147483646) {
            i_3 = 65534;
        }
        else if (i_3 == 2147483647) {
            i_3 = 65535;
        }
        else if (i_3 < 0) {
            i_3 = 65535 - ((-1 - i_3) % 65536);
        }
        else {
            i_3 = i_3 % 65536;
        }
        if (i_4 == -2147483648) {
            i_4 = 0;
        }
        else if (i_4 == -2147483647) {
            i_4 = 1;
        }
        else if (i_4 == -2147483646) {
            i_4 = 2;
        }
        else if (i_4 == 2147483646) {
            i_4 = 65534;
        }
        else if (i_4 == 2147483647) {
            i_4 = 65535;
        }
        else if (i_4 < 0) {
            i_4 = 65535 - ((-1 - i_4) % 65536);
        }
        else {
            i_4 = i_4 % 65536;
        }
        if (i_5 == -2147483648) {
            i_5 = 0;
        }
        else if (i_5 == -2147483647) {
            i_5 = 1;
        }
        else if (i_5 == -2147483646) {
            i_5 = 2;
        }
        else if (i_5 == 2147483646) {
            i_5 = 65534;
        }
        else if (i_5 == 2147483647) {
            i_5 = 65535;
        }
        else if (i_5 < 0) {
            i_5 = 65535 - ((-1 - i_5) % 65536);
        }
        else {
            i_5 = i_5 % 65536;
        }
        if (i_6 == -2147483648) {
            i_6 = 0;
        }
        else if (i_6 == -2147483647) {
            i_6 = 1;
        }
        else if (i_6 == -2147483646) {
            i_6 = 2;
        }
        else if (i_6 == 2147483646) {
            i_6 = 65534;
        }
        else if (i_6 == 2147483647) {
            i_6 = 65535;
        }
        else if (i_6 < 0) {
            i_6 = 65535 - ((-1 - i_6) % 65536);
        }
        else {
            i_6 = i_6 % 65536;
        }
        if (i_7 == -2147483648) {
            i_7 = 0;
        }
        else if (i_7 == -2147483647) {
            i_7 = 1;
        }
        else if (i_7 == -2147483646) {
            i_7 = 2;
        }
        else if (i_7 == 2147483646) {
            i_7 = 65534;
        }
        else if (i_7 == 2147483647) {
            i_7 = 65535;
        }
        else if (i_7 < 0) {
            i_7 = 65535 - ((-1 - i_7) % 65536);
        }
        else {
            i_7 = i_7 % 65536;
        }
        if (i_8 == -2147483648) {
            i_8 = 0;
        }
        else if (i_8 == -2147483647) {
            i_8 = 1;
        }
        else if (i_8 == -2147483646) {
            i_8 = 2;
        }
        else if (i_8 == 2147483646) {
            i_8 = 65534;
        }
        else if (i_8 == 2147483647) {
            i_8 = 65535;
        }
        else if (i_8 < 0) {
            i_8 = 65535 - ((-1 - i_8) % 65536);
        }
        else {
            i_8 = i_8 % 65536;
        }
        if (i_9 == -2147483648) {
            i_9 = 0;
        }
        else if (i_9 == -2147483647) {
            i_9 = 1;
        }
        else if (i_9 == -2147483646) {
            i_9 = 2;
        }
        else if (i_9 == 2147483646) {
            i_9 = 65534;
        }
        else if (i_9 == 2147483647) {
            i_9 = 65535;
        }
        else if (i_9 < 0) {
            i_9 = 65535 - ((-1 - i_9) % 65536);
        }
        else {
            i_9 = i_9 % 65536;
        }
        if (i_10 == -2147483648) {
            i_10 = 0;
        }
        else if (i_10 == -2147483647) {
            i_10 = 1;
        }
        else if (i_10 == -2147483646) {
            i_10 = 2;
        }
        else if (i_10 == 2147483646) {
            i_10 = 65534;
        }
        else if (i_10 == 2147483647) {
            i_10 = 65535;
        }
        else if (i_10 < 0) {
            i_10 = 65535 - ((-1 - i_10) % 65536);
        }
        else {
            i_10 = i_10 % 65536;
        }
        if (i_11 == -2147483648) {
            i_11 = 0;
        }
        else if (i_11 == -2147483647) {
            i_11 = 1;
        }
        else if (i_11 == -2147483646) {
            i_11 = 2;
        }
        else if (i_11 == 2147483646) {
            i_11 = 65534;
        }
        else if (i_11 == 2147483647) {
            i_11 = 65535;
        }
        else if (i_11 < 0) {
            i_11 = 65535 - ((-1 - i_11) % 65536);
        }
        else {
            i_11 = i_11 % 65536;
        }
        if (i_12 == -2147483648) {
            i_12 = 0;
        }
        else if (i_12 == -2147483647) {
            i_12 = 1;
        }
        else if (i_12 == -2147483646) {
            i_12 = 2;
        }
        else if (i_12 == 2147483646) {
            i_12 = 65534;
        }
        else if (i_12 == 2147483647) {
            i_12 = 65535;
        }
        else if (i_12 < 0) {
            i_12 = 65535 - ((-1 - i_12) % 65536);
        }
        else {
            i_12 = i_12 % 65536;
        }
        if (i_13 == -2147483648) {
            i_13 = 0;
        }
        else if (i_13 == -2147483647) {
            i_13 = 1;
        }
        else if (i_13 == -2147483646) {
            i_13 = 2;
        }
        else if (i_13 == 2147483646) {
            i_13 = 65534;
        }
        else if (i_13 == 2147483647) {
            i_13 = 65535;
        }
        else if (i_13 < 0) {
            i_13 = 65535 - ((-1 - i_13) % 65536);
        }
        else {
            i_13 = i_13 % 65536;
        }
        if (i_14 == -2147483648) {
            i_14 = 0;
        }
        else if (i_14 == -2147483647) {
            i_14 = 1;
        }
        else if (i_14 == -2147483646) {
            i_14 = 2;
        }
        else if (i_14 == 2147483646) {
            i_14 = 65534;
        }
        else if (i_14 == 2147483647) {
            i_14 = 65535;
        }
        else if (i_14 < 0) {
            i_14 = 65535 - ((-1 - i_14) % 65536);
        }
        else {
            i_14 = i_14 % 65536;
        }
        if (i_15 == -2147483648) {
            i_15 = 0;
        }
        else if (i_15 == -2147483647) {
            i_15 = 1;
        }
        else if (i_15 == -2147483646) {
            i_15 = 2;
        }
        else if (i_15 == 2147483646) {
            i_15 = 65534;
        }
        else if (i_15 == 2147483647) {
            i_15 = 65535;
        }
        else if (i_15 < 0) {
            i_15 = 65535 - ((-1 - i_15) % 65536);
        }
        else {
            i_15 = i_15 % 65536;
        }
        i_0 = i_0 + i_1 + i_2 + i_3 + i_4 + i_5 + i_6 + i_7 + i_8 + i_9 + i_10 + i_11 + i_12 + i_13 + i_14 + i_15;
        my_summation = ((my_summation % 65536) + (i_0 / 65536)) % 65536;
        i_0 = i_0 % 65536;
        if (my_summation == 32768 && i_0 < 3) {
            if (i_0 == 0) {
                return -2147483648;
            }
            if (i_0 == 1) {
                return -2147483647;
            }
            return -2147483646;
        }
        if (my_summation == 32767 && i_0 > 65533) {
            if (i_0 == 65534) {
                return 2147483646;
            }
            return 2147483647;
        }
        if (my_summation > 32767) {
            return -1 - ((65535 - my_summation) * 65536 + (65535 - i_0));
        }
        return my_summation * 65536 + i_0;
    }
}

package chess;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    int firstGroup;
    int secondGroup;
    int thirdGroup;
    int fourthGroup;
    int fifthGroup;
    int sixthGroup;
    int seventhGroup;
    int eighthGroup;
    int ninthGroup;
    int tenthGroup;
    int eleventhGroup;
    int twelfthGroup;
    int thirteenthGroup;
    int fourteenthGroup;
    int fifteenthGroup;
    int sixteenthGroup;

    public ChessBoard() {
        geBr();
        
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        int i = 1;
        if (position.getColumn() > 4) {
            i = 2;
        }
        i = i + position.getRow() * 2 - 2;
        int j = (position.getColumn() - 1) % 4;
        if (i == 1) {
            firstGroup = chg(firstGroup, piece, j);
        } else if (i == 2) {
            secondGroup = chg(secondGroup, piece, j);
        } else if (i == 3) {
            thirdGroup = chg(thirdGroup, piece, j);
        } else if (i == 4) {
            fourthGroup = chg(fourthGroup, piece, j);
        } else if (i == 5) {
            fifthGroup = chg(fifthGroup, piece, j);
        } else if (i == 6) {
            sixthGroup = chg(sixthGroup, piece, j);
        } else if (i == 7) {
            seventhGroup = chg(seventhGroup, piece, j);
        } else if (i == 8) {
            eighthGroup = chg(eighthGroup, piece, j);
        } else if (i == 9) {
            ninthGroup = chg(ninthGroup, piece, j);
        } else if (i == 10) {
            tenthGroup = chg(tenthGroup, piece, j);
        } else if (i == 11) {
            eleventhGroup = chg(eleventhGroup, piece, j);
        } else if (i == 12) {
            twelfthGroup = chg(twelfthGroup, piece, j);
        } else if (i == 13) {
            thirteenthGroup = chg(thirteenthGroup, piece, j);
        } else if (i == 14) {
            fourteenthGroup = chg(fourteenthGroup, piece, j);
        } else if (i == 15) {
            fifteenthGroup = chg(fifteenthGroup, piece, j);
        } else {
            sixteenthGroup = chg(sixteenthGroup, piece, j);
        }
    }
    private int cvVa(ChessPiece pc) {
        if (pc == null) {
            return 0;
        }
        int i = 1;
        if (pc.getPieceType() == ChessPiece.PieceType.PAWN) {
            i = 2;
        } else if (pc.getPieceType() == ChessPiece.PieceType.KING) {
            i = 3;
        } else if (pc.getPieceType() == ChessPiece.PieceType.QUEEN) {
            i = 4;
        } else if (pc.getPieceType() == ChessPiece.PieceType.ROOK) {
            i = 5;
        } else if (pc.getPieceType() == ChessPiece.PieceType.KNIGHT) {
            i = 6;
        }
        if (pc.getTeamColor() == ChessGame.TeamColor.WHITE) {
            return i;
        }
        return i + 6;
    }
    private ChessPiece cvVa(int i) {
        if (i == 0) {
            return null;
        }
        ChessPiece.PieceType pT = ChessPiece.PieceType.KNIGHT;
        if (i % 6 == 1) {
            pT = ChessPiece.PieceType.BISHOP;
        } else if (i % 6 == 2) {
            pT = ChessPiece.PieceType.PAWN;
        } else if (i % 6 == 3) {
            pT = ChessPiece.PieceType.KING;
        } else if (i % 6 == 4) {
            pT = ChessPiece.PieceType.QUEEN;
        } else if (i % 6 == 5) {
            pT = ChessPiece.PieceType.ROOK;
        }
        if (i < 7) {
            return new ChessPiece(ChessGame.TeamColor.WHITE, pT);
        }
        return new ChessPiece(ChessGame.TeamColor.BLACK, pT);
    }
    private int chg(int oldVal, ChessPiece newVal, int pos) {
        if (pos == 0) {
            return (oldVal % 2197) + cvVa(newVal) * 2197;
        } else if (pos == 1) {
            return (oldVal / 2197) * 2197 + (oldVal % 169) + 169 * cvVa(newVal);
        } else if (pos == 2) {
            return (oldVal / 169) * 169 + (oldVal % 13) + 13 * cvVa(newVal);
        }
        return (oldVal / 13) * 13 + cvVa(newVal);
    }
    private ChessPiece chg(int oldVal, int pos) {
        if (pos == 0) {
            return cvVa(oldVal / 2197);
        } else if (pos == 1) {
            return cvVa((oldVal / 169) % 13);
        } else if (pos == 2) {
            return cvVa((oldVal / 13) % 13);
        }
        return cvVa(oldVal % 13);
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        int i = 1;
        int j = position.getColumn();
        if (j > 4) {
            i = 2;
        }
        j = (j - 1) % 4;
        i = i + position.getRow() * 2 - 2;
        if (i == 1) {
            return chg(firstGroup, j);
        } else if (i == 2) {
            return chg(secondGroup, j);
        } else if (i == 3) {
            return chg(thirdGroup, j);
        } else if (i == 4) {
            return chg(fourthGroup, j);
        } else if (i == 5) {
            return chg(fifthGroup, j);
        } else if (i == 6) {
            return chg(sixthGroup, j);
        } else if (i == 7) {
            return chg(seventhGroup, j);
        } else if (i == 8) {
            return chg(eighthGroup, j);
        } else if (i == 9) {
            return chg(ninthGroup, j);
        } else if (i == 10) {
            return chg(tenthGroup, j);
        } else if (i == 11) {
            return chg(eleventhGroup, j);
        } else if (i == 12) {
            return chg(twelfthGroup, j);
        } else if (i == 13) {
            return chg(thirteenthGroup, j);
        } else if (i == 14) {
            return chg(fourteenthGroup, j);
        } else if (i == 15) {
            return chg(fifteenthGroup, j);
        } else {
            return chg(sixteenthGroup, j);
        }
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        geBr();
        addPiece(new ChessPosition(1, 4), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN));
        addPiece(new ChessPosition(1, 5), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING));
        addPiece(new ChessPosition(8, 4), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN));
        addPiece(new ChessPosition(8, 5), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING));
        addPiece(new ChessPosition(1, 3), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP));
        addPiece(new ChessPosition(1, 6), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP));
        addPiece(new ChessPosition(8, 3), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP));
        addPiece(new ChessPosition(8, 6), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP));
        addPiece(new ChessPosition(1, 2), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT));
        addPiece(new ChessPosition(1, 7), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT));
        addPiece(new ChessPosition(8, 2), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT));
        addPiece(new ChessPosition(8, 7), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT));
        addPiece(new ChessPosition(1, 1), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK));
        addPiece(new ChessPosition(1, 8), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK));
        addPiece(new ChessPosition(8, 1), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK));
        addPiece(new ChessPosition(8, 8), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK));
        int i = 1;
        while (i < 9) {
            addPiece(new ChessPosition(2, i), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN));
            addPiece(new ChessPosition(7, i), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN));
            i = i + 1;
        }
    }
    private void geBr() {
        firstGroup = 0;
        secondGroup = 0;
        thirdGroup = 0;
        fourthGroup = 0;
        fifthGroup = 0;
        sixthGroup = 0;
        seventhGroup = 0;
        eighthGroup = 0;
        ninthGroup = 0;
        tenthGroup = 0;
        eleventhGroup = 0;
        twelfthGroup = 0;
        thirteenthGroup = 0;
        fourteenthGroup = 0;
        fifteenthGroup = 0;
        sixteenthGroup = 0;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        ChessBoard ch = (ChessBoard) obj;
        int i = 1;
        int j = 1;
        while (i < 9) {
            j = 1;
            while (j < 9) {
                if (cvVa(ch.getPiece(new ChessPosition(i, j))) != cvVa(getPiece(new ChessPosition(i, j)))) {
                    return false;
                }
                j = j + 1;
            }
            i = i + 1;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return 22;
    }
    public ChessBoard clone() {
        ChessBoard cB = new ChessBoard();
        int i = 1;
        int j = 1;
        while (i < 9) {
            j = 1;
            while (j < 9) {
                cB.addPiece(new ChessPosition(i, j), getPiece(new ChessPosition(i, j)));
                j = j + 1;
            }
            i = i + 1;
        }
        return cB;
    }
}

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
    ChessGame.TeamColor pC;
    PieceType pType;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        pC = pieceColor;
        pType = type;
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
        return pC;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return pType;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        if (board.getPiece(myPosition).getPieceType() == PieceType.PAWN) {
            if (board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {
                return pawnMoves(board, myPosition, 0);
            }
            return pawnMoves(board, myPosition, 1);
        }
        if (board.getPiece(myPosition).getPieceType() == PieceType.KING || board.getPiece(myPosition).getPieceType() == PieceType.KNIGHT) {
            return kingMoves(board, myPosition);
        }
        Collection<ChessMove> cM = new ArrayList<>();
        if (board.getPiece(myPosition).getPieceType() == PieceType.QUEEN || board.getPiece(myPosition).getPieceType() == PieceType.BISHOP) {
            cM = putTogether(cM, board, myPosition, 0);
            cM = putTogether(cM, board, myPosition, 2);
            cM = putTogether(cM, board, myPosition, 6);
            cM = putTogether(cM, board, myPosition, 8);
        }
        if (board.getPiece(myPosition).getPieceType() == PieceType.QUEEN || board.getPiece(myPosition).getPieceType() == PieceType.ROOK) {
            cM = putTogether(cM, board, myPosition, 1);
            cM = putTogether(cM, board, myPosition, 3);
            cM = putTogether(cM, board, myPosition, 5);
            cM = putTogether(cM, board, myPosition, 7);
        }
        return cM;
    }
    private Collection<ChessMove> putTogether(Collection<ChessMove> pT, ChessBoard board, ChessPosition myPosition, int mve) {
        Collection<ChessMove> cM = new ArrayList<>();
        for (ChessMove i: pT) {
            cM.add(i.clone());
        }
        int r = (mve % 3) - 1;
        int c = (mve / 3) - 1;
        int mR = myPosition.getRow() + r;
        int mC = myPosition.getColumn() + c;
        ChessPosition pstn = myPosition.clone();
        boolean g;
        while (mR > 0 && mR < 9 && mC > 0 && mC < 9) {
            g = true;
            pstn = new ChessPosition(mR, mC);
            mR = mR + r;
            mC = mC + c;
            if (board.getPiece(pstn) != null) {
                if (board.getPiece(pstn).getTeamColor() == board.getPiece(myPosition).getTeamColor()) {
                    g = false;
                }
                mR = 19;
            }
            if (g) {
                cM.add(new ChessMove(myPosition, pstn, null));
            }
        }
        return cM;
    }
    private Collection<ChessMove> kingMoves(ChessBoard board, ChessPosition myPosition) {
        int ra = myPosition.getRow() - 1;
        int rb = myPosition.getRow() - 1;
        int rc = myPosition.getRow() + 1;
        int rd = myPosition.getRow() + 1;
        int re = myPosition.getRow() - 1;
        int rf = myPosition.getRow();
        int rg = myPosition.getRow() + 1;
        int rh = myPosition.getRow();
        int ca = myPosition.getColumn() + 1;
        int cb = myPosition.getColumn() - 1;
        int cc = myPosition.getColumn() - 1;
        int cd = myPosition.getColumn() + 1;
        int ce = myPosition.getColumn();
        int cf = myPosition.getColumn() - 1;
        int cg = myPosition.getColumn();
        int ch = myPosition.getColumn() + 1;
        if (board.getPiece(myPosition).getPieceType() == PieceType.KNIGHT) {
            ra = myPosition.getRow() - 2;
            rb = myPosition.getRow() + 2;
            rc = myPosition.getRow() + 2;
            rd = myPosition.getRow() - 2;
            re = myPosition.getRow() - 1;
            rf = myPosition.getRow() + 1;
            rg = myPosition.getRow() + 1;
            rh = myPosition.getRow() - 1;
            ca = myPosition.getColumn() - 1;
            cb = myPosition.getColumn() + 1;
            cc = myPosition.getColumn() - 1;
            cd = myPosition.getColumn() + 1;
            ce = myPosition.getColumn() - 2;
            cf = myPosition.getColumn() + 2;
            cg = myPosition.getColumn() - 2;
            ch = myPosition.getColumn() + 2;
        }
        if (ra < 1 || ra > 8 || ca < 1 || ca > 8) {
            ra = myPosition.getRow();
            ca = myPosition.getColumn();
        }
        if (rb < 1 || rb > 8 || cb < 1 || cb > 8) {
            rb = myPosition.getRow();
            cb = myPosition.getColumn();
        }
        if (rc < 1 || rc > 8 || cc < 1 || cc > 8) {
            rc = myPosition.getRow();
            cc = myPosition.getColumn();
        }
        if (rd < 1 || rd > 8 || cd < 1 || cd > 8) {
            rd = myPosition.getRow();
            cd = myPosition.getColumn();
        }
        if (re < 1 || re > 8 || ce < 1 || ce > 8) {
            re = myPosition.getRow();
            ce = myPosition.getColumn();
        }
        if (rf < 1 || rf > 8 || cf < 1 || cf > 8) {
            rf = myPosition.getRow();
            cf = myPosition.getColumn();
        }
        if (rg < 1 || rg > 8 || cg < 1 || cg > 8) {
            rg = myPosition.getRow();
            cg = myPosition.getColumn();
        }
        if (rh < 1 || rh > 8 || ch < 1 || ch > 8) {
            rh = myPosition.getRow();
            ch = myPosition.getColumn();
        }
        Collection<ChessMove> cM = new ArrayList<>();
        cM.add(new ChessMove(myPosition, new ChessPosition(ra, ca), null));
        cM.add(new ChessMove(myPosition, new ChessPosition(rb, cb), null));
        cM.add(new ChessMove(myPosition, new ChessPosition(rc, cc), null));
        cM.add(new ChessMove(myPosition, new ChessPosition(rd, cd), null));
        cM.add(new ChessMove(myPosition, new ChessPosition(re, ce), null));
        cM.add(new ChessMove(myPosition, new ChessPosition(rf, cf), null));
        cM.add(new ChessMove(myPosition, new ChessPosition(rg, cg), null));
        cM.add(new ChessMove(myPosition, new ChessPosition(rh, ch), null));
        Collection<ChessMove> cN = new ArrayList<>();
        for (ChessMove i: cM) {
            if (board.getPiece(i.getEndPosition()) == null) {
                cN.add(i.clone());
            } else if (board.getPiece(i.getEndPosition()).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                cN.add(i.clone());
            }
        }
        return cN;
    }
    private Collection<ChessMove> pawnMoves(ChessBoard board, ChessPosition myPosition, int cl) {
        Collection<ChessMove> cM = new ArrayList<>();
        int r = myPosition.getRow();
        int c = myPosition.getColumn();
        if (myPosition.getRow() == 2 + 5 * cl) {
            if (board.getPiece(new ChessPosition(r + 1 - 2 * cl, c)) == null) {
                if (board.getPiece(new ChessPosition(r + 2 - 4 * cl, c)) == null) {
                    cM.add(new ChessMove(myPosition, new ChessPosition(r + 2 - 4 * cl, c), null));
                }
            }
        }
        boolean cal = false;
        boolean caf = (board.getPiece(new ChessPosition(r + 1 - 2 * cl, c)) == null);
        boolean car = false;
        if (c != 1) {
            ChessPosition cP = new ChessPosition(r + 1 - 2 * cl, c - 1);
            if (board.getPiece(cP) != null) {
                if ((board.getPiece(cP).getTeamColor() == ChessGame.TeamColor.BLACK) == (cl == 0)) {
                    cal = true;
                }
            }
        }
        if (c != 8) {
            ChessPosition cP = new ChessPosition(r + 1 - 2 * cl, c + 1);
            if (board.getPiece(cP) != null) {
                if ((board.getPiece(cP).getTeamColor() == ChessGame.TeamColor.WHITE) == (cl == 1)) {
                    car = true;
                }
            }
        }
        r = r + 1 - 2 * cl;
        boolean canPromote = (myPosition.getRow() == 7 - 5 * cl);
        if (cal) {
            if (canPromote) {
                cM.add(new ChessMove(myPosition, new ChessPosition(r, c - 1), PieceType.QUEEN));
                cM.add(new ChessMove(myPosition, new ChessPosition(r, c - 1), PieceType.ROOK));
                cM.add(new ChessMove(myPosition, new ChessPosition(r, c - 1), PieceType.KNIGHT));
                cM.add(new ChessMove(myPosition, new ChessPosition(r, c - 1), PieceType.BISHOP));
            }
            else {
                cM.add(new ChessMove(myPosition, new ChessPosition(r, c - 1), null));
            }
        }
        if (caf) {
            if (canPromote) {
                cM.add(new ChessMove(myPosition, new ChessPosition(r, c), PieceType.QUEEN));
                cM.add(new ChessMove(myPosition, new ChessPosition(r, c), PieceType.ROOK));
                cM.add(new ChessMove(myPosition, new ChessPosition(r, c), PieceType.KNIGHT));
                cM.add(new ChessMove(myPosition, new ChessPosition(r, c), PieceType.BISHOP));
            }
            else {
                cM.add(new ChessMove(myPosition, new ChessPosition(r, c), null));
            }
        }
        if (car) {
            if (canPromote) {
                cM.add(new ChessMove(myPosition, new ChessPosition(r, c + 1), PieceType.QUEEN));
                cM.add(new ChessMove(myPosition, new ChessPosition(r, c + 1), PieceType.ROOK));
                cM.add(new ChessMove(myPosition, new ChessPosition(r, c + 1), PieceType.KNIGHT));
                cM.add(new ChessMove(myPosition, new ChessPosition(r, c + 1), PieceType.BISHOP));
            }
            else {
                cM.add(new ChessMove(myPosition, new ChessPosition(r, c + 1), null));
            }
        }
        return cM;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        ChessPiece ch = (ChessPiece) obj;
        String s = "s";
        return pC == ch.getTeamColor() && pType == ch.getPieceType();
    }

    @Override
    public int hashCode() {
        int i = 3;
        if (pType == ChessPiece.PieceType.PAWN) {
            i = 1;
        } else if (pType == ChessPiece.PieceType.KING) {
            i = 4;
        } else if (pType == ChessPiece.PieceType.QUEEN) {
            i = 5;
        } else if (pType == ChessPiece.PieceType.ROOK) {
            i = 2;
        } else if (pType == ChessPiece.PieceType.KNIGHT) {
            i = 0;
        }
        if (pC == ChessGame.TeamColor.BLACK) {
            return i;
        }
        return i + 6;
    }
    public ChessPiece clone() {
        return new ChessPiece(getTeamColor(), getPieceType());
    }
}

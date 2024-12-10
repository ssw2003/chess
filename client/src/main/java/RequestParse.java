import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

public class RequestParse {
    private ChessMove cM;
    private ChessPosition cP;
    private int i;
    public RequestParse(String status, String lastCommand) {
        cM = null;
        cP = null;
        i = 14;
        if (lastCommand.equals("HELP")) {
            if (status.equals("Game UI") || status.equals("Merely Logged In") || status.equals("Not Logged In")) {
                i = 0;
            }
        } else if (status.equals("Merely Logged In")) {
            switch (lastCommand) {
                case "LOGOUT" -> i = 2;
                case "CREATE GAME" -> i = 3;
                case "LIST GAMES" -> i = 4;
                case "PLAY GAME" -> i = 12;
                case "OBSERVE GAME" -> i = 13;
            }
        } else if (status.equals("Game UI")) {
            switch (lastCommand) {
                case "REDRAW BOARD" -> i = 5;
                case "RESIGN" -> i = 8;
                case "LEAVE GAME" -> i = 9;
            }
        } else if (status.equals("Not Logged In")) {
            switch (lastCommand) {
                case "QUIT" -> i = 1;
                case "REGISTER" -> i = 10;
                case "LOGIN" -> i = 11;
            }
        }
        String mC = lastCommand;
        if (!status.equals("Game UI") || mC.isEmpty()) {
            mC = "W";
        }
        if (mC.length() != 13 && mC.length() != 16) {
            if (mC.length() < 39 || mC.length() > 41) {
                mC = "W";
            }
        }
        if (mC.length() == 13) {
            if (!mC.substring(0, 11).equals("SHOW MOVES ")) {
                mC = "W";
            } else if (letterToNumber(mC.substring(11, 12)) == 0) {
                mC = "W";
            } else if (numberToNumber(mC.substring(12, 13)) == 0) {
                mC = "W";
            }
        } else if (mC.length() == 16) {
            if (!(mC.substring(0, 10) + mC.substring(12, 14)).equals("MAKE MOVE ->")) {
                mC = "W";
            } else if (letterToNumber(mC.substring(10, 11)) == 0) {
                mC = "W";
            } else if (numberToNumber(mC.substring(11, 12)) == 0) {
                mC = "W";
            } else if (letterToNumber(mC.substring(14, 15)) == 0) {
                mC = "W";
            } else if (numberToNumber(mC.substring(15, 16)) == 0) {
                mC = "W";
            }
        } else if (mC.length() > 38) {
            if (!(mC.substring(0, 10) + mC.substring(12, 14) + mC.substring(16, 35)).equals("MAKE MOVE -> WITH PROMOTION OF ")) {
                mC = "W";
            } else if (letterToNumber(mC.substring(10, 11)) == 0) {
                mC = "W";
            } else if (numberToNumber(mC.substring(11, 12)) == 0) {
                mC = "W";
            } else if (letterToNumber(mC.substring(14, 15)) == 0) {
                mC = "W";
            } else if (numberToNumber(mC.substring(15, 16)) == 0) {
                mC = "W";
            } else if (!(mC.substring(35).equals("KING") ||
                    mC.substring(35).equals("QUEEN") ||
                    mC.substring(35).equals("PAWN") ||
                    mC.substring(35).equals("ROOK") ||
                    mC.substring(35).equals("BISHOP") ||
                    mC.substring(35).equals("KNIGHT"))) {
                mC = "W";
            }
        }
        if (mC.length() == 13) {
            i = 6;
            cP = new ChessPosition(letterToNumber(mC.substring(11, 12)), numberToNumber(mC.substring(12, 13)));
        } else if (mC.length() >= 16) {
            i = 7;
            ChessPosition st = new ChessPosition(letterToNumber(mC.substring(10, 11)), numberToNumber(mC.substring(11, 12)));
            ChessPosition en = new ChessPosition(letterToNumber(mC.substring(14, 15)), numberToNumber(mC.substring(15, 16)));
            if (mC.length() >= 35) {
                switch (mC.substring(35)) {
                    case "PAWN" -> cM = new ChessMove(st, en, ChessPiece.PieceType.PAWN);
                    case "ROOK" -> cM = new ChessMove(st, en, ChessPiece.PieceType.ROOK);
                    case "KNIGHT" -> cM = new ChessMove(st, en, ChessPiece.PieceType.KNIGHT);
                    case "BISHOP" -> cM = new ChessMove(st, en, ChessPiece.PieceType.BISHOP);
                    case "KING" -> cM = new ChessMove(st, en, ChessPiece.PieceType.KING);
                    default -> cM = new ChessMove(st, en, ChessPiece.PieceType.QUEEN);
                }
            }
            else {
                cM = new ChessMove(st, en, null);
            }
        }
    }
    private int letterToNumber(String s) {
        return switch (s) {
            case "A" -> 1;
            case "B" -> 2;
            case "C" -> 3;
            case "D" -> 4;
            case "E" -> 5;
            case "F" -> 6;
            case "G" -> 7;
            case "H" -> 8;
            default -> 0;
        };
    }
    private int numberToNumber(String s) {
        return switch (s) {
            case "1" -> 1;
            case "2" -> 2;
            case "3" -> 3;
            case "4" -> 4;
            case "5" -> 5;
            case "6" -> 6;
            case "7" -> 7;
            case "8" -> 8;
            default -> 0;
        };
    }

    @Override
    public boolean equals(Object compareWith) {
        if (compareWith == null) {
            return false;
        }
        if (compareWith.getClass() != getClass()) {
            return false;
        }
        RequestParse myThing = (RequestParse) compareWith;
        boolean cw = true;
        if (myThing.getPosition() == null) {
            cw = getPosition() == null;
        } else if (getPosition() == null) {
            cw = false;
        } else {
            cw = myThing.getPosition().equals(getPosition());
        }
        if (!cw) {
            return false;
        }
        if (myThing.getMove() == null) {
            cw = getMove() == null;
        } else if (getMove() == null) {
            cw = false;
        } else {
            cw = myThing.getMove().equals(getMove());
        }
        if (!cw) {
            return false;
        }
        return (myThing.getInteger() == getInteger());
    }
    public int hashCode() {
        return 3;
    }
    public RequestParse clone() {
        RequestParse cg = new RequestParse("", "");
        cg.setMove(getMove());
        cg.setPosition(getPosition());
        cg.setInteger(getInteger());
        return cg;
    }
    public void setMove(ChessMove cw) {
        if (cw == null) {
            cM = null;
        } else {
            cM = cw.clone();
        }
    }
    public void setPosition(ChessPosition cw) {
        if (cw == null) {
            cP = null;
        } else {
            cP = cw.clone();
        }
    }
    public void setInteger(int cw) {
        i = cw;
    }
    public ChessMove getMove() {
        if (cM == null) {
            return null;
        }
        return cM.clone();
    }
    public ChessPosition getPosition() {
        if (cP == null) {
            return null;
        }
        return cP.clone();
    }
    public int getInteger() {
        return i;
    }
}

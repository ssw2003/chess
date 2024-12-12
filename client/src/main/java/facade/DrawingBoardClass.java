package facade;

import chess.*;
import ui.EscapeSequences;

import java.util.Collection;

public class DrawingBoardClass {
    private boolean i;
    public DrawingBoardClass() {
        i = false;
    }
    private void voidThings() {
        boolean v = false;
    }
    public enum Styling {
        LITTLE,
        MEDIUM,
        BIG
    }
    public enum Person {
        WHITE,
        BLACK,
        OBSERVER
    }
    public void drawBoard(ChessGame yourGame, Person playerOrObserver, ChessPosition theChessPosition, Styling styling) {
        String boardSize = "big";
        boolean isWhite = !(playerOrObserver == Person.BLACK);
        boolean isObserving = (playerOrObserver == Person.OBSERVER);
        if (styling == Styling.LITTLE) {
            boardSize = "small";
        } else if (styling == Styling.MEDIUM) {
            boardSize = "medium";
        }
        ChessBoard newGame = new ChessBoard();
        newGame.resetBoard();
        ChessPosition chessPosition = theChessPosition;
        String checkMessaging = "null";
        ChessGame game = new ChessGame();
        if (yourGame != null) {
            game = yourGame.clone();
        }
        if (game.isInCheck(ChessGame.TeamColor.WHITE)) {
            checkMessaging = "our team in check";
            if (game.isInCheckmate(ChessGame.TeamColor.WHITE)) { checkMessaging = "our team in checkmate"; }
        }
        if (game.isInCheck(ChessGame.TeamColor.BLACK)) {
            checkMessaging = "their team in check";
            if (game.isInCheckmate(ChessGame.TeamColor.BLACK)) { checkMessaging = "their team in checkmate"; }
        }
        newGame = game.clone().getBoard();
        if (!game.abortGame("Question").equals("Set")) {
            checkMessaging = "White Resigns—Black Wins";
            if (game.getBlackResigns().getTeamColor() == ChessGame.TeamColor.BLACK) { checkMessaging = "Black Resigns—White Wins"; }
        }
        if (!isWhite) {
            if (checkMessaging.equals("our team in check")) {
                checkMessaging = "their team in check";
            } else if (checkMessaging.equals("their team in check")) {
                checkMessaging = "our team in check";
            }
            if (checkMessaging.equals("our team in checkmate")) {
                checkMessaging = "their team in checkmate";
            } else if (checkMessaging.equals("their team in checkmate")) {
                checkMessaging = "our team in checkmate";
            }
            newGame = reverseBoard(newGame);
            if (chessPosition != null) {
                chessPosition = new ChessPosition(9 - chessPosition.getRow(), 9 - chessPosition.getColumn());
            }
        }
        String messageColoring = "null";
        if (checkMessaging.equals("our team in check") || checkMessaging.equals("our team in checkmate")) { messageColoring = "red"; }
        if (checkMessaging.equals("their team in check") || checkMessaging.equals("their team in checkmate")) { messageColoring = "green"; }
        if (isObserving && !messageColoring.equals("null")) { messageColoring = "blue"; }
        int bigNumber = 15;
        if (isObserving) {
            if (checkMessaging.equals("our team in check")) {
                checkMessaging = "White's in Check";
                bigNumber = 7;
            }
            if (checkMessaging.equals("our team in checkmate")) {
                checkMessaging = "Checkmate—Black Wins";
                bigNumber = 5;
            }
            if (checkMessaging.equals("their team in check")) {
                checkMessaging = "Black's in Check";
                bigNumber = 7;
            }
            if (checkMessaging.equals("their team in checkmate")) {
                checkMessaging = "Checkmate—White Wins";
                bigNumber = 5;
            }
        }
        if (checkMessaging.equals("our team in check") || checkMessaging.equals("their team in check")) {
            checkMessaging = "Check!";
            bigNumber = 12;
        }
        if (checkMessaging.equals("our team in checkmate") || checkMessaging.equals("their team in checkmate")) {
            checkMessaging = "Checkmate—White Wins";
            bigNumber = 5;
            if (messageColoring.equals("red") == isWhite) { checkMessaging = "Checkmate—Black Wins"; }
        }
        if (checkMessaging.equals("null")) { checkMessaging = ""; }
        if (checkMessaging.equals("White Resigns—Black Wins") || checkMessaging.equals("Black Resigns—White Wins")) {
            bigNumber = 3;
            messageColoring = "green";
            if (checkMessaging.equals("White Resigns—Black Wins") == isWhite) { messageColoring = "red"; }
            if (isObserving) { messageColoring = "blue"; }
        }
        if (game.isInStalemate(game.getTeamTurn())) {
            checkMessaging = "Stalemate!";
            bigNumber = 10;
            messageColoring = "blue";
        }
        dBoard(isWhite, chessPosition, newGame, game, boardSize, theChessPosition, bigNumber, messageColoring, checkMessaging);
    }
    private void dBoard(boolean isWhite, ChessPosition ps, ChessBoard ng, ChessGame z, String sz, ChessPosition tcp, int bN, String mc, String cm) {
        String[] cBE = new String[90];
        String[] labels = new String[8];
        labels = new String[] { "h", "g", "f", "e", "d", "c", "b", "a" };
        if (isWhite) { labels = new String[] { "a", "b", "c", "d", "e", "f", "g", "h" }; }
        int he = 0;
        while (he < 10) {
            cBE[he] = "yellow";
            he = he + 1;
        }
        while (he <= 89) {
            cBE[he] = "gray";
            if ((he - 1) % 10 > 7) { cBE[he] = "yellow"; } else if (((he % 10) + (he / 10)) % 2 == 1) { cBE[he] = "green"; }
            he = he + 1;
        }
        if (ps == null) {} else if (ng.getPiece(ps) == null) {
        } else {
            Collection<ChessMove> vM = z.validMoves(tcp);
            for (ChessMove move: vM) {
                ChessPosition mv = move.getEndPosition();
                if (!isWhite) { mv = new ChessPosition(9 - mv.getRow(), 9 - mv.getColumn()); }
                he = 90 + mv.getColumn() - mv.getRow() * 10;
                boolean bf = cBE[he].equals("green");
                cBE[he] = "white";
                if (bf) { cBE[he] = "magenta"; }
            }
        }
        String[] enhancedLabels = new String[90];
        String[] txc = new String[90];
        he = 0;
        while (he < 90) {
            txc[he] = "magenta";
            he = he + 1;
        }
        he = 11;
        while (he < 90) {
            txc[he] = "blue";
            if (ng.getPiece(new ChessPosition(9 - he / 10, he % 10)) == null) {
            } else if (ng.getPiece(new ChessPosition(9 - he / 10, he % 10)).getTeamColor() == ChessGame.TeamColor.BLACK) { txc[he] = "black"; }
            he = he + 1;
            if ((he - 1) % 10 > 7) { he = he + 2; }
        }
        he = 0;
        enhancedLabels[0] = " ";
        enhancedLabels[9] = " ";
        while (he < 8) {
            enhancedLabels[he + 1] = labels[he];
            he = he + 1;
            enhancedLabels[10 * he] = "" + he;
            if (isWhite) { enhancedLabels[10 * he] = "" + (9 - he); }
            enhancedLabels[10 * he + 9] = enhancedLabels[10 * he];
        }
        he = 11;
        while (he < 90) {
            ChessPiece gP = ng.getPiece(new ChessPosition(9 - he / 10, he % 10));
            enhancedLabels[he] = "B";
            if (gP == null) {
                enhancedLabels[he] = " ";
            } else if (gP.getPieceType() == ChessPiece.PieceType.PAWN) {
                enhancedLabels[he] = "P";
            } else if (gP.getPieceType() == ChessPiece.PieceType.KING) {
                enhancedLabels[he] = "K";
            } else if (gP.getPieceType() == ChessPiece.PieceType.QUEEN) {
                enhancedLabels[he] = "Q";
            } else if (gP.getPieceType() == ChessPiece.PieceType.ROOK) {
                enhancedLabels[he] = "R";
            } else if (gP.getPieceType() == ChessPiece.PieceType.KNIGHT) {
                enhancedLabels[he] = "N";
            }
            he = he + 1;
            if ((he - 1) % 10 > 7) {
                he = he + 2;
            }
        }
        he = 0;
        while (he < 90) {
            if (cBE[he].equals("yellow")) {
                cBE[he] = EscapeSequences.SET_BG_COLOR_YELLOW;
            } else if (cBE[he].equals("green")) {
                cBE[he] = EscapeSequences.SET_BG_COLOR_DARK_GREEN;
            } else if (cBE[he].equals("gray")) {
                cBE[he] = EscapeSequences.SET_BG_COLOR_LIGHT_GREY;
            } else if (cBE[he].equals("magenta")) {
                cBE[he] = EscapeSequences.SET_BG_COLOR_MAGENTA;
            } else { cBE[he] = EscapeSequences.SET_BG_COLOR_WHITE; }
            if (txc[he].equals("magenta")) {
                txc[he] = EscapeSequences.SET_TEXT_COLOR_MAGENTA;
            } else if (txc[he].equals("blue")) {
                txc[he] = EscapeSequences.SET_TEXT_COLOR_BLUE;
            } else { txc[he] = EscapeSequences.SET_TEXT_COLOR_BLACK; }
            he = he + 1;
        }
        he = 13;
        if (sz.equals("big")) {
            he = 39;
        } else if (sz.equals("medium")) { he = 37; }
        drawIt(he, txc, cBE, enhancedLabels, bN, labels, cm, mc);
    }
    private void drawIt(int heights, String[] textingColors, String[] cbe, String[] el, int bn, String[] lb, String cm, String mc) {
        int j = 0;
        int k = 0;
        String ke = "";
        int i = 1;
        while (i < heights % 10) {
            ke = ke + " ";
            i = i + 2;
        }
        i = 0;
        while (i < 9) {
            j = 20;
            while (j < heights) {
                k = 10 * i;
                while (k < 10 * i + 10) {
                    System.out.print(textingColors[k]);
                    System.out.print(cbe[k]);
                    System.out.print(ke + ke + " ");
                    System.out.print(EscapeSequences.RESET_TEXT_COLOR);
                    System.out.print(EscapeSequences.RESET_BG_COLOR);
                    k = k + 1;
                }
                j = j + 20;
                System.out.print("\n");
            }
            k = 10 * i;
            while (k < 10 * i + 10) {
                System.out.print(cbe[k]);
                System.out.print(textingColors[k]);
                System.out.print(ke + el[k] + ke);
                System.out.print(EscapeSequences.RESET_BG_COLOR);
                System.out.print(EscapeSequences.RESET_TEXT_COLOR);
                k = k + 1;
            }
            System.out.print("\n");
            if (heights > 20) {
                k = 0;
                while (k < 10) {
                    System.out.print(textingColors[10 * i + k]);
                    System.out.print(cbe[10 * i + k]);
                    System.out.print(ke + ke + " ");
                    System.out.print(EscapeSequences.RESET_BG_COLOR);
                    System.out.print(EscapeSequences.RESET_TEXT_COLOR);
                    k = k + 1;
                }
            }
            i = i + 1;
            System.out.print("\n");
        }
        finalDraw(bn, heights, lb, el, mc, cm);
    }
    private void finalDraw(int bigNumber, int heights, String[] labels, String[] enhancedLabels, String messageColoring, String checkMessaging) {
        bigNumber = ((heights - 3) % 10) * 5 + bigNumber;
        String ke;
        labels = new String[270];
        int i = 0;
        while (i < 270) {
            labels[i] = " ";
            i = i + 1;
        }
        i = 0;
        while (i < 10) {
            labels[(heights % 10) * i + ((heights % 10) / 2) + (heights / 20) * 90] = enhancedLabels[i];
            i = i + 1;
        }
        int j = (heights / 10) - 1;
        int k = (heights % 10) * 10;
        i = 0;
        while (i < j) {
            ke = "";
            j = k + 90 * i;
            while (j > 90 * i) {
                j = j - 1;
                ke = labels[j] + ke;
            }
            i = i + 1;
            j = (heights / 10) - 1;
            System.out.print(EscapeSequences.SET_BG_COLOR_YELLOW);
            System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
            System.out.print(ke);
            System.out.print(EscapeSequences.RESET_BG_COLOR);
            System.out.print(EscapeSequences.RESET_TEXT_COLOR);
            System.out.print("\n");
        }
        i = j * 90;
        k = k + i - bigNumber;
        j = j * 90 + bigNumber;
        ke = "";
        while (i < j) {
            ke = ke + labels[i];
            i = i + 1;
        }
        j = k + bigNumber;
        System.out.print(EscapeSequences.SET_BG_COLOR_YELLOW);
        System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
        System.out.print(ke);
        if (i == k) {
            voidThings();
        } else {
            System.out.print(EscapeSequences.RESET_BG_COLOR);
            System.out.print(EscapeSequences.RESET_TEXT_COLOR);
            if (messageColoring.equals("red")) {
                System.out.print(EscapeSequences.SET_BG_COLOR_RED);
            } else if (messageColoring.equals("green")) {
                System.out.print(EscapeSequences.SET_BG_COLOR_GREEN);
            } else if (messageColoring.equals("blue")) {
                System.out.print(EscapeSequences.SET_BG_COLOR_BLUE);
            }
            if (!messageColoring.equals("null")) { System.out.print(EscapeSequences.SET_TEXT_COLOR_WHITE); }
            System.out.print(checkMessaging);
            System.out.print(EscapeSequences.RESET_BG_COLOR);
            System.out.print(EscapeSequences.RESET_TEXT_COLOR);
            System.out.print(EscapeSequences.SET_BG_COLOR_YELLOW);
            System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
        }
        ke = "";
        while (k < j) {
            ke = ke + labels[k];
            k = k + 1;
        }
        System.out.print(ke);
        System.out.print(EscapeSequences.RESET_BG_COLOR);
        System.out.print(EscapeSequences.RESET_TEXT_COLOR);
        System.out.print("\n");
    }
    private ChessBoard reverseBoard(ChessBoard cB) {
        ChessBoard nB = new ChessBoard();
        nB.resetBoard();
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                ChessPiece chessPiece = cB.getPiece(new ChessPosition(i, j));
                nB.addPiece(new ChessPosition(9 - i, 9 - j), chessPiece);
            }
        }
        return nB.clone();
    }

    @Override
    public boolean equals(Object compareWith) {
        if (compareWith == null) {
            return false;
        }
        if (compareWith.getClass() != getClass()) {
            return false;
        }
        return true;
    }
    public int hashCode() {
        return 3;
    }
    public DrawingBoardClass clone() {
        DrawingBoardClass cg = new DrawingBoardClass();
        return cg;
    }
}

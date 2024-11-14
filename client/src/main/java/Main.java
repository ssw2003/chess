import chess.*;
import facade.ServerFacade;

public class Main {
    public static void main(String[] args) {
        //URI uri;
        //try {
        //    uri = new URI("http://localhost:8080");
        //} catch (URISyntaxException e) {}
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);
        ServerFacade sF = new ServerFacade("http://localhost:8080");
        //String inputThing = "";
        //boolean statusValueAboveLoggedIn = false;
        //boolean inAndNotPlaying = true;
        //ff,ft,tt,tf
        //UserData userData = null;
        //GameData gameData = null;
        //String lastCommand = "Help";
        //while (statusValueAboveLoggedIn || inAndNotPlaying) {
        //    if (!statusValueAboveLoggedIn) {
        //        if (lastCommand.equals("Help")) {
        //            Help();
        //        }
        //    }
        //}
    }
    public static void Help() {
        String[] s = new String[30];
        s[0] = "Help";
        s[1] = "";
        s[2] = "Logout";
        s[3] = "";
        s[4] = "Create Game";
        s[5] = "";
        s[6] = "List Games";
        s[7] = "";
        s[8] = "";
        s[9] = "";
        s[10] = "";
        s[11] = "";
        s[12] = "";
        s[13] = "";
        s[14] = "";
        s[15] = "";
        s[16] = "";
        s[17] = "";
        s[18] = "";
        s[19] = "";
        s[20] = "";
        s[21] = "";
        s[22] = "";
        s[23] = "";
        s[24] = "";
        s[25] = "";
        s[26] = "";
        s[27] = "";
        s[28] = "";
        s[29] = "";
        for (int i = 0; i < 15; i++) {
            if (!s[i * 2].equals("") || !s[2 * i + 1].equals("")) {
                System.out.println(s[2 * i] + s[2 * i + 1]);
            }
        }
    }
}
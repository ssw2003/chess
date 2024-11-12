package server;

import java.util.ArrayList;
import java.util.Collection;

public class GameDataSet {
    private int theSize;
    Collection<GameData> gameData = new ArrayList<>();
    public GameDataSet() {
        theSize = 0;
    }
    public int mySize() {
        return theSize;
    }
    public void addGame(GameData gd) {
        gameData.add(gd);
        theSize = theSize + 1;
    }
    public GameData getGame(int i) {
        if (i > theSize) {
            return null;
        }
        if (i < 1) {
            return null;
        }
        for (GameData gd: gameData) {
            if (gd.gameID() == i) {
                return gd;
            }
        }
        return null;
    }
    public void changeGame(int i, GameData gd) {
        if (i > 0 && i < theSize + 1) {
            Collection<GameData> newGameData = new ArrayList<>();
            int j = 1;
            while (j < i) {
                newGameData.add(this.getGame(j));
                j = j + 1;
            }
            j = i + 1;
            while (j < theSize + 1) {
                newGameData.add(this.getGame(j));
                j = j + 1;
            }
            newGameData.add(gd);
            gameData = new ArrayList<>();
            for (GameData game: newGameData) {
                gameData.add(game);
            }
        }
    }
}

package server;

import java.util.ArrayList;
import java.util.Collection;

public class GameDataSet {
    private int the_size;
    Collection<GameData> game_data = new ArrayList<>();
    public GameDataSet() {
        the_size = 0;
    }
    public int mySize() {
        return the_size;
    }
    public void addGame(GameData gd) {
        game_data.add(gd);
        the_size = the_size + 1;
    }
    public GameData getGame(int i) {
        if (i > the_size) {
            return null;
        }
        if (i < 1) {
            return null;
        }
        for (GameData gd: game_data) {
            if (gd.gameID() == i) {
                return gd;
            }
        }
        return null;
    }
    public void changeGame(int i, GameData gd) {
        if (i > 0 && i < the_size + 1) {
            Collection<GameData> new_game_data = new ArrayList<>();
            int j = 1;
            while (j < i) {
                new_game_data.add(this.getGame(j));
                j = j + 1;
            }
            j = i + 1;
            while (j < the_size + 1) {
                new_game_data.add(this.getGame(j));
                j = j + 1;
            }
            new_game_data.add(gd);
            game_data = new ArrayList<>();
            for (GameData game: new_game_data) {
                game_data.add(game);
            }
        }
    }
}

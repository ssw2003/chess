package facade;

import model.GameData;
import model.GameMetadata;

import java.util.Collection;

public record ListGamesResult(Collection<GameData> games) {
}

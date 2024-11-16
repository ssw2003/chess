package facade;

import model.GameMetadata;

import java.util.Collection;

public record ListGamesResult(Collection<GameMetadata> games) {
}

package ru.spbau.mit.world;

import ru.spbau.mit.mapper.Map;

import java.util.ArrayList;
import java.util.List;
import static ru.spbau.mit.world.GameObject.Coordinates;

/**
 * World with no creatures or chests. Only a player, wall and floor.
 */
public abstract class BaseWorld {
    private final Map map;
    private final List<Character> gameCharacterList;

    /**
     * Map should be filled with all obstacle and floor tiles.
     * @param map
     */
    BaseWorld(Map map) {
        this.map = map;
        this.gameCharacterList = new ArrayList<>();
    }

    public abstract void populateCharacters();

    public List<Character> getCharacters() {
        return gameCharacterList;
    }

    protected Map getMap() {
        return map;
    }

    protected Coordinates mapCenter() {
        return new Coordinates(getMap().width() / 2, getMap().height() / 2);
    }
}

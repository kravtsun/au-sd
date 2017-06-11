package ru.spbau.mit.world;

import ru.spbau.mit.mapper.Map;
import ru.spbau.mit.visualizer.Tile;

import java.util.ArrayList;
import java.util.List;
import static ru.spbau.mit.world.GameObject.Coordinates;

/**
 * Stores characters and map, providing default implementation of WorldProphet.
 * Declares functionality needed from World's creator object.
 */
public abstract class BaseWorld implements WorldProphet {
    private final Map map;
    private final List<Character> gameCharacterList;

    /**
     * Map should be filled with all obstacle and floor tiles.
     * @param map Map
     */
    BaseWorld(Map map) {
        this.map = map;
        this.gameCharacterList = new ArrayList<>();
    }

    public abstract void populateCharacters();

    @Override
    public GameObject getGameObjectAtPlace(Coordinates p) {
        if (getMap().getTile(p.x(), p.y()).equals(Tile.WALL)) {
            return new Obstacle(p);
        }
        return getCharacters().stream().filter((c) -> c.isAlive() && c.occupiesPosition(p)).findAny().orElse(null);
    }

    @Override
    public boolean inside(Coordinates p) {
        return p.x() >= 0 && p.x() < getMap().width() && p.y() >= 0 && p.y() < getMap().height();
    }

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

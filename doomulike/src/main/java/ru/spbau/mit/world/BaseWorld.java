package ru.spbau.mit.world;

import ru.spbau.mit.mapper.Map;
import ru.spbau.mit.visualizer.Tile;

import java.util.ArrayList;
import java.util.List;
import static ru.spbau.mit.world.GameObject.Coordinates;


public abstract class BaseWorld implements WorldProphet {
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

    @Override
    public GameObject getGameObjectAtPlace(Coordinates p) {
        if (getMap().getTile(p.x(), p.y()).equals(Tile.WALL)) {
            return new Obstacle(p);
        }
        return getCharacters().stream().filter((c) -> c.isAlive() && c.occupiesPosition(p)).findAny().orElse(null);
//        throw new IllegalStateException("Void at the Universe found.");
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

package ru.spbau.mit.world;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.jetbrains.annotations.Nullable;
import ru.spbau.mit.mapper.Map;
import ru.spbau.mit.visualizer.Tile;
import ru.spbau.mit.world.logic.Action;
import java.awt.event.KeyEvent;
import java.util.*;

import static ru.spbau.mit.world.GameObject.Coordinates;

/**
 * All objects and map container.
 * - player placement, his (or her) starting characteristics generated automatically,
 * Creatures and chests and others - likewise.
 */
public class World extends RandomWorld implements WorldProphet, Cartographer {
    private static final Logger LOGGER = LogManager.getLogger("World");

    public World(Map map) {
        super(map);
        if (getCharacters().isEmpty() || !Player.class.isInstance(getCharacters().get(0))) {
            throw new IllegalStateException();
        }
    }

    @Override
    public Map renderMap() {
        Map newMap = getMap().clone();
        List<Character> characters = getCharacters();
        for (int i = 1; i < characters.size(); ++i) {
            Character go = characters.get(i);
            Coordinates goCoordinates = go.getCoordinates();
            int x = goCoordinates.x();
            int y = goCoordinates.y();
            if (!go.isAlive()) {
                newMap.setTile(x, y, Tile.CORPSE);
            } else if (Monster.class.isInstance(go)) {
                newMap.setTile(x, y, Tile.MONSTER);
            } else if (Chest.class.isInstance(go)) {
                newMap.setTile(x, y, Tile.CHEST);
            } else {
                LOGGER.error("Unknown type of GameObject: " + go.getClass().toGenericString());
            }
        }
        if (!Objects.isNull(getPlayer())) {
            Coordinates p = getPlayer().getCoordinates();
            newMap.setTile(p.x(), p.y(), Tile.PLAYER);
        }
        return newMap;
    }

    /**
     * Handle given user input.
     * @param key {@link KeyEvent}
     */
    public void respondInput(KeyEvent key) {
        if (getCharacters().isEmpty() || Objects.isNull(getPlayer())) {
            LOGGER.error("No player exists. Ignoring commands.");
            return;
        }
        List<Action> actions = new ArrayList<>();

        // Gather all planning actions.
        for (Character go : getCharacters()) {
            go.step(this, key, actions);
        }

        // can cancel any action here.

        // Execute all actions in order (player's first).
        for (Action a : actions) {
            a.run();
        }
    }

    /**
     * @param key user input.
     * @return if world will do any reaction on provided user input.
     */
    public boolean canAnswer(KeyEvent key) {
        final Player player = getPlayer();
        return !Objects.isNull(player) && player.canAnswer(key);
    }

    @Override
    @Nullable
    public Player getPlayer() {
        if (getCharacters().isEmpty()) {
            return null;
        }
        return (Player) getCharacters().get(0);
    }
}

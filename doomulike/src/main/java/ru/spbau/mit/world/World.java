package ru.spbau.mit.world;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static ru.spbau.mit.world.MoveAction.MoveType;
import ru.spbau.mit.mapper.Map;
import ru.spbau.mit.visualizer.Tile;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static ru.spbau.mit.world.GameObject.Coordinates;

public class World implements WorldProphet, WorldRuler {
    private static final Logger logger = LogManager.getLogger("World");
    private final Map map;
    private List<Character> gameCharacterList;

    public World(Map map) {
        this.map = map;
        this.gameCharacterList = new ArrayList<>();
        Coordinates p = findEmptyPlace(map.width() / 2, map.height() / 2);
        Player player = null;
        if (Objects.isNull(p)) {
            logger.error("Map is full, unable to place player.");
        } else {
            player = new Player(p, "Player", new Characteristics(100, 10, 0));
        }
        gameCharacterList.add(player);
    }

    @Override
    public Map getMap() {
        Map newMap = map.clone();
        for (Character go : gameCharacterList) {
            Coordinates goCoordinates = go.getCoordinates();
            int x = goCoordinates.x();
            int y = goCoordinates.y();
            if (Character.class.isInstance(go)) {
                newMap.setTile(x, y, Tile.PLAYER);
            } else if (NonPlayableCharacter.class.isInstance(go)) {
                newMap.setTile(x, y, Tile.MONSTER);
            } else if (Chest.class.isInstance(go)) {
                newMap.setTile(x, y, Tile.CHEST);
            } else {
                logger.error("Unknown type of GameObject: " + go.getClass().toGenericString());
            }
        }
        return newMap;
    }

    private Coordinates findEmptyPlace(int xx, int yy) {
        final Coordinates p = new Coordinates(xx, yy);
        if (map.getTile(xx, yy).isFree()) {
            return p;
        }

        List<Coordinates> freePlaces = new ArrayList<>();
        for (int y = 0; y < map.height(); ++y) {
            for (int x = 0; x < map.width(); ++x) {
                freePlaces.add(new Coordinates(x, y));
            }
        }
        return freePlaces.stream().min((o1, o2) -> {
            int o1dist = o1.dist2(p);
            int o2dist = o1.dist2(p);
            if (o1dist < o2dist) {
                return -1;
            } else if (o1dist > o2dist) {
                return 1;
            } else {
                return 0;
            }
        }).orElse(null);
    }

    @Override
    public void respondInput(KeyEvent key) {
        if (gameCharacterList.isEmpty() || Objects.isNull(gameCharacterList.get(0))) {
            logger.error("No player exists. Ignoring commands.");
            return;
        }
        Player player = (Player)gameCharacterList.get(0);

        Action userAction = null;
        switch (key.getKeyCode()) {
            case KeyEvent.VK_UP:
                userAction = new MoveAction(player, MoveType.UP); break;
            case KeyEvent.VK_DOWN:
                userAction = new MoveAction(player, MoveType.DOWN); break;
            case KeyEvent.VK_LEFT:
                userAction = new MoveAction(player, MoveType.LEFT); break;
            case KeyEvent.VK_RIGHT:
                userAction = new MoveAction(player, MoveType.RIGHT); break;
            default:
                logger.error("Unknown command: " + key.toString());
        }
        player.setAction(userAction);
        List<Action> actions = new ArrayList<>();
        for (Character go : gameCharacterList) {
            go.step(actions);
        }

        for (Action a : actions) {
            a.run(this);
        }
    }

    @Override
    public GameObject objectAtPlace(Coordinates p) {
        for (Character character: gameCharacterList) {
            if (character.getCoordinates().equals(p)) {
                return character;
            }
        }
        if (map.getTile(p.x(), p.y()).isFree()) {
            return null;
        }
        return new Obstacle(p);
    }
}

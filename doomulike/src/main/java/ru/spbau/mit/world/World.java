package ru.spbau.mit.world;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static ru.spbau.mit.world.logic.MoveAction.MoveType;

import org.jetbrains.annotations.Nullable;
import ru.spbau.mit.mapper.Map;
import ru.spbau.mit.visualizer.PlayScreen;
import ru.spbau.mit.visualizer.Tile;
import ru.spbau.mit.world.logic.Action;
import ru.spbau.mit.world.logic.MoveAction;

import java.awt.dnd.InvalidDnDOperationException;
import java.awt.event.KeyEvent;
import java.util.*;

import static ru.spbau.mit.world.GameObject.Coordinates;
import static ru.spbau.mit.world.Character.Inventory;

/**
 * All objects and map container.
 * TODO put StochasticWorld in a different abstraction level - player placement, his (or her) starting characteristics generated automatically,
 * Creatures and chests and others - likewise.
 */
public class World extends BaseWorld implements WorldProphet, Cartographer {
    private static final Random randomizer = new Random();
    private static final Logger logger = LogManager.getLogger("World");

    public World(Map map) {
        super(map);
        placePlayer("Player", new Characteristics(100, 10, 0), mapCenter());
        populateCharacters();
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
                logger.error("Unknown type of GameObject: " + go.getClass().toGenericString());
            }
        }
        if (!Objects.isNull(getPlayer())) {
            Coordinates p= getPlayer().getCoordinates();
            newMap.setTile(p.x(), p.y(), Tile.PLAYER);
        }
        return newMap;
    }

    @Override
    public void respondInput(KeyEvent key) {
        if (getCharacters().isEmpty() || Objects.isNull(getPlayer())) {
            logger.error("No player exists. Ignoring commands.");
            return;
        }
        Player player = getPlayer();

        Action userAction;
        switch (key.getKeyCode()) {
            case KeyEvent.VK_UP:
                userAction = new MoveAction(player, MoveType.UP);
                break;
            case KeyEvent.VK_DOWN:
                userAction = new MoveAction(player, MoveType.DOWN);
                break;
            case KeyEvent.VK_LEFT:
                userAction = new MoveAction(player, MoveType.LEFT);
                break;
            case KeyEvent.VK_RIGHT:
                userAction = new MoveAction(player, MoveType.RIGHT);
                break;
            default:
                String errorMessage = "Unknown command: " + key.toString();
                logger.error(errorMessage);
                throw new InvalidDnDOperationException(errorMessage);
        }
        player.setAction(userAction);
        List<Action> actions = new ArrayList<>();
        for (Character go : getCharacters()) {
            go.step(actions);
        }

        for (Action a : actions) {
            a.run(this);
        }
    }

    /**
     * @return list of all commands which can be handled by the world.
     * TODO synchronize this function with respondInput main switch
     * i.e. via adding subscriptions or just Map<KeyEvent, Supplier<Action>>.
     */
    @Override
    public int[] availableCommands() {
        return new int[] {
                KeyEvent.VK_UP,
                KeyEvent.VK_DOWN,
                KeyEvent.VK_LEFT,
                KeyEvent.VK_RIGHT
        };
    }

    @Override
    public List<String> statusInfo() {
        List<String> result = new ArrayList<>();
        result.add("Player: " + getPlayer().getName());
        result.add("Inventory: ");
        result.addAll(getPlayer().getInventory().strs());
        result.add("Characteristics: ");
        result.addAll(getPlayer().getCharacteristics().strs());
        return result;
    }

    @Override
    public void populateCharacters() {
        generateChests();
        generateNonPlayableCharacters();
    }

    private void generateNonPlayableCharacters() {
        final int npcCount = 10;
        String baseName = "goblin";
        for (int i = 0; i < npcCount; ++i) {
            Coordinates coordinates = Coordinates.random(randomizer, getMap().width(), getMap().height());
            Inventory inventory =  Inventory.random(randomizer);
            Characteristics characteristics = Characteristics.random(randomizer);
            Character character = new Monster(coordinates, baseName + "{" + i + "}", characteristics, inventory);
            if (!placeCharacter(character)) {
                logger.warn("It was only possible to place " + i + " monsters");
                break;
            }
        }
    }

    private void generateChests() {
        final int chestsCount = 30;
        String baseName = "chest";
        for (int i = 0; i < chestsCount; ++i) {
            final int width = getMap().width();
            final int height = getMap().height();
            Coordinates coordinates = Coordinates.random(randomizer, width, height);
            Inventory inventory =  Inventory.random(randomizer);
            Character character = new Chest(coordinates, baseName + "{" + i + "}", inventory);
            if (!placeCharacter(character)) {
                logger.warn("It was only possible to place " + i + " chests");
                break;
            }
        }
    }

    @Override
    public GameObject getGameObjectAtPlace(Coordinates p) {
        if (getMap().getTile(p.x(), p.y()).equals(Tile.WALL)) {
            return new Obstacle(p);
        }
        return getCharacters().stream().filter((c) -> c.isAlive() && c.occupiesPosition(p)).findAny().orElse(null);
//        throw new IllegalStateException("Void at the Universe found.");
    }

    @Nullable
    private Player getPlayer() {
        if (getCharacters().isEmpty()) {
            return null;
        }
        return (Player) getCharacters().get(0);
    }

    private boolean placeCharacter(Character character) {
        Coordinates coordinates = findEmptyPlace(character.getCoordinates());
        if (Objects.isNull(coordinates)
                || !getMap().getTile(coordinates.x(), coordinates.y()).isFree()
                || getCharacters().contains(character))
        {
            return false;
        }
        getCharacters().add(character);
        return true;
    }

    /**
     * We have
     * @param name name of player
     * @param characteristics Character's characteristics.
     * @param preferredLocation attempted position, if it's busy with wall or something, find most close free position.
     */
    private void placePlayer(String name, Characteristics characteristics, Coordinates preferredLocation) {
        Player player = new Player(preferredLocation, name, characteristics);
        Coordinates p = findEmptyPlace(preferredLocation);
        if (!placeCharacter(player)) {
            logger.error("Map is full, unable to place a player.");
            throw new IllegalStateException();
        }
    }

    private boolean isEmptyPlace(Coordinates coordinates) {
        return getMap().getTile(coordinates.x(), coordinates.y()).isFree()
                && Objects.isNull(getGameObjectAtPlace(coordinates));
    }

    private Coordinates findEmptyPlace(Coordinates preferredLocation) {
        Map map = getMap();
        if (isEmptyPlace(preferredLocation)) {
            return preferredLocation;
        }
        List<Coordinates> freePlaces = new ArrayList<>();
        for (int y = 0; y < map.height(); ++y) {
            for (int x = 0; x < map.width(); ++x) {
                freePlaces.add(new Coordinates(x, y));
            }
        }
        return freePlaces.stream().max((o1, o2) -> {
            int o1dist = o1.dist2(preferredLocation);
            int o2dist = o1.dist2(preferredLocation);
            if (o1dist < o2dist) {
                return -1;
            } else if (o1dist > o2dist) {
                return 1;
            } else {
                return 0;
            }
        }).orElse(null);
    }
}

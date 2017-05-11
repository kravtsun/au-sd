package ru.spbau.mit.world;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static ru.spbau.mit.world.logic.MoveAction.MoveType;

import org.jetbrains.annotations.Nullable;
import ru.spbau.mit.mapper.Map;
import ru.spbau.mit.visualizer.Tile;
import ru.spbau.mit.visualizer.UserCommand;
import ru.spbau.mit.world.logic.Action;
import ru.spbau.mit.world.logic.MoveAction;

import java.awt.dnd.InvalidDnDOperationException;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.function.Function;

import static ru.spbau.mit.world.GameObject.Coordinates;
import static ru.spbau.mit.world.Character.Inventory;

/**
 * All objects and map container.
 * TODO put StochasticWorld in a different abstraction level \
 * - player placement, his (or her) starting characteristics generated automatically,
 * Creatures and chests and others - likewise.
 */
public class World extends BaseWorld implements WorldProphet, Cartographer {
    private static final Random RANDOMIZER = new Random();
    private static final Logger LOGGER = LogManager.getLogger("World");

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
                LOGGER.error("Unknown type of GameObject: " + go.getClass().toGenericString());
            }
        }
        if (!Objects.isNull(getPlayer())) {
            Coordinates p = getPlayer().getCoordinates();
            newMap.setTile(p.x(), p.y(), Tile.PLAYER);
        }
        return newMap;
    }

    @Override
    public void respondInput(KeyEvent key) {
        if (getCharacters().isEmpty() || Objects.isNull(getPlayer())) {
            LOGGER.error("No player exists. Ignoring commands.");
            return;
        }
        Player player = getPlayer();

        Action userAction;
        switch (key.getKeyCode()) {
            case KeyEvent.VK_UP:
                userAction = new MoveAction(this, player, MoveType.UP);
                break;
            case KeyEvent.VK_DOWN:
                userAction = new MoveAction(this, player, MoveType.DOWN);
                break;
            case KeyEvent.VK_LEFT:
                userAction = new MoveAction(this, player, MoveType.LEFT);
                break;
            case KeyEvent.VK_RIGHT:
                userAction = new MoveAction(this, player, MoveType.RIGHT);
                break;
            default:
                String errorMessage = "Unknown command: " + key.toString();
                LOGGER.error(errorMessage);
                throw new InvalidDnDOperationException(errorMessage);
        }
        player.setAction(userAction);
        List<Action> actions = new ArrayList<>();

        // Gather all planning actions.
        for (Character go : getCharacters()) {
            go.step(actions);
        }

        // Execute all actions in order (player's first).
        for (Action a : actions) {
            a.run();
        }
    }

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
    public List<UserCommand> availableUserCommands() {
        Function<String, UserCommand> moveCommandGenerator =
                (dir) -> {
                    String msg = "Go " + dir + " or attack creature or loot chest to the " + dir + ".";
                    return new UserCommand(dir, msg);
                };
        return Arrays.asList(
                moveCommandGenerator.apply("up"),
                moveCommandGenerator.apply("down"),
                moveCommandGenerator.apply("left"),
                moveCommandGenerator.apply("right")
        );
    }

    @Override
    public List<String> statusInfo() {
        List<String> result = new ArrayList<>();
        Player player = getPlayer();
        String playerTitle = Objects.isNull(player) ? "empty" : player.getName();
        result.add("Player: " + playerTitle);
        if (!Objects.isNull(player)) {
            result.add("Inventory: ");
            result.addAll(player.getInventory().strs());
            result.add("Characteristics: ");
            result.addAll(player.getCharacteristics().strs());
        }
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
            Coordinates coordinates = Coordinates.random(RANDOMIZER, getMap().width(), getMap().height());
            Inventory inventory =  Inventory.random(RANDOMIZER);
            Characteristics characteristics = Characteristics.random(RANDOMIZER);
            Character character = new Monster(coordinates, baseName + "{" + i + "}", characteristics, inventory);
            if (!placeCharacter(character)) {
                LOGGER.warn("It was only possible to place " + i + " monsters");
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
            Coordinates coordinates = Coordinates.random(RANDOMIZER, width, height);
            Inventory inventory =  Inventory.random(RANDOMIZER);
            Character character = new Chest(coordinates, baseName + "{" + i + "}", inventory);
            if (!placeCharacter(character)) {
                LOGGER.warn("It was only possible to place " + i + " chests");
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

    /**
     * Gets the character ruled by user.
     * @return Player.
     */
    @Nullable
    private Player getPlayer() {
        if (getCharacters().isEmpty()) {
            return null;
        }
        return (Player) getCharacters().get(0);
    }

    /**
     * Tries to place character at Character.getCoordinates().
     * @param character {@link Character} to place
     * @return true if placement was successful
     */
    private boolean placeCharacter(Character character) {
        Coordinates coordinates = findEmptyPlace(character.getCoordinates());
        if (Objects.isNull(coordinates)
                || !getMap().getTile(coordinates.x(), coordinates.y()).isFree()
                || getCharacters().contains(character)) {
            return false;
        }
        getCharacters().add(character);
        return true;
    }

    /**
     * We have
     * @param name name of player
     * @param characteristics Character's characteristics.
     * @param preferredLocation attempted position or
     * if it's busy with wall or something, find most close free position.
     */
    private void placePlayer(String name, Characteristics characteristics, Coordinates preferredLocation) {
        Coordinates actualLocation = findEmptyPlace(preferredLocation);
        Player player = new Player(actualLocation, name, characteristics);
        if (!placeCharacter(player)) {
            LOGGER.error("Map is full, unable to place a player.");
            throw new IllegalStateException();
        }
    }

    /**
     * Checks if place at coordinates is empty (not occupied by any obstacles or characters).
     * @param coordinates {@link Coordinates}
     * @return true if successful
     */
    private boolean isEmptyPlace(Coordinates coordinates) {
        // TODO get rid of appealing to low-level map.
        // World's WorldProphet (know-all guy) and WorldMapper (painter guy) should be demarkated.
        return getMap().getTile(coordinates.x(), coordinates.y()).isFree()
                && Objects.isNull(getGameObjectAtPlace(coordinates));
    }

    /**
     * Find empty spot most closest to the preferredLocation.
     * @param preferredLocation {@link Coordinates}
     * @return Coordinates if found else null.
     * TODO currently its asymptotics is Theta(H * W) which can be reduced to
     * O(number of obstacles + number of characters) in the worst case.
     */
    private Coordinates findEmptyPlace(Coordinates preferredLocation) {
        Map map = getMap();
        if (isEmptyPlace(preferredLocation)) {
            return preferredLocation;
        }
        List<Coordinates> freePlaces = new ArrayList<>();
        for (int y = 0; y < map.height(); ++y) {
            for (int x = 0; x < map.width(); ++x) {
                Coordinates coordinates = new Coordinates(x, y);
                if (isEmptyPlace(coordinates)) {
                    freePlaces.add(coordinates);
                }
            }
        }
        return freePlaces.stream().max((o1, o2) -> {
            int o1dist = o1.dist2(preferredLocation);
            int o2dist = o2.dist2(preferredLocation);
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

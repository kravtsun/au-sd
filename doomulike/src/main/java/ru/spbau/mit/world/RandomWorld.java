package ru.spbau.mit.world;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.spbau.mit.mapper.Map;
import ru.spbau.mit.world.Character.Inventory;

import java.util.*;

import static ru.spbau.mit.world.GameObject.*;

public abstract class RandomWorld extends BaseWorld {
    private static final Random RANDOMIZER = new Random();
    private static final Logger LOGGER = LogManager.getLogger(RandomWorld.class);

    /**
     * Map should be filled with all obstacle and floor tiles.
     * @param map Map
     */
    RandomWorld(Map map) {
        super(map);
        placePlayer("Player", new Characteristics(100, 10, 0), mapCenter());
        populateCharacters();
    }

    @Override
    public void populateCharacters() {
        generateChests(30);
        generateNonPlayableCharacters(10);
    }

    private void generateNonPlayableCharacters(final int npcCount) {
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

    private void generateChests(final int chestsCount) {
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

    /**
     * Tries to place character at Character.getCoordinates().
     * @param character {@link Character} to place
     * @return true if placement was successful
     */
    private boolean placeCharacter(final Character character) {
        final Coordinates coordinates = findEmptyPlace(character.getCoordinates());
        if (Objects.isNull(coordinates)
                || !getMap().getTile(coordinates.x(), coordinates.y()).isFree()
                || getCharacters().contains(character)) {
            return false;
        }
        character.setCoordinates(coordinates);
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
        final Set<GameObject.Coordinates> visited = new HashSet<>();
        visited.add(preferredLocation);
        Queue<GameObject.Coordinates> q = new LinkedList<>();
        q.add(preferredLocation);
        final int[] dx = new int[]{-1, 1, 0, 0};
        final int[] dy = new int[]{0, 0, -1, 1};
        while (!q.isEmpty()) {
            Coordinates v = q.poll();
            if (isEmptyPlace(v)) {
                return v;
            }
            if (Objects.isNull(v)) {
                throw new IllegalStateException("object in queue cannot be null");
            } else {
                for (int i = 0; i < dx.length; ++i) {
                    final Coordinates nextCoordinates = new Coordinates(v.x() + dx[i], v.y() + dy[i]);
                    if (inside(nextCoordinates) && !visited.contains(nextCoordinates)) {
                        visited.add(nextCoordinates);
                        q.add(nextCoordinates);
                    }
                }
            }
        }
        return null;
    }
}

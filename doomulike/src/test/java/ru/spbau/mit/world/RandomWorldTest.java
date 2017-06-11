package ru.spbau.mit.world;

import org.junit.Before;
import org.junit.Test;
import ru.spbau.mit.mapper.Map;
import ru.spbau.mit.world.GameObject.Coordinates;

import java.lang.reflect.Method;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class RandomWorldTest {
    private static final int WIDTH = 3;
    private static final int HEIGHT = 3;
    private RandomWorld world;

    @Before
    public void setUp() {
        world = newRandomWorld();
        world.getCharacters().clear();
    }

    @Test
    public void generateNonPlayableCharactersTest() throws ReflectiveOperationException {
        RandomWorldFacade facade = new RandomWorldFacade(world);
        for (int y = 0; y < HEIGHT; ++y) {
            for (int x = 0; x < WIDTH; ++x) {
                final Coordinates p = new Coordinates(x, y);
                assertTrue(facade.isEmptyPlace(p));
                assertNull(world.getGameObjectAtPlace(p));
            }
        }
        final int npcCount = WIDTH * HEIGHT;
        facade.generateNonPlayableCharacters(npcCount);
        assertEquals(npcCount, world.getCharacters().stream()
                .map(Character::getCoordinates)
                .distinct()
                .collect(Collectors.toList())
                .size());
        for (int y = 0; y < HEIGHT; ++y) {
            for (int x = 0; x < WIDTH; ++x) {
                final Coordinates p = new Coordinates(x, y);
                assertFalse(facade.isEmptyPlace(p));
                assertTrue(Monster.class.isInstance(world.getGameObjectAtPlace(p)));
                assertFalse(facade.placeCharacter(dummyCharacter(p)));
            }
        }
    }

    private RandomWorld newRandomWorld() {
        Map map = new Map(WIDTH, HEIGHT);
        return new RandomWorld(map) {
            @Override
            public Player getPlayer() {
                return null;
            }
        };
    }

    private Character dummyCharacter(Coordinates p) {
        return new Player(p, "dummy", new Characteristics(100, 10, 0));
    }

    private static class RandomWorldFacade {
        private final RandomWorld world;

        RandomWorldFacade(RandomWorld world) {
            this.world = world;
        }

        public void generateNonPlayableCharacters(final int npcCount) throws ReflectiveOperationException {
            Method method = RandomWorld.class.getDeclaredMethod("generateNonPlayableCharacters", int.class);
            method.setAccessible(true);
            method.invoke(world, npcCount);
        }

        public void generateChests(final int chestsCount) throws ReflectiveOperationException {
            Method method = RandomWorld.class.getDeclaredMethod("generateChests", int.class);
            method.setAccessible(true);
            method.invoke(world, chestsCount);
        }

        public boolean placeCharacter(final Character character) throws ReflectiveOperationException {
            Method method = RandomWorld.class.getDeclaredMethod("placeCharacter", Character.class);
            method.setAccessible(true);
            return (boolean) method.invoke(world, character);
        }

        public boolean isEmptyPlace(Coordinates coordinates) throws ReflectiveOperationException {
            Method method = RandomWorld.class.getDeclaredMethod("isEmptyPlace", Coordinates.class);
            method.setAccessible(true);
            return (boolean) method.invoke(world, coordinates);
        }

        private Coordinates findEmptyPlace(Coordinates preferredLocation)
                throws ReflectiveOperationException {
            Method method = RandomWorld.class.getDeclaredMethod("findEmptyPlace", Coordinates.class);
            method.setAccessible(true);
            return (Coordinates) method.invoke(world, preferredLocation);
        }
    }
}

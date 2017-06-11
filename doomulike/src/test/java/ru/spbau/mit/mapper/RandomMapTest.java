package ru.spbau.mit.mapper;

import org.junit.Before;
import org.junit.Test;
import ru.spbau.mit.visualizer.Tile;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class RandomMapTest {
    private static final int WIDTH = 3;
    private static final int HEIGHT = 4;
    private RandomMap lonelyWallMap;
    private RandomMap wallMap;

    @Before
    public void setUp() {
        lonelyWallMap = new RandomMap(WIDTH, HEIGHT);
        wallMap = new RandomMap(WIDTH, HEIGHT);
        for (int y = 0; y < HEIGHT; ++y) {
            for (int x = 0; x < WIDTH; ++x) {
                lonelyWallMap.setTile(x, y, Tile.FLOOR);
                wallMap.setTile(x, y, Tile.WALL);
            }
        }
        lonelyWallMap.setTile(1, 1, Tile.WALL);
    }

    @Test
    public void countTileNeighborhoodTest()
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final Method countTileNeighborhoodMethod = RandomMap.class.getDeclaredMethod("countTileNeighborhood",
                int.class,
                int.class,
                Tile.class);
        countTileNeighborhoodMethod.setAccessible(true);
        assertEquals(1, (int) countTileNeighborhoodMethod.invoke(lonelyWallMap, 1, 1, Tile.WALL));
        assertEquals(8, (int) countTileNeighborhoodMethod.invoke(lonelyWallMap, 1, 1, Tile.FLOOR));
    }

    @Test
    public void smoothTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final Method smoothMethod = RandomMap.class.getDeclaredMethod("smooth", int.class);
        smoothMethod.setAccessible(true);
        Map oldlonelyWallMap = lonelyWallMap.clone();
        Map oldwallMap = wallMap.clone();
        smoothMethod.invoke(lonelyWallMap, 0);
        smoothMethod.invoke(wallMap, 0);
        for (int y = 0; y < lonelyWallMap.height(); ++y) {
            for (int x = 0; x < lonelyWallMap.width(); ++x) {
                assertEquals(lonelyWallMap.getTile(x, y), oldlonelyWallMap.getTile(x, y));
            }
        }

        for (int y = 0; y < wallMap.height(); ++y) {
            for (int x = 0; x < wallMap.width(); ++x) {
                assertEquals(wallMap.getTile(x, y), oldwallMap.getTile(x, y));
            }
        }

        smoothMethod.invoke(lonelyWallMap, 1);
        smoothMethod.invoke(wallMap, 1);

        for (int y = 0; y < wallMap.height(); ++y) {
            for (int x = 0; x < wallMap.width(); ++x) {
                assertEquals(wallMap.getTile(x, y), oldwallMap.getTile(x, y));
            }
        }

        oldlonelyWallMap.setTile(1, 1, Tile.FLOOR);
        for (int y = 0; y < lonelyWallMap.height(); ++y) {
            for (int x = 0; x < lonelyWallMap.width(); ++x) {
                assertEquals(lonelyWallMap.getTile(x, y), oldlonelyWallMap.getTile(x, y));
            }
        }
    }
}

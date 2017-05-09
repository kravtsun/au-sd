package ru.spbau.mit.mapper;
import ru.spbau.mit.visualizer.Tile;
import ru.spbau.mit.common.Pair;

public class RandomMap extends Map {
    private static final int SMOOTH_TIMES = 2;

    public RandomMap(int width, int height) {
        super(width, height);
        randomizeTiles();
    }

    private void randomizeTiles() {
        for (int y = 0; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                setTile(x, y, Math.random() < 0.6 ? Tile.FLOOR : Tile.WALL);
            }
        }
        smooth(SMOOTH_TIMES);
    }

    private int countTileNeighborhood(int x, int y, Tile tile) {
        int res = 0;
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx < 2; dx++) {
                if (!inBorders(x + dx, y + dy)) {
                    continue;
                }
                if (getTile(x + dx, y + dy) == tile) {
                    res++;
                }
            }
        }
        return res;
    }

    private void smooth(int times) {
        Tile[][] newTiles = newTiles();

        for (int time = 0; time < times; time++) {
                for (int y = 0; y < height(); y++) {
                    for (int x = 0; x < width(); x++) {
                    int floors = countTileNeighborhood(x, y, Tile.FLOOR);
//                    int rocks = countTileNeighborhood(x, y, Tile.BOUNDS) + countTileNeighborhood(x, y, Tile.WALL);
                    int walls = countTileNeighborhood(x, y, Tile.WALL);
                    setTile(x, y, floors >= walls ? Tile.FLOOR : Tile.WALL, newTiles);
                }
            }
            tiles = newTiles;
        }
    }
}

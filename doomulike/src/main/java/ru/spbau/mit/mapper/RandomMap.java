package ru.spbau.mit.mapper;
import ru.spbau.mit.visualizer.Tile;

public class RandomMap extends Map {
    private static final double WALLS_PERCENTAGE = 0.4;
    private static final int SMOOTH_TIMES = 2;

    public RandomMap(int width, int height) {
        super(width, height);
        randomizeTiles();
    }

    private void randomizeTiles() {
        for (int y = 0; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                setTile(x, y, Math.random() > WALLS_PERCENTAGE ? Tile.FLOOR : Tile.WALL);
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
                    int walls = countTileNeighborhood(x, y, Tile.WALL);
                    setTile(x, y, floors >= walls ? Tile.FLOOR : Tile.WALL, newTiles);
                }
            }
            tiles = newTiles;
        }
    }
}

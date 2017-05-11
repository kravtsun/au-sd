package ru.spbau.mit.mapper;

import ru.spbau.mit.visualizer.Tile;

import java.awt.Color;

/**
 * Storer of empty space and walls representation of game world.
 */
public class Map {
    private Tile[][] tiles;

    public Map(int width, int height) {
        tiles = newTiles(width, height);
    }

    public Map clone() {
        Map newMap = new Map(width(), height());

        for (int y = 0; y < height(); ++y) {
            for (int x = 0; x < width(); ++x) {
                newMap.setTile(x, y, getTile(x, y));
            }
        }
//        newMap.tiles = tiles.clone();
        return newMap;
    }

    public int width() {
        return tiles[0].length;
    }
    public int height() {
        return tiles.length;
    }

    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public Tile getTile(int x, int y) {
        return getTile(x, y, tiles);
    }

    protected Tile getTile(int x, int y, Tile[][] fromTiles) {
        if (!inBorders(x, y)) {
            return Tile.BOUNDS;
        } else {
            return fromTiles[y][x];
        }
    }

    public void setTile(int x, int y, Tile newTile) {
        setTile(x, y, newTile, tiles);
    }

    protected void setTile(int x, int y, Tile newTile, Tile[][] toTiles) {
        toTiles[y][x] = newTile;
    }

    public char glyph(int x, int y) {
        return getTile(x, y).glyph();
    }

    public Color color(int x, int y) {
        return getTile(x, y).color();
    }

    protected boolean inBorders(int x, int y) {
        return x >= 0 && x < width() && y >= 0 && y < height();
    }

    protected Tile[][] newTiles() {
        return newTiles(width(), height());
    }

    private Tile[][] newTiles(int width, int height) {
        return new Tile[height][width];
    }
}

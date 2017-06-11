package ru.spbau.mit.mapper;

import ru.spbau.mit.visualizer.Tile;

import java.awt.Color;

/**
 * Storer of empty space and walls representation of game world
 * in a form of 2D array of tiles.
 */
public class Map {
    private Tile[][] tiles;

    public Map(int width, int height) {
        tiles = newTiles(width, height);
    }

    /**
     * Clones this map. It's ok if warning of not calling the Objects' clone() warning
     * as all resources are contained in Map as a leaf class in hierarchy.
     * @return cloned Map.
     */
    public Map clone() {
        Map newMap = new Map(width(), height());

        for (int y = 0; y < height(); ++y) {
            for (int x = 0; x < width(); ++x) {
                newMap.setTile(x, y, getTile(x, y));
            }
        }
        return newMap;
    }

    /**
     * @return width of current tiles container.
     */
    public int width() {
        return tiles[0].length;
    }

    /**
     * @return height of current tiles container.
     */
    public int height() {
        return tiles.length;
    }

    /**
     * resetting tiles container to a new ones.
     * @param tiles new tiles container.
     */
    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    /**
     * @param x Ox coordinate.
     * @param y Oy coordinate.
     * @return Tile at specified position.
     */
    public Tile getTile(int x, int y) {
        return getTile(x, y, tiles);
    }

    private Tile getTile(int x, int y, Tile[][] fromTiles) {
        if (!inBorders(x, y)) {
            return Tile.BOUNDS;
        } else {
            return fromTiles[y][x];
        }
    }

    /**
     * Set new tile for specified position.
     * @param x Ox coordinate
     * @param y Oy coordinate
     * @param newTile new Tile for specified position.
     */
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

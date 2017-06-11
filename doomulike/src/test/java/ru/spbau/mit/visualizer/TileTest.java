package ru.spbau.mit.visualizer;

import org.junit.Test;

import static org.junit.Assert.*;

public class TileTest {
    @Test
    public void isFree() {
        assertTrue(Tile.FLOOR.isFree());
        assertFalse(Tile.WALL.isFree());
        assertFalse(Tile.BOUNDS.isFree());
        assertFalse(Tile.CHEST.isFree());
        assertFalse(Tile.MONSTER.isFree());
        assertTrue(Tile.CORPSE.isFree());
        assertTrue(Tile.PLAYER.isFree());
    }

}
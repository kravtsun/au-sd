package ru.spbau.mit.mapper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.spbau.mit.visualizer.Tile;

import java.awt.*;
import static org.junit.Assert.*;

public class MapTest {
    private Map cell;

    @Before
    public void setUp() {
        cell = new Map(1, 1);
    }

    @After
    public void tearDown() {
        cell = null;
    }

    @Test
    public void glyph() throws Exception {
        assertEquals(cell.getTile(0, 0), Tile.FLOOR);
    }

    @Test
    public void color() throws Exception {
        final Color floorColor = new Color(128, 128, 0);
        assertEquals(floorColor, cell.getTile(0, 0).color());
    }

    @Test
    public void inBorders() throws Exception {
        assertTrue(cell.inBorders(0, 0));
        assertFalse(cell.inBorders(0, 1));
        assertFalse(cell.inBorders(1, 0));
        assertFalse(cell.inBorders(1, 1));
        assertFalse(cell.inBorders(0, -1));
        assertFalse(cell.inBorders(-1, -1));
        assertFalse(cell.inBorders(-1, -1));
    }

}

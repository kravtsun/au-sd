package ru.spbau.mit.world;

import org.junit.Before;
import org.junit.Test;
import ru.spbau.mit.world.GameObject.Coordinates;

import static org.junit.Assert.*;

public class PlayerTest {
    private static final Coordinates DUMMY_COORDINATES = new Coordinates(300, -10);
    private static final Characteristics STARTING_CHARACTERISTICS = new Characteristics(100, 10, 0);
    private static final Characteristics FIRST_DELTA = new Characteristics(-10, +2, -1);
    private static final Characteristics DEADLY_DELTA = new Characteristics(-1000, 33, 1000);
    private Player player;

    @Before
    public void setUp() {
        player = new Player(DUMMY_COORDINATES, "Alice", new Characteristics(
                STARTING_CHARACTERISTICS.getHealth(),
                STARTING_CHARACTERISTICS.getStrength(),
                STARTING_CHARACTERISTICS.getLuck()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testToggleEmptyInventory() {
        player.toggleItem(0);
    }

    @Test
    public void toggleItem() throws Exception {
        Item firstItem = new Item("item1", FIRST_DELTA);
        player.getInventory().add(firstItem);
        assertTrue(player.getInventory().contains(firstItem));
        assertEquals(player.getInventory().get(0), firstItem);
        assertFalse(player.isPutItem(0));
        player.toggleItem(0);
        assertTrue(player.isPutItem(0));
        final Characteristics newCharacteristics = new Characteristics(
            STARTING_CHARACTERISTICS.getHealth() + FIRST_DELTA.getHealth(),
                STARTING_CHARACTERISTICS.getStrength() + FIRST_DELTA.getStrength(),
                STARTING_CHARACTERISTICS.getLuck() + FIRST_DELTA.getLuck());
        assertEquals(newCharacteristics, player.getCharacteristics());
        player.toggleItem(0);
        assertFalse(player.isPutItem(0));
        assertEquals(player.getCharacteristics(), STARTING_CHARACTERISTICS);

        player.getInventory().add(new Item("deadly", DEADLY_DELTA));
        assertTrue(player.isAlive());
        player.toggleItem(1);
        assertFalse(player.isAlive());
    }
}

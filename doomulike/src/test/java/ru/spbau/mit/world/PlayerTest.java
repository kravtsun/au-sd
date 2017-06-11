package ru.spbau.mit.world;

import org.junit.Before;
import org.junit.Test;
import ru.spbau.mit.world.GameObject.Coordinates;

import static org.junit.Assert.*;

public class PlayerTest {
    private static final Coordinates dummyCoordinates = new Coordinates(300, -10);
    private static final Characteristics startingCharacteristics = new Characteristics(100, 10, 0);
    private static final Characteristics firstDelta = new Characteristics(-10, +2, -1);
    private static final Characteristics deadlyDelta = new Characteristics(-1000, 33, 1000);
    private Player player;

    @Before
    public void setUp() {
        player = new Player(dummyCoordinates, "Alice", new Characteristics(
                startingCharacteristics.getHealth(),
                startingCharacteristics.getStrength(),
                startingCharacteristics.getLuck()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testToggleEmptyInventory() {
        player.toggleItem(0);
    }

    @Test
    public void toggleItem() throws Exception {
        Item firstItem = new Item("item1", firstDelta);
        player.getInventory().add(firstItem);
        assertTrue(player.getInventory().contains(firstItem));
        assertEquals(player.getInventory().get(0), firstItem);
        assertFalse(player.isPutItem(0));
        player.toggleItem(0);
        assertTrue(player.isPutItem(0));
        final Characteristics newCharacteristics = new Characteristics(
            startingCharacteristics.getHealth() + firstDelta.getHealth(),
                startingCharacteristics.getStrength() + firstDelta.getStrength(),
                startingCharacteristics.getLuck() + firstDelta.getLuck());
        assertEquals(newCharacteristics, player.getCharacteristics());
        player.toggleItem(0);
        assertFalse(player.isPutItem(0));
        assertEquals(player.getCharacteristics(), startingCharacteristics);

        player.getInventory().add(new Item("deadly", deadlyDelta));
        assertTrue(player.isAlive());
        player.toggleItem(1);
        assertFalse(player.isAlive());
    }
}
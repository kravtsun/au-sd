package ru.spbau.mit.world;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.spbau.mit.common.TerminalPrintable;
import ru.spbau.mit.world.logic.Action;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public abstract class Character extends GameObject {
    protected static final Logger LOGGER = LogManager.getLogger("Character");
    private final String name;
    private final Characteristics characteristics;
    private final Inventory inventory;

    Character(Coordinates p, String name, Characteristics characteristics, Inventory inventory) {
        super(p);
        this.name = name;
        this.characteristics = characteristics;
        this.inventory = inventory;
    }

    public abstract void step(final WorldProphet world, final KeyEvent keyEvent, List<Action> actions);

    public Inventory getInventory() {
        return inventory;
    }

    public void mergeInventory(Inventory otherInventory) {
        inventory.addAll(otherInventory);
    }

    public boolean isAlive() {
        return characteristics.getHealth() > 0;
    }

    public void die() {
        LOGGER.info("character " + getName() + " died.");
        characteristics.setHealth(0);
    }

    public Characteristics getCharacteristics() {
        return characteristics;
    }

    public String getName() {
        return name;
    }

    public boolean occupiesPosition(Coordinates coordinates) {
        return getCoordinates().equals(coordinates);
    }

    public List<String> statusInfo() {
        List<String> result = new ArrayList<>();
        result.add("Player: " + getName());
        result.add("Inventory: ");
        result.addAll(getInventory().strs());
        result.add("Characteristics: ");
        result.addAll(getCharacteristics().strs());
        return result;
    }

    public static class Inventory extends ArrayList<Item> implements TerminalPrintable {
        public static Inventory random(Random randomizer) {
            final int itemsLimit = 10;
            final int maxAbsDelta = 10;

            Inventory inventory = new Inventory();
            int itemsCount = randomizer.nextInt(itemsLimit);
            for (int i = 0; i < itemsCount; ++i) {
                Characteristics characteristics = new Characteristics(
                        randomizer.nextInt(maxAbsDelta) - maxAbsDelta / 2,
                        randomizer.nextInt(maxAbsDelta) - maxAbsDelta / 2,
                        randomizer.nextInt(maxAbsDelta) - maxAbsDelta / 2
                        );
                inventory.add(new Item(String.valueOf(i), characteristics));
            }
            return inventory;
        }

        @Override
        public List<String> strs() {
            List<String> result = new ArrayList<>();
            Iterator<Item> it = super.iterator();
            while (it.hasNext()) {
                result.add(it.next().str() + ", ");
            }
            return result;
        }
    }
}

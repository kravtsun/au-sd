package ru.spbau.mit.world;

import java.util.ArrayList;
import java.util.List;

public class Chest extends GameObject {
    private List<Item> inventory;

    public Chest(Coordinates p) {
        super(p);
        this.inventory = new ArrayList<>();
    }

    public Chest addItem(Item item) {
        inventory.add(item);
        return this;
    }

    public List<Item> getInventory() {
        return inventory;
    }
}

package ru.spbau.mit.world;

import java.util.List;

public abstract class Character extends GameObject {
    private String name;
    private Characteristics characteristics;
    private List<Item> inventory;

    Character(Coordinates p, String name, Characteristics characteristics) {
        super(p);
        this.name = name;
        this.characteristics = characteristics;
    }

    public abstract void step(List<Action> actions);

    public void mergeInventory(List<Item> otherInventory) {
        inventory.addAll(otherInventory);
    }
}

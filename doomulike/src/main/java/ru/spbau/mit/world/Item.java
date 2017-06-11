package ru.spbau.mit.world;

/**
 * Game object with some properties affecting living characters characteristics.
 */
public class Item {
    private final String description;
    private final Characteristics delta;

    public Item(String description, Characteristics delta) {
        this.description = description;
        this.delta = delta;
    }

    public String str() {
        return "item{" + description + "}: " + delta.str();
    }

    public Characteristics getDelta() {
        return delta;
    }
}

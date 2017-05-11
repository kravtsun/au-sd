package ru.spbau.mit.world;

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
}

package ru.spbau.mit.world;

import ru.spbau.mit.world.logic.Action;

import java.util.List;

public class Chest extends Character {
    public Chest(Coordinates p, String name, Inventory inventory) {
        super(p, name, new Characteristics(1, 0, 0), inventory);
    }

    @Override
    public void step(List<Action> actions) {
        // does nothing.
    }
}

package ru.spbau.mit.world;

import ru.spbau.mit.world.logic.Action;

import java.util.List;

public class Monster extends Character {
    Monster(Coordinates p, String name, Characteristics characteristics, Inventory inventory) {
        super(p, name, characteristics, inventory);
    }

    @Override
    public void step(List<Action> actions) {
    }
}

package ru.spbau.mit.world;

import ru.spbau.mit.world.logic.Action;

import java.util.List;

public class Player extends Character {
    private Action action;

    Player(Coordinates p, String name, Characteristics characteristics) {
        super(p, name, characteristics, new Inventory());
    }

    @Override
    public void step(List<Action> actions) {
        actions.add(action);
    }

    public void setAction(Action action) {
        this.action = action;
    }
}

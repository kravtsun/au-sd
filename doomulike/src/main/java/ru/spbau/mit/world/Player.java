package ru.spbau.mit.world;

import java.util.List;

public class Player extends Character {
    private Action action;

    Player(Coordinates p, String name, Characteristics characteristics) {
        super(p, name, characteristics);
    }

    @Override
    public void step(List<Action> actions) {
        actions.add(action);
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}

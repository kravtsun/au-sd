package ru.spbau.mit.world;

import java.util.List;

public class NonPlayableCharacter extends Character {
    NonPlayableCharacter(Coordinates p, String name, Characteristics characteristics) {
        super(p, name, characteristics);
    }

    @Override
    public void step(List<Action> actions) {

    }
}

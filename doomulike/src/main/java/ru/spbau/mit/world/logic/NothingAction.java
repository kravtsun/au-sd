package ru.spbau.mit.world.logic;

import ru.spbau.mit.world.Character;
import ru.spbau.mit.world.WorldProphet;

public class NothingAction extends Action {
    public NothingAction(Character subject) {
        super(subject);
    }

    @Override
    public void run(WorldProphet world) {
        // does nothing.
    }
}

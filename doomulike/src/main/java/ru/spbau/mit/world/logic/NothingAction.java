package ru.spbau.mit.world.logic;

import ru.spbau.mit.world.Character;
import ru.spbau.mit.world.WorldProphet;

/**
 * Action that does nothing.
 */
public class NothingAction extends Action {
    public NothingAction(WorldProphet world, Character subject) {
        super(subject, world);
    }

    @Override
    public void run() {
        // does nothing.
    }
}

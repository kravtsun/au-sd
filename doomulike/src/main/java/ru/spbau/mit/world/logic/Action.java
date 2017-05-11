package ru.spbau.mit.world.logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.spbau.mit.world.Character;
import ru.spbau.mit.world.WorldProphet;

/**
 * base class for all actions' classes which give a notion of
 * action done by given subject
 * TODO unify logging output from actions taking place.
 */
public abstract class Action {
    protected static final Logger logger = LogManager.getLogger("Action");
    private Character subject;

    protected Action(Character subject) {
        this.subject = subject;
    }

    public Character getSubject() {
        return subject;
    }

    public abstract void run(WorldProphet world);
}

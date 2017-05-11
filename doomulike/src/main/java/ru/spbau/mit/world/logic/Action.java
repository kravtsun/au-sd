package ru.spbau.mit.world.logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.spbau.mit.world.Character;
import ru.spbau.mit.world.WorldProphet;

/**
 * Base class for all actions' classes.
 * {@link Action}'s subclasses are tightly connected to all {@link Character}'s subclasses.
 * Represent logic flow. {@link Character} subject is meant to create an {@link Action}
 * and provide it to WorldRuler essense (or whomever represents the world's governance)
 * TODO unify logging output from actions taking place.
 */
public abstract class Action {
    protected static final Logger LOGGER = LogManager.getLogger("Action");
    private final Character subject;
    private final WorldProphet world;

    /**
     * pre-Action initialization. it's meant to take no action over {@link WorldProphet}
     * @param subject who executes the {@link Action}
     * @param world environment supplier.
     */
    protected Action(Character subject, WorldProphet world) {
        this.subject = subject;
        this.world = world;
    }

    /**
     * @return action's executor.
     */
    public Character getSubject() {
        return subject;
    }

    /**
     * actual procedures and changing the world.
     * It is still not clear if the world should stay in a consistent state
     * after this run().
     */
    public abstract void run();

    /**
     * {@link WorldProphet} getter.
     * @return WorldProphet
     */
    public WorldProphet getWorld() {
        return world;
    }
}

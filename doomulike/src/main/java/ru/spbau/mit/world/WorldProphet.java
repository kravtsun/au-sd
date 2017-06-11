package ru.spbau.mit.world;

import org.jetbrains.annotations.Nullable;
import ru.spbau.mit.visualizer.UserCommand;

import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;

import static ru.spbau.mit.world.GameObject.Coordinates;

/**
 * Known everything about what happens to World and to whom.
 * Interface from world component to application.
 */
public interface WorldProphet {
    /**
     * returns Game Object on given coordinates
     * (or more like this object occupies given point (generally, it doesn't have
     * to occupy only this point).
     * @param p Coordinates
     * @return null if Game Object is not found, this Game Object itself.
     */
    @Nullable
    GameObject getGameObjectAtPlace(Coordinates p);

    boolean inside(Coordinates p);
}

package ru.spbau.mit.world;

import org.jetbrains.annotations.Nullable;
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

    /**
     * Gets the character ruled by user.
     * @return Player.
     */
    @Nullable
    Player getPlayer();

    /**
     * @param p Coordinates
     * @return true if coordinates p inside world borders.
     */
    boolean inside(Coordinates p);
}

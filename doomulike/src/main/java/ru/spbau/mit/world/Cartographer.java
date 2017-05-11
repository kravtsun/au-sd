package ru.spbau.mit.world;

import ru.spbau.mit.mapper.Map;
import static ru.spbau.mit.world.GameObject.Coordinates;

/**
 * Map container's interface it's used by screens.
 */
public interface Cartographer {
    Map renderMap();
}

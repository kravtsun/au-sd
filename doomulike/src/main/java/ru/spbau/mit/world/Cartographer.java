package ru.spbau.mit.world;

import ru.spbau.mit.mapper.Map;

/**
 * Map container's interface it's used by screens.
 */
public interface Cartographer {

    /**
     * Needed for providing map displayed to terminal.
     * @return tile map rendered
     */
    Map renderMap();
}

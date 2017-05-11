package ru.spbau.mit.world;

import java.awt.event.KeyEvent;
import java.util.List;

import static ru.spbau.mit.world.GameObject.Coordinates;

/**
 * Known everything about what happens to World and to whom.
 */
public interface WorldProphet {
    GameObject getGameObjectAtPlace(Coordinates p);

    void respondInput(KeyEvent key);

    List<String> statusInfo();

    int[] availableCommands();
}

package ru.spbau.mit.world;

/**
 * Known everything about what happens to World and to whom.
 */
public interface WorldProphet {
    GameObject objectAtPlace(GameObject.Coordinates p);
}

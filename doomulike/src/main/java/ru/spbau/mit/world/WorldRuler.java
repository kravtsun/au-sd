package ru.spbau.mit.world;

import ru.spbau.mit.mapper.Map;

import java.awt.event.KeyEvent;

public interface WorldRuler {
    Map getMap();

    void respondInput(KeyEvent key);
}

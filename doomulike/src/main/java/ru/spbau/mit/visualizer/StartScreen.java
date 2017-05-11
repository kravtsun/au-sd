package ru.spbau.mit.visualizer;

import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;
import org.jetbrains.annotations.NotNull;
import ru.spbau.mit.mapper.Map;
import ru.spbau.mit.mapper.RandomMap;

/**
 * This screen is meant to be used for configuring pre-game settings and
 * loading different maps.
 * Currently it can only generate a random map from dimensions specified in constructor.
 */
public class StartScreen extends Screen {
    private final int width;
    private final int height;

    public StartScreen(int width, int height) {
        super();
        // -2 for borders.
        this.width = width;
        this.height = height;
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.write("Doomuelike - Roguelike created by a doomed person. Bwa ha ha.", 1, 1);
        pressEnterToDoSomething(terminal, "start");
    }

    @NotNull
    public Screen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen(this::getRandomMap) : this;
    }

    private int getWidth() {
        return width;
    }

    private int getHeight() {
        return height;
    }

    private Map getRandomMap() {
        return new RandomMap(getWidth() - 2, getHeight() - 2);
    }
}

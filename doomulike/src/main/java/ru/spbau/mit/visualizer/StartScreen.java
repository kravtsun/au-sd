package ru.spbau.mit.visualizer;

import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;
import ru.spbau.mit.mapper.Map;
import ru.spbau.mit.mapper.RandomMap;

public class StartScreen extends Screen {
    private final int width;
    private final int height;
    private Map map;
    public StartScreen(int width, int height) {
        super();
        // -2 for borders.
        this.width = width;
        this.height = height;
        this.map = getRandomMap();
    }

    public void displayOutput(AsciiPanel terminal) {
        terminal.write("Doomuelike - Roguelike created by a doomed person. Bwa ha ha.", 1, 1);
        pressSomethingToDoSomething(terminal, 22, "enter", "start");
    }

    public Screen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen(() -> getRandomMap()) : this;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private Map getRandomMap() {
        return new RandomMap(width - 2, height - 2);
    }
}

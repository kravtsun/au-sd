package ru.spbau.mit.visualizer;

import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;
import ru.spbau.mit.mapper.Map;
import ru.spbau.mit.mapper.RandomMap;

public class StartScreen extends Screen {
    private Map map;
    public StartScreen(int width, int height) {
        super();
        // -2 for borders.
        this.map = new RandomMap(width - 2, height - 2);
    }

    public void displayOutput(AsciiPanel terminal) {
        terminal.write("Doomuelike - Roguelike created by a doomed person. Bwa ha ha.", 1, 1);
        terminal.writeCenter("-- press [enter] to start --", 22);
    }

    public Screen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen(this.map) : this;
    }
}

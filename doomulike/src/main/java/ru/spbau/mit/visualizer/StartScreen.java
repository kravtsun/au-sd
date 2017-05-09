package ru.spbau.mit.visualizer;

import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;

public class StartScreen extends Screen {
    public void displayOutput(AsciiPanel terminal) {
        terminal.write("Doomuelike - Roguelike created by a doomed person. Bwa ha ha.", 1, 1);
        terminal.writeCenter("-- press [enter] to start --", 22);
    }

    public Screen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen() : this;
    }
}

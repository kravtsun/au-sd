package ru.spbau.mit.visualizer;

import asciiPanel.AsciiPanel;

import java.awt.event.KeyEvent;
import java.util.List;
import java.util.function.Supplier;

public class StatusScreen extends LeafScreen {
    private List<String> info;

    public StatusScreen(Supplier<Screen> baseScreenSupplier, List<String> info) {
        super(baseScreenSupplier);
        this.info = info;
        logger.debug("Creating StatusScreen");
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.write("Status: ", 1, 1);
        int currentLine = 1;
        for (String s : info) {
            terminal.write(s, 1, currentLine++);
        }
        pressSomethingToDoSomething(terminal, currentLine++, "enter", "resume");
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ENTER ? getBaseScreen() : this;
    }
}

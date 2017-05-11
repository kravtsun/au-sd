package ru.spbau.mit.visualizer;

import asciiPanel.AsciiPanel;

import java.awt.event.KeyEvent;
import java.util.function.Supplier;

public class LooseScreen extends LeafScreen {
    LooseScreen(Supplier<Screen> baseScreenSupplier) {
        super(baseScreenSupplier);
        LOGGER.debug("Creating LooseScreen");
    }

    public void displayOutput(AsciiPanel terminal) {
        terminal.write("You lost.", 1, 1);
        pressSomethingToDoSomething(terminal, 22, "enter", "restart");
    }

    public Screen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ENTER ? getBaseScreen() : this;
    }
}

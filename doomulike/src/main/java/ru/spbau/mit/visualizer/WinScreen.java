package ru.spbau.mit.visualizer;

import java.awt.event.KeyEvent;
import java.util.function.Supplier;
import asciiPanel.AsciiPanel;

public class WinScreen extends LeafScreen {
    WinScreen(Supplier<Screen> baseScreenSupplier) {
        super(baseScreenSupplier);
        logger.debug("Creating WinScreen");
    }

    public void displayOutput(AsciiPanel terminal) {
        terminal.write("You won.", 1, 1);
        terminal.writeCenter("-- press [enter] to restart --", 22);
    }

    public Screen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ENTER ? getBaseScreen() : this;
    }
}

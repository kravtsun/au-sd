package ru.spbau.mit.visualizer;

import java.awt.event.KeyEvent;
import java.util.function.Supplier;
import asciiPanel.AsciiPanel;
import org.jetbrains.annotations.NotNull;

/**
 * Screen shown to player in case of winning.
 */
public class WinScreen extends LeafScreen {
    WinScreen(Supplier<Screen> baseScreenSupplier) {
        super(baseScreenSupplier);
        LOGGER.debug("Creating WinScreen");
    }

    public void displayOutput(AsciiPanel terminal) {
        terminal.write("You won.", 1, 1);
        terminal.writeCenter("-- press [enter] to restart --", 22);
    }

    @NotNull
    public Screen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ENTER
                ? getBaseScreen()
                : super.respondToUserInput(key);
    }
}

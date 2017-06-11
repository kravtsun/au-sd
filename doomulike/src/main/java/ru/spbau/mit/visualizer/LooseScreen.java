package ru.spbau.mit.visualizer;

import asciiPanel.AsciiPanel;
import org.jetbrains.annotations.NotNull;

import java.awt.event.KeyEvent;
import java.util.function.Supplier;

public class LooseScreen extends LeafScreen {
    LooseScreen(Supplier<Screen> baseScreenSupplier) {
        super(baseScreenSupplier);
        LOGGER.debug("Creating LooseScreen");
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.write("You lost.", 1, 1);
        pressEnterToDoSomething(terminal, "restart");
    }

    @Override
    @NotNull
    public Screen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ENTER
                ? getBaseScreen()
                : super.respondToUserInput(key);
    }
}

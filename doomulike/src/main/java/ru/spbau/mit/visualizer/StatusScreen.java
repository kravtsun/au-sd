package ru.spbau.mit.visualizer;

import asciiPanel.AsciiPanel;
import org.jetbrains.annotations.NotNull;

import java.awt.event.KeyEvent;
import java.util.List;
import java.util.function.Supplier;

public class StatusScreen extends LeafScreen {
    private final List<String> info;

    public StatusScreen(Supplier<Screen> baseScreenSupplier, List<String> info) {
        super(baseScreenSupplier);
        this.info = info;
        LOGGER.debug("Creating StatusScreen");
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.clear();
        resetLineWriter();
        writeLine(terminal, "Status: ");
        for (String s : info) {
            writeLine(terminal, s);
        }
        writeLine(terminal, "-- press [enter] to resume --");
    }

    @NotNull
    @Override
    public Screen respondToUserInput(KeyEvent key) {
        int keyCode = key.getKeyCode();
        if (keyCode == KeyEvent.VK_ENTER) {
            return getBaseScreen();
        } else {
            return super.respondToUserInput(key);
        }
    }
}

package ru.spbau.mit.visualizer;

import asciiPanel.AsciiPanel;
import org.jetbrains.annotations.NotNull;

import java.awt.event.KeyEvent;
import java.util.List;
import java.util.function.Supplier;

/**
 * Screen for displaying help on commands.
 */
public class HelpScreen extends LeafScreen {
    private final List<UserCommand> userCommands;
    HelpScreen(Supplier<Screen> baseScreenSupplier, List<UserCommand> userCommands) {
        super(baseScreenSupplier);
        this.userCommands = userCommands;
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        resetLineWriter();
        for (UserCommand uc : getUserCommands()) {
            writeLine(terminal, uc.str());
        }
    }

    @NotNull
    @Override
    public Screen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ENTER ? getBaseScreen() : super.respondToUserInput(key);
    }

    public List<UserCommand> getUserCommands() {
        return userCommands;
    }
}

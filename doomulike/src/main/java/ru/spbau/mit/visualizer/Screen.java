package ru.spbau.mit.visualizer;

import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import asciiPanel.AsciiPanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public abstract class Screen implements Scrollable {
    /**
     * Logger for any screens.
     */
    protected static final Logger LOGGER = LogManager.getLogger("Visualizer");
    private int left;
    private int top;
    private int currentLine;

    /**
     * Creates a screen with default (zero) scroll.
     */
    Screen() {
        reset();
    }

    /**
     * displays currently available info (map, expected user action, etc.) to given terminal
     * @param terminal destination AsciiPanel.
     */
    public abstract void displayOutput(AsciiPanel terminal);

    /**
     * Change current information about game state according to user command from KeyEvent key.
     * @param key KeyEvent.
     * @return next state, e.g. screen that will work with user after @p KeyEvent key handled.
     * in inherited classes: should readdress it to super class if can't answer the user input.
     */
    @NotNull
    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_H:
                return new HelpScreen(() -> this, getUserCommands());
            case KeyEvent.VK_UP:
                scrollUp();
                break;
            case KeyEvent.VK_DOWN:
                scrollDown();
                break;
            case KeyEvent.VK_LEFT:
                scrollLeft();
                break;
            case KeyEvent.VK_RIGHT:
                scrollRight();
                break;
            case KeyEvent.VK_0:
            case KeyEvent.VK_NUMPAD0:
                reset();
                break;
            default:
                LOGGER.error("Unknown command: " + key.toString());
                break;
        }
        return this;
    }

    /**
     * get list of available commands which are operable by current screen.
     * @return list of {@link UserCommand} structures.
     */
    protected List<UserCommand> getUserCommands() {
        Function<String, UserCommand> scrollCommandGenerator = (dir) -> new UserCommand(dir, "Scroll " + dir);
        return Arrays.asList(
                scrollCommandGenerator.apply("up"),
                scrollCommandGenerator.apply("down"),
                scrollCommandGenerator.apply("left"),
                scrollCommandGenerator.apply("right"),
                new UserCommand("0", "Scroll reset."),
                new UserCommand("h", "Call help.")
        );
    }

    /**
     * Prints message that requires some reaction from user at center of the terminal.
     * (reaction specified as the expected key code command).
     * @param terminal destination terminal.
     */
    public void pressEnterToDoSomething(AsciiPanel terminal, String action) {
        final int line = terminal.getHeightInCharacters() / 2;
        terminal.writeCenter("-- press [enter] to " + action + " --", line);
    }

    @Override
    public int getScrollX() {
        return left;
    }

    @Override
    public void setScrollX(int newX) {
        left = newX;
    }

    @Override
    public int getScrollY() {
        return top;
    }

    @Override
    public void setScrollY(int newY) {
        top = newY;
    }

    @Override
    public int getCurrentLine() {
        return currentLine;
    }

    @Override
    public void setCurrentLine(int currentLine) {
        this.currentLine = currentLine;
    }
}

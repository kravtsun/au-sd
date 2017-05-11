package ru.spbau.mit.visualizer;

import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Screen {
    /**
     * Logger for any screens.
     */
    public static final Logger logger = LogManager.getLogger("Visualizer");

    /**
     * displays currently available info (map, expected user action, etc.) to given terminal
     * @param terminal
     */
    abstract public void displayOutput(AsciiPanel terminal);

    /**
     * Change current information about game state according to user command from KeyEvent key.
     * @param key
     * @return next state, e.g. screen that will work with user after @p KeyEvent key handled.
     */
    abstract public Screen respondToUserInput(KeyEvent key);

    /**
     * prints message that requires some reaction from user
     * (reaction specified as the expected key code command).
     * @param terminal where to print.
     * @param line on what line.
     */
    public void pressSomethingToDoSomething(AsciiPanel terminal, int line, String expected, String action) {
        terminal.writeCenter("-- press [" + expected + "] to " + action + " --", line);
    }
}

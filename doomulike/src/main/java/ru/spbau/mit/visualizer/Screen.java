package ru.spbau.mit.visualizer;

import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Screen {
    protected static final Logger logger = LogManager.getLogger("Application");

    abstract public void displayOutput(AsciiPanel terminal);

    abstract public Screen respondToUserInput(KeyEvent key);
}
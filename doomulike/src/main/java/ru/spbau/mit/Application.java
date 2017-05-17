package ru.spbau.mit;

import javax.swing.*;

import asciiPanel.AsciiPanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.spbau.mit.visualizer.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Application extends JFrame implements KeyListener {
    private static final Logger LOGGER = LogManager.getLogger("Application");
    private static final long serialVersionUID = 1089416816205537123L;

    private final AsciiPanel terminal;
    private Screen screen;

    public Application() {
        super();
        LOGGER.debug("Starting application");
        terminal = new AsciiPanel();
        add(terminal);
        pack();
        screen = new StartScreen(terminal.getWidthInCharacters(), terminal.getHeightInCharacters());
        addKeyListener(this);
        repaint();
    }

    @Override
    public void repaint() {
        terminal.clear();
        screen.displayOutput(terminal);
        super.repaint();
    }

    public static void main(String[] args) {
        Application app = new Application();
        app.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        app.setVisible(true);
    }

    /**
     * Action on any key type is empty as logic is transferred to keyPressed handler.
     * @param e what KeyEvent happened
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // does nothing.
    }

    /**
     * Screen logic on keyPressed event.
     * @param e what KeyEvent happened
     */
    @Override
    public void keyPressed(KeyEvent e) {
        screen = screen.respondToUserInput(e);
        repaint();
    }

    /**
     * action on releasing a key event.
     * @param e what KeyEvent happened
     */
    @Override
    public void keyReleased(KeyEvent e) {
        // does nothing.
    }
}

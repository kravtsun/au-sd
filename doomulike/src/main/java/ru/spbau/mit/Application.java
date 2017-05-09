package ru.spbau.mit;

import javax.swing.*;

import asciiPanel.AsciiPanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.spbau.mit.visualizer.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Application extends JFrame implements KeyListener {
    private static final Logger logger = LogManager.getLogger("Application");
    private static final long serialVersionUID = 1089416816205537123L;

    private AsciiPanel terminal;
    private Screen screen;

    public Application() {
        super();
        logger.debug("Starting application");
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

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        screen = screen.respondToUserInput(e);
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}

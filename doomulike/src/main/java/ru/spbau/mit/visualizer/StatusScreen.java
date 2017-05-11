package ru.spbau.mit.visualizer;

import asciiPanel.AsciiPanel;

import java.awt.event.KeyEvent;
import java.util.List;
import java.util.function.Supplier;

public class StatusScreen extends LeafScreen implements Scrollable {
    private int left;
    private int top;
    private List<String> info;
    private int currentLine;

    public StatusScreen(Supplier<Screen> baseScreenSupplier, List<String> info) {
        super(baseScreenSupplier);
        this.info = info;
        LOGGER.debug("Creating StatusScreen");
        left = 0;
        top = 0;
        currentLine = 1;
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.clear();
        currentLine = 0;
        writeLine(terminal, "Status: ");
        for (String s : info) {
            writeLine(terminal, s);
        }
        writeLine(terminal, "-- press [enter] to resume --");
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        int keyCode = key.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_ENTER: return getBaseScreen();
            case KeyEvent.VK_UP: setScrollY(getScrollY() - 1); break;
            case KeyEvent.VK_DOWN: setScrollY(getScrollY() + 1); break;
            case KeyEvent.VK_LEFT: setScrollX(getScrollX() - 1); break;
            case KeyEvent.VK_RIGHT: setScrollX(getScrollX() + 1); break;
            case KeyEvent.VK_0:
                case KeyEvent.VK_NUMPAD0:
                    setScrollX(0);
                    setScrollY(0);
                    break;
            default: LOGGER.error("Unknown command: " + key.toString());
        }
        return this;
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
    public void writeLine(AsciiPanel terminal, String line) {
        int y = getScrollY() + (currentLine++);
        if (y >= 0 && y < terminal.getHeightInCharacters()) {
            for (int x = getScrollX(), j = 0; j < line.length(); ++x, ++j) {
                if (x >= 0 && x < terminal.getWidthInCharacters()) {
                    terminal.write(line.charAt(j), x, y);
                }
            }
        }
    }
}

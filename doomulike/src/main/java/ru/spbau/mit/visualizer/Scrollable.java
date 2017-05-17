package ru.spbau.mit.visualizer;

import asciiPanel.AsciiPanel;

/**
 * Scrollable interface for some class which writes to terminal with scrolling ability.
 * TODO minimize number of writers straight to terminal
 * TODO inherit Screen from Scrollable with some scroll switcher for those non-scrollable
 */
public interface Scrollable {
    int getScrollX();
    void setScrollX(int newX);

    int getScrollY();
    void setScrollY(int newY);

    int getCurrentLine();
    void setCurrentLine(int newLine);

    default void scrollUp() {
        setScrollY(getScrollY() - 1);
    }

    default void scrollDown() {
        setScrollY(getScrollY() + 1);
    }

    default void scrollLeft() {
        setScrollX(getScrollX() - 1);
    }

    default void scrollRight() {
        setScrollX(getScrollX() + 1);
    }

    /**
     * Resets current line for writing and scroll.
     */
    default void reset() {
        resetLineWriter();
        setScrollX(0);
        setScrollY(0);
    }

    /**
     * Resets current line for writing.
     */
    default void resetLineWriter() {
        setCurrentLine(0);
    }

    /**
     * Writes String line to terminal and increments current line.
     * @param terminal Terminal where to write.
     * @param line what String to write.
     */
    default void writeLine(AsciiPanel terminal, String line) {
        // + 1: because to close
        final int y = getScrollY() + (getCurrentLine()) + 1;
        if (y >= 0 && y < terminal.getHeightInCharacters()) {
            int x = getScrollX();
            for (int j = 0; j < line.length(); ++x, ++j) {
                if (x >= 0 && x < terminal.getWidthInCharacters()) {
                    terminal.write(line.charAt(j), x, y);
                }
            }
        }
        setCurrentLine(getCurrentLine() + 1);
    }
}

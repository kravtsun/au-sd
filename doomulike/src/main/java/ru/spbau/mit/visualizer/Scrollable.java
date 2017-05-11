package ru.spbau.mit.visualizer;

import asciiPanel.AsciiPanel;

public interface Scrollable {
    int getScrollX();
    void setScrollX(int newX);

    int getScrollY();
    void setScrollY(int newY);

    void writeLine(AsciiPanel terminal, String line);
}

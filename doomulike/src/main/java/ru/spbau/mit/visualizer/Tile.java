package ru.spbau.mit.visualizer;

import java.awt.Color;
import asciiPanel.AsciiPanel;

public enum Tile {
    FLOOR('.', AsciiPanel.yellow),
    WALL('#', AsciiPanel.yellow),
    BOUNDS('x', AsciiPanel.brightBlack),
    CHEST('*', AsciiPanel.brightMagenta),
    MONSTER('$', AsciiPanel.brightRed),
    CORPSE('&', AsciiPanel.brightBlack),
    PLAYER('@', AsciiPanel.green);

    private char glyph;
    public char glyph() {
        return glyph;
    }

    private Color color;
    public Color color() {
        return color;
    }

    Tile(char glyph, Color color) {
        this.glyph = glyph;
        this.color = color;
    }

    public boolean isFree() {
        return equals(FLOOR) || equals(CORPSE);
    }
}

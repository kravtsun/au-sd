package ru.spbau.mit;

public abstract class GameObject {
    public class Coordinates {
        int x, y;
        Coordinates(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public abstract void step();
}

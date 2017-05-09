package ru.spbau.mit.world;

public abstract class GameObject {

    private Coordinates coordinates;

    GameObject(Coordinates coordinates) {
        setCoordinates(coordinates);
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates newCoordinates) {
        this.coordinates = newCoordinates;
    }

    public static class Coordinates implements Cloneable {
        int x;
        int y;
        public Coordinates(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int x() {
            return x;
        }

        public int y() {
            return y;
        }

        public int dist2(Coordinates rhs) {
            return sqr(x - rhs.x) + sqr(y - rhs.y);
        }

        private static int sqr(int x) {
            return x * x;
        }

        @Override
        public Coordinates clone() {
            Coordinates newPoint = new Coordinates(x, y);
            return newPoint;
        }
    }
}

package ru.spbau.mit.world;

import java.util.Objects;
import java.util.Random;

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

    /**
     * Auxiliary class for storing notion of position of GameObject in World.
     */
    public static class Coordinates implements Cloneable {
        private final int x;
        private final int y;
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

        @Override
        public Coordinates clone() {
            return new Coordinates(x, y);
        }

        @Override
        public boolean equals(Object rhs) {
            if (Objects.isNull(rhs) || !Coordinates.class.isInstance(rhs)) {
                return false;
            } else {
                return this == rhs
                        || this.x == ((Coordinates) rhs).x && this.y == ((Coordinates) rhs).y;
            }
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        public String str() {
            return "(" + String.valueOf(x) + ", " + String.valueOf(y) + ")";
        }

        public static Coordinates random(Random randomizer, int xbound, int ybound) {
            return new Coordinates(randomizer.nextInt(xbound), randomizer.nextInt(ybound));
        }

        private static int sqr(int x) {
            return x * x;
        }
    }
}

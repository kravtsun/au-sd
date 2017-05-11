package ru.spbau.mit.world;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Characteristics {
    private static final int HEALTH_BOUND = 100;
    private static final int STRENGTH_BOUND = 5;
    private static final int LUCK_BOUND = 10;
    private int health;
    private int strength;
    private int luck; // percentage of double damage.

    public Characteristics() {
        new Characteristics(0, 0, 0);
    }

    public Characteristics(int health, int strength, int luck) {
        this.health = health;
        this.strength = strength;
        this.luck = luck;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getLuck() {
        return luck;
    }

    public void setLuck(int luck) {
        this.luck = luck;
    }

    public List<String> strs() {
        return Arrays.asList(
                "strength: " + String.valueOf(getStrength()) + ", ",
                "health: " + String.valueOf(getHealth()) + ", ",
                "luck: " + String.valueOf(getLuck())
        );
    }

    public String str() {
        return String.join("", strs());
    }

    private static int randomHealth(Random randomizer) {
        return 1 + randomizer.nextInt(HEALTH_BOUND);
    }

    private static int randomStrength(Random randomizer) {
        return randomizer.nextInt(STRENGTH_BOUND);
    }

    private static int randomLuck(Random randomizer) {
        return randomizer.nextInt(LUCK_BOUND);
    }

    public static Characteristics random(Random randomizer) {
        return new Characteristics(randomHealth(randomizer), randomStrength(randomizer), randomLuck(randomizer));
    }
}

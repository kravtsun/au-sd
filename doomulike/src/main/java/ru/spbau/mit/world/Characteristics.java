package ru.spbau.mit.world;

import ru.spbau.mit.common.TerminalPrintable;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Characteristics implements TerminalPrintable {
    private static final int HEALTH_BOUND = 100;
    private static final int STRENGTH_BOUND = 5;
    private static final int LUCK_BOUND = 10;
    private int health;
    private int strength;
    private int luck; // percentage of double damage.

    public Characteristics(int health, int strength, int luck) {
        this.health = health;
        this.strength = strength;
        this.luck = luck;
    }

    @Override
    public boolean equals(Object o) {
        if (Objects.isNull(o) || !Characteristics.class.isInstance(o)) {
            return false;
        }
        final Characteristics rhs = (Characteristics) o;
        return this == rhs || health == rhs.getHealth()
                && strength == rhs.getStrength()
                && luck == rhs.getLuck();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
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

    @Override
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

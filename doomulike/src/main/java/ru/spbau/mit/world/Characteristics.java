package ru.spbau.mit.world;

public class Characteristics {
    private int hp;
    private int strength;
    private int luck; // percentage of double damage.

    public Characteristics(int hp, int strength, int luck) {
        this.hp = hp;
        this.strength = strength;
        this.luck = luck;
    }
}

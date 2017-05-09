package ru.spbau.mit.common;

public class Pair<F, S> {
    private F first;
    private S second;

    public <F1 extends F, S1 extends S> Pair(F1 first, S1 second) {
        this.first = first;
        this.second = second;
    }

    public F first() {
        return this.first;
    }

    public S second() {
        return this.second;
    }

    public String str() {
        return "(" + first.toString()
                + ", " + second.toString()
                + ")";
    }
}

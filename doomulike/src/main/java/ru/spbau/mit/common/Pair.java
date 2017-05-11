package ru.spbau.mit.common;

/**
 * Simple generic class for storing pairs of Objects of any types.
 * @param <F> first object's type.
 * @param <S> second object's type.
 */
public class Pair<F, S> {
    private F first;
    private S second;

    public <F1 extends F, S1 extends S> Pair(F1 first, S1 second) {
        this.first = first;
        this.second = second;
    }

    /**
     * @return first object.
     */
    public F first() {
        return this.first;
    }

    /**
     *
     * @return second object.
     */
    public S second() {
        return this.second;
    }

    /**
     * string representation for following pretty output at terminal.
     * TODO come up with own Printable interface which will handle string-by-string print on terminal,
     * i.e. which returns List<String> for PlayScreen be able to place it before the user.
     * @return String
     */
    public String str() {
        return "(" + first.toString()
                + ", " + second.toString()
                + ")";
    }
}

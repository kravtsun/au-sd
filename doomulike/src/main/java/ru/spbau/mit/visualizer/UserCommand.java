package ru.spbau.mit.visualizer;

/**
 * data storage for data about available user input.
 * TODO make it available to store events so that it can be used as a subscription object.
 */
public class UserCommand {
    private final String keyCombination;
    private final String description;

    public UserCommand(String keyCombination, String description) {
        this.keyCombination = keyCombination;
        this.description = description;
    }

    public String str() {
        return keyCombination + ": " + description;
    }
}

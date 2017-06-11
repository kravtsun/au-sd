package ru.spbau.mit.world;

import ru.spbau.mit.visualizer.UserCommand;

import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;

public interface Playable {
    /**
     * @return list of all commands' KeyEvent keys which can be handled by the world.
     * TODO synchronize this function with respondInput main switch
     * i.e. via adding subscriptions or just Map<KeyEvent, Supplier<Action>>.
     */
    int[] availableCommands();

    /**
     * User Commands (currently descriptive parts only) for for handled commands
     * mapped to availableCommands() key codes.
     * @return List<UserCommand>
     */
    List<UserCommand> availableUserCommands();

    /**
     * If world has some answer to given KeyEvent.
     * @param key KeyEvent
     * @return true if world is capable of handling given KeyEvent.
     */
    default boolean canAnswer(KeyEvent key) {
        final int keyCode = key.getKeyCode();
        return Arrays.stream(availableCommands()).anyMatch((k) -> k == keyCode);
    }
}

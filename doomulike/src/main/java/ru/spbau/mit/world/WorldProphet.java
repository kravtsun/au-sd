package ru.spbau.mit.world;

import org.jetbrains.annotations.Nullable;
import ru.spbau.mit.visualizer.UserCommand;

import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;

import static ru.spbau.mit.world.GameObject.Coordinates;

/**
 * Known everything about what happens to World and to whom.
 * Interface from world component to application.
 */
public interface WorldProphet {
    /**
     * returns Game Object on given coordinates
     * (or more like this object occupies given point (generally, it doesn't have
     * to occupy only this point).
     * @param p Coordinates
     * @return null if Game Object is not found, this Game Object itself.
     */
    @Nullable
    GameObject getGameObjectAtPlace(Coordinates p);

    /**
     * For showing at StatusScreen.
     * @return list of lines printable at StatusScreen.
     */
    List<String> statusInfo();

    /**
     * Handle given user input.
     * @param key {@link KeyEvent}
     */
    void respondInput(KeyEvent key);

    /**
     * User Commands (currently descriptive parts only) for for handled commands
     * mapped to availableCommands() key codes.
     * @return List<UserCommand>
     */
    List<UserCommand> availableUserCommands();

    /**
     * @return list of all commands' KeyEvent keys which can be handled by the world.
     * TODO synchronize this function with respondInput main switch
     * i.e. via adding subscriptions or just Map<KeyEvent, Supplier<Action>>.
     */
    int[] availableCommands();

    /**
     * If world has some answer to given KeyEvent.
     * @param key KeyEvent
     * @return true if world is capable of handling given KeyEvent.
     */
    default boolean canAnswer(KeyEvent key) {
        int keyCode = key.getKeyCode();
        return Arrays.stream(availableCommands()).anyMatch((k) -> k == keyCode);
    }
}

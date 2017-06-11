package ru.spbau.mit.world;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.spbau.mit.visualizer.UserCommand;
import ru.spbau.mit.world.logic.Action;
import ru.spbau.mit.world.logic.MoveAction;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class Player extends Character implements Playable {
    Player(Coordinates p, String name, Characteristics characteristics) {
        super(p, name, characteristics, new Inventory());
    }

    @Override
    public void step(final WorldProphet world, final KeyEvent key, List<Action> actions) {
        LOGGER.info("user pressed key: " + key.toString());
        Action userAction;
        switch (key.getKeyCode()) {
            case KeyEvent.VK_UP:
                userAction = new MoveAction(world, this, MoveAction.MoveType.UP);
                break;
            case KeyEvent.VK_DOWN:
                userAction = new MoveAction(world, this, MoveAction.MoveType.DOWN);
                break;
            case KeyEvent.VK_LEFT:
                userAction = new MoveAction(world, this, MoveAction.MoveType.LEFT);
                break;
            case KeyEvent.VK_RIGHT:
                userAction = new MoveAction(world, this, MoveAction.MoveType.RIGHT);
                break;
            default:
                String errorMessage = "Unknown command: " + key.toString();
                LOGGER.error(errorMessage);
                throw new IllegalArgumentException(errorMessage);
        }
        actions.add(userAction);
    }

    @Override
    public int[] availableCommands() {
        return new int[] {
                KeyEvent.VK_UP,
                KeyEvent.VK_DOWN,
                KeyEvent.VK_LEFT,
                KeyEvent.VK_RIGHT
        };
    }

    @Override
    public List<UserCommand> availableUserCommands() {
        Function<String, UserCommand> moveCommandGenerator =
                (dir) -> {
                    String msg = "Go " + dir + " or attack creature or loot chest to the " + dir + ".";
                    return new UserCommand(dir, msg);
                };
        return Arrays.asList(
                moveCommandGenerator.apply("up"),
                moveCommandGenerator.apply("down"),
                moveCommandGenerator.apply("left"),
                moveCommandGenerator.apply("right")
        );
    }
}

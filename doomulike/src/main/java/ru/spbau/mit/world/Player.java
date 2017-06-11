package ru.spbau.mit.world;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.spbau.mit.visualizer.UserCommand;
import ru.spbau.mit.world.logic.Action;
import ru.spbau.mit.world.logic.MoveAction;

import java.awt.event.KeyEvent;
import java.util.*;
import java.util.function.Function;

public class Player extends Character implements Playable {
    private Set putItems;

    Player(Coordinates p, String name, Characteristics characteristics) {
        super(p, name, characteristics, new Inventory());
        this.putItems = new HashSet();
    }

    @Override
    public void step(final WorldProphet world, final KeyEvent key, List<Action> actions) {
        LOGGER.info("user pressed key: " + key.toString());
        if (!isAlive()) {
            return;
        }
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

    public void toggleItem(final int itemIndex) {
        if (itemIndex < 0 || itemIndex >= getInventory().size()) {
            throw new IllegalArgumentException("itemIndex does not correspond to inventory size");
        }

        final Characteristics characteristics = getCharacteristics();
        final Item item = getInventory().get(itemIndex);
        final Characteristics delta = item.getDelta();
        if (putItems.contains(item)) {
            putItems.remove(item);
            characteristics.setHealth(characteristics.getHealth() - delta.getHealth());
            characteristics.setStrength(characteristics.getStrength() - delta.getStrength());
            characteristics.setLuck(characteristics.getLuck() - delta.getLuck());
        } else {
            putItems.add(item);
            characteristics.setHealth(characteristics.getHealth() + delta.getHealth());
            characteristics.setStrength(characteristics.getStrength() + delta.getStrength());
            characteristics.setLuck(characteristics.getLuck() + delta.getLuck());
        }

        if (characteristics.getHealth() < 0) {
            die();
        }
    }

    public boolean isPutItem(final int itemIndex) {
        if (itemIndex < 0 || itemIndex > getInventory().size()) {
            return false;
        }
        final Item item = getInventory().get(itemIndex);
        return putItems.contains(item);
    }
}

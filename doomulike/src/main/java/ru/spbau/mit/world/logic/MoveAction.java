package ru.spbau.mit.world.logic;
import ru.spbau.mit.world.*;
import ru.spbau.mit.world.Character;
import ru.spbau.mit.world.GameObject.Coordinates;

import java.security.InvalidParameterException;
import java.util.Objects;

/**
 * action that tries to move object in given direction.
 */
public class MoveAction extends Action {
    public enum MoveType {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }
    private int dx;
    private int dy;

    @Override
    public void run() {
        Coordinates currentPoint = getSubject().getCoordinates();
        Coordinates newCoordinates = new Coordinates(currentPoint.x() + dx, currentPoint.y() + dy);
        GameObject object = getWorld().getGameObjectAtPlace(newCoordinates);
        if (Objects.isNull(object)) {
            move(newCoordinates);
        } else if (Chest.class.isInstance(object)) {
            loot((Chest) object);
            move(newCoordinates);
        } else if (Obstacle.class.isInstance(object)) {
            LOGGER.info("Subject " + getSubject().getName()
                    + " hit an obstacle at" + newCoordinates.str());
            // just hit a wall.
        } else if (Character.class.isInstance(object)) {
            new AttackAction(getWorld(), getSubject(), (Character) object).run();
        } else {
            LOGGER.error("Character " + getSubject().getName()
                    + " faced unknown object: " + object.toString());
        }
    }

    public MoveAction(WorldProphet world, Character subject, MoveType moveType) {
        super(subject, world);
        switch (moveType) {
            case UP:
                dx = 0;
                dy = -1;
                break;
            case DOWN:
                dx = 0;
                dy = +1;
                break;
            case LEFT:
                dx = -1;
                dy = 0;
                break;
            case RIGHT:
                dx = +1;
                dy = 0;
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    private void loot(Chest chest) {
        LOGGER.info(getSubject().getName() + ": loot from " + chest.getName() + ": ");
        Character.Inventory chestInventory = chest.getInventory();
        for (Item i : chestInventory) {
            LOGGER.info(i.str());
        }
        getSubject().mergeInventory(chestInventory);
        chest.getInventory().clear();
        chest.die();
    }

    private void move(Coordinates to) {
        Coordinates from = getSubject().getCoordinates();
        LOGGER.info(getSubject().getName() + ": move from " + from.str() + " to " + to.str());
        getSubject().setCoordinates(to);
    }
}

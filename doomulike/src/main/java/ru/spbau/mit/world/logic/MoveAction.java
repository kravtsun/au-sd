package ru.spbau.mit.world.logic;
import ru.spbau.mit.world.*;
import ru.spbau.mit.world.Character;
import ru.spbau.mit.world.GameObject.Coordinates;

import java.lang.*;
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
    };
    private int dx;
    private int dy;

    @Override
    public void run(WorldProphet world) {
        Coordinates currentPoint = getSubject().getCoordinates();
        Coordinates newCoordinates = new Coordinates(currentPoint.x() + dx, currentPoint.y() + dy);
        GameObject object = world.getGameObjectAtPlace(newCoordinates);
        if (Objects.isNull(object)) {
            move(newCoordinates);
        } else if (Chest.class.isInstance(object)) {
            loot((Chest) object);
            move(newCoordinates);
        } else if (Obstacle.class.isInstance(object)) {
            // just hit a wall.
        } else if (ru.spbau.mit.world.Character.class.isInstance(object)) {
            new AttackAction(getSubject(), (ru.spbau.mit.world.Character) object).run(world);
        } else {
            logger.error("Character " + getSubject().getName() + " faced unknown object: " + object.toString());
        }
    }

    public MoveAction(Character subject, MoveType moveType) {
        super(subject);
        switch (moveType) {
            case UP: dx = 0; dy = -1; break;
            case DOWN: dx = 0; dy = +1; break;
            case LEFT: dx = -1; dy = 0; break;
            case RIGHT: dx = +1; dy = 0; break;
        }
    }

    private void loot(Chest chest) {
        logger.info(getSubject().getName() + ": loot from " + chest.getName() + ": ");
        ru.spbau.mit.world.Character.Inventory chestInventory = chest.getInventory();
        for (Item i : chestInventory) {
            logger.info(i.str());
        }
        getSubject().mergeInventory(chestInventory);
        chest.getInventory().clear();
        chest.die();
    }

    private void move(Coordinates to) {
        Coordinates from = getSubject().getCoordinates();
        logger.info(getSubject().getName() + ": move from " + from.str() + " to " + to.str());
        getSubject().setCoordinates(to);
    }
}

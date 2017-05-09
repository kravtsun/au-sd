package ru.spbau.mit.world;
import ru.spbau.mit.world.GameObject.Coordinates;

import java.lang.*;
import java.util.Objects;

public class MoveAction extends Action {
    public static enum MoveType {
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
        GameObject object = world.objectAtPlace(newCoordinates);
        if (Objects.isNull(object)) {
            getSubject().setCoordinates(newCoordinates);
        } else if (Chest.class.isInstance(object)) {
            getSubject().mergeInventory(((Chest) object).getInventory());
            getSubject().setCoordinates(newCoordinates);
        } else if (Obstacle.class.isInstance(object)) {
            // just hit a wall.
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
}

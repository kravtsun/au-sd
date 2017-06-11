package ru.spbau.mit.world;

import ru.spbau.mit.world.logic.Action;
import ru.spbau.mit.world.logic.MoveAction;
import ru.spbau.mit.world.logic.NothingAction;

import java.awt.event.KeyEvent;
import java.util.List;

public class Monster extends Character {
    Monster(Coordinates p, String name, Characteristics characteristics, Inventory inventory) {
        super(p, name, characteristics, inventory);
    }

    @Override
    public void step(final WorldProphet world, final KeyEvent keyEvent, List<Action> actions) {
        final int depthOfView = 10;
        Coordinates playerCoordinates = world.getPlayer().getCoordinates();
        if (playerCoordinates.dist2(getCoordinates()) > depthOfView || !isAlive() || !world.getPlayer().isAlive()) {
            // statue mode.
            return;
        }

        int dx = playerCoordinates.x() - getCoordinates().x();
        int dy = playerCoordinates.y() - getCoordinates().y();
        if (dx != 0) {
            dx /= Math.abs(dx);
        }
        if (dy != 0) {
            dy /= Math.abs(dy);
        }
        if ((dx == 0) == (dy == 0)) {
            if (dx == 0) {
                throw new IllegalStateException(playerCoordinates.str() + " -> (" + getCoordinates().str());
            }
            if (Math.random() > 0.5) {
                dx = 0;
            } else {
                dy = 0;
            }
        }
        MoveAction.MoveType moveType = MoveAction.MoveType.valueOf(dx, dy);
        actions.add(new MoveAction(world, this, moveType));
    }
}

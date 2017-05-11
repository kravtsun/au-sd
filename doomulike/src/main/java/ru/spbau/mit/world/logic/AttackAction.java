package ru.spbau.mit.world.logic;

import ru.spbau.mit.world.Character;
import ru.spbau.mit.world.WorldProphet;

/**
 * This action takes care of dealing with characters' characteristics after
 * Action::subject hits AttackAction object.
 * AttackAction does nothing about order of attacking or a counter-attack.
 * It just assumes subject is attacking the object now (and nothing can stop him).
 */
public class AttackAction extends Action {
    private final Character object; // whom to attack

    AttackAction(WorldProphet world, Character subject, Character object) {
        super(subject, world);
        this.object = object;
    }

    /**
     * Rules:
     * if subject is dead, do nothing.
     * if object is whether dead or has become unreachable, do nothing.
     */
    @Override
    public void run() {
        Character subject = getSubject();
        boolean objectIsReachable = getWorld().getGameObjectAtPlace(object.getCoordinates()) == object;
        if (subject.isAlive() && objectIsReachable && subject.isAlive() && object.isAlive()) {
            int subjectStrength = subject.getCharacteristics().getStrength();
            int objectHealth = object.getCharacteristics().getHealth();
            object.getCharacteristics().setHealth(objectHealth - subjectStrength);
            if (object.getCharacteristics().getHealth() <= 0) {
                object.die();
            }
            LOGGER.info("Attack: " + subject.getName()
                    + " hits " + object.getName()
                    + " with " + subjectStrength + " points");
        }
    }
}

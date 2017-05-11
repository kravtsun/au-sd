package ru.spbau.mit.world.logic;

import ru.spbau.mit.world.Character;
import ru.spbau.mit.world.WorldProphet;

/**
 * This action takes care of dealing with characters' characteristics after
 * Action::subject hits AttackAction object.
 * AttackAction does nothing about order of attacking or a counter-attack.
 * It just assumes subject is attacking the object now (and nothing can stop him).
 *
 */
public class AttackAction extends Action {
    private Character object; // whom to attack

    AttackAction(Character subject, Character object) {
        super(subject);
        this.object = object;
    }

    @Override
    public void run(WorldProphet world) {
        Character subject = getSubject();
        // attack cannot be post-mortem
        boolean objectStillThere = world.getGameObjectAtPlace(object.getCoordinates()) == object;
        if (objectStillThere && subject.isAlive() && object.isAlive()) {
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

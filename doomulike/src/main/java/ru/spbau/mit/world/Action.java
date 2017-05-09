package ru.spbau.mit.world;

public abstract class Action {
    private Character subject;

    protected Action(Character subject) {
        this.subject = subject;
    }

    public Character getSubject() {
        return subject;
    }

    public abstract void run(WorldProphet world);
}

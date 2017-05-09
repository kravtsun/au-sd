package ru.spbau.mit.visualizer;

import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;

public class PlayScreen extends Screen {
    PlayScreen() {
        super();
        logger.debug("Creating PlayScreen");
    }

    public void displayOutput(AsciiPanel terminal) {
        terminal.write("You are having fun.", 1, 1);
        terminal.writeCenter("-- press [escape] to lose or [enter] to win --", 22);
    }

    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()){
            case KeyEvent.VK_ESCAPE: return new LooseScreen();
            case KeyEvent.VK_ENTER: return new WinScreen();
        }

        return this;
    }
}
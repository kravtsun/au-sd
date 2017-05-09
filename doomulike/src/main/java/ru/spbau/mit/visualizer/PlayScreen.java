package ru.spbau.mit.visualizer;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;
import ru.spbau.mit.world.World;
import ru.spbau.mit.mapper.Map;

public class PlayScreen extends Screen {
//    private Supplier<Map> mapSupplier;

    // map to return if reset.
    private final Map defaultMap;
    private final World world;

    PlayScreen(Map defaultMap) {
        super();
        logger.debug("Creating PlayScreen");
        this.defaultMap = defaultMap;
        this.world = new World(defaultMap);
    }

    public void displayOutput(AsciiPanel terminal) {
        Visualizer visualizer = new Visualizer(world.getMap());
        visualizer.drawMap(terminal);
    }

    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()){
            case KeyEvent.VK_ESCAPE: return new LooseScreen(() -> new PlayScreen(defaultMap));
            case KeyEvent.VK_ENTER: return new WinScreen(() -> new PlayScreen(defaultMap));
            case KeyEvent.VK_Q: {
                logger.debug("Exiting");
                System.exit(0);
                break;
            }
            default: world.respondInput(key);
        }
        return this;
    }
}
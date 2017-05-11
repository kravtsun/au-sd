package ru.spbau.mit.visualizer;

import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.function.Supplier;

import asciiPanel.AsciiPanel;
import org.apache.logging.log4j.message.ExitMessage;
import ru.spbau.mit.world.Cartographer;
import ru.spbau.mit.world.World;
import ru.spbau.mit.mapper.Map;
import ru.spbau.mit.world.WorldProphet;

/**
 * main screen, where game is going on.
 * Created new PlayScreen at each new game session.
 */
public class PlayScreen extends Screen {
//    private Supplier<Map> mapSupplier;

    // map to return if reset.
    private final Supplier<Map> defaultMapSupplier;
    private final World world;

    PlayScreen(Supplier<Map> defaultMapSupplier) {
        super();
        logger.debug("Creating PlayScreen");
        this.defaultMapSupplier = defaultMapSupplier;
        this.world = new World(defaultMapSupplier.get());
    }

    public void displayOutput(AsciiPanel terminal) {
        Visualizer visualizer = new Visualizer(getCartographer().renderMap());
        visualizer.drawMap(terminal);
    }

    public Screen respondToUserInput(KeyEvent key) {
        try {
            int keyCode = key.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_ESCAPE:
                    return new LooseScreen(() -> new PlayScreen(defaultMapSupplier));
                case KeyEvent.VK_ENTER:
                    return new WinScreen(() -> new PlayScreen(defaultMapSupplier));
                case KeyEvent.VK_I:
                    return new StatusScreen(() -> this, getProphet().statusInfo());
                case KeyEvent.VK_Q: {
                    logger.debug("Exiting");
                    System.exit(0);
                    break;
                }
                default:
                    if (Arrays.stream(getProphet().availableCommands()).anyMatch((k) -> k == keyCode)) {
                        getProphet().respondInput(key);
                    } else {
                        logger.error("Unknown command: " + key.toString());
                    }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            System.exit(1);
        }
        return this;
    }

    Cartographer getCartographer() {
        return world;
    }

    WorldProphet getProphet() {
        return world;
    }
}
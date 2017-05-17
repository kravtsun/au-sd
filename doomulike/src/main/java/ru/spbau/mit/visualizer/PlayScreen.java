package ru.spbau.mit.visualizer;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import asciiPanel.AsciiPanel;
import org.jetbrains.annotations.NotNull;
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
        LOGGER.debug("Creating PlayScreen");
        this.defaultMapSupplier = defaultMapSupplier;
        this.world = new World(defaultMapSupplier.get());
    }

    public void displayOutput(AsciiPanel terminal) {
        Visualizer visualizer = new Visualizer(getCartographer().renderMap());
        visualizer.drawMap(terminal);
    }

    @NotNull
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
                case KeyEvent.VK_Q:
                    LOGGER.info("Exiting");
                    System.exit(0);
                    break;
                default:
                    if (getProphet().canAnswer(key)) {
                        getProphet().respondInput(key);
                    } else {
                        return super.respondToUserInput(key);
                    }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            System.exit(1);
        }
        return this;
    }

    @Override
    public List<UserCommand> getUserCommands() {
        List<UserCommand> result = new ArrayList<>();
        result.addAll(getProphet().availableUserCommands());
        result.add(new UserCommand("enter", "to win."));
        result.add(new UserCommand("escape", "to loose."));
        result.add(new UserCommand("i", "status."));
        result.add(new UserCommand("q", "exit."));
        result.addAll(super.getUserCommands());
        return result;
    }

    private Cartographer getCartographer() {
        return world;
    }

    private WorldProphet getProphet() {
        return world;
    }
}

package ru.spbau.mit.visualizer;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import asciiPanel.AsciiPanel;
import org.jetbrains.annotations.NotNull;
import ru.spbau.mit.world.Cartographer;
import ru.spbau.mit.world.Player;
import ru.spbau.mit.world.World;
import ru.spbau.mit.mapper.Map;

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
                    return new StatusScreen(() -> this, getPlayer());
                case KeyEvent.VK_Q:
                    LOGGER.info("Exiting");
                    System.exit(0);
                    break;
                default:
                    if (world.canAnswer(key)) {
                        world.respondInput(key);
                    } else {
                        return super.respondToUserInput(key);
                    }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            System.exit(1);
        }
        final Player player = world.getPlayer();
        if (Objects.isNull(player)) {
            throw new IllegalStateException("Player is null");
        }

        if (!player.isAlive()) {
            return new LooseScreen(() -> new PlayScreen(defaultMapSupplier));
        }
        return this;
    }

    @Override
    public List<UserCommand> getUserCommands() {
        final Player player = world.getPlayer();
        if (Objects.isNull(player)) {
            return Collections.emptyList();
        }
        List<UserCommand> result = player.availableUserCommands();
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

    private Player getPlayer() {
        return world.getPlayer();
    }
}

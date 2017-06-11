package ru.spbau.mit.visualizer;

import asciiPanel.AsciiPanel;
import org.jetbrains.annotations.NotNull;
import ru.spbau.mit.common.TerminalPrintable;
import ru.spbau.mit.world.Character;
import ru.spbau.mit.world.Item;
import ru.spbau.mit.world.Player;

import java.awt.event.KeyEvent;
import java.util.function.Supplier;

/**
 * Screen for displaying current information about player and
 * putting on and taking off items in inventory.
 */
public class StatusScreen extends LeafScreen {
    private int inventoryCursor;
    private final Player player;

    public StatusScreen(Supplier<Screen> baseScreenSupplier, Player player) {
        super(baseScreenSupplier);
        this.inventoryCursor = 0;
        this.player = player;
        LOGGER.debug("Creating StatusScreen");
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.clear();
        resetLineWriter();
        writeLine(terminal, "Status: ");
        writeLine(terminal, "Player: " + player.getName());
        writeLine(terminal, "Inventory: ");
        final Character.Inventory inventory = player.getInventory();
        for (int i = 0; i < inventory.size(); ++i) {
            final StringBuilder sb = new StringBuilder();
            sb.append(inventoryCursor == i ? "- " : "  ");
            final Item item = inventory.get(i);
            sb.append(player.isPutItem(i) ? "[*]" : "[ ]");
            sb.append(item.str());
            writeLine(terminal, sb.toString());
        }
        writeLine(terminal, "Characteristics: ");
        final TerminalPrintable charactersticsPrintable = player.getCharacteristics();
        for (String s : charactersticsPrintable.strs()) {
            writeLine(terminal, s);
        }
        writeLine(terminal, "-- navigate with j(down) or k(up) to choose an inventory item --");
        writeLine(terminal, "-- press space to put on or take off an inventory item --");
        writeLine(terminal, "-- press [enter] to resume --");
    }

    @Override
    @NotNull
    public Screen respondToUserInput(KeyEvent key) {
        final int keyCode = key.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_ENTER:
                return getBaseScreen();
            case KeyEvent.VK_K:
                updateInventoryCursor(inventoryCursor - 1);
                break;
            case KeyEvent.VK_J:
                updateInventoryCursor(inventoryCursor + 1);
                break;
            case KeyEvent.VK_SPACE:
                player.toggleItem(inventoryCursor);
                break;
            default:
                return super.respondToUserInput(key);
        }
        return this;
    }

    private void updateInventoryCursor(final int newInventoryCursor) {
        if (newInventoryCursor >= 0 && newInventoryCursor < player.getInventory().size()) {
            inventoryCursor = newInventoryCursor;
        }
    }
}

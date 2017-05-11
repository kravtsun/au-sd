package ru.spbau.mit.visualizer;

import asciiPanel.AsciiPanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.spbau.mit.common.Pair;
import ru.spbau.mit.mapper.Map;

public class Visualizer {
    private static final Logger LOGGER = LogManager.getLogger("Visualizer");
    private final Map map;

    /**
     * creates visualizer for given map (with objects' tiles already placed appropriately).
     * @param map Tiles container.
     */
    public Visualizer(Map map) {
        // too much output on the following line as any movement produces a new Visualizer.
//        LOGGER.debug("Creating Visualizer");
        this.map = map;
    }

    /**
     * Draw current map in terminal.
     * @param terminal destination terminal.
     */
    public void drawMap(AsciiPanel terminal) {
        final int screenHeight = terminal.getHeightInCharacters();
        final int screenWidth = terminal.getWidthInCharacters();

        if (screenHeight < map.height() || screenWidth < map.width()) {
            Pair<Integer, Integer> mapSize = new Pair<>(map.width(), map.height());
            Pair<Integer, Integer> terminalSize = new Pair<>(screenWidth, screenHeight);
            String errorMessage = "Map with sizes: " + mapSize.str()
                    + " does not fit into terminal with sizes: " + terminalSize.str();
            LOGGER.error(errorMessage);
            throw new IllegalArgumentException();
        }

        // ybase, xbase - for adding equal borders around map if map doesn't fit into screen.
        final int ybase = -(screenHeight - map.height()) / 2;
        final int xbase = -(screenWidth - map.width()) / 2;
        for (int sy = 0; sy < screenHeight; ++sy) {
            for (int sx = 0; sx < screenWidth; ++sx) {
                int x = sx + xbase;
                int y = sy + ybase;
                terminal.write(map.glyph(x, y), sx, sy, map.color(x, y));
            }
        }
    }
}

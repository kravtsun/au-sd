package ru.spbau.mit.visualizer;

import java.awt.event.KeyEvent;
import java.util.function.Supplier;

import asciiPanel.AsciiPanel;
import ru.spbau.mit.mapper.Map;

public class PlayScreen extends Screen {
//    private Supplier<Map> mapSupplier;

    // map to return if reset.
    private final Map defaultMap;

    // current map for redraw.
    private Map map;

    PlayScreen(Map defaultMap) {
        super();
        logger.debug("Creating PlayScreen");
        this.defaultMap = defaultMap;
        this.map = defaultMap;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public void displayOutput(AsciiPanel terminal) {
        Visualizer visualizer = new Visualizer(map);
        visualizer.drawMap(terminal);
    }

    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()){
            case KeyEvent.VK_ESCAPE: return new LooseScreen(() -> new PlayScreen(defaultMap));
            case KeyEvent.VK_ENTER: return new WinScreen(() -> new PlayScreen(defaultMap));
            case KeyEvent.VK_F10: System.exit(0);
        }
        return this;
    }


}
package ru.spbau.mit.visualizer;

import java.util.function.Supplier;

public abstract class LeafScreen extends Screen {
    Supplier<Screen> baseScreenSupplier;

    LeafScreen(Supplier<Screen> baseScreenSupplier) {
        setBaseScreenSupplier(baseScreenSupplier);
    }

    public Screen getBaseScreen() {
        return baseScreenSupplier.get();
    }

    public void setBaseScreenSupplier(Supplier<Screen> baseScreenSupplier) {
        this.baseScreenSupplier = baseScreenSupplier;
    }
}

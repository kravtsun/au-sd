package ru.spbau.mit.visualizer;

import java.util.function.Supplier;

/**
 * Accompanying screen which has no power over creating other screens -
 * it can just go back (by contract) to screen created via given supplier.
 */
public abstract class LeafScreen extends Screen {
    private final Supplier<Screen> baseScreenSupplier;

    /***
     * supplies with screen to which this screen will transfer the control.
     * @param baseScreenSupplier {@link Screen} supplier.
     */
    LeafScreen(Supplier<Screen> baseScreenSupplier) {
        this.baseScreenSupplier = baseScreenSupplier;
    }

    public Screen getBaseScreen() {
        return baseScreenSupplier.get();
    }
}

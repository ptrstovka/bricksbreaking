package sk.tuke.gamestudio.game.bricksbreaking.stovka.ui.console;

import java.util.ArrayList;
import java.util.List;

public class ConsoleMessageBag {

    private final List<String> items = new ArrayList<>();

    public void flash(String message) {
        items.add(message);
    }

    // Nothing is inverted.
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void render(Console console) {
        items.forEach(console::w);
        items.clear();

        try {
            Thread.sleep(2500);
        } catch (InterruptedException ignore) { }
    }

}

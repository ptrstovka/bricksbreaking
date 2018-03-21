package com.peterstovka.universe.bricksbreaking;

import com.peterstovka.universe.bricksbreaking.orm.Database;
import com.peterstovka.universe.bricksbreaking.ui.console.ConsoleMenu;

public class Application {

    public static void main(String[] args) {
        new Application().run();
    }

    /**
     * Runs the Application.
     */
    private void run() {
        onPreRun();

        onRun();

        onPostRun();
    }

    private void onRun() {
        new ConsoleMenu();
    }

    private void onPreRun() {
        Database.instance().open();
    }

    private void onPostRun() {
        Database.instance().close();
    }

}

package com.peterstovka.universe.bricksbreaking.ui.console;

import com.peterstovka.universe.bricksbreaking.foundation.Strings;

import java.util.Scanner;

import static com.peterstovka.universe.bricksbreaking.foundation.Lists.times;
import static java.util.stream.Collectors.joining;

public class Console {

    private static Console instance;

    public static Console instance() {
        if (instance == null) {
            instance = new Console();
        }

        return instance;
    }

    private Console() {
        // no -op
    }

    public String waitForInput(String challenge) {
        if (challenge != null) {
            System.out.print(challenge);
        }
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public void enterToExit() {
        emptyLine();
        emptyLine();
        waitForInput("Press enter to exit.");
    }

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void render(String content) {
        System.out.println(content);
    }

    public void emptyLine() {
        System.out.println();
    }

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_WHITE = "\u001B[37m";
    private static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    private static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    private static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";

    public void e(String message) {
        e(message, true);
    }

    public void d(String message) {
        d(message, true);
    }

    public void w(String message) {
        w(message, true);
    }

    // It is public API that can be used, no reason to make it private.
    @SuppressWarnings("WeakerAccess")
    public void e(String message, boolean emoji) {
        println(ANSI_RED_BACKGROUND, ANSI_RED, message, emoji);
    }

    public void d(String message, boolean emoji) {
        println(ANSI_GREEN_BACKGROUND, ANSI_WHITE, message, emoji);
    }

    public void w(String message, boolean emoji) {
        println(ANSI_YELLOW_BACKGROUND, ANSI_BLACK, message, emoji);
    }

    private void println(String background, String font, String message, boolean emoji) {
        String fix = times(" ", 10).stream().collect(joining());
        String result = Strings.f("%s%s%s", fix, message, fix);
        int length = emoji ? result.length() - 1 : result.length();
        String before = times(" ", length).stream().collect(joining());
        render(background + font+ before + ANSI_RESET);
        System.out.println(background + font+ result + ANSI_RESET);
        System.out.println(background + font+ before + ANSI_RESET);
    }

}

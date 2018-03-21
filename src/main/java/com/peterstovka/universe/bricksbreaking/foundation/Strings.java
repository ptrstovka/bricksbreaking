package com.peterstovka.universe.bricksbreaking.foundation;

import java.util.Locale;

/**
 * String helpers.
 */
public class Strings {

    /**
     * Formats the given string.
     * @param format the string format
     * @param args format arguments
     * @return formatted string
     */
    public static String f(String format, Object... args) {
        return String.format(Locale.getDefault(), format, args);
    }

    public static String firstUpperCase(String str) {
        if (str.isEmpty()) {
            return str;
        }

        return f("%s%s", str.substring(0, 1).toUpperCase(), str.substring(1, str.length()));
    }

}

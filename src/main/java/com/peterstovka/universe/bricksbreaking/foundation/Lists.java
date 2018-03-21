package com.peterstovka.universe.bricksbreaking.foundation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Lists {

    private static final Random random = new Random();

    public static <T> T randomElement(List<T> collection) {
        return collection.get(random.nextInt(collection.size()));
    }

    public static <T> List<T> copy(List<T> collection) {
        return new ArrayList<>(collection);
    }

    public static <T> List<T> times(T value, int count) {
        List<T> items = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            items.add(value);
        }
        return items;
    }

    public static <T> T first(List<T> list) {
        if (list.size() > 0) {
            return list.get(0);
        }

        return null;
    }

    @SafeVarargs
    public static <T> List<T> collect(T... args) {
        return Arrays.asList(args);
    }

}

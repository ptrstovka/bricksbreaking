package test;

import java.util.List;

import static org.junit.Assert.*;

public class ListAssertions {

    public static <T> void assertContentEqual(List<T> first, List<T> second) {
        assertNotNull(first);
        assertNotNull(second);

        assertEquals(first.size(), second.size());

        first.forEach(it -> assertTrue(second.contains(it)));
    }

    public static <T> void assertEmpty(List<T> list) {
        assertNotNull(list);
        assertEquals(0, list.size());
    }

    public static <T> void assertNotEmpty(List<T> list) {
        assertNotNull(list);
        assertNotEquals(0, list.size());
    }

    public static <T> void assertListContains(List<T> list, ListItemChecker<T> checker) {
        list.forEach(it -> assertTrue(checker.contains(it)));
    }

    public interface ListItemChecker<T> {

        boolean contains(T itm);

    }

}

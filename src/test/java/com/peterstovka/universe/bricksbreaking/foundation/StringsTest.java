package com.peterstovka.universe.bricksbreaking.foundation;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StringsTest {

    @Test
    public void should_format_string() {
        String format = Strings.f("serus %s, %d", "peter", 10);

        assertEquals(format, "serus peter, 10");
    }

}

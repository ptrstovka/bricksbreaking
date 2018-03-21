package com.peterstovka.universe.bricksbreaking.foundation;

import com.peterstovka.universe.bricksbreaking.foundation.exception.InvalidArgumentException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ColorTest {

    @Test
    public void should_create_color_instance_wihout_alpha() {
        Color color = new Color(148, 168, 188);

        assertEquals(148, color.getRed());
        assertEquals(168, color.getGreen());
        assertEquals(188, color.getBlue());
        assertEquals(1, color.getAlpha(), 0);
    }

    @Test
    public void should_create_color_instance_with_alpha() {
        Color color = new Color(148, 168, 188, 0.4f);

        assertEquals(148, color.getRed());
        assertEquals(168, color.getGreen());
        assertEquals(188, color.getBlue());
        assertEquals(0.4f, color.getAlpha(), 0);
    }

    @Test(expected = InvalidArgumentException.class)
    public void should_throw_exception_when_invalid_alpha() {
        new Color(0, 0, 0, 2f);
    }

    @Test(expected = InvalidArgumentException.class)
    public void should_throw_exception_when_invalid_red_component_negative() {
        new Color(-1, 0, 0);
    }

    @Test(expected = InvalidArgumentException.class)
    public void should_throw_exception_when_invalid_green_component_negative() {
        new Color(0, -1, 0);
    }

    @Test(expected = InvalidArgumentException.class)
    public void should_throw_exception_when_invalid_blue_component_negative() {
        new Color(0, 0, -1);
    }

    @Test(expected = InvalidArgumentException.class)
    public void should_throw_exception_when_invalid_red_component_positive() {
        new Color(1000, 0, 0);
    }

    @Test(expected = InvalidArgumentException.class)
    public void should_throw_exception_when_invalid_green_component_positive() {
        new Color(0, 1000, 0);
    }

    @Test(expected = InvalidArgumentException.class)
    public void should_throw_exception_when_invalid_blue_component_positive() {
        new Color(0, 0, 1000);
    }

}

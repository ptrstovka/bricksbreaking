package com.peterstovka.universe.bricksbreaking.foundation;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PositionTest {

    @Test
    public void should_create_position_object_with_values() {
        Position position = new Position(10, 20);

        assertEquals(10, position.getX());
        assertEquals(20, position.getY());
    }

    @Test
    public void should_update_position_values() {
        Position position = new Position(10, 20);
        position.set(30, 40);

        assertEquals(30, position.getX());
        assertEquals(40, position.getY());
    }

}

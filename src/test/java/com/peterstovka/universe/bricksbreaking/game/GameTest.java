package com.peterstovka.universe.bricksbreaking.game;

import com.peterstovka.universe.bricksbreaking.foundation.Position;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GameTest {

    @Test
    public void should_be_position_valid() {
        Game game = new Game(null, null);

        assertTrue(game.isPositionValid(position(0, 0)));
        assertTrue(game.isPositionValid(position(1, 1)));
        assertTrue(game.isPositionValid(position(1, 2)));
        assertTrue(game.isPositionValid(position(4, 1)));
        assertTrue(game.isPositionValid(position(10, 12)));
    }

    @Test
    public void should_not_be_position_valid() {
        Game game = new Game(null, null);

        assertFalse(game.isPositionValid(position(-1, -1)));
        assertFalse(game.isPositionValid(position(15, 15)));
    }

    private Position position(int x, int y) {
        return new Position(x, y);
    }

}

package com.peterstovka.universe.bricksbreaking.game.board;

import com.peterstovka.universe.bricksbreaking.foundation.Color;
import com.peterstovka.universe.bricksbreaking.foundation.Lists;
import com.peterstovka.universe.bricksbreaking.foundation.Position;
import org.junit.Test;

import java.util.List;

import static test.ListAssertions.*;

public class ShapeFinderTest {

    @Test
    public void should_not_find_any_tiles() {
        Tile[][] tiles = {
            { t(), t(), r() },
            { g(), b(), g() },
            { r(), r(), g() }
        };

        Board board = new Board(tiles);

        ShapeFinder shapeFinder = new ShapeFinder(board);

        List<Position> positions = shapeFinder.findConnected(0, 0);

        assertEmpty(positions);
    }

    @Test
    public void should_find_connected() {
        Tile[][] tiles = {
                { t(), t(), r() },
                { g(), b(), g() },
                { r(), r(), g() }
        };

        Board board = new Board(tiles);

        ShapeFinder shapeFinder = new ShapeFinder(board);

        List<Position> positions = shapeFinder.findConnected(2, 1);

        assertNotEmpty(positions);

        List<Position> foundPositions = Lists.collect(
            position(2, 1),
            position(2, 2)
        );

        assertContentEqual(positions, foundPositions);

        ShapeFinder anotherFinder = new ShapeFinder(board);

        List<Position> anotherPositions = anotherFinder.findConnected(0, 2);

        assertNotEmpty(anotherPositions);

        List<Position> anotherFoundPositions = Lists.collect(
                position(0, 2),
                position(1, 2)
        );

        assertContentEqual(anotherPositions, anotherFoundPositions);
    }

    private Position position(int x, int y) {
        return new Position(x, y);
    }

    private Tile r() {
        return new Tile(Color.RED);
    }

    private Tile g() {
        return new Tile(Color.GREEN);
    }

    private Tile b() {
        return new Tile(Color.BLUE);
    }

    private Tile t() {
        return new Tile(Color.TRANSPARENT);
    }

}

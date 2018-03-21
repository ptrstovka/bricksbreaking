package com.peterstovka.universe.bricksbreaking.game.board;

import com.peterstovka.universe.bricksbreaking.foundation.Color;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class BoardJoinerTest {

    @Test
    public void should_join_tiles_vertically() {
        Tile[][] tiles = {
                { r(), r(), r() },
                { t(), t(), g() },
                { r(), r(), t() }
        };

        Board board = new Board(tiles);

        BoardJoiner joiner = new BoardJoiner(board);
        joiner.join();

        Tile[][] joinedTiles = {
                { t(), t(), t() },
                { r(), r(), r() },
                { r(), r(), g() }
        };

        assertArrayEquals(joinedTiles, tiles);
    }

    @Test
    public void should_join_tiles_horizontally() {
        Tile[][] tiles = {
                { t(), t(), r() },
                { t(), r(), g() },
                { t(), r(), r() }
        };

        Board board = new Board(tiles);

        BoardJoiner joiner = new BoardJoiner(board);
        joiner.join();

        Tile[][] joinedTiles = {
                { t(), r(), t() },
                { r(), g(), t() },
                { r(), r(), t() }
        };

        assertArrayEquals(joinedTiles, tiles);
    }

    @Test
    public void should_center_tiles() {
        Tile[][] tiles = {
                { r(), g(), b(), t(), t() },
                { r(), g(), b(), t(), t() },
                { r(), g(), b(), t(), t() },
                { r(), g(), b(), t(), t() },
                { r(), g(), b(), t(), t() },
        };

        Board board = new Board(tiles);

        BoardJoiner joiner = new BoardJoiner(board);
        joiner.join();

        Tile[][] joinedTiles = {
                { t(), r(), g(), b(), t() },
                { t(), r(), g(), b(), t() },
                { t(), r(), g(), b(), t() },
                { t(), r(), g(), b(), t() },
                { t(), r(), g(), b(), t() },
        };

        assertArrayEquals(joinedTiles, tiles);
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

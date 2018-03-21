package com.peterstovka.universe.bricksbreaking.game.board;

import com.peterstovka.universe.bricksbreaking.foundation.Position;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BoardTest {

    @Test
    public void should_generate_tiles() {
        Board board = new Board(3, 3);

        board.generate();

        for (int y = 0; y < board.getHeight(); y++) {
            for (int x = 0; x < board.getHeight(); x++) {
                Tile tile = board.getTiles()[y][x];
                assertNotNull(tile);
                assertNotEquals(tile, Tile.EMPTY);
            }
        }
    }

    @Test
    public void should_copy_board() {
        Board board = new Board(3, 3);
        board.generate();

        Board copy = board.copy();

        assertNotSame(copy, board);

        for (int y = 0; y < board.getHeight(); y++) {
            for (int x = 0; x < board.getHeight(); x++) {
                assertEquals(board.getTiles()[y][x], copy.getTiles()[y][x]);
            }
        }
    }

    @Test
    public void should_be_empty() {
        Board board = new Board(3, 3);

        for (int y = 0; y < board.getHeight(); y++) {
            for (int x = 0; x < board.getHeight(); x++) {
                board.getTiles()[y][x] = Tile.EMPTY;
            }
        }

        assertTrue(board.isEmpty());
    }

    @Test
    public void should_not_be_empty() {
        Board board = new Board(3, 3);

        board.generate();

        assertFalse(board.isEmpty());
    }

    @Test
    public void should_remove_from_tiles() {

        Board board = new Board(3, 3);

        board.generate();

        List<Position> positions = new ArrayList<>();

        for (int y = 0; y < board.getHeight(); y++) {
            for (int x = 0; x < board.getHeight(); x++) {
                positions.add(new Position(x, y));
            }
        }

        assertFalse(board.isEmpty());

        board.remove(positions);

        assertTrue(board.isEmpty());

    }
}

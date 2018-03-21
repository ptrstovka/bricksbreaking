package com.peterstovka.universe.bricksbreaking.game.board;

import com.peterstovka.universe.bricksbreaking.foundation.Color;

import java.util.Objects;

/**
 * Represents one block of matrix array.
 * The Tile can have color and position.
 */
public class Tile {

    public static final Tile EMPTY = new Tile(Color.TRANSPARENT);

    /**
     * Determines how the tile should be colored.
     */
    private Color color;

    Tile(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Tile tile = (Tile) object;
        return Objects.equals(color, tile.color);
    }

    @Override
    public int hashCode() {

        return Objects.hash(color);
    }
}

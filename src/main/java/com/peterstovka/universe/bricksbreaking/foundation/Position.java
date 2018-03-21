package com.peterstovka.universe.bricksbreaking.foundation;

import java.util.Objects;

/**
 * Determines the position within two dimensional matrix.
 */
public class Position {

    /**
     * Horizontal position.
     */
    private int x;

    /**
     * Vertical position.
     */
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Set the position values
     * @param x horizontal axis
     * @param y vertical axis
     */
    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return Strings.f("[%d:%d]", getX(), getY());
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Position position = (Position) object;
        return x == position.x &&
                y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}

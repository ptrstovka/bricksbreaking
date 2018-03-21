package com.peterstovka.universe.bricksbreaking.game.board;

import com.peterstovka.universe.bricksbreaking.foundation.Color;
import com.peterstovka.universe.bricksbreaking.foundation.Lists;
import com.peterstovka.universe.bricksbreaking.foundation.Position;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private Tile[][] tiles;

    private int width;
    private int height;

    public Board(Tile[][] tiles) {
        this.tiles = tiles;
        this.width = tiles[0].length;
        this.height = tiles.length;
    }

    public Board(int width, int height) {
        this.tiles = new Tile[height][width];
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public Board copy() {
        Board board = new Board(width, height);
        for (int y = 0; y < height; y++) {
            System.arraycopy(tiles[y], 0, board.tiles[y], 0, width);
        }

        return board;
    }

    public void generate() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Tile tile = new Tile(Lists.randomElement(this.getColors()));
                tiles[y][x] = tile;
            }
        }
    }

    public void remove(List<Position> positions) {
        positions.forEach(position -> tiles[position.getY()][position.getX()] = Tile.EMPTY);
    }

    public boolean isEmpty() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (! Tile.EMPTY.equals(tiles[y][x])) {
                    return false;
                }
            }
        }

        return true;
    }

    // For dev purposes.
    @SuppressWarnings("unused")
    public void dump() {
        System.out.println();
        System.out.println("---------- BOARD DUMP -----------");
        for (Tile[] aPicture : tiles) {
            for (int j = 0; j < tiles[0].length; j++) {
                System.out.print(aPicture[j].getColor().dump() + "\t");
            }
            System.out.println();
        }
        System.out.println("---------------------------------");
        System.out.println();
    }

    private List<Color> colors;

    private List<Color> getColors() {
        if (colors != null) {
            return colors;
        }

        colors = new ArrayList<>();

        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.BLUE);

        return colors;
    }

}

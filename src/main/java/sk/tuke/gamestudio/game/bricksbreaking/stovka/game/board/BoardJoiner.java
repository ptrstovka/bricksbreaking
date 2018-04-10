package sk.tuke.gamestudio.game.bricksbreaking.stovka.game.board;

import java.util.Stack;

public class BoardJoiner {

    private Board board;

    public BoardJoiner(Board board) {
        this.board = board;
    }

    public void join() {
        Tile[][] picture = board.getTiles();
        joinVertical(picture);
        joinHorizontal(picture);
        center(picture);
    }

    private void joinVertical(Tile picture[][]) {
        for (int x = 0; x < board.getWidth(); x++) {

            Stack<Tile> items = new Stack<>();
            for (int y = 0; y < board.getHeight(); y++) {
                Tile el = picture[y][x];

                if (! Tile.EMPTY.equals(el)) {
                    items.push(el);
                }

                picture[y][x] = Tile.EMPTY;
            }


            for (int y = board.getHeight() - 1; y >= 0; y--) {
                if (items.empty()) {
                    break;
                }

                picture[y][x] = items.pop();
            }
        }
    }

    private void moveToLeft(Tile[][] picture, int emptyColumn) {
        for (int x = emptyColumn; x < board.getWidth() - 1; x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                picture[y][x] = picture[y][x + 1];
                picture[y][x + 1] = Tile.EMPTY;
            }
        }
    }

    private boolean isColumnEmpty(Tile[][] picture, int column) {
        for (int y = 0; y < board.getHeight(); y++) {
            Tile item = picture[y][column];
            if (! Tile.EMPTY.equals(item)) {
                return false;
            }
        }

        return true;
    }

    private void joinHorizontal(Tile[][] picture) {
        for (int x = 0; x < board.getWidth(); x++) {
            if (isColumnEmpty(picture, x)) {
                moveToLeft(picture, x);
            }
        }
    }

    private void center(Tile[][] picture) {
        joinHorizontal(picture);

        int rightSpace = 0;

        for (int x = board.getWidth() - 1; x >= 0; x--) {
            if (isColumnEmpty(picture, x)) {
                rightSpace++;
            } else {
                break;
            }
        }

        if (rightSpace > 1) {
            int steps = Math.round(rightSpace  / 2);
            for (int x = board.getWidth() - 1 - rightSpace; x >= 0; x--) {
                for (int y = 0; y < board.getHeight(); y++) {
                    picture[y][x + steps] = picture[y][x];
                    picture[y][x] = Tile.EMPTY;
                }
            }
        }
    }

}

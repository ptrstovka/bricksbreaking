package sk.tuke.gamestudio.game.bricksbreaking.stovka.game.board;

import sk.tuke.gamestudio.game.bricksbreaking.stovka.foundation.Color;
import sk.tuke.gamestudio.game.bricksbreaking.stovka.foundation.Lists;
import sk.tuke.gamestudio.game.bricksbreaking.stovka.foundation.Position;

import java.util.ArrayList;
import java.util.List;

public class ShapeFinder {

    private final List<Position> positions = new ArrayList<>();

    private Board board;

    public ShapeFinder(Board board) {
        this.board = board;
    }

    public List<Position> findConnected(int x, int y) {
        positions.clear();

        Tile[][] picture = board.copy().getTiles();
        apply(picture, getValueAt(picture, y, x), y, x);

        return Lists.copy(positions);
    }

    private void apply(Tile[][] picture, Color colorToReplace, int y, int x) {
        Color currentColor = getValueAt(picture, y, x);

        if (Color.TRANSPARENT.equals(currentColor)) {
            return;
        }

        if (colorToReplace.equals(currentColor)) {
            picture[y][x] = Tile.EMPTY;
            this.positions.add(new Position(x, y));
            apply(picture, colorToReplace, y + 1, x);
            apply(picture, colorToReplace, y - 1, x);
            apply(picture, colorToReplace, y, x + 1);
            apply(picture, colorToReplace, y, x - 1);
        }
    }

    private Color getValueAt(Tile[][] picture, int y, int x) {
        if (y < 0 || x < 0 || y > board.getHeight() - 1 || x > board.getWidth() - 1) {
            return null;
        } else {
            return picture[y][x].getColor();
        }
    }

}

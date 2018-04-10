package sk.tuke.gamestudio.game.bricksbreaking.stovka.ui.console;

import sk.tuke.gamestudio.game.bricksbreaking.stovka.foundation.Strings;
import sk.tuke.gamestudio.game.bricksbreaking.stovka.game.board.Board;
import sk.tuke.gamestudio.game.bricksbreaking.stovka.game.board.Tile;
import org.apache.commons.lang3.StringUtils;

public class ConsoleBoard {

    private Console console;
    private Board board;

    ConsoleBoard(Console console, Board board) {
        this.console = console;
        this.board = board;
    }

    public void render() {
        StringBuilder sb = new StringBuilder();

        Tile[][] matrix =  board.getTiles();

        int horizontalSize = (board.getWidth() * 3) + 4;

        sb.append("[y:x]");

        for (int i = 0; i < board.getWidth(); i++) {
            sb.append(Strings.f("%d  ", (i + 1) % 10));
        }

        sb.append("\n");

        for (int i = 0; i < horizontalSize; i++) {
            sb.append("-");
        }

        sb.append("\n");

        for (int y = 0; y < board.getHeight(); y++) {
            sb.append('[');
            sb.append(StringUtils.leftPad(Strings.f("%d", y + 1 % 10), 2));
            sb.append(']');

            sb.append("|");
            for (int x = 0; x < board.getWidth(); x++) {
                Tile item = matrix[y][x];

                if (item != null) {
                    sb.append(Strings.f("%s", item.getColor().dump()));
                } else {
                    sb.append(" ");
                }


                if (x != board.getWidth() - 1) {
                    sb.append("  ");
                }
            }

            sb.append("|");
            sb.append("\n");
        }

        for (int i = 0; i < horizontalSize; i++) {
            sb.append("-");
        }

        sb.append("\n");

        console.render(sb.toString());
    }

}

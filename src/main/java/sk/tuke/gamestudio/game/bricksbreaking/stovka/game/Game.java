package sk.tuke.gamestudio.game.bricksbreaking.stovka.game;

import sk.tuke.gamestudio.game.bricksbreaking.stovka.foundation.Position;
import sk.tuke.gamestudio.game.bricksbreaking.stovka.game.board.Board;
import sk.tuke.gamestudio.game.bricksbreaking.stovka.game.board.BoardJoiner;
import sk.tuke.gamestudio.game.bricksbreaking.stovka.game.board.ShapeFinder;

import java.util.List;

public class Game implements InputHandler {

    private Board board;
    private GameRenderer renderer;
    private GameListener gameListener;

    public Game(GameRenderer renderer, GameListener gameListener) {
        this.renderer = renderer;
        this.gameListener = gameListener;
        this.board = new Board(15, 15);
        this.board.generate();
    }

    public void play() {
        renderer.init(this);
    }

    public boolean isPositionValid(Position position) {
        return position.getX() >= 0 &&
                position.getY() >= 0 &&
                position.getX() < board.getWidth() &&
                position.getY() < board.getHeight();
    }

    @Override
    public void onTileClicked(Position position) {
        ShapeFinder finder = new ShapeFinder(board);
        List<Position> positions = finder.findConnected(position.getX(), position.getY());
        board.remove(positions);
        BoardJoiner joiner = new BoardJoiner(board);
        joiner.join();

        gameListener.onTilesRemoved(positions.size());

        if (board.isEmpty()) {
            gameListener.onRoundEnded();
        }

        renderer.render(this);
    }

    public Board getBoard() {
        return board;
    }

    public void invalidate() {
        renderer.render(this);
    }

}

package sk.tuke.gamestudio.game.bricksbreaking.stovka.ui.console;

import sk.tuke.gamestudio.game.bricksbreaking.stovka.foundation.Position;
import sk.tuke.gamestudio.game.bricksbreaking.stovka.foundation.Strings;
import sk.tuke.gamestudio.game.bricksbreaking.stovka.game.*;
import sk.tuke.gamestudio.game.bricksbreaking.stovka.game.board.Board;
import sk.tuke.gamestudio.game.bricksbreaking.stovka.ui.RenderedGame;

import static sk.tuke.gamestudio.game.bricksbreaking.stovka.foundation.Lists.times;
import static java.util.stream.Collectors.joining;

public class ConsoleGame implements GameRenderer, RenderedGame, GameListener {

    private Game game;
    private Health health;
    private Score score;

    private InputHandler gameInput;

    private Console console;
    private ConsoleMessageBag messages;
    private ConsoleGameListener gameListener;

    ConsoleGame(ConsoleGameListener gameListener) {
        createGame();

        console = Console.instance();
        score = new Score();
        health = new Health();

        messages = new ConsoleMessageBag();
        this.gameListener = gameListener;
    }

    private void createGame() {
        game = new Game(this, this);
        gameInput = game;
    }

    private void endGame() {
        gameListener.onGameEnded(score.getScore());
    }

    @Override
    public void onRoundEnded() {
        score.recordRound();

        if (health.isAlive()) {
            createGame();
            start();
        } else {
            endGame();
        }
    }

    @Override
    public void onTilesRemoved(int count) {
        health.record(count);
        score.record(count);
    }

    @Override
    public void start() {
        game.play();
    }

    @Override
    public void init(Game game) {
        render(game);
    }

    @Override
    public void render(Game game) {
        if (! health.isAlive()) {
            endGame();
            return;
        }

        console.clearScreen();

        renderBoard(game.getBoard());
        renderHealth(health.getHealth());
        renderScore(score.getScore());

        if (! messages.isEmpty()) {
            console.emptyLine();
            messages.render(console);
            game.invalidate();
            return;
        }

        getPositionsFromUser();
    }

    private void renderScore(int score) {
        String scoreLabel = Strings.f("⚡\tSCORE\t%d", score);
        console.render(scoreLabel);
    }

    private void renderHealth(int health) {
        String healthLabel = Strings.f("😎\tHEALTH\t%s", times("❤️", health).stream().collect(joining("\t")));
        console.render(healthLabel);
    }

    private void renderBoard(Board board) {
        new ConsoleBoard(console, board).render();
    }

    private void getPositionsFromUser() {
        console.emptyLine();
        String input = console.waitForInput("> Enter the tile position (two nums with space delimiter): ");

        if ("exit".equals(input)) {
            endGame();
            return;
        }

        Position position = parsePositionInput(input);

        if (position != null) {
            if (! game.isPositionValid(position)) {
                messages.flash("The position is invalid. Look at the board. See those coords? \uD83D\uDE27");
                game.invalidate();
            } else {
                gameInput.onTileClicked(position);
            }
        } else {
            messages.flash("No, the format is wrong. Try x y with space. \uD83D\uDC7C");
            game.invalidate();
        }
    }

    private Position parsePositionInput(String input) {
        String pos = input.trim().replaceAll("[ ]{2,}", " ");
        if (pos.isEmpty()) {
            return null;
        }

        String[] coords = pos.split(" ");

        if (coords.length != 2) {
            return null;
        }

        int x,y;

        try {
            x =  Integer.valueOf(coords[0]);
        } catch(NumberFormatException e) {
            return null;
        }

        try {
            y =  Integer.valueOf(coords[1]);
        } catch(NumberFormatException e) {
            return null;
        }

        return new Position(x - 1, y - 1);
    }

}

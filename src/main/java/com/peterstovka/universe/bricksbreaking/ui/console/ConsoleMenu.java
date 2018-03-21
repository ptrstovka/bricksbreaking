package com.peterstovka.universe.bricksbreaking.ui.console;

import com.peterstovka.universe.bricksbreaking.commands.AddCommentCommand;
import com.peterstovka.universe.bricksbreaking.commands.AddRatingCommand;
import com.peterstovka.universe.bricksbreaking.commands.AddScoreCommand;
import com.peterstovka.universe.bricksbreaking.foundation.Lists;
import com.peterstovka.universe.bricksbreaking.game.Player;
import com.peterstovka.universe.bricksbreaking.service.Ratings;
import com.peterstovka.universe.bricksbreaking.service.Scores;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.CommentException;

import java.util.stream.Collectors;

import static com.peterstovka.universe.bricksbreaking.foundation.Strings.f;

public class ConsoleMenu implements ConsoleGameListener {

    private Console console;

    private ConsoleMessageBag messages;

    private boolean exit = false;

    private String logo;

    public ConsoleMenu() {
        console = Console.instance();
        messages = new ConsoleMessageBag();
        logo = HeaderHelper.getLogo();
        while (! exit) {
            render();
        }
        console.clearScreen();
    }

    private void renderHeader() {
        console.render(logo);

        Score score = getHighestScore();
        if (score != null) {
            console.render(f("\uD83C\uDFC6  \uD83C\uDFC6  \uD83C\uDFC6  The best score is [%d] by player [%s].  \uD83C\uDFC6  \uD83C\uDFC6  \uD83C\uDFC6", score.getPoints(), score.getPlayer()));
            console.emptyLine();
        }

        int rating = getRating();
        if (rating > 0) {
            String ratingLabel = Lists.times("⭐", rating).stream().collect(Collectors.joining("  ")).trim();
            console.render(f("\uD83D\uDE80  The rating of this game is %s.", ratingLabel));
            console.emptyLine();
        }

    }

    private void renderMenu() {
        console.d("Available commands \uD83D\uDE32");
        console.emptyLine();
        console.render("\tnew - starts a new game");
        console.render("\tscore - shows the leader board");
        console.render("\tcomments - shows get comments");
        console.render("\tcomments:add <comment> - adds a new comment");
        console.render("\trate <rating> - rate this game");
        console.render(f("\tname <name> - change the player name (current: %s)", Player.instance().getName()));
        console.render("\texit - exits the game");
        console.emptyLine();
    }

    private void render() {
        console.clearScreen();

        renderHeader();

        renderMenu();

        if (!messages.isEmpty()) {
            messages.render(console);
            return;
        }

        readCommand();
    }

    private void showNewGame() {
        new ConsoleGame(this).start();
    }

    private void showLeaderBoard() {
        new ConsoleScoreReader().open();
    }

    private void showComments() {
        new ConsoleCommentReader().open();
    }

    private Score getHighestScore() {
        return new Scores().getBestScore();
    }

    private int getRating() {
        return new Ratings().getRating();
    }

    private void setName(String name) {
        if (name.length() > 10) {
            messages.flash("The username could not have more than 10 characters. \uD83D\uDE20");
            return;
        }

        Player.instance().setName(name);
        messages.flash(f("Welcome, %s! \uD83C\uDF7B", name));
    }

    private void addRating(String rating) {
        try {
            int rate = Integer.valueOf(rating);

            if (rate > 5 || rate < 1) {
                messages.flash("The rating should be from 1 to 5. \uD83D\uDE20");
                return;
            }

            try {
                new AddRatingCommand(rate).run();
                messages.flash("Thank you for you rating. \uD83D\uDE07");
            } catch (Exception e) {
                messages.flash("We could not add rating right now. \uD83D\uDE20");
            }
        } catch (NumberFormatException e) {
            messages.flash("This is not a valid rating. \uD83D\uDE20");
        }
    }

    private void addComent(String comment) {
        try {
            new AddCommentCommand(comment).run();
            messages.flash("Comment posted. \uD83D\uDE0E");
        } catch (CommentException e) {
            messages.flash("We could not add comment right now. \uD83D\uDE20");
        }
    }

    private void readCommand() {
        String input = console.waitForInput("> Enter command: ").trim();

        if ("exit".equals(input)) {
            exit = true;
        } else if ("new".equals(input)) {
            showNewGame();
        } else if ("score".equals(input)) {
            showLeaderBoard();
        } else if ("comments".equals(input)) {
            showComments();
        } else if (input.startsWith("comments:add")) {
            addComent(input.replace("comments:add", "").trim());
        } else if (input.startsWith("rate")) {
            addRating(input.replace("rate", "").trim());
        } else if (input.startsWith("name")) {
            setName(input.replace("name", "").trim());
        } else {
            messages.flash("Wrong command \uD83D\uDC7C");
        }
    }

    @Override
    public void onGameEnded(int score) {
        if (score > 0) {
            new AddScoreCommand(score).run();
        }

        messages.flash(f("You scored %d! \uD83C\uDFC6", score));
    }
}

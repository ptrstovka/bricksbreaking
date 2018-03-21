package com.peterstovka.universe.bricksbreaking.ui.console;

import com.peterstovka.universe.BuildConfig;
import com.peterstovka.universe.bricksbreaking.foundation.Strings;
import com.peterstovka.universe.bricksbreaking.service.Scores;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.ScoreException;

import java.util.List;

public class ConsoleScoreReader {

    private Scores scores;
    private Console console;

    ConsoleScoreReader() {
        this.scores = new Scores();
        this.console = Console.instance();
    }

    public void open() {
        console.clearScreen();

        try {
            List<Score> scores = this.scores.getBestScores(BuildConfig.NAME);
            renderScores(scores);
        } catch (ScoreException e) {
            console.e("Whoops, we could not get the scores right now. \uD83D\uDE20");
            console.enterToExit();
        }
    }

    private void renderScores(List<Score> scores) {

        if (scores.isEmpty()) {
            console.w("This game does not have any leaderboard. Do you wanna be the first one with highest score? \uD83D\uDE07");
            console.enterToExit();
            return;
        }

        console.w("LEADER BOARD", false);

        console.emptyLine();

        for (int i = 0; i < scores.size(); i++) {
            Score score = scores.get(i);
            String label = Strings.f("%d. [%s] - %d", (i + 1), score.getPlayer(), score.getPoints());

            if (i == 0) {
                label = Strings.f("%s \uD83C\uDFC6", label);
            }

            console.render(label);
        }

        console.enterToExit();
    }

}

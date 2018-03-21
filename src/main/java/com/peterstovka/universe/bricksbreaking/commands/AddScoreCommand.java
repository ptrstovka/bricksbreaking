package com.peterstovka.universe.bricksbreaking.commands;

import com.peterstovka.universe.BuildConfig;
import com.peterstovka.universe.bricksbreaking.game.Player;
import com.peterstovka.universe.bricksbreaking.service.Scores;
import sk.tuke.gamestudio.entity.Score;

import java.util.Calendar;

public class AddScoreCommand implements Command {

    private int score;

    private Scores scores;

    public AddScoreCommand(int score) {
        this.score = score;
        scores = new Scores();
    }

    @Override
    public void run() {
        Score score = new Score(
                BuildConfig.NAME,
                Player.instance().getName(),
                this.score,
                Calendar.getInstance().getTime()
        );

        scores.addScore(score);
    }
}

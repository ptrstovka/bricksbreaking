package sk.tuke.gamestudio.game.bricksbreaking.stovka.commands;

import com.peterstovka.universe.BuildConfig;
import sk.tuke.gamestudio.game.bricksbreaking.stovka.game.Player;
import sk.tuke.gamestudio.game.bricksbreaking.stovka.service.Scores;
import sk.tuke.gamestudio.server.entity.Score;
import sk.tuke.gamestudio.server.service.ScoreService;

import java.util.Calendar;

public class AddScoreCommand implements Command {

    private int score;

    private Scores scores;

    public AddScoreCommand(ScoreService scoreService, int score) {
        this.score = score;
        scores = new Scores(scoreService);
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

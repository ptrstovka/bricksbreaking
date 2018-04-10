package sk.tuke.gamestudio.game.bricksbreaking.stovka.service;

import com.peterstovka.universe.BuildConfig;
import sk.tuke.gamestudio.game.bricksbreaking.stovka.foundation.Lists;
import sk.tuke.gamestudio.server.entity.Score;
import sk.tuke.gamestudio.server.service.ScoreException;
import sk.tuke.gamestudio.server.service.ScoreService;

import java.util.List;

public class Scores implements ScoreService {

    private ScoreService scoreService;

    public Scores(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @Override
    public void addScore(Score score) throws ScoreException {
        scoreService.addScore(score);
    }

    @Override
    public List<Score> getBestScores(String game) throws ScoreException {
        return scoreService.getBestScores(game);
    }

    public Score getBestScore() {
        return Lists.first(getBestScores(BuildConfig.NAME));
    }

}

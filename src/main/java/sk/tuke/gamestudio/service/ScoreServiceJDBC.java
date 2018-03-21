package sk.tuke.gamestudio.service;

import com.peterstovka.universe.bricksbreaking.orm.Database;
import sk.tuke.gamestudio.entity.Score;

import java.util.List;

public class ScoreServiceJDBC implements ScoreService {

    @Override
    public void addScore(Score score) throws ScoreException {
        Database.instance().insert(score);
    }

    @Override
    public List<Score> getBestScores(String game) throws ScoreException {
        return Database.instance()
                .select(Score.class)
                .where("game", "=", game)
                .sort("points", "DESC")
                .limit(10)
                .get(Score.class);
    }

}

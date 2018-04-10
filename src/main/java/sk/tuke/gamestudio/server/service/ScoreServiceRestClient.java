package sk.tuke.gamestudio.server.service;

import sk.tuke.gamestudio.server.entity.Score;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.List;

public class ScoreServiceRestClient extends HttpClient implements ScoreService {

    @Override
    public void addScore(Score score) throws ScoreException {
        post("/", "score", jsonEntity(score), Response.class);
    }

    @Override
    public List<Score> getBestScores(String game) throws ScoreException {
        return get("/", "score/" +  game, new GenericType<List<Score>>() {});
    }
}

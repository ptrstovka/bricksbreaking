package sk.tuke.gamestudio.server.service;

import org.springframework.transaction.annotation.Transactional;
import sk.tuke.gamestudio.server.entity.Score;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;

@Transactional
public class ScoreServiceJPA implements ScoreService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addScore(Score score) throws ScoreException {
        entityManager.persist(score);
    }

    @Override
    public List<Score> getBestScores(String game) throws ScoreException {
        List<Score> result = entityManager.createNamedQuery("Score.getBestScores")
                .setParameter("game", game).setMaxResults(10).getResultList();

        if (result == null) {
            return Collections.emptyList();
        }

        return result;
    }
}

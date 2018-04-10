package sk.tuke.gamestudio.server.service;

import org.springframework.transaction.annotation.Transactional;
import sk.tuke.gamestudio.game.bricksbreaking.stovka.foundation.Lists;
import sk.tuke.gamestudio.server.entity.Rating;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Transactional
public class RatingServiceJPA implements RatingService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void setRating(Rating rating) throws RatingException {
        entityManager.persist(rating);
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        List<Rating> result = entityManager.createNamedQuery("Rating.getGameRating")
                .setParameter("game", game)
                .getResultList();

        if (result == null) {
            result = new ArrayList<>();
        }

        return (int) result.stream()
                .mapToInt(Rating::getRating)
                .average()
                .orElse(0);
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        Object result = entityManager.createNamedQuery("Rating.getRating")
                .setParameter("game", game)
                .setParameter("player", player)
                .getSingleResult();

        if (result instanceof Integer) {
            return (int) result;
        } else {
            return 0;
        }
    }
}

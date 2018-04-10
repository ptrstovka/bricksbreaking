package sk.tuke.gamestudio.game.bricksbreaking.stovka.service;

import com.peterstovka.universe.BuildConfig;
import sk.tuke.gamestudio.server.entity.Rating;
import sk.tuke.gamestudio.server.service.RatingException;
import sk.tuke.gamestudio.server.service.RatingService;

public class Ratings implements RatingService {

    private RatingService ratingService;

    public Ratings(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @Override
    public void setRating(Rating rating) throws RatingException {
        ratingService.setRating(rating);
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        return ratingService.getAverageRating(game);
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        return ratingService.getRating(game, player);
    }

    public int getRating() {
        try {
            return getAverageRating(BuildConfig.NAME);
        } catch (RatingException e) {
            return 0;
        }
    }

}

package sk.tuke.gamestudio.game.bricksbreaking.stovka.commands;

import com.peterstovka.universe.BuildConfig;
import sk.tuke.gamestudio.game.bricksbreaking.stovka.game.Player;
import sk.tuke.gamestudio.game.bricksbreaking.stovka.service.Ratings;
import sk.tuke.gamestudio.server.entity.Rating;
import sk.tuke.gamestudio.server.service.RatingService;

import java.util.Calendar;

public class AddRatingCommand implements Command {

    private int rating;

    private Ratings ratings;

    public AddRatingCommand(RatingService ratingService, int rating) {
        this.rating = rating;
        this.ratings = new Ratings(ratingService);
    }

    @Override
    public void run() throws Exception {
        Rating rate = new Rating(
                Player.instance().getName(),
                BuildConfig.NAME,
                rating,
                Calendar.getInstance().getTime()
        );

        ratings.setRating(rate);
    }
}

package sk.tuke.gamestudio.service;

import com.peterstovka.universe.bricksbreaking.foundation.Strings;
import com.peterstovka.universe.bricksbreaking.orm.Database;
import sk.tuke.gamestudio.entity.Rating;

import static com.peterstovka.universe.bricksbreaking.foundation.Lists.collect;

public class RatingServiceJDBC implements RatingService {

    @Override
    public void setRating(Rating rating) throws RatingException {
        Rating existingRating = Database.instance()
                .select(Rating.class)
                .where("player", "=", rating.getPlayer())
                .where("game", "=", rating.getGame())
                .first(Rating.class);

        if (existingRating == null) {
            Database.instance().insert(rating);
            return;
        }

        Database.instance().update(rating, collect("player", "game"));
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        return (int) Database.instance()
                .select(Rating.class)
                .get(Rating.class)
                .stream()
                .mapToInt(Rating::getRating)
                .average()
                .orElse(0);
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        Rating rating = Database.instance()
                .select(Rating.class)
                .where("game", "=", game)
                .where("player", "=", player)
                .first(Rating.class);

        if (rating == null) {
            throw new RatingException(Strings.f("The rating of game [%s] from [%s] does not exist.", game, player));
        }

        return rating.getRating();
    }
}

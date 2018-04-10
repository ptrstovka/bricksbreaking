package sk.tuke.gamestudio.server.service;

import sk.tuke.gamestudio.server.entity.Rating;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

public class RatingServiceRestClient extends HttpClient implements RatingService {

    @Override
    public void setRating(Rating rating) throws RatingException {
        post("/", "rating", jsonEntity(rating), Response.class);
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        return get("/", "rating/" + game, new GenericType<Integer>() {});
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        return get("/", "rating/" + game + "/" + player, new GenericType<Integer>() {});
    }
}

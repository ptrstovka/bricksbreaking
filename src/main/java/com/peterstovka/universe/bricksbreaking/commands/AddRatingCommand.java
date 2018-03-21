package com.peterstovka.universe.bricksbreaking.commands;

import com.peterstovka.universe.BuildConfig;
import com.peterstovka.universe.bricksbreaking.game.Player;
import com.peterstovka.universe.bricksbreaking.service.Ratings;
import sk.tuke.gamestudio.entity.Rating;

import java.util.Calendar;

public class AddRatingCommand implements Command {

    private int rating;

    private Ratings ratings;

    public AddRatingCommand(int rating) {
        this.rating = rating;
        this.ratings = new Ratings();
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

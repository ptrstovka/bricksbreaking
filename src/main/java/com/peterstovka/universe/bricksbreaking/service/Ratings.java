package com.peterstovka.universe.bricksbreaking.service;

import com.peterstovka.universe.BuildConfig;
import com.peterstovka.universe.bricksbreaking.foundation.Lists;
import com.peterstovka.universe.bricksbreaking.game.Player;
import com.peterstovka.universe.bricksbreaking.orm.Database;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.service.RatingException;
import sk.tuke.gamestudio.service.RatingServiceJDBC;

import java.util.Calendar;
import java.util.Date;

import static com.peterstovka.universe.bricksbreaking.foundation.Lists.collect;

public class Ratings extends RatingServiceJDBC {

    public int getRating() {
        try {
            return getAverageRating(BuildConfig.NAME);
        } catch (RatingException e) {
            return 0;
        }
    }

}

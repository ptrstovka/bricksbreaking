package com.peterstovka.universe.bricksbreaking.service;

import com.peterstovka.universe.BuildConfig;
import com.peterstovka.universe.bricksbreaking.orm.Database;
import org.junit.*;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;

import javax.xml.crypto.Data;
import java.util.Calendar;

import static com.peterstovka.universe.bricksbreaking.foundation.Lists.collect;
import static org.junit.Assert.assertEquals;

public class RatingsTest {

    @BeforeClass
    public static void before() {
        Database.instance().open();
        Database.instance().truncate(Comment.class, Score.class, Rating.class);
    }

    @AfterClass
    public static void after() {
        Database.instance().close();
    }

    private Ratings ratings;

    @Before
    public void setUp() {
        ratings = new Ratings();
    }

    @Test
    public void should_get_game_rating() {
        Database.instance().truncate(Rating.class);

        int rating = ratings.getRating();

        assertEquals(0, rating);

        collect(
                rating("r_player_2", 1),
                rating("r_player_3", 2),
                rating("r_player_4", 3),
                rating("r_player_5", 4),
                rating("r_player_6", 5)
        ).forEach(Database.instance()::insert);

        int currentRating = ratings.getRating();

        assertEquals(3, currentRating);
    }

    private Rating rating(String player, int rating) {
        return new Rating(player, BuildConfig.NAME, rating, Calendar.getInstance().getTime());
    }

}

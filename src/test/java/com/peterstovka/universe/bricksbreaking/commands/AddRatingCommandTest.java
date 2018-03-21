package com.peterstovka.universe.bricksbreaking.commands;

import com.peterstovka.universe.BuildConfig;
import com.peterstovka.universe.bricksbreaking.game.Player;
import com.peterstovka.universe.bricksbreaking.orm.Database;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AddRatingCommandTest {

    @BeforeClass
    public static void before() {
        Database.instance().open();
        Database.instance().truncate(Comment.class, Score.class, Rating.class);
    }

    @AfterClass
    public static void after() {
        Database.instance().close();
    }

    @Test
    public void should_add_rating_to_database() throws Exception {
        Player.instance().setName("db_player_2");

        new AddRatingCommand(3).run();

        Rating rating = Database.instance()
                .select(Rating.class)
                .where("player", "=", "db_player_2")
                .where("game", "=", BuildConfig.NAME)
                .first(Rating.class);

        assertNotNull(rating);

        assertEquals(3, rating.getRating());
    }
}

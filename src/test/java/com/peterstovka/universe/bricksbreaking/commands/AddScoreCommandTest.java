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

public class AddScoreCommandTest {

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
    public void should_set_score_in_database() {
        Player.instance().setName("db_player_3");

        new AddScoreCommand(54321).run();

        Score score = Database.instance()
                .select(Score.class)
                .where("player", "=", "db_player_3")
                .where("game", "=", BuildConfig.NAME)
                .first(Score.class);

        assertNotNull(score);

        assertEquals(54321, score.getPoints());
    }
}

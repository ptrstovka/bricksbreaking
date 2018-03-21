package com.peterstovka.universe.bricksbreaking.service;

import com.peterstovka.universe.BuildConfig;
import com.peterstovka.universe.bricksbreaking.orm.Database;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;

import java.util.Calendar;

import static com.peterstovka.universe.bricksbreaking.foundation.Lists.collect;
import static org.junit.Assert.*;

public class ScoresTest {

    @BeforeClass
    public static void before() {
        Database.instance().open();
        Database.instance().truncate(Comment.class, Score.class, Rating.class);
    }

    @AfterClass
    public static void after() {
        Database.instance().close();
    }

    private Scores scores;

    @Before
    public void setUp() {
        scores = new Scores();
    }

    @Test
    public void should_get_best_score() {
        Score score = scores.getBestScore();

        assertNull(score);

        collect(
                score("bs_player_1", 10),
                score("bs_player_2", 20),
                score("bs_player_3", 30),
                score("bs_player_4", 40),
                score("bs_player_5", 50),
                score("bs_player_6", 60),
                score("bs_player_7", 70),
                score("bs_player_8", 80),
                score("bs_player_9", 90),
                score("bs_player_10", 100),
                score("bs_player_11", 110),
                score("bs_player_12", 120)
        ).forEach(Database.instance()::insert);

        Score best = scores.getBestScore();

        assertNotNull(best);

        assertEquals("bs_player_12", best.getPlayer());
        assertEquals(BuildConfig.NAME, best.getGame());
        assertEquals(120, best.getPoints());

    }

    private Score score(String player, int score) {
        return new Score(BuildConfig.NAME, player, score, Calendar.getInstance().getTime());
    }

}

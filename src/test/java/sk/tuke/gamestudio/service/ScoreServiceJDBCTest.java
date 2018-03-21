package sk.tuke.gamestudio.service;

import com.peterstovka.universe.bricksbreaking.orm.Database;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;

import java.util.Calendar;
import java.util.List;

import static com.peterstovka.universe.bricksbreaking.foundation.Lists.collect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static test.ListAssertions.assertListContains;

public class ScoreServiceJDBCTest {

    @BeforeClass
    public static void before() {
        Database.instance().open();
        Database.instance().truncate(Comment.class, Score.class, Rating.class);
    }

    @AfterClass
    public static void after() {
        Database.instance().close();
    }

    private ScoreService scoreService;

    @Before
    public void setUp() {
        scoreService = new ScoreServiceJDBC();
    }

    @Test
    public void should_add_new_score() {
        Score score = score("best_player_1", "best_game_1", 12345);

        scoreService.addScore(score);

        Score record = Database.instance()
                .select(Score.class)
                .where("player", "=", "best_player_1")
                .where("game", "=", "best_game_1")
                .where("points", "=", 12345)
                .first(Score.class);

        assertNotNull(record);
        assertEquals("best_player_1", record.getPlayer());
        assertEquals("best_game_1", record.getGame());
        assertEquals(12345, record.getPoints());
    }

    @Test
    public void should_retrieve_best_scores() {
        collect(
                score("s_player_1", "s_game_1", 10),
                score("s_player_2", "s_game_1", 20),
                score("s_player_3", "s_game_1", 30),
                score("s_player_4", "s_game_1", 40),
                score("s_player_5", "s_game_1", 50),
                score("s_player_6", "s_game_1", 60),
                score("s_player_7", "s_game_1", 70),
                score("s_player_8", "s_game_1", 80),
                score("s_player_9", "s_game_1", 90),
                score("s_player_10", "s_game_1", 100),
                score("s_player_11", "s_game_1", 110),
                score("s_player_12", "s_game_1", 120)
        ).forEach(Database.instance()::insert);

        List<Integer> bestScores = collect(120, 110, 100, 90, 80, 70, 60, 50, 40, 30);

        List<Score> scores = scoreService.getBestScores("s_game_1");

        assertEquals(10, scores.size());

        assertListContains(scores, it -> bestScores.contains(it.getPoints()));
    }

    private Score score(String player, String game, int score) {
        return new Score(game, player, score, Calendar.getInstance().getTime());
    }

}

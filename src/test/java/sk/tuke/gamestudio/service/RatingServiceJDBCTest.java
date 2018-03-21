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

public class RatingServiceJDBCTest {

    @BeforeClass
    public static void before() {
        Database.instance().open();
        Database.instance().truncate(Comment.class, Score.class, Rating.class);
    }

    @AfterClass
    public static void after() {
        Database.instance().close();
    }

    private RatingService ratingService;

    @Before
    public void setUp() {
        ratingService = new RatingServiceJDBC();
    }

    @Test
    public void should_set_rating() throws Exception {
        Rating rating = rating("r_player_1", "r_game_1", 2);

        ratingService.setRating(rating);

        Rating record = Database.instance()
                .select(Rating.class)
                .where("player", "=", "r_player_1")
                .where("game", "=", "r_game_1")
                .where("rating", "=", 2)
                .first(Rating.class);

        assertNotNull(record);
        assertEquals(2, record.getRating());
        assertEquals("r_game_1", record.getGame());
        assertEquals("r_player_1", record.getPlayer());
    }

    @Test
    public void should_update_player_rating() throws Exception {
        Rating rating = rating("unique_player", "unique_game", 2);

        Database.instance().insert(rating);

        rating.setRating(5);

        ratingService.setRating(rating);

        List<Rating> records = Database.instance()
                .select(Rating.class)
                .where("player", "=", "unique_player")
                .where("game", "=", "unique_game")
                .get(Rating.class);

        assertNotNull(records);
        assertEquals(1, records.size());

        Rating item = records.get(0);

        assertNotNull(item);

        assertEquals("unique_player", item.getPlayer());
        assertEquals("unique_game", item.getGame());
        assertEquals(5, item.getRating());
    }

    @Test
    public void should_get_average_rating() throws Exception {
        collect(
                rating("r_player_2", "r_game_2", 1),
                rating("r_player_3", "r_game_2", 2),
                rating("r_player_4", "r_game_2", 3),
                rating("r_player_5", "r_game_2", 4),
                rating("r_player_6", "r_game_2", 5)
        ).forEach(Database.instance()::insert);

        int rating = ratingService.getAverageRating("r_game_2");

        assertEquals(3, rating);
    }

    @Test
    public void should_retrieve_rating() throws Exception {
        Rating rating = rating("r_player_x", "r_game_x", 5);
        Database.instance().insert(rating);

        int record = ratingService.getRating("r_game_x", "r_player_x");

        assertEquals(5, record);
    }

    private Rating rating(String player, String game, int rating) {
        return new Rating(player, game, rating, Calendar.getInstance().getTime());
    }

}

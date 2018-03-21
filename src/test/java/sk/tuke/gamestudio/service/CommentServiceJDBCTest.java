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

public class CommentServiceJDBCTest {

    @BeforeClass
    public static void before() {
        Database.instance().open();
        Database.instance().truncate(Comment.class, Score.class, Rating.class);
    }

    @AfterClass
    public static void after() {
        Database.instance().close();
    }

    private CommentService commentService;

    @Before
    public void setUp() {
        commentService = new CommentServiceJDBC();
    }

    @Test
    public void should_add_new_comment() throws Exception {
        Comment comment = comment(
                "test_player_1",
                "test_game_1",
                "This is a test comment."
        );

        commentService.addComment(comment);

        Comment record = Database.instance()
                .select(Comment.class)
                .where("player", "=", "test_player_1")
                .where("game", "=", "test_game_1")
                .where("comment", "=", "This is a test comment.")
                .first(Comment.class);

        assertNotNull(record);
        assertEquals("test_player_1", record.getPlayer());
        assertEquals("test_game_1", record.getGame());
        assertEquals("This is a test comment.", record.getComment());
    }

    @Test
    public void test_should_retrieve_one_comment() throws Exception {
        Comment comment = comment("test_player_3", "test_game_3", "comment");

        Database.instance().insert(comment);

        List<Comment> comments = commentService.getComments("test_game_3");

        assertEquals(1, comments.size());

        Comment first = comments.get(0);

        assertNotNull(first);

        assertEquals("test_player_3", first.getPlayer());
        assertEquals("comment", first.getComment());
        assertEquals("test_game_3", first.getGame());
    }

    @Test
    public void should_retrieve_comments_for_game() throws Exception {

        collect(
                comment("test_player_2", "test_game_2", "c1"),
                comment("test_player_3", "test_game_2", "c2"),
                comment("test_player_4", "test_game_2", "c3"),
                comment("test_player_5", "test_game_2", "c4"),
                comment("test_player_6", "test_game_2", "c5"),
                comment("test_player_7", "test_game_2", "c6"),
                comment("test_player_8", "test_game_2", "c7"),
                comment("test_player_9", "test_game_2", "c8"),
                comment("test_player_10", "test_game_2", "c9")
        ).forEach(Database.instance()::insert);

        List<Comment> comments = commentService.getComments("test_game_2");

        assertNotNull(comments);
        assertEquals(9, comments.size());

        comments.forEach(comment -> {
            assertNotNull(comment);
            assertEquals(comment.getGame(), "test_game_2");
        });
    }

    private Comment comment(String player, String game, String comment) {
        return new Comment(
                player,
                game,
                comment,
                Calendar.getInstance().getTime()
        );
    }

}

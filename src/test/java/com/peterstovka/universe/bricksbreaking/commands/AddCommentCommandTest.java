package com.peterstovka.universe.bricksbreaking.commands;

import com.peterstovka.universe.BuildConfig;
import com.peterstovka.universe.bricksbreaking.game.Player;
import com.peterstovka.universe.bricksbreaking.orm.Database;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.CommentException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AddCommentCommandTest {

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
    public void should_add_comment_to_database() throws CommentException {
        Player.instance().setName("db_player_1");

        new AddCommentCommand("serus").run();

        Comment comment = Database.instance()
                .select(Comment.class)
                .where("player", "=", "db_player_1")
                .where("game", "=", BuildConfig.NAME)
                .first(Comment.class);

        assertNotNull(comment);

        assertEquals("serus", comment.getComment());
    }

}

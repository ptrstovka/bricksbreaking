package com.peterstovka.universe.bricksbreaking.commands;

import com.peterstovka.universe.BuildConfig;
import com.peterstovka.universe.bricksbreaking.game.Player;
import com.peterstovka.universe.bricksbreaking.service.Comments;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.service.CommentException;

import java.util.Calendar;

public class AddCommentCommand implements Command {

    private Comments comments;

    private String comment;

    public AddCommentCommand(String comment) {
        this.comment = comment;
        this.comments = new Comments();
    }

    @Override
    public void run() throws CommentException {
        Comment model = new Comment(
                Player.instance().getName(),
                BuildConfig.NAME,
                comment,
                Calendar.getInstance().getTime()
        );

        comments.addComment(model);
    }
}

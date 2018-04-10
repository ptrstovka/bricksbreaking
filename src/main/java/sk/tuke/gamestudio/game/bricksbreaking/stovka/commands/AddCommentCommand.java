package sk.tuke.gamestudio.game.bricksbreaking.stovka.commands;

import com.peterstovka.universe.BuildConfig;
import sk.tuke.gamestudio.game.bricksbreaking.stovka.game.Player;
import sk.tuke.gamestudio.game.bricksbreaking.stovka.service.Comments;
import sk.tuke.gamestudio.server.entity.Comment;
import sk.tuke.gamestudio.server.service.CommentException;
import sk.tuke.gamestudio.server.service.CommentService;

import java.util.Calendar;

public class AddCommentCommand implements Command {

    private Comments comments;

    private String comment;

    public AddCommentCommand(CommentService commentService, String comment) {
        this.comment = comment;
        this.comments = new Comments(commentService);
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

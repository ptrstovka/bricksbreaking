package sk.tuke.gamestudio.game.bricksbreaking.stovka.ui.console;

import com.peterstovka.universe.BuildConfig;
import sk.tuke.gamestudio.game.bricksbreaking.stovka.foundation.Strings;
import sk.tuke.gamestudio.game.bricksbreaking.stovka.service.Comments;
import sk.tuke.gamestudio.server.entity.Comment;
import sk.tuke.gamestudio.server.service.CommentException;
import sk.tuke.gamestudio.server.service.CommentService;

import java.util.List;

public class ConsoleCommentReader {

    private Comments comments;
    private Console console;

    ConsoleCommentReader(CommentService commentService) {
        this.comments = new Comments(commentService);
        this.console = Console.instance();
    }

    public void open() {
        console.clearScreen();

        try {
            List<Comment> comments = this.comments.getComments(BuildConfig.NAME);
            renderComments(comments);
        } catch (CommentException e) {
            console.e("Whoops, we could not get the comments right now. \uD83D\uDE20");
            console.enterToExit();
        }
    }

    private void renderComments(List<Comment> comments) {

        if (comments.isEmpty()) {
            console.w("This game does not have any comments. What about add the first one? \uD83D\uDE07");
            console.enterToExit();
            return;
        }

        comments.forEach(comment -> {
            console.render(Strings.f("%s wrote:", comment.getPlayer()));
            console.d(Strings.f("> %s", comment.getComment()), false);
            console.emptyLine();
        });

        console.enterToExit();
    }

}

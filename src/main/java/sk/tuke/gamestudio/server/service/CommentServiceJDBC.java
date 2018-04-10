package sk.tuke.gamestudio.server.service;

import sk.tuke.gamestudio.game.bricksbreaking.stovka.orm.Database;
import sk.tuke.gamestudio.server.entity.Comment;

import java.util.List;

public class CommentServiceJDBC implements CommentService {

    @Override
    public void addComment(Comment comment) throws CommentException {
        Database.instance().insert(comment);
    }

    @Override
    public List<Comment> getComments(String game) throws CommentException {
        return Database.instance()
                .select(Comment.class)
                .where("game", "=", game)
                .get(Comment.class);
    }
}

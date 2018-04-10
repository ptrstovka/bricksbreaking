package sk.tuke.gamestudio.game.bricksbreaking.stovka.service;

import sk.tuke.gamestudio.server.entity.Comment;
import sk.tuke.gamestudio.server.service.CommentException;
import sk.tuke.gamestudio.server.service.CommentService;

import java.util.List;

public class Comments implements CommentService {

    private CommentService commentService;

    public Comments(CommentService commentService) {
        this.commentService = commentService;
    }

    public void addComment(Comment comment) throws CommentException {
        commentService.addComment(comment);
    }

    public List<Comment> getComments(String game) throws CommentException {
        return commentService.getComments(game);
    }

}

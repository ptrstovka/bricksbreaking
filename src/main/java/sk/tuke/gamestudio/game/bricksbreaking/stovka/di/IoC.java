package sk.tuke.gamestudio.game.bricksbreaking.stovka.di;

import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.gamestudio.server.service.CommentService;
import sk.tuke.gamestudio.server.service.RatingService;
import sk.tuke.gamestudio.server.service.ScoreService;

public class IoC {

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private CommentService commentService;

    public ScoreService getScoreService() {
        return scoreService;
    }

    public RatingService getRatingService() {
        return ratingService;
    }

    public CommentService getCommentService() {
        return commentService;
    }
}

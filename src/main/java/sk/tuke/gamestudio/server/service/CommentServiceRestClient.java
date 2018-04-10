package sk.tuke.gamestudio.server.service;

import sk.tuke.gamestudio.server.entity.Comment;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.List;

public class CommentServiceRestClient extends HttpClient implements CommentService {

    @Override
    public void addComment(Comment comment) throws CommentException {
        post("/", "comment", jsonEntity(comment), Response.class);
    }

    @Override
    public List<Comment> getComments(String game) throws CommentException {
        return get("/", "comment/" + game, new GenericType<List<Comment>>() {});
    }
}

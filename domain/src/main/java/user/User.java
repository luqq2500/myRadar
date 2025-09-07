package user;

import adversity.SubAdversity;
import geo.Coordinate;
import post.Post;
import post.Description;
import post.vote.Vote;
import post.vote.VoteType;

import java.util.UUID;

public class User {
    private final UUID userId;
    private String username;

    public User(String username) {
        this.userId = UUID.randomUUID();
        this.username = username;
    }

    public void changeUsername(String username) {
        this.username = username;
    }

    public Post post(SubAdversity subAdversity, Coordinate coordinate, Description description){
        return new Post(userId, subAdversity, coordinate ,description);
    }

    public Vote upVote(Post post) {
        post.updateVote(VoteType.UPVOTE);
        return new Vote(post.getPostId(), userId, VoteType.UPVOTE);
    }

    public Vote downVote(Post post) {
        post.updateVote(VoteType.DOWNVOTE);
        return new Vote(post.getPostId(), userId, VoteType.DOWNVOTE);
    }
}

package post;

import adversity.Adversity;
import adversity.coordinate.Coordinate;
import post.vote.VoteType;

import java.time.LocalDateTime;
import java.util.UUID;

public class Post {
    private final UUID postId;
    private final UUID userId;
    private final Adversity adversity;
    private final Description description;
    private final LocalDateTime postDate;
    private int votes;

    public Post(UUID userId, Adversity adversity, Description description){
        this.postId = UUID.randomUUID();
        this.userId = userId;
        this.adversity = adversity;
        this.description = description;
        this.postDate = LocalDateTime.now();
    }
    public void updateVote(VoteType voteType){
        if (voteType.equals(VoteType.UPVOTE)){
            votes++;
        } else if (voteType.equals(VoteType.DOWNVOTE)){
            votes--;
        }
    }
    public int getVote(){
        return votes;
    }

    public Coordinate getCoordinate(){
        return adversity.coordinate();
    }

    public String getMainAdversity(){
        return adversity.mainAdversity();
    }

    public String getSubAdversity(){
        return adversity.subAdversity();
    }

    public LocalDateTime getPostDate(){
        return postDate;
    }

    public UUID getPostId() {
        return postId;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getDescription() {
        return description.description();
    }
}

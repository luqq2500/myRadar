package post;

import adversity.Adversity;
import adversity.AdversityDetails;
import adversity.SubAdversity;
import geo.Coordinate;
import post.vote.VoteType;

import java.time.LocalDateTime;
import java.util.UUID;

public class Post {
    private final UUID postId;
    private final UUID userId;
    private final SubAdversity subAdversity;
    private final Coordinate coordinate;
    private final Description description;
    private final LocalDateTime postDate;
    private int votes;

    public Post(UUID userId, SubAdversity subAdversity, Coordinate coordinate, Description description){
        this.postId = UUID.randomUUID();
        this.userId = userId;
        this.subAdversity = subAdversity;
        this.coordinate = coordinate;
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

    public double getLongitude(){
        return coordinate.longitude();
    }

    public double getLatitude(){
        return coordinate.latitude();
    }

    public Adversity getAdversity(){
        return subAdversity.getMainAdversity();
    }

    public SubAdversity getSubAdversity(){
        return subAdversity;
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

package adversity;

import geo.Coordinate;
import vote.VoteType;

import java.time.LocalDateTime;
import java.util.UUID;

public class Adversity {
    private final UUID id;
    private final UUID userId;
    private final SubAdversity subAdversity;
    private final Coordinate coordinate;
    private final Description description;
    private final LocalDateTime date;
    private int upvotes;
    private int downvotes;

    public Adversity(UUID userId, SubAdversity subAdversity, Coordinate coordinate, Description description){
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.subAdversity = subAdversity;
        this.coordinate = coordinate;
        this.description = description;
        this.date = LocalDateTime.now();
        this.upvotes = 0;
        this.downvotes = 0;
    }
    public void applyVote(VoteType voteType){
        if (voteType == VoteType.UPVOTE){
            upvotes++;}
        else if (voteType == VoteType.DOWNVOTE){
            downvotes++;
        }
    }
    public void undoVote(VoteType voteType){
        if (voteType == VoteType.UPVOTE){
            upvotes--;}
        else if (voteType == VoteType.DOWNVOTE){
            downvotes--;
        }
    }
    public int getVoteScore(){
        return upvotes - downvotes;
    }
    public UUID getId() {
        return id;
    }
}

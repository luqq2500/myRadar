package adversity;

import geo.Coordinate;
import vote.Vote;
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
    private int voteScore;

    public Adversity(UUID userId, SubAdversity subAdversity, Coordinate coordinate, Description description){
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.subAdversity = subAdversity;
        this.coordinate = coordinate;
        this.description = description;
        this.date = LocalDateTime.now();
        this.upvotes = 0;
        this.downvotes = 0;
        this.voteScore = 0;
    }
    public void addVote(VoteType voteType){
        if (voteType == VoteType.UPVOTE){
            upvotes++;
        } else if (voteType == VoteType.DOWNVOTE){
            downvotes++;
        }
        calculateNewVoteScore();
    }

    public void removeVote(VoteType voteType){
        if (voteType == VoteType.UPVOTE){
            upvotes--;
        }else if (voteType == VoteType.DOWNVOTE){
            downvotes--;
        }
        calculateNewVoteScore();
    }

    public void calculateNewVoteScore(){
        voteScore = upvotes - downvotes;
    }
    public int getVoteScore(){
        return voteScore;
    }
    public UUID getId() {
        return id;
    }
}

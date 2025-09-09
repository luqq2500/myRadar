package user;

import adversity.SubAdversity;
import geo.Coordinate;
import adversity.Adversity;
import adversity.Description;
import vote.Vote;
import vote.VoteType;

import java.time.LocalDateTime;
import java.util.UUID;

public class User {
    private final UUID id;
    private String username;
    public User(String username) {
        this.id = UUID.randomUUID();
        this.username = username;
    }
    public Adversity post(SubAdversity subAdversity, Coordinate coordinate, Description description){
        return new Adversity(id, subAdversity, coordinate ,description);
    }
    public Vote upVote(Adversity adversity){
        adversity.addVote(VoteType.UPVOTE);
        return new Vote(adversity.getId(), id, VoteType.UPVOTE, LocalDateTime.now());
    }
    public Vote downVote(Adversity adversity){
        adversity.addVote(VoteType.DOWNVOTE);
        return new Vote(adversity.getId(), id, VoteType.DOWNVOTE, LocalDateTime.now());
    }
    public void undoUpVote(Adversity adversity){
        adversity.removeVote(VoteType.UPVOTE);
    }
    public void undoDownVote(Adversity adversity){
        adversity.removeVote(VoteType.DOWNVOTE);
    }
    public UUID getId(){return id;}
}

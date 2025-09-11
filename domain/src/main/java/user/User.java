package user;

import event.SubAdversity;
import geo.Coordinate;
import event.Event;
import event.Description;
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
    public Event post(SubAdversity subAdversity, Coordinate coordinate, Description description){
        return new Event(id, subAdversity, coordinate ,description);
    }
    public Vote upVote(Event event){
        event.addVote(VoteType.UPVOTE);
        return new Vote(event.getId(), id, VoteType.UPVOTE, LocalDateTime.now());
    }
    public Vote downVote(Event event){
        event.addVote(VoteType.DOWNVOTE);
        return new Vote(event.getId(), id, VoteType.DOWNVOTE, LocalDateTime.now());
    }
    public void undoUpVote(Event event){
        event.removeVote(VoteType.UPVOTE);
    }
    public void undoDownVote(Event event){
        event.removeVote(VoteType.DOWNVOTE);
    }
    public UUID getId(){return id;}
    public void changeUsername(String newUsername){
        this.username = newUsername;
    }
}

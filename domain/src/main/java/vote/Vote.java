package vote;

import java.time.LocalDateTime;
import java.util.UUID;

public record Vote(UUID eventId, UUID userId, VoteType voteType, LocalDateTime createdAt){
    public boolean isUpVote(){
        return voteType.equals(VoteType.UPVOTE);
    }
    public boolean isDownVote(){
        return voteType.equals(VoteType.DOWNVOTE);
    }
}

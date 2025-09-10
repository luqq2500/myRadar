package vote.api;

import vote.Vote;

import java.util.UUID;

public interface IVoteService {
    void upVote(UUID userId, UUID adversityId);
    void downVote(UUID userId, UUID adversityId);
}

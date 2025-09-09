package vote.api;

import java.util.UUID;

public interface IVoteService {
    void upVote(UUID userId, UUID adversityId);
    void downVote(UUID userId, UUID adversityId);
    void undoUpVote(UUID userId, UUID adversityId);
    void undoDownVote(UUID userId, UUID adversityId);
}

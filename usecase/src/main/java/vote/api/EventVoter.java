package vote.api;

import java.util.UUID;

public interface EventVoter {
    void upVote(UUID userId, UUID adversityId);
    void downVote(UUID userId, UUID adversityId);
}

package vote.spi;

import vote.Vote;
import vote.VoteType;

import java.util.Optional;
import java.util.UUID;

public interface VoteRepository {
    void add(Vote vote);
    Vote get(UUID userId, UUID adversityId);
    Optional<Vote> find(UUID userId, UUID adversityId);
    void delete(Vote vote);
}

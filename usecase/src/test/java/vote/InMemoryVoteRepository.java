package vote;

import adversity.Adversity;
import vote.spi.VoteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class InMemoryVoteRepository implements VoteRepository {
    private List<Vote> votes = new ArrayList<>();
    @Override
    public void add(Vote vote) {
        votes.add(vote);
    }
    @Override
    public Vote get(UUID userId, UUID adversityId) {
        return votes.stream()
                .filter(vote -> vote.adversityId().equals(adversityId))
                .filter(vote -> vote.userId().equals(userId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Optional<Vote> find(UUID userId, UUID adversityId) {
        return votes.stream()
                .filter(vote -> vote.userId().equals(userId))
                .filter(vote -> vote.adversityId().equals(adversityId))
                .findFirst();
    }

    @Override
    public void delete(Vote vote) {
        votes.remove(vote);
    }

    @Override
    public void update(Vote vote) {
        votes.remove(get(vote.adversityId(), vote.userId()));
        votes.add(vote);
    }
}

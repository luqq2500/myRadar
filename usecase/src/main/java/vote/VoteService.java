package vote;

import adversity.Adversity;
import adversity.spi.AdversityRepository;
import user.User;
import user.UserRepository;
import vote.api.IVoteService;
import vote.spi.VoteRepository;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

public class VoteService implements IVoteService {
    private final UserRepository userRepository;
    private final AdversityRepository adversityRepository;
    private final VoteRepository voteRepository;

    public VoteService(UserRepository userRepository, AdversityRepository adversityRepository, VoteRepository voteRepository) {
        this.userRepository = userRepository;
        this.adversityRepository = adversityRepository;
        this.voteRepository = voteRepository;
    }

    @Override
    public void upVote(UUID userId, UUID adversityId) {
        Optional<Vote> previousVote = deletePreviousVote(userId, adversityId);
        if ((previousVote.isPresent() && previousVote.get().isDownVote()) || previousVote.isEmpty()) {
            User user = userRepository.get(userId);
            Adversity adversity = adversityRepository.get(adversityId);
            Vote vote = user.upVote(adversity);
            voteRepository.add(vote);
        }
    }

    @Override
    public void downVote(UUID userId, UUID adversityId) {
        Optional<Vote> previousVote = deletePreviousVote(userId, adversityId);
        if ((previousVote.isPresent() && previousVote.get().isUpVote()) || previousVote.isEmpty()) {
            User user = userRepository.get(userId);
            Adversity adversity = adversityRepository.get(adversityId);
            Vote vote = user.downVote(adversity);
            voteRepository.add(vote);
        }
    }

    private Optional<Vote> deletePreviousVote(UUID userId, UUID adversityId) {
        Optional<Vote> findVote = voteRepository.find(userId, adversityId);
        findVote.ifPresent(voteRepository::delete);
        return findVote;
    }
}

package vote;

import adversity.Adversity;
import adversity.spi.AdversityRepository;
import user.User;
import user.UserRepository;
import vote.api.IVoteService;
import vote.spi.VoteRepository;
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
        User user = userRepository.get(userId);
        Adversity adversity = adversityRepository.get(adversityId);
        Optional<Vote> previousVote = deletePreviousVote(user, adversity);
        if ((previousVote.isPresent() && previousVote.get().isDownVote()) || previousVote.isEmpty()) {
            Vote vote = user.upVote(adversity);
            voteRepository.add(vote);
        }
    }

    @Override
    public void downVote(UUID userId, UUID adversityId) {
        User user = userRepository.get(userId);
        Adversity adversity = adversityRepository.get(adversityId);
        Optional<Vote> previousVote = deletePreviousVote(user, adversity);
        if ((previousVote.isPresent() && previousVote.get().isUpVote()) || previousVote.isEmpty()) {
            Vote vote = user.downVote(adversity);
            voteRepository.add(vote);
        }
    }

    private Optional<Vote> deletePreviousVote(User user, Adversity adversity) {
        Optional<Vote> previousVote = voteRepository.find(user.getId(), adversity.getId());
        if (previousVote.isPresent()){
            Vote vote = previousVote.get();
            if (vote.isDownVote()){
                user.undoDownVote(adversity);}
            if (vote.isUpVote()){
                user.undoUpVote(adversity);}
            voteRepository.delete(vote);
        }
        return previousVote;
    }
}

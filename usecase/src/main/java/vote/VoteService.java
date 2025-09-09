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
        Optional<Vote> findVote = voteRepository.find(userId, adversityId);
        if (findVote.isPresent()) {
            Vote vote = findVote.get();
            if (vote.isUpVote()){
                throw new InvalidVote("Vote has already been submitted.");
            }if (vote.isDownVote()){
                undoDownVote(userId, adversityId);
            }
        }
        User user = userRepository.get(userId);
        Adversity adversity = adversityRepository.get(adversityId);

        Vote vote = user.upVote(adversity);
        voteRepository.add(vote);
    }

    @Override
    public void undoUpVote(UUID userId, UUID adversityId) {
        Optional<Vote> findVote = voteRepository.find(userId, adversityId);
        if (findVote.isEmpty() || !findVote.get().isUpVote()){
            throw new InvalidVote("Vote has not been submitted.");
        }

        Vote vote = voteRepository.get(userId, adversityId);
        Adversity adversity = adversityRepository.get(vote.adversityId());
        User user = userRepository.get(vote.userId());

        user.undoUpVote(adversity);
        voteRepository.delete(vote);
    }

    @Override
    public void downVote(UUID userId, UUID adversityId) {
        Optional<Vote> findVote = voteRepository.find(userId, adversityId);
        if (findVote.isPresent()) {
            Vote vote = findVote.get();
            if (vote.isDownVote()){
                throw new InvalidVote("Vote has already been submitted.");
            }if (vote.isUpVote()){
                undoUpVote(userId, adversityId);
            }
        }
        User user = userRepository.get(userId);
        Adversity adversity = adversityRepository.get(adversityId);

        Vote vote = user.downVote(adversity);
        voteRepository.add(vote);
    }

    @Override
    public void undoDownVote(UUID userId, UUID adversityId) {
        Optional<Vote> findVote = voteRepository.find(userId, adversityId);
        if (findVote.isEmpty() || !findVote.get().isDownVote()){
            throw new InvalidVote("Vote has not been submitted.");
        }

        Vote vote = voteRepository.get(userId, adversityId);
        Adversity adversity = adversityRepository.get(vote.adversityId());
        User user = userRepository.get(vote.userId());

        user.undoDownVote(adversity);
        voteRepository.delete(vote);
    }
}

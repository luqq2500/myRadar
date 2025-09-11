package vote;

import event.Event;
import event.spi.EventRepository;
import user.User;
import user.UserRepository;
import vote.api.EventVoter;
import vote.spi.VoteRepository;
import java.util.Optional;
import java.util.UUID;

public class VoteEvent implements EventVoter {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final VoteRepository voteRepository;

    public VoteEvent(UserRepository userRepository, EventRepository eventRepository, VoteRepository voteRepository) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.voteRepository = voteRepository;
    }

    @Override
    public void upVote(UUID userId, UUID eventId) {
        User user = userRepository.get(userId);
        Event event = eventRepository.get(eventId);
        Optional<Vote> previousVote = undoPreviousVote(user, event);
        if ((previousVote.isPresent() && previousVote.get().isDownVote()) || previousVote.isEmpty()) {
            Vote vote = user.upVote(event);
            voteRepository.add(vote);
        }
    }

    @Override
    public void downVote(UUID userId, UUID eventId) {
        User user = userRepository.get(userId);
        Event event = eventRepository.get(eventId);
        Optional<Vote> previousVote = undoPreviousVote(user, event);
        if ((previousVote.isPresent() && previousVote.get().isUpVote()) || previousVote.isEmpty()) {
            Vote vote = user.downVote(event);
            voteRepository.add(vote);
        }
    }

    private Optional<Vote> undoPreviousVote(User user, Event event) {
        Optional<Vote> previousVote = voteRepository.find(user.getId(), event.getId());
        if (previousVote.isPresent()){
            Vote vote = previousVote.get();
            if (vote.isUpVote()){
                user.undoUpVote(event);
            }
            if (vote.isDownVote()){
                user.undoDownVote(event);
            }
            voteRepository.delete(vote);
        }return previousVote;
    }
}

package vote;

import adversity.Adversity;
import adversity.InMemoryAdversityRepository;
import adversity.SubAdversity;
import adversity.spi.AdversityRepository;
import geo.MockGeoLocator;
import geo.api.GeoLocator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import adversity.PostAdversity;
import adversity.api.AdversityPoster;
import user.InMemoryUserRepository;
import user.User;
import user.UserRepository;
import vote.api.IVoteService;
import vote.spi.VoteRepository;

public class voteAdversityUseCaseTest {
    private IVoteService voteService;
    private User testUser1;
    private User testUser2;
    private Adversity testPostedAdversity;

    @Before
    public void setUp() {
        UserRepository userRepository = new InMemoryUserRepository();
        AdversityRepository adversityRepository = new InMemoryAdversityRepository();
        VoteRepository voteRepository = new InMemoryVoteRepository();
        GeoLocator geoLocator = new MockGeoLocator();

        testUser1 = new User("test-user-1");
        testUser2 = new User("test-user-2");
        userRepository.add(testUser1);
        userRepository.add(testUser2);

        AdversityPoster adversityPoster = new PostAdversity(userRepository, adversityRepository, geoLocator);
        voteService = new VoteService(userRepository, adversityRepository, voteRepository);

        adversityPoster.post(testUser1.getId(), SubAdversity.ROAD_HAZARD, "big crack.");
        testPostedAdversity = adversityRepository.getFirst();

    }

    @Test
    public void upVoteOnce_shouldNotThrowException() {
        voteService.upVote(testUser1.getId(), testPostedAdversity.getId());
    }

    @Test
    public void upVoteTwice_shouldThrowException() {
        voteService.upVote(testUser1.getId(), testPostedAdversity.getId());
        Assert.assertThrows(InvalidVote.class, () -> voteService.upVote(testUser1.getId(), testPostedAdversity.getId()));
    }

    @Test
    public void downVoteOnce_shouldNotThrowException() {
        voteService.downVote(testUser1.getId(), testPostedAdversity.getId());
    }

    @Test
    public void downVoteTwice_shouldThrowException() {
        voteService.downVote(testUser1.getId(), testPostedAdversity.getId());
        Assert.assertThrows(InvalidVote.class, () -> voteService.downVote(testUser1.getId(), testPostedAdversity.getId()));
    }

    @Test
    public void newUpVoteAfterAnotherUpVote_shouldNotThrowException() {
        voteService.upVote(testUser1.getId(), testPostedAdversity.getId());
        voteService.upVote(testUser2.getId(), testPostedAdversity.getId());
    }

    @Test
    public void newDownVoteAfterAnotherDownVote_shouldNotThrowException() {
        voteService.downVote(testUser1.getId(), testPostedAdversity.getId());
        voteService.downVote(testUser2.getId(), testPostedAdversity.getId());
    }

    @Test
    public void upVote_thenUndoUpVote_shouldNotThrowException() {
        voteService.upVote(testUser1.getId(), testPostedAdversity.getId());
        voteService.undoUpVote(testUser1.getId(), testPostedAdversity.getId());
    }

    @Test
    public void downVote_thenUndoDownVote_shouldNotThrowException() {
        voteService.downVote(testUser1.getId(), testPostedAdversity.getId());
        voteService.undoDownVote(testUser1.getId(), testPostedAdversity.getId());
    }

    @Test
    public void upVote_thenUndoUpVote_thenDownVote_shouldNotThrowException() {
        voteService.upVote(testUser1.getId(), testPostedAdversity.getId());
        voteService.undoUpVote(testUser1.getId(), testPostedAdversity.getId());
        voteService.downVote(testUser1.getId(), testPostedAdversity.getId());
    }

    @Test
    public void downVote_thenUndoDownVote_thenUpVote_shouldNotThrowException() {
        voteService.downVote(testUser1.getId(), testPostedAdversity.getId());
        voteService.undoDownVote(testUser1.getId(), testPostedAdversity.getId());
        voteService.upVote(testUser1.getId(), testPostedAdversity.getId());
    }

    @Test
    public void undoNonExistingVote_shouldThrowException() {
        Assert.assertThrows(InvalidVote.class, () -> voteService.undoUpVote(testUser1.getId(), testPostedAdversity.getId()));
        Assert.assertThrows(InvalidVote.class, () -> voteService.undoDownVote(testUser1.getId(), testPostedAdversity.getId()));
    }

    @Test
    public void undoVoteTwice_shouldThrowException() {
        voteService.upVote(testUser1.getId(), testPostedAdversity.getId());
        voteService.undoUpVote(testUser1.getId(), testPostedAdversity.getId());
        Assert.assertThrows(InvalidVote.class, ()-> voteService.undoUpVote(testUser1.getId(), testPostedAdversity.getId()));

        voteService.downVote(testUser1.getId(), testPostedAdversity.getId());
        voteService.undoDownVote(testUser1.getId(), testPostedAdversity.getId());
        Assert.assertThrows(InvalidVote.class, ()-> voteService.undoDownVote(testUser1.getId(), testPostedAdversity.getId()));
    }
}

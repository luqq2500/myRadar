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
    private VoteRepository voteRepository;
    private User testUser1;
    private User testUser2;
    private Adversity testPostedAdversity;

    @Before
    public void setUp() {
        UserRepository userRepository = new InMemoryUserRepository();
        AdversityRepository adversityRepository = new InMemoryAdversityRepository();
        voteRepository = new InMemoryVoteRepository();
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
    public void downVoteOnce_shouldNotThrowException() {
        voteService.downVote(testUser1.getId(), testPostedAdversity.getId());
    }

    @Test
    public void upVoteTwice_upVoteShouldBeDeleted(){
        voteService.upVote(testUser1.getId(), testPostedAdversity.getId());
        voteService.upVote(testUser1.getId(), testPostedAdversity.getId());
        Assert.assertNull(voteRepository.get(testUser1.getId(), testPostedAdversity.getId()));
    }

    @Test
    public void downVoteTwice_downVoteShouldBeDeleted(){
        voteService.downVote(testUser1.getId(), testPostedAdversity.getId());
        voteService.downVote(testUser1.getId(), testPostedAdversity.getId());
        Assert.assertNull(voteRepository.get(testUser1.getId(), testPostedAdversity.getId()));
    }

    @Test
    public void upVoteThreeTimes_upVoteShouldBeNotNull(){
        voteService.upVote(testUser1.getId(), testPostedAdversity.getId());
        voteService.upVote(testUser1.getId(), testPostedAdversity.getId());
        voteService.upVote(testUser1.getId(), testPostedAdversity.getId());
        Assert.assertNotNull(voteRepository.get(testUser1.getId(), testPostedAdversity.getId()));
    }

    @Test
    public void downVoteThreeTimes_downVoteShouldBeNotNull(){
        voteService.downVote(testUser1.getId(), testPostedAdversity.getId());
        voteService.downVote(testUser1.getId(), testPostedAdversity.getId());
        voteService.downVote(testUser1.getId(), testPostedAdversity.getId());
        Assert.assertNotNull(voteRepository.get(testUser1.getId(), testPostedAdversity.getId()));
    }

    @Test
    public void upVote_thenDownVote_voteShouldBeDownVote(){
        voteService.upVote(testUser1.getId(), testPostedAdversity.getId());
        voteService.downVote(testUser1.getId(), testPostedAdversity.getId());
        Assert.assertTrue(voteRepository.get(testUser1.getId(), testPostedAdversity.getId()).isDownVote());
    }

    @Test
    public void downVote_thenUpVote_voteShouldBeUpVote(){
        voteService.downVote(testUser1.getId(), testPostedAdversity.getId());
        voteService.upVote(testUser1.getId(), testPostedAdversity.getId());
        Assert.assertTrue(voteRepository.get(testUser1.getId(), testPostedAdversity.getId()).isUpVote());
    }

    @Test
    public void twoNewUpVotes_shouldNotThrowException() {
        voteService.upVote(testUser1.getId(), testPostedAdversity.getId());
        voteService.upVote(testUser2.getId(), testPostedAdversity.getId());
    }

    @Test
    public void twoNewDownVotes_shouldNotThrowException() {
        voteService.downVote(testUser1.getId(), testPostedAdversity.getId());
        voteService.downVote(testUser2.getId(), testPostedAdversity.getId());
    }
}

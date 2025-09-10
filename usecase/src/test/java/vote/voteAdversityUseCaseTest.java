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
    private AdversityRepository adversityRepository;
    private User testUser1;
    private User testUser2;
    private Adversity testPostedAdversity;

    @Before
    public void setUp() {
        UserRepository userRepository = new InMemoryUserRepository();
        adversityRepository = new InMemoryAdversityRepository();
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
    public void upVoteOnce_voteScoreShouldBeOne() {
        voteService.upVote(testUser1.getId(), testPostedAdversity.getId());
        Assert.assertEquals(1, adversityRepository.get(testPostedAdversity.getId()).getVoteScore());
    }

    @Test
    public void downVoteOnce_voteScoreShouldBeNegativeOne() {
        voteService.downVote(testUser1.getId(), testPostedAdversity.getId());
        Assert.assertEquals(-1, adversityRepository.get(testPostedAdversity.getId()).getVoteScore());
    }

    @Test
    public void upVoteTwice_upVoteShouldBeDeletedAndVoteScoreShouldBeZero() {
        voteService.upVote(testUser1.getId(), testPostedAdversity.getId());
        voteService.upVote(testUser1.getId(), testPostedAdversity.getId());
        Assert.assertNull(voteRepository.get(testUser1.getId(), testPostedAdversity.getId()));
        Assert.assertEquals(0, adversityRepository.get(testPostedAdversity.getId()).getVoteScore());
    }

    @Test
    public void downVoteTwice_downVoteShouldBeDeletedAndVoteScoreShouldBeZero() {
        voteService.downVote(testUser1.getId(), testPostedAdversity.getId());
        voteService.downVote(testUser1.getId(), testPostedAdversity.getId());
        Assert.assertNull(voteRepository.get(testUser1.getId(), testPostedAdversity.getId()));
        Assert.assertEquals(0, adversityRepository.get(testPostedAdversity.getId()).getVoteScore());
    }

    @Test
    public void upVoteThreeTimes_voteScoreShouldBeOne() {
        voteService.upVote(testUser1.getId(), testPostedAdversity.getId());
        voteService.upVote(testUser1.getId(), testPostedAdversity.getId());
        voteService.upVote(testUser1.getId(), testPostedAdversity.getId());
        Assert.assertNotNull(voteRepository.get(testUser1.getId(), testPostedAdversity.getId()));
        Assert.assertEquals(1, adversityRepository.get(testPostedAdversity.getId()).getVoteScore());
    }

    @Test
    public void downVoteThreeTimes_voteScoreShouldBeNegativeOne() {
        voteService.downVote(testUser1.getId(), testPostedAdversity.getId());
        voteService.downVote(testUser1.getId(), testPostedAdversity.getId());
        voteService.downVote(testUser1.getId(), testPostedAdversity.getId());
        Assert.assertNotNull(voteRepository.get(testUser1.getId(), testPostedAdversity.getId()));
        Assert.assertEquals(-1, adversityRepository.get(testPostedAdversity.getId()).getVoteScore());
    }

    @Test
    public void upVote_thenDownVote_voteShouldBeDownVoteAndVoteScoreShouldBeNegativeOne() {
        voteService.upVote(testUser1.getId(), testPostedAdversity.getId());
        voteService.downVote(testUser1.getId(), testPostedAdversity.getId());
        Assert.assertTrue(voteRepository.get(testUser1.getId(), testPostedAdversity.getId()).isDownVote());
        Assert.assertEquals(-1, adversityRepository.get(testPostedAdversity.getId()).getVoteScore());

    }

    @Test
    public void downVote_thenUpVote_voteShouldBeUpVoteAndVoteScoreShouldBePositiveOne() {
        voteService.downVote(testUser1.getId(), testPostedAdversity.getId());
        voteService.upVote(testUser1.getId(), testPostedAdversity.getId());
        Assert.assertTrue(voteRepository.get(testUser1.getId(), testPostedAdversity.getId()).isUpVote());
        Assert.assertEquals(1, adversityRepository.get(testPostedAdversity.getId()).getVoteScore());
    }

    @Test
    public void twoNewUpVotes_voteScoreShouldBeTwo() {
        voteService.upVote(testUser1.getId(), testPostedAdversity.getId());
        voteService.upVote(testUser2.getId(), testPostedAdversity.getId());
        Assert.assertEquals(2, adversityRepository.get(testPostedAdversity.getId()).getVoteScore());
    }

    @Test
    public void twoNewDownVotes_voteScoreShouldBeNegativeTwo() {
        voteService.downVote(testUser1.getId(), testPostedAdversity.getId());
        voteService.downVote(testUser2.getId(), testPostedAdversity.getId());
        Assert.assertEquals(-2, adversityRepository.get(testPostedAdversity.getId()).getVoteScore());
    }
}

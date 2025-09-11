package vote;

import event.Event;
import event.InMemoryEventRepository;
import event.SubAdversity;
import event.spi.EventRepository;
import geo.MockGeoLocator;
import geo.api.GeoLocator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import event.PostEvent;
import event.api.EventPoster;
import user.InMemoryUserRepository;
import user.User;
import user.UserRepository;
import vote.api.EventVoter;
import vote.spi.VoteRepository;

public class voteEventUseCaseTest {
    private EventVoter voteService;
    private VoteRepository voteRepository;
    private EventRepository eventRepository;
    private User testUser1;
    private User testUser2;
    private Event testPostedEvent;

    @Before
    public void setUp() {
        UserRepository userRepository = new InMemoryUserRepository();
        eventRepository = new InMemoryEventRepository();
        voteRepository = new InMemoryVoteRepository();
        GeoLocator geoLocator = new MockGeoLocator();

        testUser1 = new User("test-user-1");
        testUser2 = new User("test-user-2");
        userRepository.add(testUser1);
        userRepository.add(testUser2);

        EventPoster eventPoster = new PostEvent(userRepository, eventRepository, geoLocator);
        voteService = new VoteEvent(userRepository, eventRepository, voteRepository);

        eventPoster.post(testUser1.getId(), SubAdversity.ROAD_HAZARD, "big crack.");
        testPostedEvent = eventRepository.getFirst();

    }

    @Test
    public void upVoteOnce_voteScoreShouldBeOne() {
        voteService.upVote(testUser1.getId(), testPostedEvent.getId());
        Assert.assertEquals(1, eventRepository.get(testPostedEvent.getId()).getVoteScore());
    }

    @Test
    public void downVoteOnce_voteScoreShouldBeNegativeOne() {
        voteService.downVote(testUser1.getId(), testPostedEvent.getId());
        Assert.assertEquals(-1, eventRepository.get(testPostedEvent.getId()).getVoteScore());
    }

    @Test
    public void upVoteTwice_upVoteShouldBeDeletedAndVoteScoreShouldBeZero() {
        voteService.upVote(testUser1.getId(), testPostedEvent.getId());
        voteService.upVote(testUser1.getId(), testPostedEvent.getId());
        Assert.assertNull(voteRepository.get(testUser1.getId(), testPostedEvent.getId()));
        Assert.assertEquals(0, eventRepository.get(testPostedEvent.getId()).getVoteScore());
    }

    @Test
    public void downVoteTwice_downVoteShouldBeDeletedAndVoteScoreShouldBeZero() {
        voteService.downVote(testUser1.getId(), testPostedEvent.getId());
        voteService.downVote(testUser1.getId(), testPostedEvent.getId());
        Assert.assertNull(voteRepository.get(testUser1.getId(), testPostedEvent.getId()));
        Assert.assertEquals(0, eventRepository.get(testPostedEvent.getId()).getVoteScore());
    }

    @Test
    public void upVoteThreeTimes_voteScoreShouldBeOne() {
        voteService.upVote(testUser1.getId(), testPostedEvent.getId());
        voteService.upVote(testUser1.getId(), testPostedEvent.getId());
        voteService.upVote(testUser1.getId(), testPostedEvent.getId());
        Assert.assertNotNull(voteRepository.get(testUser1.getId(), testPostedEvent.getId()));
        Assert.assertEquals(1, eventRepository.get(testPostedEvent.getId()).getVoteScore());
    }

    @Test
    public void downVoteThreeTimes_voteScoreShouldBeNegativeOne() {
        voteService.downVote(testUser1.getId(), testPostedEvent.getId());
        voteService.downVote(testUser1.getId(), testPostedEvent.getId());
        voteService.downVote(testUser1.getId(), testPostedEvent.getId());
        Assert.assertNotNull(voteRepository.get(testUser1.getId(), testPostedEvent.getId()));
        Assert.assertEquals(-1, eventRepository.get(testPostedEvent.getId()).getVoteScore());
    }

    @Test
    public void upVote_thenDownVote_voteShouldBeDownVoteAndVoteScoreShouldBeNegativeOne() {
        voteService.upVote(testUser1.getId(), testPostedEvent.getId());
        voteService.downVote(testUser1.getId(), testPostedEvent.getId());
        Assert.assertTrue(voteRepository.get(testUser1.getId(), testPostedEvent.getId()).isDownVote());
        Assert.assertEquals(-1, eventRepository.get(testPostedEvent.getId()).getVoteScore());
    }

    @Test
    public void downVote_thenUpVote_voteShouldBeUpVoteAndVoteScoreShouldBePositiveOne() {
        voteService.downVote(testUser1.getId(), testPostedEvent.getId());
        voteService.upVote(testUser1.getId(), testPostedEvent.getId());
        Assert.assertTrue(voteRepository.get(testUser1.getId(), testPostedEvent.getId()).isUpVote());
        Assert.assertEquals(1, eventRepository.get(testPostedEvent.getId()).getVoteScore());
    }

    @Test
    public void twoNewUpVotes_voteScoreShouldBeTwo() {
        voteService.upVote(testUser1.getId(), testPostedEvent.getId());
        voteService.upVote(testUser2.getId(), testPostedEvent.getId());
        Assert.assertEquals(2, eventRepository.get(testPostedEvent.getId()).getVoteScore());
    }

    @Test
    public void twoNewDownVotes_voteScoreShouldBeNegativeTwo() {
        voteService.downVote(testUser1.getId(), testPostedEvent.getId());
        voteService.downVote(testUser2.getId(), testPostedEvent.getId());
        Assert.assertEquals(-2, eventRepository.get(testPostedEvent.getId()).getVoteScore());
    }
}

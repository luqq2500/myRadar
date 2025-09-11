package user;

import event.SubAdversity;
import geo.Coordinate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import event.Description;
import event.Event;

public class userDT {
    private User user;
    private Event event;

    @Before
    public void setUp() throws Exception {
        user = new User("luqq2500");
        Coordinate coordinate = new Coordinate(42.09, 32.05);
        Description description = new Description("longkang besar bahaya");
        event = user.post(SubAdversity.ROAD_HAZARD,coordinate , description);
    }

    @Test
    public void upVoteOnce_voteShouldBeOne(){
        user.upVote(event);
        Assert.assertEquals(1, event.getVoteScore());
        System.out.println(event.getVoteScore());
    }

    @Test
    public void downVoteOnce_voteShouldBeMinusOne(){
        user.downVote(event);
        Assert.assertEquals(-1, event.getVoteScore());
        System.out.println(event.getVoteScore());
    }

    @Test
    public void upVoteTwice_voteShouldBeTwo(){
        user.upVote(event);
        user.upVote(event);
        Assert.assertEquals(2, event.getVoteScore());
        System.out.println(event.getVoteScore());
    }

    @Test
    public void downVoteTwice_voteShouldBeTwo(){
        user.downVote(event);
        user.downVote(event);
        Assert.assertEquals(-2, event.getVoteScore());
        System.out.println(event.getVoteScore());
    }

    @Test
    public void downVoteTwice_upVoteThreeTimes_voteScoreShouldBeOne(){
        user.downVote(event);
        user.downVote(event);
        user.upVote(event);
        user.upVote(event);
        user.upVote(event);
        Assert.assertEquals(1, event.getVoteScore());
    }

    @Test
    public void upVoteOnce_andUndoUpVote_voteScoreShouldBeZero(){
        user.upVote(event);
        user.undoUpVote(event);
        Assert.assertEquals(0, event.getVoteScore());
    }

    @Test
    public void upVoteOnce_undoUpVoteOnce_andUpVoteOnceAgain_voteScoreShouldBeOne(){
        user.upVote(event);
        user.undoUpVote(event);
        user.upVote(event);
        Assert.assertEquals(1, event.getVoteScore());
    }
}

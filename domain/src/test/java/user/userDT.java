package user;

import adversity.SubAdversity;
import geo.Coordinate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import adversity.Description;
import adversity.Adversity;

public class userDT {
    private User user;
    private Adversity adversity;

    @Before
    public void setUp() throws Exception {
        user = new User("luqq2500");
        Coordinate coordinate = new Coordinate(42.09, 32.05);
        Description description = new Description("longkang besar bahaya");
        adversity = user.post(SubAdversity.ROAD_HAZARD,coordinate , description);
    }

    @Test
    public void upVoteOnce_voteShouldBeOne(){
        user.upVote(adversity);
        Assert.assertEquals(1, adversity.getVoteScore());
        System.out.println(adversity.getVoteScore());
    }

    @Test
    public void downVoteOnce_voteShouldBeMinusOne(){
        user.downVote(adversity);
        Assert.assertEquals(-1, adversity.getVoteScore());
        System.out.println(adversity.getVoteScore());
    }

    @Test
    public void upVoteTwice_voteShouldBeTwo(){
        user.upVote(adversity);
        user.upVote(adversity);
        Assert.assertEquals(2, adversity.getVoteScore());
        System.out.println(adversity.getVoteScore());
    }

    @Test
    public void downVoteTwice_voteShouldBeTwo(){
        user.downVote(adversity);
        user.downVote(adversity);
        Assert.assertEquals(-2, adversity.getVoteScore());
        System.out.println(adversity.getVoteScore());
    }

    @Test
    public void downVoteTwice_upVoteThreeTimes_voteScoreShouldBeOne(){
        user.downVote(adversity);
        user.downVote(adversity);
        user.upVote(adversity);
        user.upVote(adversity);
        user.upVote(adversity);
        Assert.assertEquals(1, adversity.getVoteScore());
    }

    @Test
    public void upVoteOnce_andUndoUpVote_voteScoreShouldBeZero(){
        user.upVote(adversity);
        user.undoUpVote(adversity);
        Assert.assertEquals(0, adversity.getVoteScore());
    }

    @Test
    public void upVoteOnce_undoUpVoteOnce_andUpVoteOnceAgain_voteScoreShouldBeOne(){
        user.upVote(adversity);
        user.undoUpVote(adversity);
        user.upVote(adversity);
        Assert.assertEquals(1, adversity.getVoteScore());
    }
}

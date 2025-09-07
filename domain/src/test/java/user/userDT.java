package user;

import adversity.Adversity;
import adversity.coordinate.Coordinate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import post.Description;
import post.Post;

public class userDT {
    private User user;
    private Post post;

    @Before
    public void setUp() throws Exception {
        user = new User("luqq2500");
        Coordinate coordinate = new Coordinate(42.09, 32.05);
        Adversity adversity = new Adversity("road", "damaged-road", coordinate);
        Description description = new Description("longkang besar bahaya");
        post = user.post(adversity, description);
    }

    @Test
    public void upVoteOnce_voteShouldBeOne(){
        user.upVote(post);
        Assert.assertEquals(1, post.getVote());
    }

    @Test
    public void downVoteOnce_voteShouldBeMinusOne(){
        user.downVote(post);
        Assert.assertEquals(-1, post.getVote());
    }

    @Test
    public void upVoteTwice_voteShouldBeTwo(){
        user.upVote(post);
        user.upVote(post);
        Assert.assertEquals(2, post.getVote());
    }

    @Test
    public void downVoteTwice_voteShouldBeTwo(){
        user.downVote(post);
        user.downVote(post);
        Assert.assertEquals(-2, post.getVote());
    }
}

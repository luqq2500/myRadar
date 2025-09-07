package post.vote;

public class InvalidVote extends RuntimeException {
    public InvalidVote(String message) {
        super(message);
    }
}

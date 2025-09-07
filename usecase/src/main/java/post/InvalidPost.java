package post;

public class InvalidPost extends RuntimeException {
    public InvalidPost(String message) {
        super(message);
    }
}

package adversity;

public class InvalidPost extends RuntimeException {
    public InvalidPost(String message) {
        super(message);
    }
}

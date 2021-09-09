package kirris.blog.exception;

public class ConflictException extends RuntimeException { //IllegalArgumentException
    public ConflictException(String username) {
        super("username '" + username + "' already exists");
    }
}

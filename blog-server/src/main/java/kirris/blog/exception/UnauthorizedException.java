package kirris.blog.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super("Unauthorized: " + message);
    }
}

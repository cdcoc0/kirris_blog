package kirris.blog.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException() {
        super("Bad Request 400");
    }
}

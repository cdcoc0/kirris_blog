package kirris.blog.exception;

public class NotFoundException extends RuntimeException { //IllegalArgumentException
    public NotFoundException(Long id) {
        super("Not found id=" + id);
    }
}

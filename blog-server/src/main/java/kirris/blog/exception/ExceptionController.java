package kirris.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity NotFoundHandler(NotFoundException notFound) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFound.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity BadRequestHandler(BadRequestException badRequest) {
        return ResponseEntity.badRequest().body(badRequest.getMessage());
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity ConflictHandler(ConflictException conflict) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(conflict.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity UnauthorizedHandler(UnauthorizedException unauthorized) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(unauthorized.getMessage());
    }

    @ExceptionHandler({Exception.class}) //try catch 대신
    public ResponseEntity InternalServerErrorHandler(final Exception ex) {
        return ResponseEntity.internalServerError().body(ex.getMessage());
    }
}

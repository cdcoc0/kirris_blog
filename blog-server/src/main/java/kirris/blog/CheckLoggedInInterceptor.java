package kirris.blog;

import kirris.blog.domain.auth.AuthResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CheckLoggedInInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws IOException /*JwtException*/ {
        if(request.getHeader("user_id") == null) {
            response.setStatus(401);
            return false;
        }

        return true;
    }
}

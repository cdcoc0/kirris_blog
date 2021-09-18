package kirris.blog;

import kirris.blog.exception.UnauthorizedException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CheckLoggedInInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws IOException /*JwtException*/ {

        if(request.getAttribute("user") == null) {
            throw new UnauthorizedException("please sign in");
        }

        return true;
    }
}

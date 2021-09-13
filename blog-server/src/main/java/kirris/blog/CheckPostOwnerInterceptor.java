package kirris.blog;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CheckPostOwnerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws IOException {
        long id = Long.parseLong(request.getHeader("user_id"));
        //getPostByUsername => setAttribute로 저장
        if(request.getHeader("user_username") == null) {
            response.setStatus(401);
            return false;
        }

        return true;
    }
}

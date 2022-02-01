package kirris.blog.interceptor;

import kirris.blog.domain.auth.AuthResponseDto;
import kirris.blog.domain.posts.Posts;
import kirris.blog.exception.NotFoundException;
import kirris.blog.exception.UnauthorizedException;
import kirris.blog.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.StringTokenizer;

@RequiredArgsConstructor
@Component
public class CheckPostOwnerInterceptor implements HandlerInterceptor {

    private final PostsRepository postsRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws IOException {
        String param = request.getRequestURI();
        StringTokenizer st = new StringTokenizer(param, "/");
        while(st.hasMoreTokens()) {
            param = st.nextToken();
        }

        long longParam = Long.parseLong(param);
        Posts posts = postsRepository.findById(longParam).orElseThrow(() -> new NotFoundException(longParam));
        AuthResponseDto auth = (AuthResponseDto) request.getAttribute("user");

        if(auth.getId() != posts.getAuth().getId())
            return false;

        return true;
    }
}

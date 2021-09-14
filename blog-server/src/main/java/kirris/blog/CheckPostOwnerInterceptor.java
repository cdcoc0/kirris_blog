package kirris.blog;

import kirris.blog.domain.auth.AuthResponseDto;
import kirris.blog.domain.posts.Posts;
import kirris.blog.domain.posts.PostsResponseDto;
import kirris.blog.exception.UnauthorizedException;
import kirris.blog.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
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

        Posts posts = postsRepository.findById(Long.parseLong(param)).orElseThrow(() -> new UnauthorizedException("access unauthorized"));
        AuthResponseDto auth = (AuthResponseDto) request.getAttribute("user");

        if(auth.getId() != posts.getAuth().getId())
            return false;

        return true;
    }
}

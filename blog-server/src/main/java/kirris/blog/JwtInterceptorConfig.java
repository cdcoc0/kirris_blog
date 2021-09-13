package kirris.blog;

import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class JwtInterceptorConfig implements WebMvcConfigurer {

    private final CheckLoggedInInterceptor checkLoggedIn;
    private final CheckPostOwnerInterceptor checkPostOwner;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> checkLoggedPath = new ArrayList<>();
        checkLoggedPath.add("/{id}");
        checkLoggedPath.add("/write");

        registry.addInterceptor(checkLoggedIn).addPathPatterns(checkLoggedPath);
        registry.addInterceptor(checkPostOwner).addPathPatterns("/{id}");
    }
}

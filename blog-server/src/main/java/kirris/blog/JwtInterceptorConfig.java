package kirris.blog;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Configuration
public class JwtInterceptorConfig implements WebMvcConfigurer {

    private final CheckLoggedInInterceptor checkLoggedIn;
//    private final CheckPostOwnerInterceptor checkPostOwner;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        List<String> checkLoggedPath = new ArrayList<>();
//        checkLoggedPath.add("/{id}");
//        checkLoggedPath.add("/write");

        registry.addInterceptor(checkLoggedIn).addPathPatterns("/auth");
//        registry.addInterceptor(checkPostOwner).addPathPatterns("/{id}");
    }
}

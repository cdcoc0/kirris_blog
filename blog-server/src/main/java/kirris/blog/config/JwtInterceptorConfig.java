package kirris.blog.config;

import kirris.blog.interceptor.CheckLoggedInInterceptor;
import kirris.blog.interceptor.CheckPostOwnerInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class JwtInterceptorConfig implements WebMvcConfigurer {

    private final CheckLoggedInInterceptor checkLoggedIn;
    private final CheckPostOwnerInterceptor checkPostOwner;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(checkLoggedIn).addPathPatterns("/auth/**");
        registry.addInterceptor(checkPostOwner).addPathPatterns("/auth/own/**");
    }
}

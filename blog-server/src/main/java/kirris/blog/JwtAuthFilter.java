package kirris.blog;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthFilter extends GenericFilterBean {
    private final JwtToken jwtToken;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //헤더에서 JWT 받아오기
        String token = jwtToken.resolveToken((HttpServletRequest) request);

        //유효한 토큰인지 확인
        if(token != null && jwtToken.validateToken(token)) {
            //토큰이 유효하면 토큰으로부터 유저 정보 가져오기
            Authentication authentication = jwtToken.getAuthentication(token);

            //SecurityContext에 Authentication 객체 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);

            //토큰의 유효기간이 3일 미만이면 재발급
        }
        chain.doFilter(request, response);
    }
}

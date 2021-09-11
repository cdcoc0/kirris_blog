package kirris.blog;

import kirris.blog.domain.auth.AuthResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
//@Order(1)
public class JwtAuthFilter extends GenericFilterBean {
    private final JwtToken jwtToken;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        //헤더에서 JWT 받아오기
        String token = jwtToken.resolveToken((HttpServletRequest) request);

        //유효한 토큰인지 확인
        if(token != null && jwtToken.validateToken(token)) { //null?
            //토큰이 유효하면 토큰으로부터 유저 정보 가져오기
//            Authentication authentication = jwtToken.getAuthentication(token);

            //SecurityContext에 Authentication 객체 저장
//            SecurityContextHolder.getContext().setAuthentication(authentication);

            //토큰이 유효하면 토큰으로부터 유저 정보 가져와 헤더에 저장해 응답
            AuthResponseDto userInfo = jwtToken.getUserInfo(token);
            httpServletResponse.setHeader("user_id", userInfo.getId().toString());
            httpServletResponse.setHeader("user_username", userInfo.getUsername());

            //토큰의 유효기간이 3일 미만이면 재발급
        }
        chain.doFilter(request, response);
    }
}

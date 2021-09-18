package kirris.blog;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kirris.blog.domain.auth.Auth;
import kirris.blog.domain.auth.AuthResponseDto;
import kirris.blog.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RequiredArgsConstructor
@Component
public class JwtToken {

    private final AuthRepository authRepository;

    private String secretKey = "krblogjsonwebtoken";
    private long tokenValidTime = 1000L * 60 * 60 * 24 * 7; //유효기간 7일

    //객체 초기화, secretKey를 Base64로 인코딩
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    //Token 생성
    public String generateToken(AuthResponseDto userInfo) {
        Claims claims = Jwts.claims().setSubject(userInfo.getUsername()); //JWT payload 에 저장되는 정보단위
        claims.put("id", userInfo.getId());
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) //정보 저장
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    //JWT 토큰에서 인증 정보 조회
//    public Authentication getAuthentication(String token) {
//        UserDetails userDetails = authRepository.findByUsername(this.getUserInfo(token)).get(0);
//        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
//    }

    //JWT 토큰에서 회원 정보 추출
    public AuthResponseDto getUserInfo(String token) {
        String username = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        List<Auth> user = authRepository.findByUsername(username);

        if(user.isEmpty())
            return null;

        return user.get(0).deletePassword();
    }

    //Request의 Header에서 token 가져오기
    public String resolveToken(HttpServletRequest request) {

        if(request.getCookies() != null) {
            Cookie[] cookies = request.getCookies();

            if (cookies.length != 0) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("access_token"))
                        return cookie.getValue();
                }
            }
        }

        return "";
    }

    //토큰 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    //3일 이하로 남은 경우 재연장
    public void refreshToken(String jwtToken) {
        long now = (new Date()).getTime();
        long refresh = 1000 * 60 * 60 * 24 * 3;

        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
        if(claims.getBody().getExpiration().before(new Date(now + refresh)))
            claims.getBody().setExpiration(new Date(now + tokenValidTime));
    }
}
package kirris.blog;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kirris.blog.domain.auth.Auth;
import kirris.blog.domain.auth.AuthResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtToken {

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
    /*public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }*/

    //JWT 토큰에서 회원 정보 추출
    public String getUserInfo(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    //Request의 Header에서 token 가져오기
    /*public String resolveToken(HttpServletRequest request) {
        return request.getHeader("X-AUTH-TOKEN");
    }*/

    //토큰 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}

/*
UserSchema.methods.generateToken = function() {
        //파라미터 1)토큰 안에 넣을 데이터, 2)JWT 암호, 3)기타 옵션
        const token = jwt.sign(
        {
        _id: this.id,
        username: this.username
        },
        process.env.JWT_SECRET,
        {
        expiresIn: '7d' //7일동안 유효
        }
        );
        return token;
        }*/

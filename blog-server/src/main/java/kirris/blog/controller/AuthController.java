package kirris.blog.controller;

import kirris.blog.JwtToken;
import kirris.blog.domain.auth.Auth;
import kirris.blog.domain.auth.AuthRequestDto;
import kirris.blog.domain.auth.AuthResponseDto;
import kirris.blog.exception.BadRequestException;
import kirris.blog.exception.UnauthorizedException;
import kirris.blog.repository.AuthRepository;
import kirris.blog.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthRepository authRepository;
    private final AuthService authService;
    private final JwtToken jwtToken;

    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody AuthRequestDto authRequest, BindingResult result) {
        //validate
        if(result.hasErrors())
            throw new BadRequestException();

        //save, get userinfo except password
        AuthResponseDto authResponse = authService.register(authRequest);

        //generate token, set cookie
        ResponseCookie cookie = ResponseCookie.from("access_token", jwtToken.generateToken(authResponse))
                .httpOnly(true)
                .maxAge(60 * 60 * 24 * 7) //
                .build();

//        (HttpServletResponse response)
//        Cookie cookie = new Cookie("access_token", new JwtToken().generateToken(authResponse));
//        cookie.setHttpOnly(true);
//        cookie.setMaxAge(1000 * 60 * 60 * 24 * 7);
//        response.addCookie(cookie);

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(authResponse);
    }

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody AuthRequestDto authRequest, BindingResult result){
        //validate, 401 unauthorized
        if(result.hasErrors())
            //return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            throw new UnauthorizedException("validation failed");

        //userinfo
        AuthResponseDto authResponse = authService.login(authRequest);

        //generate token and set cookie
        ResponseCookie cookie = ResponseCookie.from("access_token", jwtToken.generateToken(authResponse))
                .httpOnly(true)
                .maxAge(60 * 60 * 24 * 7)
                .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(authResponse);
    }

    @GetMapping("/check")
    public ResponseEntity check(HttpServletRequest request) {
        String token = jwtToken.resolveToken(request);
        if(token.equals("") || !jwtToken.validateToken(token))
            throw new UnauthorizedException("not signed in");

        //토큰이 유효하면 토큰으로부터 유저 정보 가져오기
        AuthResponseDto userInfo = jwtToken.getUserInfo(token);
        return ResponseEntity.ok().body(userInfo);
    }

    @PostMapping("/out") //왜 logout 에러?
    public ResponseEntity logout() {
        //쿠키에서 토큰 삭제
        ResponseCookie cookie = ResponseCookie.from("access_token", "").build();
        return ResponseEntity.noContent().header(HttpHeaders.SET_COOKIE, cookie.toString()).build();
    }
}

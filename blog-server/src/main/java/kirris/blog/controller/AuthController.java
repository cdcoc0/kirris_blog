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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthRepository authRepository;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody AuthRequestDto authRequest, BindingResult result) {
        //validate
        if(result.hasErrors())
            throw new BadRequestException();

        //save, get userinfo except password
        AuthResponseDto authResponse = authService.register(authRequest);

        //generate token, set cookie
        ResponseCookie cookie = ResponseCookie.from("access_token", new JwtToken().generateToken(authResponse))
                .httpOnly(true)
                .maxAge(60 * 60 * 24 * 7) //==확인 필요== 1000 빼면?//
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
        ResponseCookie cookie = ResponseCookie.from("access_token", new JwtToken().generateToken(authResponse))
                .httpOnly(true)
                .maxAge(60 * 60 * 24 * 7)
                .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(authResponse);
    }

    @GetMapping("/check")
    public void check() {}

    @PostMapping("/logout")
    public void logout() {
        //쿠키에서 토큰 삭제
    }
}

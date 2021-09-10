package kirris.blog.controller;

import kirris.blog.JwtToken;
import kirris.blog.domain.auth.AuthRequestDto;
import kirris.blog.domain.auth.AuthResponseDto;
import kirris.blog.exception.BadRequestException;
import kirris.blog.repository.AuthRepository;
import kirris.blog.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthRepository authRepository;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody AuthRequestDto authRequest, BindingResult result, HttpServletResponse response) {
        //validate
        if(result.hasErrors())
            throw new BadRequestException();

        //save, get userinfo except password
        AuthResponseDto authResponse = authService.register(authRequest);

        //generate token, set cookie
        ResponseCookie cookie = ResponseCookie.from("access_token", new JwtToken().generateToken(authResponse))
                .httpOnly(true)
                .maxAge(1000L * 60 * 60 * 24 * 7)
                .build();

//        Cookie cookie = new Cookie("access_token", new JwtToken().generateToken(authResponse));
//        cookie.setHttpOnly(true);
//        cookie.setMaxAge(1000 * 60 * 60 * 24 * 7);
//        response.addCookie(cookie);

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(authResponse);

    }

    @PostMapping("/login")
    public void login(){
        //
    }

    @GetMapping("/check")
    public void check() {}

    @PostMapping("/logout")
    public void logout() {
        //쿠키에서 토큰 삭제
    }
}

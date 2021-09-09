package kirris.blog.controller;

import kirris.blog.domain.auth.AuthRequestDto;
import kirris.blog.domain.auth.AuthResponseDto;
import kirris.blog.exception.BadRequestException;
import kirris.blog.repository.AuthRepository;
import kirris.blog.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthRepository authRepository;
    private final AuthService authService;

    @PostMapping("/register")
    public void register(@Valid @RequestBody AuthRequestDto authRequest, BindingResult result) {
        //validate
        if(result.hasErrors())
            throw new BadRequestException();

        //check exist
        //hash password, save entity
        //get userinfo except password
        AuthResponseDto authResponse = authService.register(authRequest);

        //토큰 발급
        //cookie set
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

package kirris.blog.controller;

import kirris.blog.domain.auth.AuthRequestDto;
import kirris.blog.exception.BadRequestException;
import kirris.blog.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/register")
    public void register(@Valid @RequestBody AuthRequestDto auth, BindingResult result) {
        //validate
        if(result.hasErrors())
            throw new BadRequestException();

        //check exist

        //save entity
        //password? hashed password

        //토큰 발급

        //cookie set

        //Service? Entity?//
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

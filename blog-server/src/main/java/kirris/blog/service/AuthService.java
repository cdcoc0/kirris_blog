package kirris.blog.service;

import kirris.blog.domain.auth.Auth;
import kirris.blog.domain.auth.AuthRequestDto;
import kirris.blog.domain.auth.AuthResponseDto;
import kirris.blog.exception.ConflictException;
import kirris.blog.exception.UnauthorizedException;
import kirris.blog.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class AuthService {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AuthResponseDto register(AuthRequestDto authRequest) {

        //==check username whether exists
        checkExist(authRequest);

        //==encrypt password
        setPassword(authRequest);

        return authRepository.save(authRequest);
    }

    @Transactional
    public AuthResponseDto login(AuthRequestDto authRequest) {

        //==find by username
        List<Auth> getUser = authRepository.findByUsername(authRequest.getUsername());
        if(getUser.isEmpty())
            throw new UnauthorizedException("user not found");

        //==confirm password
        Auth user = getUser.get(0);
        matchPassword(authRequest.getPassword(), user.getPassword());

        return user.deletePassword();
    }

    private void checkExist(AuthRequestDto auth) {
        List<Auth> exist = authRepository.findByUsername(auth.getUsername());
        if(!exist.isEmpty())
            throw new ConflictException(auth.getUsername());
    }

    private void setPassword(AuthRequestDto authRequest) {
        String hashedPassword = passwordEncoder.encode(authRequest.getPassword());
        authRequest.setPassword(hashedPassword);
    }

    private void matchPassword(String requestPassword, String password) {
        if(!passwordEncoder.matches(requestPassword, password))
            throw new UnauthorizedException("password not match");
    }
}

package kirris.blog.service;

import kirris.blog.domain.auth.Auth;
import kirris.blog.domain.auth.AuthRequestDto;
import kirris.blog.domain.auth.AuthResponseDto;
import kirris.blog.exception.ConflictException;
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
        //check exist
        checkExist(authRequest);

        //encrypt password, save entity
        //return without password
        setPassword(authRequest);
        return authRepository.save(authRequest);
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
}

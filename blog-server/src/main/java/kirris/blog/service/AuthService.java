package kirris.blog.service;

import kirris.blog.domain.auth.Auth;
import kirris.blog.domain.auth.AuthRequestDto;
import kirris.blog.exception.ConflictException;
import kirris.blog.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class AuthService {

    private final AuthRepository authRepository;

    @Transactional
    public void register(AuthRequestDto auth) {
        //check exist
        checkExist(auth);

        //save entity
        //password? hashed password

        //토큰 발급

        //cookie set

    }

    private void checkExist(AuthRequestDto auth) {
        List<Auth> exist = authRepository.findByUsername(auth.getUsername());
        if(!exist.isEmpty())
            throw new ConflictException(auth.getUsername());
    }
}

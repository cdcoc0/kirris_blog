package kirris.blog.repository;

import kirris.blog.domain.auth.Auth;
import kirris.blog.domain.auth.AuthRequestDto;
import kirris.blog.domain.auth.AuthResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class AuthRepository {

    private final EntityManager em;

    public List<Auth> findByUsername(String username) {
        return em.createQuery("SELECT m from member m where m.username = :username", Auth.class)
                .setParameter("username", username)
                .getResultList();
    }

    public AuthResponseDto save(AuthRequestDto authRequest) {
        Auth auth = authRequest.toEntity();
        em.persist(auth);
        em.flush();

        return auth.deletePassword();
    }

    public Optional<Auth> findById(Long id) {
        Auth user = em.find(Auth.class, id);
        return Optional.ofNullable(user);
    }
}

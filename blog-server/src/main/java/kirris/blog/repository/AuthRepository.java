package kirris.blog.repository;

import kirris.blog.domain.auth.Auth;
import kirris.blog.domain.auth.AuthRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class AuthRepository {

    private final EntityManager em;

    public List<Auth> findByUsername(String username) {
        return em.createQuery("SELECT m from member m where m.username = :username", Auth.class)
                .setParameter("username", username)
                .getResultList();
    }

    public void save(AuthRequestDto authRequest) {
        em.persist(authRequest.toEntity());
    }
}

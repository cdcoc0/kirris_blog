package kirris.blog.repository;

import kirris.blog.domain.Posts;
import kirris.blog.domain.PostsRequestDto;
import kirris.blog.domain.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class PostsRepository {

    private final EntityManager em;

    public Long save(PostsRequestDto posts) {
        Posts post = posts.toEntity();
        em.persist(post);
        em.flush();
        return post.getId();
    }

    public List<Posts> findAll() {
        return em.createQuery("select p from Posts p order by p.id desc")
                //.setFirstResult()
                //.setMaxResults(9)
                .getResultList();
    }
}

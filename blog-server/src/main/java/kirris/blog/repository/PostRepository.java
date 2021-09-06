package kirris.blog.repository;

import kirris.blog.domain.Posts;
import kirris.blog.domain.PostsRequestDto;
import kirris.blog.domain.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@RequiredArgsConstructor
@Repository
public class PostRepository {

    private final EntityManager em;

    public Long save(PostsRequestDto posts) {
        Posts post = posts.toEntity();
        em.persist(post);
        em.flush();
        return post.getId();
    }
}

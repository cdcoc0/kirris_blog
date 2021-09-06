package kirris.blog.repository;

import kirris.blog.domain.Posts;
import kirris.blog.domain.PostsRequestDto;
import kirris.blog.domain.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

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
        return em.createQuery("SELECT p from Posts p ORDER BY p.id desc", Posts.class)
                //.setFirstResult()
                //.setMaxResults(9)
                .getResultList();
    }

    public Optional<Posts> findById(Long id) {
        Posts post =  em.find(Posts.class, id);
        return Optional.ofNullable(post);
    }

    /*public Posts update(Long id, PostsRequestDto post) {
        Posts targetPost = em.find(Posts.class, id);
        return em.merge(targetPost.builder().id(id).title(post.getTitle()).body(post.getBody()).tags(post.getTags()).build());
    }*/

    public void delete(Long id) {
        TypedQuery<Posts> query = em.createQuery("SELECT p from Posts p WHERE p.id = :id", Posts.class);
        query.setParameter("id", id);

        List<Posts> post = query.getResultList();
//        if(post.size() != 1) {
//            return false;
//        }
        em.remove(post.get(0));
    }
}

package kirris.blog.repository;

import kirris.blog.domain.posts.Posts;
import kirris.blog.domain.posts.PostsRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class PostsRepository {

    private final EntityManager em;

    public Posts save(PostsRequestDto posts) {
        Posts post = posts.toEntity();
        em.persist(post);
        em.flush();
        return post;
    }

    public List<Posts> findAll(int page) {
        return em.createQuery("SELECT p from Posts p ORDER BY p.id desc", Posts.class)
                .setFirstResult((page - 1) * 9)
                .setMaxResults(9)
                .getResultList();
        //last-page 전달?
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

    public Long countAll() {
        Query query = em.createQuery("SELECT COUNT(p) from Posts p");
        return (Long)query.getSingleResult();
    }
}

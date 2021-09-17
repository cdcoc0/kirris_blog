package kirris.blog.repository;

import kirris.blog.domain.auth.Auth;
import kirris.blog.domain.posts.Posts;
import kirris.blog.domain.posts.PostsRequestDto;
import kirris.blog.exception.NotFoundException;
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

    public Posts save(PostsRequestDto posts, Auth user) {
        Posts post = posts.toEntity();
        post.addAuth(user);
        em.persist(post);
        em.flush();
        return post;
    }

    public List<Posts> findAll(int page) {
        return em.createQuery("SELECT p from Posts p ORDER BY p.id desc", Posts.class)
                .setFirstResult((page - 1) * 9)
                .setMaxResults(9)
                .getResultList();
    }

    public List<Posts> findAllByTag(int page, String tag) {
        return em.createQuery("SELECT p from Posts p WHERE p.tags LIKE CONCAT('%', :tag, '%') ORDER BY p.id desc", Posts.class)
                .setParameter("tag", tag)
                .setFirstResult((page - 1) * 9)
                .setMaxResults(9)
                .getResultList();
    }

    public Optional<Posts> findById(Long id) {
        Posts post = em.find(Posts.class, id);
        return Optional.ofNullable(post);
    }

    public void delete(Long id) {
        TypedQuery<Posts> query = em.createQuery("SELECT p from Posts p WHERE p.id = :id", Posts.class);
        query.setParameter("id", id);

        List<Posts> post = query.getResultList();
        if(post.size() != 1) {
            throw new NotFoundException(id);
        }
        em.remove(post.get(0));
    }

    public Long countAll() {
        Query query = em.createQuery("SELECT COUNT(p) from Posts p");
        return (Long)query.getSingleResult();
    }
}

package hello.mylogin.forum.post;

import hello.mylogin.forum.page.PageParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Transactional
@Slf4j
public class JpaPostRepositoryV1 implements PostRepository {
    //querydsl 없이 jpql을 사용한 버전
    private final EntityManager em;

    public JpaPostRepositoryV1(EntityManager em) {
        this.em = em;
    }

    @Override
    public Post addPost(Post post) {
        em.persist(post);
        return post;
    }

    @Override
    public void updatePost(Long id, Post updatePost) {
        Post findPost = em.find(Post.class,id);
        findPost.setTitle(updatePost.getTitle());
        findPost.setContents(updatePost.getContents());
        findPost.setUpdated(true);
    }

    @Override
    public void deletePost(Long id, Long forumUserId) {
        Post target = em.find(Post.class, id);
        if(target.getMember().getId().equals(forumUserId)) {
            target.setDeleted(true);
        }
    }

    @Override
    public Optional<Post> findById(Long id) {
        Post post = em.find(Post.class, id);
        return Optional.ofNullable(post);
    }

    @Override
    public List<Post> findAll() {
        String jpql = "select p from Post p";
        TypedQuery<Post> query = em.createQuery(jpql,Post.class);

        return query.getResultList();
    }

    @Override
    public List<Post> findLimit(PageParam pageParam) {
        String jpql = "select p from Post p where p.isDeleted = false order by p.id DESC";
        TypedQuery<Post> query = em.createQuery(jpql,Post.class);

        query.setFirstResult(pageParam.getSkip());
        query.setMaxResults(pageParam.getAmount());
        return query.getResultList();
    }

    @Override
    public List<Post> findByTitle(String keyword, PageParam pageParam) {
        String jpql = "select p from Post p";
        jpql += " where p.title like concat('%',:keyword,'%') and p.isDeleted = false order by p.id DESC";
        TypedQuery<Post> query = em.createQuery(jpql,Post.class).setParameter("keyword",keyword);

        query.setFirstResult(pageParam.getSkip());
        query.setMaxResults(pageParam.getAmount());

        return query.getResultList();
    }

    @Override
    public List<Post> findByTitleAll(String keyword) {
        String jpql = "select p from Post p";
        jpql += " where p.title like concat('%',:keyword,'%') and p.isDeleted = false order by p.id DESC";
        TypedQuery<Post> query = em.createQuery(jpql,Post.class).setParameter("keyword",keyword);

        return query.getResultList();
    }
}

package hello.mylogin.forum.post;

import hello.mylogin.member.Member;

import java.util.*;

public class MemoryPostRepository implements PostRepository {

    private final static Map<Long, Post> store = new HashMap<>();
    private static long sequence = 0L;


    @Override
    public Post addPost(Post post) {
        return null;
    }

    @Override
    public Post updatePost(Post updatePost) {
        return null;
    }

    @Override
    public void deletePost(Long id, Long forumUserId) {

    }

    @Override
    public List<Post> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Post findById(Long id) {
        return null;
    }

    @Override
    public Optional<Member> findByUserId(Long forumUserId) {
        return Optional.empty();
    }
}

package hello.mylogin.forum.post;

import java.util.List;

public class JpaPostRepositoryV1 implements PostRepository {
    @Override
    public Post addPost(Post post, Long userId, String userName) {
        return null;
    }

    @Override
    public Post updatePost(Long id, Post updatePost) {
        return null;
    }

    @Override
    public void deletePost(Long id, Long forumUserId) {

    }

    @Override
    public Post findById(Long id) {
        return null;
    }

    @Override
    public List<Post> findAll() {
        return null;
    }
}

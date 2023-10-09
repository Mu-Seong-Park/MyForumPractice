package hello.mylogin.service;

import hello.mylogin.forum.post.Post;
import hello.mylogin.forum.post.PostRepository;
import hello.mylogin.member.Member;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
public class ForumService {
    private final PostRepository postRepository;

    public ForumService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post writePost(Post post) {
        Post newPost = postRepository.addPost(post);
        log.info("글쓰기 완료");

        return newPost;
    }

    public void deletePost(Long postId, Long userId) {
        postRepository.deletePost(postId, userId);
        log.info("삭제 완료");
    }

    public void updatePost(Post update) {
        postRepository.updatePost(update);
        log.info("수정 완료");
    }

    public List<Post> findAll() {

        log.info("전체 검색 완료");

        return postRepository.findAll();
    }
}

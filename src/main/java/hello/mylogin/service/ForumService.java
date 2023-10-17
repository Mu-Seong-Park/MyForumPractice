package hello.mylogin.service;

import hello.mylogin.forum.post.Post;
import hello.mylogin.forum.post.PostRepository;
import hello.mylogin.forum.post.SearchPostDto;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class ForumService {
    private final PostRepository postRepository;

    public ForumService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post writePost(Post post,Long userId, String userName) {
        Post newPost = postRepository.addPost(post,userId, userName);
        log.info("글쓰기 완료");

        return newPost;
    }

    public void deletePost(Long postId, Long userId) {
        postRepository.deletePost(postId, userId);
        log.info("삭제 완료");
    }

    public void updatePost(Long id , Post update) {
        postRepository.updatePost(id ,update);
        log.info("수정 완료");
    }

    public Post findPostById(Long id) {
        return postRepository.findById(id);
    }

    public List<Post> findAll() {

        log.info("전체 검색 완료");

        return postRepository.findAll();
    }

    public List<Post> searchPost(String keyword) {

        log.info("일부 검색 완료");

        return postRepository.findByTitle(keyword);
    }
}

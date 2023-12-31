package hello.mylogin.forum.post;

import hello.mylogin.forum.page.PageParam;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    //게시글 등록
    Post addPost(Post post);

    //게시글 수정
    void updatePost(Long id, Post updatePost);

    //게시글 삭제(데이터는 남겨두고 boolean 타입으로 삭제 제어)
    void deletePost(Long id, Long forumUserId);

    //게시글 찾기
    Optional<Post> findById(Long id);

    List<Post> findAll();

    List<Post> findLimit(PageParam pageParam);

    List<Post> findByTitle(String keyword, PageParam pageParam);

    List<Post> findByTitleAll(String keyword);
}

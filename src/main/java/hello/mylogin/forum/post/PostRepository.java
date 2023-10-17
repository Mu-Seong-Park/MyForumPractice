package hello.mylogin.forum.post;

import hello.mylogin.member.Member;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    //게시글 등록
    Post addPost(Post post, Long userId, String userName);

    //게시글 수정
    Post updatePost(Long id, Post updatePost);

    //게시글 삭제(데이터는 남겨두고 boolean 타입으로 삭제 제어)
    void deletePost(Long id, Long forumUserId);

    //게시글 찾기
    Post findById(Long id);

    List<Post> findAll();

    List<Post> findByTitle(String keyword);
}

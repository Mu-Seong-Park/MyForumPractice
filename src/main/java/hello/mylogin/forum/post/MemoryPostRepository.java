package hello.mylogin.forum.post;

import hello.mylogin.member.Member;

import java.util.*;

public class MemoryPostRepository implements PostRepository {

    private final static Map<Long, Post> store = new HashMap<>();
    private static long sequence = 0L;


    @Override
    public Post addPost(Post post) {
        post.setId(++sequence);
        store.put(post.getId(),post);
        return post;
    }

    @Override
    public Post updatePost(Post updatePost) {
        Post prePost = store.get(updatePost.getId());
        prePost.setContents(updatePost.getContents());
        prePost.setUpdateDate(new Date());
        return prePost;
    }

    @Override
    public void deletePost(Long id, Long forumUserId) {
        Post targetPost = store.get(id);
        if(targetPost.getForumUserId() == forumUserId) {
            targetPost.setDeleted(true);
        }

    }

    @Override
    public List<Post> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Post findById(Long id) {
        return null;
    }

    //추가할 기능 : 검색 결과를 보여줄 때 무엇을 기준으로 할지 (user id , 제목 , 컨텐츠 내용)

}

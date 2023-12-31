package hello.mylogin.forum.post;

import hello.mylogin.forum.page.PageParam;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.*;

public class MemoryPostRepository implements PostRepository {

    private final static Map<Long, Post> store = new HashMap<>();
    private static long sequence = 0L;


//    @Override
//    public Post addPost(Post post, Long userId,String userName) {
//        post.setId(++sequence);
//        post.setWrittenDate(LocalDateTime.now());
////        post.setForumUserId(userId);
////        post.setForumUserName(userName);
//        store.put(post.getId(),post);
//        return post;
//    }

    @Override
    public Post addPost(Post post) {
        return null;
    }

    @Override
    public void updatePost(Long id, Post updatePost) {
        Post prePost = store.get(id);
        prePost.setTitle(updatePost.getTitle());
        prePost.setContents(updatePost.getContents());
        prePost.setUpdateDate(LocalDateTime.now());
        prePost.setUpdated(true);
    }

    @Override
    public void deletePost(Long id, Long forumUserId) {
        Post targetPost = store.get(id);
//        if(targetPost.getForumUserId() == forumUserId) {
//            targetPost.setDeleted(true);
//        }

    }

    @Override
    public List<Post> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public List<Post> findLimit(PageParam pageParam) {
        return null;
    }

    @Override
    public Optional<Post> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    //추가할 기능 : 검색 결과를 보여줄 때 무엇을 기준으로 할지 (user id , 제목 , 컨텐츠 내용)

    @Override
    public List<Post> findByTitle(String keyword, PageParam pageParam) {
        List<Post> result = new ArrayList<>();
        for (Post target : store.values()) {
            if(target.getTitle().contains(keyword)) {
                result.add(target);
            }
        }
        return result;
    }

    @Override
    public List<Post> findByTitleAll(String keyword) {
        return null;
    }

}

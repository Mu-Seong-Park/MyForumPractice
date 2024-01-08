package hello.mylogin.service;

import hello.mylogin.forum.page.PageParam;
import hello.mylogin.forum.post.Post;
import hello.mylogin.forum.post.PostRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class PageService {

    PostRepository postRepository;

    public PageService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> pageList(PageParam page) {
        return postRepository.findLimit(page);
    }
}

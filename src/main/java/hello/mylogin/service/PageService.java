package hello.mylogin.service;

import hello.mylogin.forum.post.PostRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PageService {

    PostRepository postRepository;

    public PageService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Page
}

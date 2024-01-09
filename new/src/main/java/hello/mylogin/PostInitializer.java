package hello.mylogin;

import hello.mylogin.forum.post.JpaPostRepositoryV1;
import hello.mylogin.forum.post.Post;
import hello.mylogin.forum.post.PostRepository;
import hello.mylogin.member.JpaMemberRepositoryV1;
import hello.mylogin.member.Member;
import hello.mylogin.member.MemberRepository;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class PostInitializer {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    public PostInitializer(PostRepository postRepository, MemberRepository memberRepository) {
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
    }

    @PostConstruct
    public void init() {
        // 더미 데이터 작성 및 저장
        Member member = new Member();
        member.setEmail("test");
        member.setPassword("1");
        member.setName("tester");
        memberRepository.addMember(member);
        for (int i = 0 ; i < 50 ; i++) {
            Post post = new Post();
            post.setTitle(i+"번째 게시글");
            post.setContents(i+"번째 게시글의 내용");
            post.setMember(member);
            postRepository.addPost(post);
        }
    }
}

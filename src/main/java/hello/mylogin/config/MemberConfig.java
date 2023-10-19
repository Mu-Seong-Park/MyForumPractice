package hello.mylogin.config;

import hello.mylogin.forum.post.JpaPostRepositoryV1;
import hello.mylogin.forum.post.PostRepository;
import hello.mylogin.member.JpaMemberRepositoryV1;
import hello.mylogin.member.MemberRepository;
import hello.mylogin.service.ForumService;
import hello.mylogin.service.LoginService;
import hello.mylogin.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
public class MemberConfig {
    //repository를 리팩토링하기 위해서 빈을 수동 등록한다.

    private final EntityManager em;

    public MemberConfig(EntityManager em) {
        this.em = em;
    }


    @Bean
    public MemberRepository memberRepository() {
        return new JpaMemberRepositoryV1(em);
    }

    @Bean
    public PostRepository postRepository() {
        return new JpaPostRepositoryV1(em);
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public LoginService loginService() {
        return new LoginService(memberRepository());
    }

    @Bean
    public ForumService forumService() {
        return new ForumService(postRepository());
    }


}

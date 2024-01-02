package hello.mylogin.config;

import hello.mylogin.forum.post.JpaPostRepositoryV1;
import hello.mylogin.forum.post.PostRepository;
import hello.mylogin.member.JpaMemberRepositoryV1;
import hello.mylogin.member.MemberRepository;
import hello.mylogin.service.*;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.client.RestTemplate;

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

    @Bean
    public PageService pageService() {
        return new PageService(postRepository());
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    @Bean
    public VideoService videoService(ApplicationEventPublisher eventPublisher) {
        return new VideoService(eventPublisher,restTemplate());
    }

}

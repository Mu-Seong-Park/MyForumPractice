package hello.mylogin.config;

import hello.mylogin.controller.MemberController;
import hello.mylogin.member.MemberRepository;
import hello.mylogin.member.MemoryMemberRepository;
import hello.mylogin.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class MemberConfig {
    //repository를 리팩토링하기 위해서 빈을 직접 등록한다.

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberController memberController() {
        log.info("Controller Bean 등록");
        return new MemberController(memberService());
    }

}

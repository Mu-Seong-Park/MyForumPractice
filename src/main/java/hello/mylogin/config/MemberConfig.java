package hello.mylogin.config;

import hello.mylogin.controller.MemberController;
import hello.mylogin.member.MemberRepository;
import hello.mylogin.member.MemoryMemberRepository;
import hello.mylogin.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
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
        return new MemberController(memberService());
    }

}

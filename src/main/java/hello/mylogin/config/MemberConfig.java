package hello.mylogin.config;

import hello.mylogin.controller.MemberController;
import hello.mylogin.member.MemberRepository;
import hello.mylogin.member.MemoryMemberRepository;
import hello.mylogin.service.LoginService;
import hello.mylogin.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MemberConfig {
    //repository를 리팩토링하기 위해서 빈을 수동 등록한다.

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public LoginService loginService() {
        return new LoginService(memberRepository());
    }


}

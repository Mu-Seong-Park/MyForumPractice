package hello.mylogin.service;

import hello.mylogin.member.Member;
import hello.mylogin.member.MemberRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginService {

    private final MemberRepository memberRepository;


    public LoginService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member login(String email, String password) {
        return memberRepository.findByEmail(email)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }
}
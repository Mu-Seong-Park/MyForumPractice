package hello.mylogin.service;

import hello.mylogin.member.LoginDto;
import hello.mylogin.member.Member;
import hello.mylogin.member.MemberRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
public class LoginService {

    private final MemberRepository memberRepository;


    public LoginService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member login(String email, String password) {

        return memberRepository.findByEmail(email).stream().filter(m -> m.getPassword().equals(password)).findFirst().get();

    }
}
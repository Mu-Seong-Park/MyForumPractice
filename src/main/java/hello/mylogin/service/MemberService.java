package hello.mylogin.service;

import hello.mylogin.member.Member;
import hello.mylogin.member.MemberRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void addMember(Member member) {
        memberRepository.addMember(member);
    }
}

package hello.mylogin.service;

import hello.mylogin.member.Member;
import hello.mylogin.member.MemberRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Optional<Member> addMember(Member member) {
        Optional<Member> registeredMember = memberRepository.addMember(member);
        log.info("저장 완료");

        return registeredMember;
    }

    public void deleteMember(Long id) {
        memberRepository.deleteMember(id);
        log.info("삭제 완료");
    }

    public void updateMember(Member member) {
        memberRepository.updateMember(member);
        log.info("수정 완료");
    }

    public Member findById(Long id) {
        Member foundMember = memberRepository.findById(id);
        log.info("검색 완료");

        return foundMember;
    }

    public Optional<Member> findByEmail(String email) {
        Optional<Member> foundMember = memberRepository.findByEmail(email);
        log.info("검색 완료");

        return foundMember;
    }


}

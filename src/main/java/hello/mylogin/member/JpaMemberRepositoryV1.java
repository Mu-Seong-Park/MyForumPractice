package hello.mylogin.member;

import java.util.Optional;

public class JpaMemberRepositoryV1 implements MemberRepository{

    @Override
    public Optional<Member> addMember(Member member) {
        return null;
    }

    @Override
    public Member updateMember(Member updateMember) {
        return null;
    }

    @Override
    public void deleteMember(Long id) {

    }

    @Override
    public Member findById(Long id) {
        return null;
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return null;
    }
}

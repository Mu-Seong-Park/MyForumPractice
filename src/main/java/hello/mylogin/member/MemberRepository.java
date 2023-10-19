package hello.mylogin.member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    //멤버 등록
    Optional<Member> addMember(Member member);

    //멤버 수정
    void updateMember(Member updateMember);

    //멤버 삭제(데이터는 남겨두고 boolean 타입으로 삭제 제어
    void deleteMember(Long id);

    //멤버 찾기
    Optional<Member> findById(Long id);
    List<Member> findByEmail(String email);
}

package hello.mylogin.member;

public interface MemberRepository {

    //멤버 등록
    Member addMember(Member member);

    //멤버 수정
    Member updateMember(Long id);

    //멤버 삭제(데이터는 남겨두고 boolean 타입으로 삭제 제어
    void deleteMember(Long id);

    //멤버 찾기
    Member findById(Long id);
    Member findByEmail(String email);
}

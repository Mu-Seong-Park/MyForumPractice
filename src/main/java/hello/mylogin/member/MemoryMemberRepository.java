package hello.mylogin.member;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class MemoryMemberRepository implements MemberRepository{

    private final static Map<Long,Member> store = new HashMap<>();
    private static long sequence = 0L;


    @Override
    public Member addMember(Member member) {
        member.setId(++sequence);
        store.put(member.getId(),member);
        return member;
    }

    @Override
    public Member updateMember(Member updateMember) {

        Member member = new Member();
        member.setId(updateMember.getId());
        member.setName(updateMember.getName());
        member.setEmail(updateMember.getEmail());
        member.setPassword(updateMember.getPassword());
        member.setDeleted(false);

        store.put(member.getId(),member);

        return member;
    }

    @Override
    public void deleteMember(Long id) {
        Member member = store.get(id);
        member.setDeleted(true);
        log.info("delete member id and name : "+member.getId()+" "+member.getName());
    }

    @Override
    public Member findById(Long id) {
        return store.get(id);
    }

    @Override
    public Member findByEmail(String email) {
        for (Member member : store.values()) {
            if(member.getEmail().equals(email)) {
                return member;
            }
        }
        log.info("찾을 수 없는 회원 입니다.");
        return null;
    }
}

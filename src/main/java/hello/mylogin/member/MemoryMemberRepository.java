package hello.mylogin.member;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class MemoryMemberRepository implements MemberRepository{

    private final static Map<Long,Member> store = new HashMap<>();
    private static long sequence = 0L;


    @Override
    public Optional<Member> addMember(Member member) {

        if(findByEmail(member.getEmail()).isEmpty()) {
            log.info("이미 존재하는 회원입니다.");
            return Optional.empty();
        }

        member.setId(++sequence);
        store.put(member.getId(),member);
        log.info("멤버 저장 완료, 이름 : {}, email : {} , password : {}",member.getName(), member.getEmail(), member.getPassword());
        Optional<Member> addedMember = Optional.of(member);
        return addedMember;
    }

    @Override
    public void updateMember(Member updateMember) {

        Member member = new Member();
        member.setId(updateMember.getId());
        member.setName(updateMember.getName());
        member.setEmail(updateMember.getEmail());
        member.setPassword(updateMember.getPassword());
        member.setDeleted(false);

        store.put(member.getId(),member);
        log.info("멤버 저장 완료, 이름 : {}, email : {} , password : {}",member.getName(), member.getEmail(), member.getPassword());
    }

    @Override
    public void deleteMember(Long id) {
        Member member = store.get(id);
        member.setDeleted(true);
        log.info("delete member id and name : "+member.getId()+" "+member.getName());
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public List<Member> findByEmail(String email) {
//        return findAll().stream()
//                .filter(m -> m.getEmail().equals(email))
//                .findFirst();
        return findAll();
    }

}

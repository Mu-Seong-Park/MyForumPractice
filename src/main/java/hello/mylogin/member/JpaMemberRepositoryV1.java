package hello.mylogin.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Transactional
@Slf4j
public class JpaMemberRepositoryV1 implements MemberRepository{

    private final EntityManager em;

    public JpaMemberRepositoryV1(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Member> addMember(Member member) {
        if(!findByEmail(member.getEmail()).isEmpty()) {
            log.info("이미 존재하는 회원입니다.");
            return Optional.empty();
        }
        em.persist(member);
        log.info("멤버 저장 완료, 이름 : {}, email : {} , password : {}",member.getName(), member.getEmail(), member.getPassword());
        return Optional.of(member);
    }

    @Override
    public void updateMember(Member updateMember) {
        Member findMember = em.find(Member.class,updateMember.getId());
        findMember.setName(updateMember.getName());
        findMember.setRole(updateMember.getRole());
        findMember.setEmail(updateMember.getEmail());
        findMember.setPassword(updateMember.getPassword());
        //패스워드를 바꾸는 것은 따로 설정을 해야할 지도 모른다.
    }

    @Override
    public void deleteMember(Long id) {
        Member target = em.find(Member.class, id);
        target.setDeleted(true);
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member target = em.find(Member.class, id);
        return Optional.ofNullable(target);
    }

    @Override
    public List<Member> findByEmail(String email) {
        String jpql = "select m from Member m";

        jpql += " where m.email = :email";

        log.info("jpql={}",jpql);

        TypedQuery<Member> query = em.createQuery(jpql,Member.class).setParameter("email",email);

        return query.getResultList();
    }
}

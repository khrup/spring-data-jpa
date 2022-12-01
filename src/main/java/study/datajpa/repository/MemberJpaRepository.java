package study.datajpa.repository;

import org.springframework.stereotype.Repository;
import study.datajpa.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberJpaRepository {

    //
    @PersistenceContext
    private EntityManager em;

    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    public void delete(Member member) {
        em.remove(member);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from MEMBER m", Member.class) //JPQL 문법
                .getResultList();
    }

    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    public Long count() {
        return em.createQuery("select count(m) from MEMBER m", Long.class).getSingleResult();
    }

    public Member find(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findByUsername(String username) {
        return em.createNamedQuery("Member.findByUsername", Member.class)
                .setParameter("username", username)
                .getResultList(); //Member.java 에 선언해놓은 쿼리 사용가능
    }

    public List<Member> findByPage(int age, int offset, int limit) {
        return em.createQuery("select m from MEMBER m where m.age = :age order by m.username desc")
                .setParameter("age", age)
                .setFirstResult(offset) //몇번째 부터 가져올 것인지
                .setMaxResults(limit) //몇개 가져올것인지
                .getResultList();
    }

    public long totalCount(int age) {
        return em.createQuery("select count(m) from MEMBER m where m.age = :age", Long.class)
                .setParameter("age", age)
                .getSingleResult();
    }

    public int bulkAgePlus(int age) {
        return em.createQuery("update MEMBER m set m.age = m.age + 1 where m.age >= :age")
                .setParameter("age", age)
                .executeUpdate();
    }
}

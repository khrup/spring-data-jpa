package study.datajpa.repository;

import lombok.RequiredArgsConstructor;
import study.datajpa.entity.Member;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
//MemberRepositoryImpl -> MemberRepository + 'Impl' 규칙을 맞춰줘야한다. Interface 는 규칙이 별도로 없음.
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final EntityManager em;

    @Override
    public List<Member> findMemberCustom() {
        return em.createQuery("select m from Member m ")
                .getResultList();
    }
}

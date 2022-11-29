package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void testMember() {
        Member member = new Member("memberA");
        Member savedMember = memberRepository.save(member);

        Member findMember = memberRepository.findById(savedMember.getId()).orElse(new Member("memberB"));

        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        Assertions.assertThat(findMember).isEqualTo(member);

    }

    @Test
    public void 회원CRUD_테스트() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");

        memberRepository.save(member1);
        memberRepository.save(member2);

        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();

        assertEquals(member1, findMember1);
        assertEquals(member2, findMember2);

        List<Member> all = memberRepository.findAll();
        assertEquals(all.size(), 2);

        long count = memberRepository.count();
        assertEquals(count, 2);

        memberRepository.delete(member1);
        memberRepository.delete(member2);
        long deleteCount = memberRepository.count();
        assertEquals(deleteCount, 0);
    }

    @Test
    public void WHERE조건_테스트(){

        Member member1 = new Member("AAA", 10);
        Member member2 = new Member("AAA", 12);

        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> findByMember = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 11);

        assertEquals(1, findByMember.size());
        assertEquals(12, findByMember.get(0).getAge());
    }

    @Test
    public void testNamedQuery() {
        Member member1 = new Member("member1", 10);
        Member member2 = new Member("member2", 20);

        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> result = memberRepository.findByUsername("member2");
        Member member = result.get(0);
        assertEquals(member2, member);

    }

}

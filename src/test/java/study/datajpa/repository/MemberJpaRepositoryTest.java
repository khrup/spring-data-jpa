package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberJpaRepositoryTest {

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    public void testMember() {
        Member member = new Member("memberA");
        Member savedMember = memberJpaRepository.save(member);

        Member findMember = memberJpaRepository.find(savedMember.getId());

        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        Assertions.assertThat(findMember).isEqualTo(member); // findMember == member 비교함, jpa는 같은 트랜잭션안에서는 영속성을 유지함
    }

    @Test
    public void 회원CRUD() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");

        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        Member findMember1 = memberJpaRepository.findById(member1.getId()).get();
        Member findMember2 = memberJpaRepository.findById(member2.getId()).get();

        assertEquals(member1, findMember1);
        assertEquals(member2, findMember2);

        List<Member> all = memberJpaRepository.findAll();
        assertEquals(all.size(), 2);

        long count = memberJpaRepository.count();
        assertEquals(count, 2);

        memberJpaRepository.delete(member1);
        memberJpaRepository.delete(member2);
        long deleteCount = memberJpaRepository.count();
        assertEquals(deleteCount, 0);
    }

    @Test
    public void testNamedQuery() {
        Member member1 = new Member("member1", 10);
        Member member2 = new Member("member2", 20);

        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        List<Member> result = memberJpaRepository.findByUsername("member2");
        Member member = result.get(0);
        assertEquals(member2, member);

    }

    @Test
    public void paging() {

        memberJpaRepository.save(new Member("member1", 10));
        memberJpaRepository.save(new Member("member2", 10));
        memberJpaRepository.save(new Member("member3", 10));
        memberJpaRepository.save(new Member("member4", 10));
        memberJpaRepository.save(new Member("member5", 10));
        memberJpaRepository.save(new Member("member6", 10));
        memberJpaRepository.save(new Member("member7", 10));
        memberJpaRepository.save(new Member("member8", 10));

        int age = 10;
        int offset = 4; //3 -> 5,4,3 , 2 -> 6,5,4 , 1 -> 7,6,5 , 0 -> 8,7,6
        int limit = 3;


        //when
        List<Member> members = memberJpaRepository.findByPage(age, offset, limit);

        members.stream().forEach(member -> System.out.println("member = " + member));

        long totalCount = memberJpaRepository.totalCount(age);

        //then
        assertEquals(members.size(), 3);
        assertEquals(totalCount, 8);
    }

    @Test
    public void bulkUpdate() {
        memberJpaRepository.save(new Member("member1", 10));
        memberJpaRepository.save(new Member("member2", 11));
        memberJpaRepository.save(new Member("member3", 12));
        memberJpaRepository.save(new Member("member4", 13));
        memberJpaRepository.save(new Member("member5", 14));
        memberJpaRepository.save(new Member("member6", 15));
        memberJpaRepository.save(new Member("member7", 16));
        memberJpaRepository.save(new Member("member8", 17));

        int i = memberJpaRepository.bulkAgePlus(13);
        assertEquals(5, i);
    }

}
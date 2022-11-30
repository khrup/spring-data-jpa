package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TeamRepository teamRepository;

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
    public void WHERE조건_테스트() {

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

    @Test
    public void testQuery() {
        Member member1 = new Member("member1", 10);
        Member member2 = new Member("member2", 20);

        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> result = memberRepository.findUser("member2", 20);
        Member member = result.get(0);
        assertEquals(member2, member);
    }

    @Test
    public void testFindUsername() {
        Member member1 = new Member("member1", 10);
        Member member2 = new Member("member2", 20);

        memberRepository.save(member1);
        memberRepository.save(member2);

        List<String> allUserName = memberRepository.findAllUserName();
        allUserName.stream().forEach(o -> System.out.println(o));

    }

    @Test
    public void findMemberDto() {

        Team team_a = new Team("TEAM_A");
        teamRepository.save(team_a);

        Member member = new Member("AAA", 10);
        member.setTeam(team_a);
        memberRepository.save(member);

        List<MemberDto> memberDto = memberRepository.findMemberDto();
        memberDto.stream().forEach(o -> System.out.println("o = " + o));
    }

    @Test
    public void testFindUsernames() {
        Member member1 = new Member("member1", 10);
        Member member2 = new Member("member2", 20);

        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> allUserName = memberRepository.findByNames(List.of("member1", "member2"));
        allUserName.stream().forEach(o -> System.out.println(o));

    }

    @Test
    public void testReturnType() {
        Member member1 = new Member("member1", 10);
        Member member2 = new Member("member2", 20);

        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> findMember1 = memberRepository.findListByUsername1("member1");
        Member findMember2 = memberRepository.findListByUsername2("member2");
        Optional<Member> findMember3 = memberRepository.findListByUsername3("member1");

        System.out.println("findMember2 = " + findMember2);
    }
}

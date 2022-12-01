package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @PersistenceContext
    private EntityManager em;
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

    @Test
    public void paging() {

        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));
        memberRepository.save(new Member("member6", 10));
        memberRepository.save(new Member("member7", 10));
        memberRepository.save(new Member("member8", 10));

        int age = 10;
        //spring data jpa 의 페이징은 0부터 시작한다.
        //0번째에서 3개를 가져온다는 의미, username 기준으로 desc 정렬
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));

        //when
        Page<Member> pagedMember = memberRepository.findByAge(age, pageRequest);
        List<Member> members = pagedMember.getContent();
        //getContent 와 toList 의 차이는 뭘까??

        //Entity를 곧바로 사용하여 controller에서 return 하면 api 스팩이 바뀔수 있기 때문에 항상 dto 로 변환을 하고 return 해야한다.
        Page<MemberDto> pagedMemberDto = pagedMember.map(m -> new MemberDto(m.getId(), m.getUsername(), null));

        members.stream().forEach(member -> System.out.println("member = " + member));
        //then
        assertEquals(3, members.size());
        assertEquals(8, pagedMember.getTotalElements());
        assertEquals(0, pagedMember.getNumber()); //페이지 번호
        assertEquals(3, pagedMember.getTotalPages()); //총 페이지 개수
        assertEquals(true, pagedMember.isFirst()); //첫번째 페이지인가?
        assertEquals(true, pagedMember.hasNext()); //다음페이지를 가지고있는가?
        assertEquals(false, pagedMember.isLast()); //마지막 페이지인가?


    }

    @Test
    public void slice() {

        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));
        memberRepository.save(new Member("member6", 10));
        memberRepository.save(new Member("member7", 10));
        memberRepository.save(new Member("member8", 10));

        //spring data jpa 의 페이징은 0부터 시작한다.
        //0번째에서 3개를 가져온다는 의미, username 기준으로 desc 정렬
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));
        int age = 10;

        //when
        Slice<Member> pagedMember = memberRepository.findSliceByAge(age, pageRequest);
        List<Member> members = pagedMember.getContent();
        //getContent 와 toList의 차이는 뭘까??

        members.stream().forEach(member -> System.out.println("member = " + member));
        //then
        assertEquals(3, members.size());
//        assertEquals(8, pagedMember.getTotalElements());
        assertEquals(0, pagedMember.getNumber()); //페이지 번호
//        assertEquals(3, pagedMember.getTotalPages()); //총 페이지 개수
        assertEquals(true, pagedMember.isFirst()); //첫번째 페이지인가?
        assertEquals(true, pagedMember.hasNext()); //다음페이지를 가지고있는가?
        assertEquals(false, pagedMember.isLast()); //마지막 페이지인가?

    }

    @Test
    public void bulkUpdate() {
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 11));
        memberRepository.save(new Member("member3", 12));
        memberRepository.save(new Member("member4", 13));
        memberRepository.save(new Member("member5", 14));
        memberRepository.save(new Member("member6", 15));
        memberRepository.save(new Member("member7", 16));
        memberRepository.save(new Member("member8", 17));

        int i = memberRepository.bulkAgePlus(13);

//        em.flush(); //
//        em.clear(); //영속성 컨텍스트 내용 책보고 이해하기


        List<Member> member5 = memberRepository.findByUsername("member5");
        Member member = member5.get(0);
        System.out.println("member = " + member);

        assertEquals(5, i);
    }
}

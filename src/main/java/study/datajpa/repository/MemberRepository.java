package study.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, JpaSpecificationExecutor<Member> { //Long은 Member 의 key

    //1. 메소드 이름으로 쿼리생성
    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    List<Member> findTop3HelloBy();

    @Query(name = "Member.findByUsername")
        //사실 @Query없어도 잘돌아감.. 관례상 findByUsername을 먼저 찾고 없으면 그 때 메소드쿼리가 실행됨.
    List<Member> findByUsername(@Param("username") String username); //@Param은 Member.java의 query파라미터에 따라 설정해야함

    @Query("select m from MEMBER m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from MEMBER m")
    List<String> findAllUserName();

    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) from MEMBER m join m.team t")
    List<MemberDto> findMemberDto();

    @Query("select m from MEMBER m where m.username in :names")
    List<Member> findByNames(@Param("names") List<String> names);

    @Query("select m from MEMBER m where m.username in :name")
    List<Member> findListByUsername1(@Param("name") String username);

    @Query("select m from MEMBER m where m.username in :name")
    Member findListByUsername2(@Param("name") String username);

    @Query("select m from MEMBER m where m.username in :name")
    Optional<Member> findListByUsername3(@Param("name") String username);// 단건 조회는 왠만하면 Optional을 사용하자!!

    @Query(value = "select m from MEMBER m left join m.team t",
            countQuery = "select count(m) from MEMBER m") //카운트 쿼리를 분기할 수 있다.
    Page<Member> findByAge(int age, Pageable pageable);
    Slice<Member> findSliceByAge(int age, Pageable pageable); //Slice는 totalCount를 조회하지 않는다.

    @Modifying(clearAutomatically = true)
    @Query("update MEMBER m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);
}

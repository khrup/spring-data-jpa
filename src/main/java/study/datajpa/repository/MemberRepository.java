package study.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, JpaSpecificationExecutor<Member>, MemberRepositoryCustom { //Long은 Member 의 key

    //1. 메소드 이름으로 쿼리생성
    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    List<Member> findTop3HelloBy();

    @Query(name = "Member.findByUsername")
        //사실 @Query없어도 잘돌아감.. 관례상 findByUsername을 먼저 찾고 없으면 그 때 메소드쿼리가 실행됨.
    List<Member> findByUsername(@Param("username") String username); //@Param은 Member.java의 query파라미터에 따라 설정해야함

    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
    List<String> findAllUserName();

    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") List<String> names);

    @Query("select m from Member m where m.username in :name")
    List<Member> findListByUsername1(@Param("name") String username);

    @Query("select m from Member m where m.username in :name")
    Member findListByUsername2(@Param("name") String username);

    @Query("select m from Member m where m.username in :name")
    Optional<Member> findListByUsername3(@Param("name") String username);// 단건 조회는 왠만하면 Optional을 사용하자!!

    @Query(value = "select m from Member m left join m.team t",
            countQuery = "select count(m) from Member m")
        //카운트 쿼리를 분기할 수 있다.
    Page<Member> findByAge(int age, Pageable pageable);

    Slice<Member> findSliceByAge(int age, Pageable pageable); //Slice 는 totalCount 를 조회하지 않는다.

    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    @Query("select m from Member m left join fetch m.team")
// fetch join 연관된 쿼리를 한번에 조회한다.
    List<Member> findMemberFetchJoin();

    @Override
    @EntityGraph(attributePaths = {"team"})
//
    List<Member> findAll();

    @Query("select m from Member m")
    @EntityGraph(attributePaths = {"team"})
    List<Member> findMemberEntityGraph();

    @EntityGraph(attributePaths = {"team"})
    List<Member> findEntityGraphByUsername(@Param("username") String username);

    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Member findReadOnlyByUsername(String username);

//    @Lock(LockModeType.PESSIMISTIC_WRITE) //jpa 가 지원하는 lock annotation , 쉽게 Lock 걸게해줌
//    List<Member> findLockByUserName(String username);

    List<UsernameOnly> findProjectionByUsername(@Param("username") String username);

    //단점 :
    // 1. Entity 에 맞게 colum 을 일일히 지정하거나 *로 표시해야함
    // 2. 리턴타입이 애매함.
    // 3. 차라리 jdbcTemplate or mybatis 쓰는게 낫다.
    // 4. 동적 쿼리 불가
    // 5. 애플리케이션 로딩시점에 문법 확인불가
    @Query(value = "select * from member where username = ?", nativeQuery = true)
    Member findByNativeQuery(String username);


    @Query(value = "select m.member_id as id, m.username from Member m, Team t where m.team_id = t.team_id",
            countQuery = "select count(*) from member"
            , nativeQuery = true)
    Page<MemberProjection> findByNativeProjection(Pageable pageable);

    @Query(value = "select m.member_id as id, m.username from Member m, Team t where m.team_id = t.team_id", nativeQuery = true)
    List<MemberProjection> findByNativeProjection();

}

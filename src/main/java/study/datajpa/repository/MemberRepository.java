package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datajpa.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long>, JpaSpecificationExecutor<Member> { //Long은 Member 의 key

    //1. 메소드 이름으로 쿼리생성
    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    List<Member> findTop3HelloBy();

    @Query(name = "Member.findByUsername") //사실 @Query없어도 잘돌아감.. 관례상 findByUsername을 먼저 찾고 없으면 그 때 메소드쿼리가 실행됨.
    List<Member> findByUsername(@Param("username") String username); //@Param은 Member.java의 query파라미터에 따라 설정해야함
}

package study.datajpa.entity;

import lombok.*;

import javax.persistence.*;

@Entity // (name = "MEMBER") name을 설정하면 DB의 테이블 명을 맞출 수 있다.
@Getter
@Setter
@NoArgsConstructor//Entity를 만들때 디폴트생성자가 필요함. protected 까지 열어놔야함,
@ToString(of = {"id", "username", "age"}) //of : 지정한 변수만 toString 로그 생성
@NamedQuery(
        name = "Member.findByUsername",
        query = "select m from Member m where m.username = :username"
)//잘 사용안하는 방법임
public class Member extends BaseEntity {//JpaBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //GenerationType.IDENTITY : DB가 알아서 하게 냅둠
    //SEQUENCE : 시퀀스 생성한다.
    //TABLE : 키 생성용 테이블사용, 모든 DB에서 사용
    //AUTO : 자동으로 생성
    //키 생성 권장 : Long + 대체키 + 키 생성전략 사용
    @Column(name = "member_id") //변수명과 DB 컬럼명이 다를때 사용

    private Long id;
    private String username;
    private int age;

    //@Lob , CLOB, BLOB에 매핑할때 사용
    //private String cont;
    // @Transient, DB랑 매핑 안하게 한다.
    @ManyToOne(fetch = FetchType.LAZY)//왠만하면 ManyToOne 은 LAZY 로 셋팅
    @JoinColumn(name = "team_id")
    private Team team;

    public Member(String username) {
        this.username = username;
    }

    public Member(String username, int age) {
        this.username = username;
        this.age = age;
    }

    public Member(String username, int age, Team team) {
        this.username = username;
        this.age = age;
        if (team != null) {
            changeTeam(team);
        }
    }

    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }
}

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
        query="select m from Member m where m.username = :username"
)//잘 사용안하는 방법임
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//jpa가 알아서 시퀀스값을 만들어줌
    @Column(name = "member_id")
    private Long id;
    private String username;
    private int age;

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

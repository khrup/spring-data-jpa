package study.datajpa.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@MappedSuperclass //변수들을 내려서 테이블에 영향을 준다.
public class JpaBaseEntity {

    @Column(updatable = false) //createDate는 수정되지 못하게 updateable false로 주었음.
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    @PrePersist//persist 하기전에 실행됨
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createDate = now;
        this.updateDate = now;
    }

    @PreUpdate //update 하기전에 실행됨
    public void preUpdate() {
        updateDate = LocalDateTime.now();
    }
}

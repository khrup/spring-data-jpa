package study.datajpa.entity;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
@ToString(onlyExplicitlyIncluded = true)//onlyExplicitlyIncluded = true 일 떄는 @ToString.Include가 붙은 필드만 출력 가능하다
public class BaseEntity extends BaseTimeEntity {

    @ToString.Include
    @CreatedBy
    @Column(updatable = false)
    private String createBy;

    @LastModifiedBy
//    @Column(updatable = false)
    private String updateBy;

}



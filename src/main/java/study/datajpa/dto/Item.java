package study.datajpa.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item implements Persistable<Long> {

    @Id
//    @GeneratedValue //persist 할 때 키가 생성됨,
//    GeneratedValue 선언하고 별도로 키값을 지정하면 InvalidDataAccessApiUsageException 에러 발생한다.
    private Long id;

    @CreatedDate
    private LocalDateTime createdDate;

    public Item(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public boolean isNew() {
        return createdDate == null;
    }
}


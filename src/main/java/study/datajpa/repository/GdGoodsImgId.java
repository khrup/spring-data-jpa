package study.datajpa.repository;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GdGoodsImgId implements Serializable {

    @EqualsAndHashCode.Include
    private Long goodsNo;
    @EqualsAndHashCode.Include
    private Long imgSeq;

}
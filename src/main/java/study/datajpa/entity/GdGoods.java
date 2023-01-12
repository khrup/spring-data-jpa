package study.datajpa.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)//Entity를 만들때 디폴트생성자가 필요함. protected 까지 열어놔야함,
public class GdGoods extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long goodsNo;
    private String goodsNm;

    public GdGoods(String goodsNm) {
        this.goodsNm = goodsNm;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "gdGoods")
    @JoinColumn(name = "goods_no")
    private List<GdGoodsImg> gdGoodsImgList = new ArrayList<>();
}
